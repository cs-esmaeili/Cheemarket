package www.cheemarket.com.javadesmaeili.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
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

import pl.droidsonroids.gif.GifImageView;
import www.cheemarket.com.javadesmaeili.ActivityMain;
import www.cheemarket.com.javadesmaeili.Converts;
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

    public Adapter(ArrayList<KalaStructure> datasetonekala, ArrayList<KalaStructure> datasettwokala , int layout) {
        this.datasetonekala = datasetonekala;
        this.datasettwokala = datasettwokala;

        this.finaldataset = datasettwokala;

        this.layout = layout;
    }

    public void changelayout(RecyclerView recyclerView, Adapter adapter, LinearLayoutManager linearLayoutManager) {


        if(this.layout == R.layout.listtwo){
            this.layout = R.layout.listonemid;
        }else if (this.layout == R.layout.listonemid){
            this.layout = R.layout.listonelarg;
        }else if (this.layout == R.layout.listonelarg){
            this.layout = R.layout.listtwo;
        }

        changedataset();

        int temp = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(temp);
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
        if(finaldataset.get(position).Status1.equals("2")){
            holder.gifone.setVisibility(View.VISIBLE);
        }else{
            holder.gifone.setVisibility(View.GONE);
        }

        Textconfig.settext(holder.txtnameone, finaldataset.get(position).Name1);
        Textconfig.settext(holder.textPriceone, "" + finaldataset.get(position).Price1);


        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {


                Picasso.get()
                        .load(G.Baseurl + "Listimages/" + finaldataset.get(position).Image1 + "/" + finaldataset.get(position).Image1 + ".png")
                        //.resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(holder.imageone, new Callback() {
                            @Override
                            public void onSuccess() {
                                MaterialImageLoading.animate(holder.imageone).setDuration(1000).start();
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });

            }
        });


        holder.cardone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Converts.openactivity(finaldataset, position, ActivityMain.class);
                //   ScrollingActivity.dataset = ActivityMain.class;
            }
        });



        if (layout == R.layout.listtwo)
        {


            if (finaldataset.get(position).OldPrice2.equals("0")) {
                holder.textoffPricetwo.setVisibility(View.GONE);
            } else {
                holder.textoffPricetwo.setText(finaldataset.get(position).OldPrice2 + "");

            }

            if(finaldataset.get(position).Status2.equals("2")){
                holder.giftwo.setVisibility(View.VISIBLE);
            }else{
                holder.giftwo.setVisibility(View.GONE);
            }

            Textconfig.settext(holder.txtnametwo, finaldataset.get(position).Name2);
            Textconfig.settext(holder.textPricetwo, "" + finaldataset.get(position).Price2);


            G.HANDLER.post(new Runnable() {
                @Override
                public void run() {


                    Picasso.get()
                            .load(G.Baseurl + "Listimages/" + finaldataset.get(position).Image2 + "/" + finaldataset.get(position).Image2 + ".png")
                            //.resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .into(holder.imagetwo, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(holder.imagetwo).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {

                                }
                            });


                }
            });


            holder.cardtwo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Converts.openactivity(finaldataset, position, ActivityMain.class);
                    //   ScrollingActivity.dataset = ActivityMain.class;
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
        public GifImageView gifone;

        public TextView txtnametwo;
        public TextView textPricetwo;
        public Lineimage textoffPricetwo;
        public ImageView imagetwo;
        public CardView cardtwo;
        public GifImageView giftwo;


        public ViewHolder(View itemView) {
            super(itemView);

            txtnameone = (TextView) itemView.findViewById(R.id.txtnameone);
            textPriceone = (TextView) itemView.findViewById(R.id.textPriceone);
            imageone = (ImageView) itemView.findViewById(R.id.imageone);
            textoffPriceone = (Lineimage) itemView.findViewById(R.id.textoffPriceone);
            cardone = (CardView) itemView.findViewById(R.id.Cardone);
            gifone = (GifImageView) itemView.findViewById(R.id.gifone);


            if (layout == R.layout.listtwo) {
                txtnametwo = (TextView) itemView.findViewById(R.id.txtnametwo);
                textPricetwo = (TextView) itemView.findViewById(R.id.textPricetwo);
                imagetwo = (ImageView) itemView.findViewById(R.id.imagetwo);
                textoffPricetwo = (Lineimage) itemView.findViewById(R.id.textoffPricetwo);
                cardtwo = (CardView) itemView.findViewById(R.id.Cardtwo);
                giftwo = (GifImageView) itemView.findViewById(R.id.giftwo);
            }


        }
    }

    private void changedataset(){
        for (int i = 0; i < datasetonekala.size(); i++) {
            Log.i("LOG",datasetonekala.get(i).Name1 + "??" + datasetonekala.get(i).Name2);
        }

        if(this.layout == R.layout.listtwo) {
            finaldataset = datasettwokala;
        }else {
            finaldataset = datasetonekala;
        }

    }
   
}
