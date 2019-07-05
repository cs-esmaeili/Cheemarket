package com.cheemarket.JavadEsmaeili.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.cheemarket.JavadEsmaeili.ActivityAtelaatkala;
import com.cheemarket.JavadEsmaeili.Commands;
import com.cheemarket.JavadEsmaeili.Customview.Lineimage;
import com.cheemarket.JavadEsmaeili.G;
import com.cheemarket.JavadEsmaeili.R;
import com.cheemarket.JavadEsmaeili.Structure.KalaStructure;
import com.cheemarket.JavadEsmaeili.Textconfig;

/**
 * Created by user on 8/21/2018.
 */

public class Listpaymentadapter extends RecyclerView.Adapter<Listpaymentadapter.ViewHolder>{

    private ArrayList<KalaStructure> mdataset;

    public Listpaymentadapter(ArrayList<KalaStructure> mdataset) {
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.listone, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if(mdataset.get(position).OldPrice1.equals("0")){
            holder.textoffPrice.setVisibility(View.GONE);
        }else {
            holder.textoffPrice.setText(mdataset.get(position).OldPrice1 + "");
        }

        if (mdataset.get(position).Status1 != null && mdataset.get(position).Status1.equals("2")) {
            holder.gifone.setVisibility(View.VISIBLE);
        } else {
            holder.gifone.setVisibility(View.GONE);
        }

        Textconfig.settext(holder.txtname, "" + mdataset.get(position).Name1);
        Textconfig.settext(holder.textPrice, "" + mdataset.get(position).Price1);
        Textconfig.settext(holder.mess, "تعداد انتخاب شده: " + mdataset.get(position).Ordernumber1);


        holder.mess.setVisibility(View.VISIBLE);



        Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image1 + "/" +  mdataset.get(position).Image1 + ".jpg", null, holder.image, true);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Commands.openactivity(mdataset, position,true, ActivityAtelaatkala.class);
            }
        });






    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtname;
        public TextView textPrice;
        public Lineimage textoffPrice;
        public ImageView image;
        public CardView card;
        public ImageView gifone;
        public TextView mess;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.txtnameone);
            textPrice = (TextView) itemView.findViewById(R.id.textPriceone);
            image =(ImageView) itemView.findViewById(R.id.imageone);
            textoffPrice = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            card = (CardView)itemView.findViewById(R.id.Cardone);
            gifone = (ImageView) itemView.findViewById(R.id.gifone);
            mess = (TextView) itemView.findViewById(R.id.mess);
        }
    }
}
