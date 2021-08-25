package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Home extends AppCompatActivity {
    //interface
    ListView lv;
    ViewFlipper vf;
    LayoutInflater li;





    //variables
    boolean canshatdown=false;
    ArrayList<PosterItem>posterlist;
    ArrayList<ProductCatagory>productCatagories;
    BaseAdapter ba;








    //firebase
    DatabaseReference productdr,posterdr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //firebase
        productdr= FirebaseDatabase.getInstance().getReference(KeyF.firebase.productpath);
        productdr.keepSynced(true);
        posterdr=FirebaseDatabase.getInstance().getReference(KeyF.firebase.posterpath);
        posterdr.keepSynced(true);







        //variables
        posterlist=new ArrayList<>();
        productCatagories=new ArrayList<>();






        //interface
        lv=findViewById(R.id.home_listView);
        vf=findViewById(R.id.home_viewFlipper);
        vf.setInAnimation(this,R.anim.inanim);
        vf.setOutAnimation(this,R.anim.outanim);
        vf.setFlipInterval(3000);
        vf.setAutoStart(true);
        li=getLayoutInflater();









        View.OnClickListener ocl=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,Search.class);
                intent.putExtra(Search.intenttypekey,Search.typecatagorylist);
                startActivity(intent);
            }
        };




        findViewById(R.id.home_catagory_imageView).setOnClickListener(ocl);
        findViewById(R.id.home_catagory_textview).setOnClickListener(ocl);






        findViewById(R.id.home_searchView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onSearchRequested();
            }
        });






        ba=new BaseAdapter() {
            @Override
            public int getCount() {
                return productCatagories.size();
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
                ViewGroup vg=lv;

                int limit=ba.getCount();
                int height=0;
                for(int x=0;x<limit;x++) {
                    View v= ba.getView(x,null,vg);
                    v.measure(0,0);
                    height+=v.getMeasuredHeight();
                }
                ViewGroup.LayoutParams lp = lv.getLayoutParams();
                lp.height = height;
                lv.setLayoutParams(lp);
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
            public View getView(final int position, View cv, ViewGroup parent) {
                if (cv==null){
                    cv=li.inflate(R.layout.homelistitem,null);
                }
                RecyclerView rv=cv.findViewById(R.id.home_list_item_recycleview);
                LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rv.setLayoutManager(layoutManager);

                TextView catagory=cv.findViewById(R.id.home_list_list_item_catagory_textview);
                catagory.setText(productCatagories.get(position).getCatagory());


                rv.setAdapter(new HorizontalAdapter(productCatagories.get(position).getProductItem(),Home.this));


                cv.findViewById(R.id.home_list_item_show_all_textview).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Home.this,Search.class);
                        intent.putExtra(Search.intenttypekey,Search.typecatagoryitemlist);
                        intent.putExtra(Search.catagorynamekey,productCatagories.get(position).getCatagory());
                        startActivity(intent);
                    }
                });

                return cv;
            }
        };





        lv.setAdapter(ba);






        posterdr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                vf.removeAllViews();
                posterlist.removeAll(posterlist);
                for (DataSnapshot d:ds.getChildren()){
                    final PosterItem pi=d.getValue(PosterItem.class);
                    View v=li.inflate(R.layout.fragment_slider_image,null);
                    Picasso.with(Home.this).load(Uri.parse(pi.getPicurl())).into((ImageView )v.findViewById(R.id.sliderimage_imageview));
                    v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final ProgressDialog pd=new ProgressDialog(Home.this);
                            pd.setCancelable(false);
                            pd.setMessage("Please Wait");
                            DatabaseReference d=FirebaseDatabase.getInstance().getReference(KeyF.firebase.productpath)
                                    .child(pi.getCatgory()).child(pi.getProductid());
                            d.keepSynced(true);
                            d.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    pd.dismiss();
                                    ProductItem pi=dataSnapshot.getValue(ProductItem.class);
                                    if (pi==null)return;
                                    Intent in=genarateIntent(Home.this,ItemDetails.class,
                                            pi);
                                    startActivity(in);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    pd.dismiss();
                                }
                            });

                        }
                    });
                    vf.addView(v);
                    posterlist.add(pi);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        productdr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                productCatagories.removeAll(productCatagories);
                for(DataSnapshot d:ds.getChildren()){
                    Comparator<ProductItem>com=new Comparator<ProductItem>() {
                        @Override
                        public int compare(ProductItem o1, ProductItem o2) {
                            return (-1)*(o1.getRating()-o2.getRating());
                        }
                    };
                    String catagory=d.getKey();
                    ArrayList<ProductItem>pi=new ArrayList<>();
                    for (DataSnapshot dds:d.getChildren()){
                        pi.add(dds.getValue(ProductItem.class));
                    }
                    Collections.sort(pi,com);
                    productCatagories.add(new ProductCatagory(catagory,pi.subList(0,pi.size()<5?pi.size():4)));
                }
                ba.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public static Intent genarateIntent(Context context,Class c,ProductItem pi){
        String pics[]=new String[pi.getImagelinks().size()];
        int count=0;
        for (String one:pi.getImagelinks()){
            pics[count++]=one;
        }
        Intent intent=new Intent(context,c);
        intent.putExtra(KeyF.sharedprefarence.detailskey,pi.getProductdescription());
        intent.putExtra(KeyF.sharedprefarence.picurlkey,pics);
        intent.putExtra(KeyF.sharedprefarence.namekey,pi.getProductname());
        intent.putExtra(KeyF.sharedprefarence.prizekey,pi.getProductprize());
        intent.putExtra(KeyF.sharedprefarence.productcatagorykey,pi.getCatagory());
        intent.putExtra(KeyF.sharedprefarence.productidkey,pi.getId());
        return intent;
    }




    @Override
    public void onBackPressed() {
        if (canshatdown){
            finishAffinity();
        }else{
            canshatdown=true;
            Toast.makeText(getApplication(), "Press again to exit", Toast.LENGTH_SHORT).show();
        }
    }
}