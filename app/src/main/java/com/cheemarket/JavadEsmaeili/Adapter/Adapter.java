package com.cheemarket.JavadEsmaeili.Adapter;

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

import com.cheemarket.JavadEsmaeili.ActivityAtelaatkala;
import com.cheemarket.JavadEsmaeili.Commands;
import com.cheemarket.JavadEsmaeili.G;
import com.cheemarket.JavadEsmaeili.Customview.Lineimage;
import com.cheemarket.JavadEsmaeili.R;

import com.cheemarket.JavadEsmaeili.Structure.KalaStructure;
import com.cheemarket.JavadEsmaeili.Textconfig;

/**
 * Created by user on 8/21/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<KalaStructure> datasetonekala;
    private ArrayList<KalaStructure> datasettwokala;
    private ArrayList<KalaStructure> finaldataset;

    private int layout;
    private ImageView.ScaleType scaleType = ImageView.ScaleType.FIT_XY;

    public Adapter(ArrayList<KalaStructure> datasetonekala, ArrayList<KalaStructure> datasettwokala, int layout) {
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


        Log.i("LOG", "posiition =" + temp);
        recyclerView.setAdapter(adapter);
        linearLayoutManager.scrollToPosition(temp);

    }

    public void resetlayout(final RecyclerView recyclerView, Adapter adapter, LinearLayoutManager linearLayoutManager) {

        int temp = linearLayoutManager.findLastVisibleItemPosition();


        temp = temp / 2;
        this.layout = R.layout.listtwo;


        changedataset();


        Log.i("LOG", "posiition =" + temp);
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
            holder.cardone.setVisibility(View.GONE);
        } else {

            if (finaldataset.get(position).OldPrice1 == null || finaldataset.get(position).OldPrice1.equals("0")) {
                holder.textoffPriceone.setVisibility(View.GONE);
            } else {
                holder.textoffPriceone.setText(finaldataset.get(position).OldPrice1 + "تومان");
            }
            if (finaldataset.get(position).Price1 != null && !finaldataset.get(position).Price1.equals("0")) {
                Textconfig.settext(holder.textPriceone,  finaldataset.get(position).Price1 + "تومان" );
            } else {
                holder.textPriceone.setVisibility(View.GONE);
            }

            if (finaldataset.get(position).Status1 != null && finaldataset.get(position).Status1.equals("2")) {
                holder.gifone.setVisibility(View.VISIBLE);
            } else {
                holder.gifone.setVisibility(View.GONE);
            }

            if (finaldataset.get(position).Name1 != null && !finaldataset.get(position).Name1.equals("")) {
                Textconfig.settext(holder.txtnameone, finaldataset.get(position).Name1);
            } else {
                holder.txtnameone.setVisibility(View.GONE);
            }


            if (finaldataset.get(position).Image1 != null && !finaldataset.get(position).Image1.equals("")) {
                Commands.showimage(G.Baseurl + "Listimages/" + finaldataset.get(position).Image1 + "/" + finaldataset.get(position).Image1 + ".png", null, holder.imageone, true);
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
                holder.cardtwo.setVisibility(View.GONE);
            } else {


                if (finaldataset.get(position).OldPrice2 == null || finaldataset.get(position).OldPrice2.equals("0")) {
                    holder.textoffPricetwo.setVisibility(View.GONE);
                } else {
                    holder.textoffPricetwo.setText(finaldataset.get(position).OldPrice2 + "تومان");
                }

                if (finaldataset.get(position).Price2 != null && !finaldataset.get(position).Price2.equals("0")) {
                    Textconfig.settext(holder.textPricetwo,  finaldataset.get(position).Price2 + "تومان");
                } else {
                    holder.textPricetwo.setVisibility(View.GONE);
                }

                if (finaldataset.get(position).Status2 != null && finaldataset.get(position).Status2.equals("2")) {
                    holder.giftwo.setVisibility(View.VISIBLE);
                } else {
                    holder.giftwo.setVisibility(View.GONE);
                }

                if (finaldataset.get(position).Name2 != null && !finaldataset.get(position).Name2.equals("")) {
                    Textconfig.settext(holder.txtnametwo, finaldataset.get(position).Name2);
                } else {
                    holder.txtnametwo.setVisibility(View.GONE);
                }


                if (finaldataset.get(position).Image2 != null && !finaldataset.get(position).Image2.equals("")) {
                    Commands.showimage(G.Baseurl + "Listimages/" + finaldataset.get(position).Image2 + "/" + finaldataset.get(position).Image2 + ".png", null, holder.imagetwo, true);
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
        public ImageView gifone;

        public TextView txtnametwo;
        public TextView textPricetwo;
        public Lineimage textoffPricetwo;
        public ImageView imagetwo;
        public CardView cardtwo;
        public ImageView giftwo;


        public TextView mess;

        public ViewHolder(View itemView) {
            super(itemView);

            txtnameone = (TextView) itemView.findViewById(R.id.txtnameone);
            textPriceone = (TextView) itemView.findViewById(R.id.textPriceone);
            imageone = (ImageView) itemView.findViewById(R.id.imageone);
            textoffPriceone = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            cardone = (CardView) itemView.findViewById(R.id.Cardone);
            gifone = (ImageView) itemView.findViewById(R.id.gifone);

            mess = (TextView) itemView.findViewById(R.id.mess);

            if (layout == R.layout.listtwo) {
                txtnametwo = (TextView) itemView.findViewById(R.id.txtnametwo);
                textPricetwo = (TextView) itemView.findViewById(R.id.textPricetwo);
                imagetwo = (ImageView) itemView.findViewById(R.id.imagetwo);
                textoffPricetwo = (Lineimage) itemView.findViewById(R.id.textoffPricetwo);
                cardtwo = (CardView) itemView.findViewById(R.id.Cardtwo);
                giftwo = (ImageView) itemView.findViewById(R.id.giftwo);
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
