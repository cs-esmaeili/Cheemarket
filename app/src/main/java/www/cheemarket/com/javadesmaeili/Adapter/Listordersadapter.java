package www.cheemarket.com.javadesmaeili.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.Orderinformation;
import www.cheemarket.com.javadesmaeili.Orders;
import www.cheemarket.com.javadesmaeili.R;

/**
 * Created by user on 8/21/2018.
 */

public class Listordersadapter extends RecyclerView.Adapter<Listordersadapter.ViewHolder>{

    private ArrayList<Orders.order> mdataset;
    private ArrayList<String> status =new ArrayList<String>() {{
        add("ارجاع به انبار");
        add("تحویل به پیک");
        add("ارسال سفارش");
        add("تحویل سفارش");
    }};


    public Listordersadapter(ArrayList<Orders.order> mdataset) {
        this.mdataset = mdataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.adapterlistorders, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtCode.setText(mdataset.get(position).Category);
        holder.txtprice.setText(mdataset.get(position).sum);



        switch (Integer.parseInt(mdataset.get(position).Vaziyat)){
            case 1:
                holder.txtvaziyat.setText(status.get(0));
                break;
            case 2:
                holder.txtvaziyat.setText(status.get(1));
                break;
            case 3:
                holder.txtvaziyat.setText(status.get(2));
                break;
            case 4:
                holder.txtvaziyat.setText(status.get(3));
                break;
        }


        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity, Orderinformation.class);
                intent.putExtra("position" , position);
                G.CurrentActivity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txtCode;
        public TextView txtprice;
        public TextView txtvaziyat;
        public CardView Card;


        public ViewHolder(View itemView) {
            super(itemView);
            txtCode = (TextView) itemView.findViewById(R.id.txtCode);
            txtprice = (TextView) itemView.findViewById(R.id.txtprice);
            txtvaziyat = (TextView) itemView.findViewById(R.id.txtvaziyat);
            Card = (CardView)itemView.findViewById(R.id.Card);


        }
    }
}