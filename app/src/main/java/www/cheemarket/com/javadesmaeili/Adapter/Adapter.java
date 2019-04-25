package www.cheemarket.com.javadesmaeili.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import www.cheemarket.com.javadesmaeili.ActivityAtelaatkala;
import www.cheemarket.com.javadesmaeili.Commands;
import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.Customview.Lineimage;
import www.cheemarket.com.javadesmaeili.R;

import www.cheemarket.com.javadesmaeili.Structure.KalaStructure;
import www.cheemarket.com.javadesmaeili.Textconfig;

/**
 * Created by user on 8/21/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<KalaStructure> datasetonekala;
    private ArrayList<KalaStructure> datasettwokala;
    private ArrayList<KalaStructure> finaldataset;

    private int layout;

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


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (finaldataset.get(position).OldPrice1.equals("0")) {
            holder.textoffPriceone.setVisibility(View.GONE);
        } else {
            holder.textoffPriceone.setText(finaldataset.get(position).OldPrice1 + "");

        }
        if (finaldataset.get(position).Status1.equals("2")) {
            holder.gifone.setVisibility(View.VISIBLE);
        } else {
            holder.gifone.setVisibility(View.GONE);
        }

        Textconfig.settext(holder.txtnameone, finaldataset.get(position).Name1);
        Textconfig.settext(holder.textPriceone, "" + finaldataset.get(position).Price1);


        Commands.showimage(G.Baseurl + "Listimages/" + finaldataset.get(position).Image1 + "/" + finaldataset.get(position).Image1 + ".png" ,null,holder.imageone,true);

        holder.cardone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Commands.openactivity(finaldataset, position, ActivityAtelaatkala.class);

            }
        });


        if (layout == R.layout.listtwo) {


            if (finaldataset.get(position).OldPrice2.equals("0")) {
                holder.textoffPricetwo.setVisibility(View.GONE);
            } else {
                holder.textoffPricetwo.setText(finaldataset.get(position).OldPrice2 + "");

            }

            if (finaldataset.get(position).Status2.equals("2")) {
                holder.giftwo.setVisibility(View.VISIBLE);
            } else {
                holder.giftwo.setVisibility(View.GONE);
            }

            Textconfig.settext(holder.txtnametwo, finaldataset.get(position).Name2);
            Textconfig.settext(holder.textPricetwo, "" + finaldataset.get(position).Price2);



            Commands.showimage(G.Baseurl + "Listimages/" + finaldataset.get(position).Image2 + "/" + finaldataset.get(position).Image2 + ".png", null,holder.imagetwo,true);

            holder.cardtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Commands.openactivity(finaldataset, position, ActivityAtelaatkala.class);
                }
            });

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


        public ViewHolder(View itemView) {
            super(itemView);

            txtnameone = (TextView) itemView.findViewById(R.id.txtnameone);
            textPriceone = (TextView) itemView.findViewById(R.id.textPriceone);
            imageone = (ImageView) itemView.findViewById(R.id.imageone);
            textoffPriceone = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            cardone = (CardView) itemView.findViewById(R.id.Cardone);
            gifone = (ImageView) itemView.findViewById(R.id.gifone);


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
