package www.cheemarket.com.javadesmaeili.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import www.cheemarket.com.javadesmaeili.Commands;
import www.cheemarket.com.javadesmaeili.Dastebandimahsolat;
import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.R;
import www.cheemarket.com.javadesmaeili.Structure.Subdastebandi;
import www.cheemarket.com.javadesmaeili.Structure.KalaStructure;
import www.cheemarket.com.javadesmaeili.Webservice;

import static www.cheemarket.com.javadesmaeili.Subdastebandi.namayeshkalaha;

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

               www.cheemarket.com.javadesmaeili.Subdastebandi.namayeshkalaha(mdataset.get(position).Subdastebandi , mdataset.get(position).Title);
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
