package www.cheemarket.com.javadesmaeili.Adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.R;
import www.cheemarket.com.javadesmaeili.SabadActivity;
import www.cheemarket.com.javadesmaeili.Structure.sabad;
import www.cheemarket.com.javadesmaeili.Webservice;

/**
 * Created by user on 8/21/2018.
 */

public class SabadAdapter extends RecyclerView.Adapter<SabadAdapter.ViewHolder>{

    private ArrayList<sabad> mdataset;

    public SabadAdapter(ArrayList<sabad> mdataset) {
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.sabadadapter, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txt1.setText("نام کالا : " +mdataset.get(position).Name);
        holder.txt2.setText("وزن کالا : " + mdataset.get(position).Weight);
        holder.txt3.setText("حجم کالا : " + mdataset.get(position).Volume);
        holder.txt4.setText("قیمت کالا : " + mdataset.get(position).Price);
        holder.txt5.setText("بارکد کالا : " + mdataset.get(position).Code);
        holder.txt6.setText("کد کالا : " + mdataset.get(position).Id);
        holder.edt.setText(mdataset.get(position).Tedad + "");


        Picasso.get()
                .load(G.Baseurl + "Listimages/" + mdataset.get(position).Image + "/" + mdataset.get(position).Image + ".png")
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
                        if (e instanceof SocketTimeoutException) {
                            e.printStackTrace();
                            Webservice.handelerro("timeout");
                        } else {
                            e.printStackTrace();
                            Webservice.handelerro(null);
                        }
                    }
                });


        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                G.mdatasetsabad.remove(G.mdatasetsabad.get(position));
                SabadActivity.Adapter.notifyDataSetChanged();
                SabadActivity.pagework();
            }
        });

        holder.edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.toString().length() == 0){
                    return;
                }



                if (Integer.parseInt(s.toString()) > Integer.parseInt(mdataset.get(position).Ordernumber)) {
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(G.CurrentActivity,"مقدار غیر مجاز !", Toast.LENGTH_LONG).show();
                            holder.edt.setText(mdataset.get(position).Ordernumber + "");
                        }
                    });
                }else {
                   G.mdatasetsabad.get(position).Tedad = s.toString();
             //       mdataset.get(position).Ordernumber = s.toString();
                    SabadActivity.setghaymat();

                }


            }
        });



    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txt1;
        public TextView txt2;
        public TextView txt3;
        public TextView txt4;
        public TextView txt5;
        public TextView txt6;
        public TextView txt7;
        public EditText edt;
        public ImageView image;
        public ImageView imgdelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            txt3 = (TextView) itemView.findViewById(R.id.txt3);
            txt4 = (TextView) itemView.findViewById(R.id.txt4);
            txt5 = (TextView) itemView.findViewById(R.id.txt5);
            txt6 = (TextView) itemView.findViewById(R.id.txt6);
            txt7 = (TextView) itemView.findViewById(R.id.txt7);
            edt = (EditText) itemView.findViewById(R.id.edt);

            image =(ImageView) itemView.findViewById(R.id.img);
            imgdelete =(ImageView) itemView.findViewById(R.id.imgdelete);



        }
    }
}
