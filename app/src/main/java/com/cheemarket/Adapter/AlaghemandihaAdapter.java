package com.cheemarket.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.cheemarket.ActivityAtelaatkala;
import com.cheemarket.Alaghemandiha;
import com.cheemarket.Commands;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.G;
import com.cheemarket.R;
import com.cheemarket.Structure.KalaStructure;
import com.cheemarket.Textconfig;

/**
 * Created by user on 8/21/2018.
 */

public class AlaghemandihaAdapter extends RecyclerView.Adapter<AlaghemandihaAdapter.ViewHolder> {

    private ArrayList<KalaStructure> mdataset;

    public AlaghemandihaAdapter(ArrayList<KalaStructure> mdataset) {
        this.mdataset = mdataset;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listalaghemandiha, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(mdataset.get(position).Id1 == null){
            holder.cardone.setVisibility(View.INVISIBLE);
        }else{
            if (mdataset.get(position).OldPrice1 == null || mdataset.get(position).OldPrice1.equals("0")) {
                holder.textoffPriceone.setVisibility(View.INVISIBLE);
            } else {

                holder.textoffPriceone.setText(mdataset.get(position).OldPrice1 + "");
            }
            if (mdataset.get(position).Price1 != null && !mdataset.get(position).Price1.equals("0")) {
                Textconfig.settext(holder.textPriceone, "" + mdataset.get(position).Price1);
            } else {
                holder.textPriceone.setVisibility(View.INVISIBLE);
            }

            if (mdataset.get(position).Status1 != null && mdataset.get(position).Status1.equals("2")) {
                holder.gifone.setVisibility(View.VISIBLE);
            } else {
                holder.gifone.setVisibility(View.GONE);
            }

            if (mdataset.get(position).Name1 != null && !mdataset.get(position).Name1.equals("")) {
                Textconfig.settext(holder.txtnameone, mdataset.get(position).Name1);
            } else {
                holder.txtnameone.setVisibility(View.INVISIBLE);
            }

            if (mdataset.get(position).Image1 != null && !mdataset.get(position).Image1.equals("")) {
                Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image1 + "/" + mdataset.get(position).Image1 + ".jpg", null, holder.imageone, true);
            }




            holder.cardone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Commands.openactivity(mdataset, position,true, ActivityAtelaatkala.class);

                }
            });

            holder.deleteone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alaghemandiha.deletealaghemandiha(mdataset.get(position).Id1);
                }
            });
        }



        if(mdataset.get(position).Id2 == null){
            holder.cardtwo.setVisibility(View.INVISIBLE);
        }else{

            if (mdataset.get(position).OldPrice2 == null || mdataset.get(position).OldPrice2.equals("0")) {
                holder.textoffPricetwo.setVisibility(View.INVISIBLE);
            } else {
                holder.textoffPricetwo.setText(mdataset.get(position).OldPrice2 + "");
            }

            if (mdataset.get(position).Status2 != null && mdataset.get(position).Status2.equals("2")) {
                holder.giftwo.setVisibility(View.VISIBLE);
            } else {
                holder.giftwo.setVisibility(View.GONE);
            }
            if (mdataset.get(position).Name2 != null && !mdataset.get(position).Name2.equals("")) {
                Textconfig.settext(holder.txtnametwo, mdataset.get(position).Name2);
            } else {
                holder.txtnametwo.setVisibility(View.INVISIBLE);
            }
            if (mdataset.get(position).Price2 != null && !mdataset.get(position).Price2.equals("0")) {
                Textconfig.settext(holder.textPricetwo, "" + mdataset.get(position).Price2);
            } else {
                holder.textPricetwo.setVisibility(View.INVISIBLE);
            }


            if (mdataset.get(position).Image2 != null && !mdataset.get(position).Image2.equals("")) {
                Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image2 + "/" + mdataset.get(position).Image2 + ".jpg", null, holder.imagetwo, true);
            }


            holder.cardtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Commands.openactivity(mdataset, position,false, ActivityAtelaatkala.class);
                }
            });

            holder.deletetwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alaghemandiha.deletealaghemandiha(mdataset.get(position).Id2);
                }
            });

        }





    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtnameone;
        public TextView textPriceone;
        public Lineimage textoffPriceone;
        public ImageView imageone;
        public CardView cardone;
        public ImageView gifone;
        public TextView deleteone;


        public TextView txtnametwo;
        public TextView textPricetwo;
        public Lineimage textoffPricetwo;
        public ImageView imagetwo;
        public CardView cardtwo;
        public ImageView giftwo;
        public TextView deletetwo;

        public ViewHolder(View itemView) {
            super(itemView);

            txtnameone = (TextView) itemView.findViewById(R.id.txtnameone);
            textPriceone = (TextView) itemView.findViewById(R.id.textPriceone);
            imageone = (ImageView) itemView.findViewById(R.id.imageone);
            textoffPriceone = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            cardone = (CardView) itemView.findViewById(R.id.Cardone);
            gifone = (ImageView) itemView.findViewById(R.id.gifone);
            deleteone = (TextView) itemView.findViewById(R.id.deleteone);

            txtnametwo = (TextView) itemView.findViewById(R.id.txtnametwo);
            textPricetwo = (TextView) itemView.findViewById(R.id.textPricetwo);
            imagetwo = (ImageView) itemView.findViewById(R.id.imagetwo);
            textoffPricetwo = (Lineimage) itemView.findViewById(R.id.textoffPricetwo);
            cardtwo = (CardView) itemView.findViewById(R.id.Cardtwo);
            giftwo = (ImageView) itemView.findViewById(R.id.giftwo);
            deletetwo = (TextView) itemView.findViewById(R.id.deletetwo);

        }
    }


}
