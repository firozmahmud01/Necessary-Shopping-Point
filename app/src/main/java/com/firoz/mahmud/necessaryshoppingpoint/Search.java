package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.service.autofill.Dataset;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;

public class Search extends AppCompatActivity {
    //static
    public static String intenttypekey="IntentTypeKey";
    public static String typecatagorylist="IntentTypecatagorylishKey";
    public static String typecatagoryitemlist="IntentTypecatagoryitemlistKey";
    public static String typesearchkey="TypeSearchKey";
    public static String catagorynamekey="CatagoryBanekey";






    //interface
    GridView gv;





    //variables
    BaseAdapter ba=null;
    ArrayList<ProductCatagory>catagories;
    ArrayList<ProductItem>items;
    String type;
    DatabaseReference dr;
    boolean fromcatagory;
    Comparator<ProductItem>com;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final Intent intent  = getIntent();

        //interface
        gv=findViewById(R.id.search_listview);
        com=new Comparator<ProductItem>() {
            @Override
            public int compare(ProductItem o1, ProductItem o2) {
                return (-1)*(o1.getRating()-o2.getRating());
            }
        };



        //firebase
        dr= FirebaseDatabase.getInstance().getReference(KeyF.firebase.productpath);
        dr.keepSynced(true);




        //variables
        items=new ArrayList<>();
        catagories=new ArrayList<>();






        //saving search data
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SearchProvider.AUTHORITY, SearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            type=typesearchkey;
            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    items.removeAll(items);
                    for (DataSnapshot ds:dataSnapshot.getChildren()){
                        for (DataSnapshot d:ds.getChildren()){
                            ProductItem pi=d.getValue(ProductItem.class);
                            if (pi.getProductname().contains(query)){
                                items.add(pi);
                            }
                        }
                    }
                    ba.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Search.this, databaseError.getDetails(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            type=getIntent().getStringExtra(intenttypekey);
            if (type.equals(typecatagoryitemlist)){
                dr=dr.child(getIntent().getStringExtra(catagorynamekey));
                fromcatagory=false;
            }else{
                fromcatagory=true;
            }
            dr.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (type.equals(typecatagorylist)){
                        loadCatagorys(dataSnapshot);
                    }else{
                        loadItems(dataSnapshot);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        findViewById(R.id.search_Search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearchRequested();
            }
        });


        //baseadapter
        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                if (type.equals(typecatagorylist)){
                    return catagories.size();
                }else{
                    return items.size();
                }
            }

            @Override
            public void notifyDataSetChanged() {
                if (!type.equals(typecatagorylist)) Collections.sort(items,com);
                super.notifyDataSetChanged();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View cv, ViewGroup parent) {
                if(cv==null) {
                    cv = getLayoutInflater().inflate(R.layout.productitem, null);
                }
                TextView namet=cv.findViewById(R.id.product_item_name_textview)
                        ,prizet=cv.findViewById(R.id.product_item_prize_textview);
                String image,name;
                if (type.equals(typecatagorylist)){
                    name=catagories.get(position).getCatagory();
                    prizet.setText("");
                    prizet.setVisibility(View.GONE);
                    image=catagories.get(position).getProductItem().get(0).getThemlink();
                }else{
                    name=items.get(position).getProductname();
                    prizet.setText(items.get(position).getProductprize());
                    prizet.setVisibility(View.VISIBLE);
                    image=items.get(position).getThemlink();
                }
                Picasso.with(Search.this).load(Uri.parse(image)).into((ImageView)cv.findViewById(R.id.product_item_imageview));
                namet.setText(name);

                return cv;
            }
        };




        gv.setAdapter(ba);




        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals(typecatagorylist)){
                    items.removeAll(items);
                    items.addAll(catagories.get(position).getProductItem());
                    type=typesearchkey;
                    ba.notifyDataSetChanged();
                }else{
                    Intent inte=Home.genarateIntent(Search.this,ItemDetails.class,items.get(position));
                    startActivity(inte);
                }
            }
        });





    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }



    private void loadItems(DataSnapshot ds){
        items.removeAll(items);
        for (DataSnapshot d:ds.getChildren()){
            items.add(d.getValue(ProductItem.class));
        }
        if(ba!=null)ba.notifyDataSetChanged();
    }
    private void loadCatagorys(DataSnapshot ds){
        catagories.removeAll(catagories);
        for (DataSnapshot d1:ds.getChildren()){
            ArrayList<ProductItem>pi=new ArrayList<>();
            for(DataSnapshot d:d1.getChildren()){
                pi.add(d.getValue(ProductItem.class));
            }
            catagories.add(new ProductCatagory(d1.getKey(),pi));
            if (ba!=null)ba.notifyDataSetChanged();
        }
    }


    @Override
    public void onBackPressed() {
        if (fromcatagory){
            if (!type.equals(typecatagorylist)){
                type=typecatagorylist;
                ba.notifyDataSetChanged();
            }else{
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }
    }
}