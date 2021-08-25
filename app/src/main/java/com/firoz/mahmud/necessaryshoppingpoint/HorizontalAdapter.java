package com.firoz.mahmud.necessaryshoppingpoint;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
    private List<ProductItem>pi;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView name,prize;
        public MyViewHolder(View v) {
            super(v);
            this.iv=v.findViewById(R.id.product_item_imageview);
            this.name=v.findViewById(R.id.product_item_name_textview);
            this.prize=v.findViewById(R.id.product_item_prize_textview);
        }
    }




    public HorizontalAdapter(List<ProductItem> pi, Context context) {
        this.pi=pi;
        this.context=context;
    }





    @Override
    public HorizontalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View v =LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productitem, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(pi.get(position).getProductname());
        holder.prize.setText(pi.get(position).getProductprize());
        Picasso.with(context).load(Uri.parse(pi.get(position).getThemlink())).into(holder.iv);
        View.OnClickListener oc=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(Home.genarateIntent(context,ItemDetails.class,pi.get(position)));
            }
        };
        holder.name.setOnClickListener(oc);
        holder.prize.setOnClickListener(oc);
        holder.iv.setOnClickListener(oc);
    }





    @Override
    public int getItemCount() {
        return pi.size();
    }
}