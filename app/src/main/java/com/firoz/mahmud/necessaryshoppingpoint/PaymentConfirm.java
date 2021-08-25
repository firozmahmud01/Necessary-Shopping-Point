package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Set;

public class PaymentConfirm extends AppCompatActivity {
    //variables
    SharedPreferences sp;
    Set<String> piclinks=null;





    //interface
    PagerAdapter pa;
    ViewPager vp;
    LinearLayout ll;
    TextView[] dot;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm);
        //variables
        sp=getSharedPreferences(KeyF.sharedprefarence.databasename,MODE_PRIVATE);



        //declearing and assing
        ((TextView)findViewById(R.id.item_details_name_textview))
                .setText(sp.getString(KeyF.sharedprefarence.namekey,""));
        ((TextView)findViewById(R.id.item_details_descripttion_textview))
                .setText(sp.getString(KeyF.sharedprefarence.detailskey,""));
        ((TextView)findViewById(R.id.item_details_prize_textview))
                .setText(sp.getString(KeyF.sharedprefarence.prizekey,""));







        //assing variables
        piclinks= sp.getStringSet(KeyF.sharedprefarence.picurlkey,null);

        dot=new TextView[piclinks.size()];




        //interface
        ll=findViewById(R.id.item_details_dot_layout);
        vp=findViewById(R.id.item_image_viewpager);
        pa=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                String link=piclinks.toArray()[position].toString();
                return new SliderImage(PaymentConfirm.this,link);
            }

            @Override
            public int getCount() {
                return piclinks.size();
            }
        };







        vp.setAdapter(pa);
        SharedPreferences.Editor spe=sp.edit();
        spe.putBoolean(KeyF.sharedprefarence.isbuyingkey,false);
        spe.commit();





        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addDot();


        Button b= findViewById(R.id.item_details_but_button);
        b.setText("Done");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PaymentConfirm.this,Home.class));
                finish();
            }
        });
        setDot(0);

    }


    public void addDot() {
        for (int i = 0; i < dot.length; i++) {;
            dot[i] = new TextView(this);
            dot[i].setText(Html.fromHtml("&#9673;"));
            dot[i].setTextSize(35);
            dot[i].setTextColor(getResources().getColor(android.R.color.darker_gray));
            ll.addView(dot[i]);
        }
    }
    int prev=0;
    private void setDot(int position){
        dot[prev].setTextColor(getResources().getColor(android.R.color.darker_gray));
        dot[position].setTextColor(getResources().getColor(android.R.color.holo_red_light));
        prev=position;
    }



    @Override
    public void onBackPressed() {
        SharedPreferences.Editor spe =sp.edit();
        spe.putBoolean(KeyF.sharedprefarence.isbuyingkey,false);
        spe.commit();
        startActivity(new Intent(PaymentConfirm.this,Home.class));
        finish();
    }
}