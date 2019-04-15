package www.cheemarket.com.javadesmaeili.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    private ArrayList<KalaStructure> mdataset;
    private int layout;

    public Adapter(ArrayList<KalaStructure> mdataset, int layout) {
        this.mdataset = mdataset;
        this.layout = layout;
    }

    public void changelayout(int layout, RecyclerView recyclerView, Adapter adapter, LinearLayoutManager linearLayoutManager) {
        this.layout = layout;
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

        if (mdataset.get(position).oldPrice1.equals("0")) {
            holder.textoffPrice.setVisibility(View.GONE);



        } else {
           holder.textoffPrice.setText(mdataset.get(position).oldPrice1 + "");

        }

        Textconfig.settext(holder.txtname,mdataset.get(position).Name1);
        Textconfig.settext(holder.txtWeight,mdataset.get(position).Weight1);
        Textconfig.settext(holder.textPrice,"" + mdataset.get(position).Price1);
        Textconfig.settext(holder.textVolume,mdataset.get(position).Volume1);




        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {



                Picasso.get()
                        .load(G.Baseurl + "Listimages/" + mdataset.get(position).Image1 + "/" + mdataset.get(position).Image1 + ".png")
                        .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .into(holder.image, new Callback() {
                            @Override
                            public void onSuccess() {
                                MaterialImageLoading.animate(holder.image).setDuration(1000).start();
                            }

                            @Override
                            public void onError(Exception e) {

                            }
                        });


            }
        });


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Converts.openactivity(mdataset, position, ActivityMain.class);
                //   ScrollingActivity.dataset = ActivityMain.class;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtname;
        public TextView txtWeight;
        public TextView textPrice;
        public Lineimage textoffPrice;
        public TextView textVolume;
        public ImageView image;
        public CardView card;


        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            txtWeight = (TextView) itemView.findViewById(R.id.txtWeight);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            textVolume = (TextView) itemView.findViewById(R.id.textVolume);
            image = (ImageView) itemView.findViewById(R.id.image);
            textoffPrice = (Lineimage) itemView.findViewById(R.id.textoffPrice);
            card = (CardView) itemView.findViewById(R.id.Card);


        }
    }
}
