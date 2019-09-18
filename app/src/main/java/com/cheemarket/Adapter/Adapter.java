package com.cheemarket.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.cheemarket.ActivityAtelaatkala;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.R;

import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Textconfig;

/**
 * Created by user on 8/21/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<PoductStructure> datasetonekala;
    private ArrayList<PoductStructure> datasettwokala;
    private ArrayList<PoductStructure> finaldataset;

    private int layout;
    private ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;

    public Adapter(ArrayList<PoductStructure> datasetonekala, ArrayList<PoductStructure> datasettwokala, int layout) {
        this.datasetonekala = datasetonekala;
        this.datasettwokala = datasettwokala;

        this.finaldataset = datasettwokala;

        this.layout = layout;
    }

    public void changelayout(final RecyclerView recyclerView, Adapter adapter, LinearLayoutManager linearLayoutManager) {

        int temp = linearLayoutManager.findLastVisibleItemPosition();

        if (this.layout == R.layout.listtwo) {
            temp = temp * 2;
            this.layout = R.layout.listonemid;
        } else if (this.layout == R.layout.listonemid) {
            this.layout = R.layout.listonelarg;
        } else if (this.layout == R.layout.listonelarg) {
            temp = temp / 2;
            this.layout = R.layout.listtwo;
        }

        changedataset();



        recyclerView.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(temp);

    }

    public void resetlayout(final RecyclerView recyclerView, Adapter adapter, LinearLayoutManager linearLayoutManager) {

        int temp = linearLayoutManager.findLastVisibleItemPosition();


        temp = temp / 2;
        this.layout = R.layout.listtwo;


        changedataset();



        recyclerView.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(temp);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (finaldataset.get(position).Id1 == null) {
            holder.cardone.setVisibility(View.INVISIBLE);
        } else {

            if (finaldataset.get(position).OldPrice1 == null || finaldataset.get(position).OldPrice1.equals("0")) {
                holder.textoffPriceone.setVisibility(View.INVISIBLE);
            } else {
                holder.textoffPriceone.setVisibility(View.VISIBLE);
                holder.textoffPriceone.setText(finaldataset.get(position).OldPrice1 + "تومان");
            }
            if (finaldataset.get(position).Price1 != null && !finaldataset.get(position).Price1.equals("0")) {
                holder.textPriceone.setVisibility(View.VISIBLE);
                Textconfig.settext(holder.textPriceone,  finaldataset.get(position).Price1 + "تومان" );
            } else {
                holder.textPriceone.setVisibility(View.INVISIBLE);
            }

            if (finaldataset.get(position).Status1 != null && finaldataset.get(position).Status1.equals("2")) {
                holder.specialsellone.setVisibility(View.VISIBLE);
            } else {
                holder.specialsellone.setVisibility(View.GONE);
            }

            if (finaldataset.get(position).Name1 != null && !finaldataset.get(position).Name1.equals("")) {
                holder.txtnameone.setVisibility(View.VISIBLE);
                Textconfig.settext(holder.txtnameone, finaldataset.get(position).Name1);
            } else {
                holder.txtnameone.setVisibility(View.INVISIBLE);
            }



            if (finaldataset.get(position).Image_thumbnail1 != null && !finaldataset.get(position).Image_thumbnail1.equals("")) {

                Commands.showimage(finaldataset.get(position).Image_thumbnail1, null, holder.imageone);
            }


            holder.cardone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Commands.openactivity(finaldataset, position, true, ActivityAtelaatkala.class);


                }
            });

            if (layout == R.layout.listone && finaldataset.get(position).Ordernumber1 != null) {
                holder.mess.setVisibility(View.VISIBLE);
                holder.mess.setText("تعداد انتخاب شده: " + finaldataset.get(position).Ordernumber1);
            }

            if(layout == R.layout.listonemid){
                holder.imageone.setScaleType(scaleType);
            }
        }


        if (layout == R.layout.listtwo) {


            if (finaldataset.get(position).Id2 == null) {
                holder.cardtwo.setVisibility(View.INVISIBLE);
            } else {


                if (finaldataset.get(position).OldPrice2 == null || finaldataset.get(position).OldPrice2.equals("0")) {
                    holder.textoffPricetwo.setVisibility(View.INVISIBLE);
                } else {
                    holder.textoffPricetwo.setVisibility(View.VISIBLE);
                    holder.textoffPricetwo.setText(finaldataset.get(position).OldPrice2 + "تومان");
                }

                if (finaldataset.get(position).Price2 != null && !finaldataset.get(position).Price2.equals("0")) {
                    holder.textPricetwo.setVisibility(View.VISIBLE);
                    Textconfig.settext(holder.textPricetwo,  finaldataset.get(position).Price2 + "تومان");
                } else {
                    holder.textPricetwo.setVisibility(View.INVISIBLE);
                }

                if (finaldataset.get(position).Status2 != null && finaldataset.get(position).Status2.equals("2")) {
                    holder.specialselltwo.setVisibility(View.VISIBLE);
                } else {
                    holder.specialselltwo.setVisibility(View.GONE);
                }

                if (finaldataset.get(position).Name2 != null && !finaldataset.get(position).Name2.equals("")) {
                    holder.txtnametwo.setVisibility(View.VISIBLE);
                    Textconfig.settext(holder.txtnametwo, finaldataset.get(position).Name2);
                } else {
                    holder.txtnametwo.setVisibility(View.INVISIBLE);
                }


                if (finaldataset.get(position).Image_thumbnail2 != null && !finaldataset.get(position).Image_thumbnail2.equals("")) {
                    Commands.showimage( finaldataset.get(position).Image_thumbnail2, null, holder.imagetwo);
                }


                holder.cardtwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Commands.openactivity(finaldataset, position, false, ActivityAtelaatkala.class);
                    }
                });


            }


        }

    }

    @Override
    public int getItemCount() {
        return finaldataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtnameone;
        public TextView textPriceone;
        public Lineimage textoffPriceone;
        public ImageView imageone;
        public CardView cardone;
        public TextView specialsellone;

        public TextView txtnametwo;
        public TextView textPricetwo;
        public Lineimage textoffPricetwo;
        public ImageView imagetwo;
        public CardView cardtwo;
        public TextView specialselltwo;


        public TextView mess;

        public ViewHolder(View itemView) {
            super(itemView);

            txtnameone = (TextView) itemView.findViewById(R.id.txtnameone);
            textPriceone = (TextView) itemView.findViewById(R.id.textPriceone);
            imageone = (ImageView) itemView.findViewById(R.id.imageone);
            textoffPriceone = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            cardone = (CardView) itemView.findViewById(R.id.Cardone);
            specialsellone = (TextView) itemView.findViewById(R.id.specialsellone);

            mess = (TextView) itemView.findViewById(R.id.mess);

            if (layout == R.layout.listtwo) {
                txtnametwo = (TextView) itemView.findViewById(R.id.txtnametwo);
                textPricetwo = (TextView) itemView.findViewById(R.id.textPricetwo);
                imagetwo = (ImageView) itemView.findViewById(R.id.imagetwo);
                textoffPricetwo = (Lineimage) itemView.findViewById(R.id.textoffPricetwo);
                cardtwo = (CardView) itemView.findViewById(R.id.Cardtwo);
                specialselltwo = (TextView) itemView.findViewById(R.id.specialselltwo);
            }


        }
    }


    private void changedataset() {


        if (this.layout == R.layout.listtwo) {
            finaldataset = datasettwokala;
        } else {
            finaldataset = datasetonekala;
        }

    }

}
