package com.cheemarket.JavadEsmaeili.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import com.cheemarket.JavadEsmaeili.ActivitySabad;
import com.cheemarket.JavadEsmaeili.Commands;
import com.cheemarket.JavadEsmaeili.G;
import com.cheemarket.JavadEsmaeili.R;
import com.cheemarket.JavadEsmaeili.Structure.sabad;
import com.cheemarket.JavadEsmaeili.Textconfig;
/**
 * Created by user on 8/21/2018.
 */

public class SabadAdapter extends RecyclerView.Adapter<SabadAdapter.ViewHolder> {

    private ArrayList<sabad> mdataset;

    public SabadAdapter(ArrayList<sabad> mdataset) {
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptersabad, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(mdataset.get(position).Name != null && !mdataset.get(position).Name.equals("")){
            Textconfig.settext(holder.txt1, "نام کالا : " + mdataset.get(position).Name);
        }else{
            holder.txt1.setVisibility(View.GONE);
        }

        if(mdataset.get(position).Price != null && !mdataset.get(position).Price.equals("0")){
            Textconfig.settext(holder.txt4, "قیمت کالا : " + mdataset.get(position).Price);
        }else{
            holder.txt4.setVisibility(View.GONE);
        }

        if(mdataset.get(position).Code != null && !mdataset.get(position).Code.equals("")){
            Textconfig.settext(holder.txt5, "بارکد کالا : " + mdataset.get(position).Code);
        }else{
            holder.txt5.setVisibility(View.GONE);
        }

        if(mdataset.get(position).Id != null && !mdataset.get(position).Id.equals("")){
            Textconfig.settext(holder.txt6, "کد کالا : " + mdataset.get(position).Id);
        }else{
            holder.txt6.setVisibility(View.GONE);
        }

        Log.i("LOG","salam =" + mdataset.get(position).Tedad);
        if(mdataset.get(position).Tedad != null && !mdataset.get(position).Tedad.equals("0") && mdataset.get(position).Ordernumber != null && !mdataset.get(position).Ordernumber.equals("0") ){
            Textconfig.settext(holder.tedad, mdataset.get(position).Tedad + "");
            holder.tedad.setVisibility(View.VISIBLE);
        }else{
            holder.tedad.setVisibility(View.GONE);
        }




        if (mdataset.get(position).Weight == null || mdataset.get(position).Weight.equals("")) {
            holder.txt2.setVisibility(View.GONE);
        } else {
            Textconfig.settext(holder.txt2, "وزن کالا : " + mdataset.get(position).Weight);
        }

        if (mdataset.get(position).Volume == null || mdataset.get(position).Volume.equals("")) {
            holder.txt3.setVisibility(View.GONE);
        } else {
            Textconfig.settext(holder.txt3, "حجم کالا : " + mdataset.get(position).Volume);
        }

        if(mdataset.get(position).Image != null && !mdataset.get(position).Image.equals("")){
            Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image + "/" + mdataset.get(position).Image + ".png", null, holder.image, true);
        }



        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mdatasetsabad.remove(G.mdatasetsabad.get(position));
                ActivitySabad.Adapter.notifyDataSetChanged();
               // ActivitySabad.pagework();
                ActivitySabad.setghaymat();
            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Integer.parseInt(mdataset.get(position).Tedad)  + 1 ) <= Integer.parseInt(mdataset.get(position).Ordernumber)){
                    mdataset.get(position).Tedad = (Integer.parseInt(mdataset.get(position).Tedad)  + 1 ) + "";
                    Textconfig.settext(holder.tedad, mdataset.get(position).Tedad + "");
                    ActivitySabad.setghaymat();
                }

            }
        });

        holder.mynes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((Integer.parseInt(mdataset.get(position).Tedad)  - 1 ) <= Integer.parseInt(mdataset.get(position).Ordernumber) && (Integer.parseInt(mdataset.get(position).Tedad)  - 1 ) != 0) {
                    mdataset.get(position).Tedad = (Integer.parseInt(mdataset.get(position).Tedad)  - 1 ) + "";
                    Textconfig.settext(holder.tedad, mdataset.get(position).Tedad + "");
                    ActivitySabad.setghaymat();
                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txt1;
        public TextView txt2;
        public TextView txt3;
        public TextView txt4;
        public TextView txt5;
        public TextView txt6;
        public TextView txt7;
        public ImageView image;
        public ImageView imgdelete;

        public TextView tedad;
        public TextView mynes;
        public TextView plus;

        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            txt3 = (TextView) itemView.findViewById(R.id.txt3);
            txt4 = (TextView) itemView.findViewById(R.id.txt4);
            txt5 = (TextView) itemView.findViewById(R.id.txt5);
            txt6 = (TextView) itemView.findViewById(R.id.txt6);
            txt7 = (TextView) itemView.findViewById(R.id.txt7);
            tedad = (TextView) itemView.findViewById(R.id.tedad);
            mynes = (TextView) itemView.findViewById(R.id.mynes);
            plus = (TextView) itemView.findViewById(R.id.plus);

            image = (ImageView) itemView.findViewById(R.id.img);
            imgdelete = (ImageView) itemView.findViewById(R.id.imgdelete);





        }
    }
}
