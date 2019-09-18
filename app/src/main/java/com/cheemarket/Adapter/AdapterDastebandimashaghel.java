package com.cheemarket.Adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheemarket.ActivityMashaghel;
import com.cheemarket.R;
import com.cheemarket.Structure.Mashagheldastebandi;

import java.util.ArrayList;

import static com.cheemarket.ActivityMashaghel.pagetitle;
import static com.cheemarket.ActivityMashaghel.searchView;

/**
 * Created by user on 8/21/2018.
 */

public class AdapterDastebandimashaghel extends RecyclerView.Adapter<AdapterDastebandimashaghel.ViewHolder> {

    private ArrayList<Mashagheldastebandi> dataset;


    public AdapterDastebandimashaghel(ArrayList<Mashagheldastebandi> dataset) {
        this.dataset = dataset;


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptermashagheldastebandi, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt.setText(dataset.get(position).Name);
        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagetitle.setText(dataset.get(position).Name);
                ActivityMashaghel.id = dataset.get(position).Id;
                ActivityMashaghel.search("");
                searchView.setQuery("",false);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt;


        public ViewHolder(View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.txt);


        }
    }


}
