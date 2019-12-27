package com.cheemarket.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.ActivityAddress;
import com.cheemarket.ActivityEdite;
import com.cheemarket.G;
import com.cheemarket.Paymentstep;
import com.cheemarket.R;
import com.cheemarket.Structure.AddressStructure;

import java.util.ArrayList;

/**
 * Created by user on 8/21/2018.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


    private ArrayList<AddressStructure> mdataset;
    private int selected = -1;
    private boolean needselection;
    private TextView txtempty;

    public AddressAdapter(ArrayList<AddressStructure> mdataset, boolean needselection, int selected, TextView txtempty) {
        this.mdataset = mdataset;
        this.selected = selected;
        this.needselection = needselection;
        this.txtempty = txtempty;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapteraddress, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        if (position == selected) {
            holder.card.setBackgroundColor(Color.parseColor("#E9F1E9"));
        } else {
            holder.card.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }


        holder.txtname.setText(mdataset.get(position).Name);
        holder.txtostan.setText(mdataset.get(position).Ostan);
        holder.txtshahr.setText(mdataset.get(position).Shahr);
        holder.txtaddress.setText(mdataset.get(position).Address);
        holder.txtphonenumber.setText(mdataset.get(position).Phonenumber);
        holder.txthomenumber.setText(mdataset.get(position).Homenumber);

        holder.txtdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:

                                if (needselection) {
                                    ActivityAddress.deleteaddress(mdataset.get(position).Id, mdataset, Paymentstep.AdapterList, txtempty);
                                } else {
                                    ActivityAddress.deleteaddress(mdataset.get(position).Id, mdataset, ActivityAddress.AdapterList, txtempty);
                                }


                                break;

                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(G.CurrentActivity);
                builder.setMessage("آیا از حذف این آدرس اطمینان دارید؟").setPositiveButton("بله", dialogClickListener)
                        .setNegativeButton("خیر", dialogClickListener).show();


            }
        });

        holder.lyoutedite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(G.CurrentActivity, ActivityEdite.class);
                intent.putExtra("position", position);
                G.CurrentActivity.startActivity(intent);
            }
        });

        if (needselection) {
            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Paymentstep.Addressid = mdataset.get(position).Id;
                    selected = position;
                    notifyDataSetChanged();
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtname;
        public TextView txtostan;
        public TextView txtshahr;
        public TextView txtaddress;
        public TextView txtphonenumber;
        public TextView txthomenumber;
        public TextView txtdelete;
        public LinearLayout lyoutedite;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);

            txtname = (TextView) itemView.findViewById(R.id.txtname);
            txtostan = (TextView) itemView.findViewById(R.id.txtostan);
            txtshahr = (TextView) itemView.findViewById(R.id.txtshahr);
            txtaddress = (TextView) itemView.findViewById(R.id.txtaddress);
            txtphonenumber = (TextView) itemView.findViewById(R.id.txtphonenumber);
            txthomenumber = (TextView) itemView.findViewById(R.id.txthomenumber);
            txtdelete = (TextView) itemView.findViewById(R.id.txtdelete);
            lyoutedite = (LinearLayout) itemView.findViewById(R.id.lyoutedite);
            card = (CardView) itemView.findViewById(R.id.card);
        }
    }
}
