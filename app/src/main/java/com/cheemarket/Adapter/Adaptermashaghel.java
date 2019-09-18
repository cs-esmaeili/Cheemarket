package com.cheemarket.Adapter;


import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheemarket.ActivityAtelaatshoghl;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.R;
import com.cheemarket.Structure.Mashaghel;
import com.cheemarket.Structure.Mashagheldastebandi;

import java.util.ArrayList;

/**
 * Created by user on 8/21/2018.
 */

public class Adaptermashaghel extends RecyclerView.Adapter<Adaptermashaghel.ViewHolder> {

    private ArrayList<Mashaghel> dataset;


    public Adaptermashaghel(ArrayList<Mashaghel> dataset) {
        this.dataset = dataset;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptermashaghel, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt.setText(dataset.get(position).Title);
        Commands.showimage(dataset.get(position).Image,null,holder.img);

        holder.Cardone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityAtelaatshoghl.img = dataset.get(position).Image;
                ActivityAtelaatshoghl.title = dataset.get(position).Title;
                ActivityAtelaatshoghl.tozihat = dataset.get(position).Tozihat;
                Intent intent = new Intent(G.CurrentActivity, ActivityAtelaatshoghl.class);
                G.CurrentActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt;
        public ImageView img;
        public CardView Cardone;


        public ViewHolder(View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.txt);
            img = (ImageView) itemView.findViewById(R.id.img);
            Cardone = (CardView) itemView.findViewById(R.id.Cardone);

        }
    }


}
