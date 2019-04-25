package www.cheemarket.com.javadesmaeili.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import www.cheemarket.com.javadesmaeili.Commands;
import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.R;
import www.cheemarket.com.javadesmaeili.SabadActivity;
import www.cheemarket.com.javadesmaeili.Structure.sabad;
import www.cheemarket.com.javadesmaeili.Textconfig;
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

        Textconfig.settext(holder.txt1, "نام کالا : " + mdataset.get(position).Name);
        Textconfig.settext(holder.txt4, "قیمت کالا : " + mdataset.get(position).Price);
        Textconfig.settext(holder.txt5, "بارکد کالا : " + mdataset.get(position).Code);
        Textconfig.settext(holder.txt6, "کد کالا : " + mdataset.get(position).Id);
        Textconfig.settext(holder.tedad, mdataset.get(position).Tedad + "");


        if (mdataset.get(position).Weight.equals("")) {
            holder.txt2.setVisibility(View.GONE);
        } else {

            Textconfig.settext(holder.txt2, "وزن کالا : " + mdataset.get(position).Weight);
        }

        if (mdataset.get(position).Volume.equals("")) {
            holder.txt3.setVisibility(View.GONE);
        } else {
            Textconfig.settext(holder.txt3, "حجم کالا : " + mdataset.get(position).Volume);
        }


        Commands.showimage(G.Baseurl + "Listimages/" + mdataset.get(position).Image + "/" + mdataset.get(position).Image + ".png", null, holder.image, true);


        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mdatasetsabad.remove(G.mdatasetsabad.get(position));
                SabadActivity.Adapter.notifyDataSetChanged();
                SabadActivity.pagework();
            }
        });



    }

    Boolean loop = true;
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
