package com.cheemarket.JavadEsmaeili.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import com.cheemarket.JavadEsmaeili.Commands;
import com.cheemarket.JavadEsmaeili.G;
import com.cheemarket.JavadEsmaeili.R;
import com.cheemarket.JavadEsmaeili.Structure.Subdastebandi;

/**
 * Created by user on 8/21/2018.
 */

public class SubdastebandiAdapter extends RecyclerView.Adapter<SubdastebandiAdapter.ViewHolder>{

    private ArrayList<Subdastebandi> mdataset;

    public SubdastebandiAdapter(ArrayList<Subdastebandi> mdataset) {
        this.mdataset = mdataset;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptersubdastebandi, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



        if(mdataset.get(position).Image != null && !mdataset.get(position).Image.equals("")){
            Commands.showimage(G.Baseurl + mdataset.get(position).Image,null,holder.img,true);
        }



        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(G.context, "Width =" + holder.img.getWidth() + " ?? Height =" + holder.img.getHeight(), Toast.LENGTH_LONG).show();

               com.cheemarket.JavadEsmaeili.Subdastebandi.namayeshkalaha(mdataset.get(position).Subdastebandi , mdataset.get(position).Title);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView img;



        public ViewHolder(View itemView) {
            super(itemView);

            img =(ImageView) itemView.findViewById(R.id.img);


        }
    }


}
