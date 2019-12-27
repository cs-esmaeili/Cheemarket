package com.cheemarket.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Commands;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.R;
import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Textconfig;

import java.util.ArrayList;

/**
 * Created by user on 8/21/2018.
 */

public class Listpaymentadapter extends RecyclerView.Adapter<Listpaymentadapter.ViewHolder>{

    private ArrayList<PoductStructure> mdataset;

    public Listpaymentadapter(ArrayList<PoductStructure> mdataset) {
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
            holder.textoffPrice.setVisibility(View.INVISIBLE);
        }else {
            holder.textoffPrice.setText( mdataset.get(position).OldPrice1 + " تومان" );
        }

        if (mdataset.get(position).Status1 != null && mdataset.get(position).Status1.equals("2")) {
            holder.specialsellone.setVisibility(View.VISIBLE);
        } else {
            holder.specialsellone.setVisibility(View.GONE);
        }

        Textconfig.settext(holder.txtname, "" + mdataset.get(position).Name1);
        Textconfig.settext(holder.textPrice, "تومان " + mdataset.get(position).Price1);
        Textconfig.settext(holder.mess, "تعداد انتخاب شده: " + mdataset.get(position).Ordernumber1);


        holder.mess.setVisibility(View.VISIBLE);



        Commands.showimage(mdataset.get(position).Image_thumbnail1, null, holder.image);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Commands.openactivity(mdataset, position,true, ActivityAtelaatkala.class);
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
        public TextView specialsellone;
        public TextView mess;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.txtnameone);
            textPrice = (TextView) itemView.findViewById(R.id.textPriceone);
            image =(ImageView) itemView.findViewById(R.id.imageone);
            textoffPrice = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            card = (CardView)itemView.findViewById(R.id.Cardone);
            specialsellone = (TextView) itemView.findViewById(R.id.specialsellone);
            mess = (TextView) itemView.findViewById(R.id.mess);
        }
    }
}
