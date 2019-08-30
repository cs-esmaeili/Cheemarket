package com.cheemarket.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cheemarket.ActivitySubdastebandi;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.R;
import com.cheemarket.Structure.Maindastebandi;
import com.cheemarket.Structure.Maindastebandifistpage;

import java.util.ArrayList;

/**
 * Created by user on 8/21/2018.
 */

public class Maindastebandiadapter extends RecyclerView.Adapter<Maindastebandiadapter.ViewHolder> {

    private ArrayList<Maindastebandi> mdataset;


    public Maindastebandiadapter(ArrayList<Maindastebandi> mdataset) {
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterdastebandi, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (mdataset.get(position).Id1 != null) {
            holder.cat1.setVisibility(View.VISIBLE);
            holder.txt1.setText(mdataset.get(position).Title1);
            Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image1 + "/" + mdataset.get(position).Image1 + ".jpg", null, holder.img1);

            holder.cat1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                    intent.putExtra("subdastebandistring", mdataset.get(position).Id1);
                    G.CurrentActivity.startActivity(intent);
                }
            });

        }


        if (mdataset.get(position).Id2 != null) {

            holder.cat2.setVisibility(View.VISIBLE);
            holder.txt2.setText(mdataset.get(position).Title2);
            Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image2 + "/" + mdataset.get(position).Image2 + ".jpg", null, holder.img2);

            holder.cat2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                    intent.putExtra("subdastebandistring", mdataset.get(position).Id2);
                     G.CurrentActivity.startActivity(intent);
                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt1;
        public ImageView img1;
        public RelativeLayout cat1;

        public TextView txt2;
        public ImageView img2;
        public RelativeLayout cat2;

        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            img1 = (ImageView) itemView.findViewById(R.id.img1);
            cat1 = (RelativeLayout) itemView.findViewById(R.id.cat1);

            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
            cat2 = (RelativeLayout) itemView.findViewById(R.id.cat2);

        }
    }
}
