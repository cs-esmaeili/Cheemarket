package com.cheemarket.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.ActivityAlaghemandiha;
import com.cheemarket.ActivityAtelaatkala;
import com.cheemarket.Commands;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.R;
import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Textconfig;

import java.util.ArrayList;

/**
 * Created by user on 8/21/2018.
 */

public class AlaghemandihaAdapter extends RecyclerView.Adapter<AlaghemandihaAdapter.ViewHolder> {

    private ArrayList<PoductStructure> mdataset;

    public AlaghemandihaAdapter(ArrayList<PoductStructure> mdataset) {
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

        if (mdataset.get(position).Id1 == null) {
            holder.cardone.setVisibility(View.INVISIBLE);
        } else {
            if (mdataset.get(position).OldPrice1 == null || mdataset.get(position).OldPrice1.equals("0")) {
                holder.textoffPriceone.setVisibility(View.INVISIBLE);
            } else {

                holder.textoffPriceone.setText(mdataset.get(position).OldPrice1 + " تومان");
            }
            if (mdataset.get(position).Price1 != null && !mdataset.get(position).Price1.equals("0")) {
                Textconfig.settext(holder.textPriceone, mdataset.get(position).Price1 + " تومان");
            } else {
                holder.textPriceone.setVisibility(View.INVISIBLE);
            }

            if (mdataset.get(position).Status1 != null && mdataset.get(position).Status1.equals("2")) {
                holder.specialsellone.setVisibility(View.VISIBLE);
            } else {
                holder.specialsellone.setVisibility(View.GONE);
            }

            if (mdataset.get(position).Name1 != null && !mdataset.get(position).Name1.equals("")) {
                Textconfig.settext(holder.txtnameone, mdataset.get(position).Name1);
            } else {
                holder.txtnameone.setVisibility(View.INVISIBLE);
            }

            if (mdataset.get(position).Image_thumbnail1 != null && !mdataset.get(position).Image_thumbnail1.equals("")) {
                Commands.showimage(mdataset.get(position).Image_thumbnail1, null, holder.imageone);
            }


            holder.cardone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Commands.openactivity(mdataset, position, true, ActivityAtelaatkala.class);

                }
            });

            holder.deleteone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityAlaghemandiha.deletealaghemandiha(mdataset.get(position).Id1);
                }
            });
        }


        if (mdataset.get(position).Id2 == null) {
            holder.cardtwo.setVisibility(View.INVISIBLE);
        } else {

            if (mdataset.get(position).OldPrice2 == null || mdataset.get(position).OldPrice2.equals("0")) {
                holder.textoffPricetwo.setVisibility(View.INVISIBLE);
            } else {
                holder.textoffPricetwo.setText(mdataset.get(position).OldPrice2 + " تومان");
            }

            if (mdataset.get(position).Status2 != null && mdataset.get(position).Status2.equals("2")) {
                holder.specialselltwo.setVisibility(View.VISIBLE);
            } else {
                holder.specialselltwo.setVisibility(View.GONE);
            }
            if (mdataset.get(position).Name2 != null && !mdataset.get(position).Name2.equals("")) {
                Textconfig.settext(holder.txtnametwo, mdataset.get(position).Name2);
            } else {
                holder.txtnametwo.setVisibility(View.INVISIBLE);
            }
            if (mdataset.get(position).Price2 != null && !mdataset.get(position).Price2.equals("0")) {
                Textconfig.settext(holder.textPricetwo, mdataset.get(position).Price2 + " تومان");
            } else {
                holder.textPricetwo.setVisibility(View.INVISIBLE);
            }


            if (mdataset.get(position).Image_thumbnail2 != null && !mdataset.get(position).Image_thumbnail2.equals("")) {
                Commands.showimage(mdataset.get(position).Image_thumbnail2, null, holder.imagetwo);
            }


            holder.cardtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Commands.openactivity(mdataset, position, false, ActivityAtelaatkala.class);
                }
            });

            holder.deletetwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityAlaghemandiha.deletealaghemandiha(mdataset.get(position).Id2);
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
        public TextView specialsellone;
        public TextView deleteone;


        public TextView txtnametwo;
        public TextView textPricetwo;
        public Lineimage textoffPricetwo;
        public ImageView imagetwo;
        public CardView cardtwo;
        public TextView specialselltwo;
        public TextView deletetwo;

        public ViewHolder(View itemView) {
            super(itemView);

            txtnameone = (TextView) itemView.findViewById(R.id.txtnameone);
            textPriceone = (TextView) itemView.findViewById(R.id.textPriceone);
            imageone = (ImageView) itemView.findViewById(R.id.imageone);
            textoffPriceone = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            cardone = (CardView) itemView.findViewById(R.id.Cardone);
            specialsellone = (TextView) itemView.findViewById(R.id.specialsellone);
            deleteone = (TextView) itemView.findViewById(R.id.deleteone);

            txtnametwo = (TextView) itemView.findViewById(R.id.txtnametwo);
            textPricetwo = (TextView) itemView.findViewById(R.id.textPricetwo);
            imagetwo = (ImageView) itemView.findViewById(R.id.imagetwo);
            textoffPricetwo = (Lineimage) itemView.findViewById(R.id.textoffPricetwo);
            cardtwo = (CardView) itemView.findViewById(R.id.Cardtwo);
            specialselltwo = (TextView) itemView.findViewById(R.id.specialselltwo);
            deletetwo = (TextView) itemView.findViewById(R.id.deletetwo);

        }
    }


}
