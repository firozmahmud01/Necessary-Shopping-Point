package com.firoz.mahmud.necessaryshoppingpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemDetails extends AppCompatActivity {
    //variables
    String piclinks[]=null;





    //interface
    PagerAdapter pa;
    ViewPager vp;
    LinearLayout ll;
    TextView[] dot =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);


        //geting intent
        final Intent intent=getIntent();




        //declearing and assing
        ((TextView)findViewById(R.id.item_details_name_textview))
                .setText(intent.getStringExtra(KeyF.sharedprefarence.namekey));
        ((TextView)findViewById(R.id.item_details_descripttion_textview))
                .setText(intent.getStringExtra(KeyF.sharedprefarence.detailskey));
        ((TextView)findViewById(R.id.item_details_prize_textview))
                .setText(intent.getStringExtra(KeyF.sharedprefarence.prizekey));







        //assing variables
        piclinks=intent.getStringArrayExtra(KeyF.sharedprefarence.picurlkey);
        dot=new TextView[piclinks.length];





        //interface
        ll=findViewById(R.id.item_details_dot_layout);
        vp=findViewById(R.id.item_image_viewpager);
        pa=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return new SliderImage(ItemDetails.this,piclinks[position]);
            }

            @Override
            public int getCount() {
                return piclinks.length;
            }
        };







        vp.setAdapter(pa);






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

        setDot(0);




        findViewById(R.id.item_details_but_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ItemDetails.this,UserDetails.class);
                in.putExtras(intent);
                startActivity(in);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
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

}