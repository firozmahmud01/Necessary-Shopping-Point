package com.firoz.mahmud.necessaryshoppingpoint;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class SliderImage extends Fragment {

    private String link;
    Context context;
    public SliderImage(Context context,String link){
        this.link=link;
        this.context=context;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_slider_image, container, false);

        Picasso.with(context)
                .load(Uri.parse(link))
                .into(((ImageView)view.findViewById(R.id.sliderimage_imageview)));

        return view;
    }
}