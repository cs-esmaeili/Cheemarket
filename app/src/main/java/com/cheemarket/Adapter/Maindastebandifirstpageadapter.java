package com.cheemarket.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheemarket.ActivitySubdastebandi;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.R;
import com.cheemarket.Structure.Maindastebandifistpage;

import java.util.ArrayList;

/**
 * Created by user on 8/21/2018.
 */

public class Maindastebandifirstpageadapter extends RecyclerView.Adapter<Maindastebandifirstpageadapter.ViewHolder> {

    private ArrayList<Maindastebandifistpage> mdataset;


    public Maindastebandifirstpageadapter(ArrayList<Maindastebandifistpage> mdataset) {
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterdastebandifirstpage, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image + "/"  + mdataset.get(position).Image  + ".jpg", null,holder.img);
        holder.txt.setText(mdataset.get(position).Title);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                intent.putExtra("subdastebandistring", mdataset.get(position).Id);
                G.CurrentActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt;
        public de.hdodenhof.circleimageview.CircleImageView img;
        public LinearLayout layout;



        public ViewHolder(View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.txt);
            img = (de.hdodenhof.circleimageview.CircleImageView) itemView.findViewById(R.id.img);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);



        }
    }
}
