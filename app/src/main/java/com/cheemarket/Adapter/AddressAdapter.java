package com.cheemarket.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.cheemarket.ActivityAddress;
import com.cheemarket.G;
import com.cheemarket.R;
import com.cheemarket.Structure.AddressStructure;
import com.cheemarket.Textconfig;
import com.cheemarket.Webservice;

import static com.cheemarket.ActivityAddress.spinershahrha;

/**
 * Created by user on 8/21/2018.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {


    private ArrayList<AddressStructure> mdataset;
    public static ArrayList<Integer> colors = new ArrayList<>();
    public static String updateid;


    public AddressAdapter(ArrayList<AddressStructure> mdataset, ArrayList<Integer> colors) {
        this.mdataset = mdataset;
        this.colors = colors;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapteraddress, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.card.setBackgroundColor(colors.get(position));

        holder.layout.setBackgroundColor(colors.get(position));

        Textconfig.settext(holder.txtname, "" + mdataset.get(position).Name);
//        Textconfig.settext(holder.txtphonenumber, "" +mdataset.get(position).Phonenumber);
        holder.txtphonenumber.setText("" + mdataset.get(position).Phonenumber);
        Textconfig.settext(holder.txtaddress, "" + mdataset.get(position).Address);


        holder.card.setBackgroundColor(colors.get(position));

        holder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Webservice.requestparameter param1 = new Webservice.requestparameter();
                param1.key = "token";
                param1.value = G.token;
                Webservice.requestparameter param2 = new Webservice.requestparameter();
                param2.key = "Id";
                param2.value = mdataset.get(position).Id;
                ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                array.add(param1);
                array.add(param2);
                Webservice.request("Store.php?action=deleteaddress", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Webservice.handelerro(e, new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {

                                return null;
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String input = response.body().string();
                        if (input != null && !input.equals("")) {
                            if (input.equals("Ok")) {
                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(G.context, "آدرس شما حذف شد", Toast.LENGTH_LONG).show();
                                        ActivityAddress.pagework();
                                    }
                                });
                            }
                        }
                    }
                }, array);

            }
        });


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (colors.get(position).equals(Color.WHITE)) {


                    colors.set(position, Color.TRANSPARENT);
                    holder.card.setBackgroundColor(colors.get(position));


/*
                    Textconfig.settext( ActivityAddress.edtaddress, "" +mdataset.get(position).Address);
                    Textconfig.settext( ActivityAddress.edtcodeposti, "" +mdataset.get(position).Codeposti);
                    Textconfig.settext( ActivityAddress.edtname, "" +mdataset.get(position).Name);
                    Textconfig.settext( ActivityAddress.edthomenumber, "" +mdataset.get(position).Homenumber);
                    Textconfig.settext( ActivityAddress.edtphonenumber, "" +mdataset.get(position).Phonenumber);

*/
                    ActivityAddress.edtaddress.setText("" + mdataset.get(position).Address);
                    ActivityAddress.edtcodeposti.setText("" + mdataset.get(position).Codeposti);
                    ActivityAddress.edtname.setText("" + mdataset.get(position).Name);
                    ActivityAddress.edthomenumber.setText("" + mdataset.get(position).Homenumber);
                    ActivityAddress.edtphonenumber.setText("" + mdataset.get(position).Phonenumber);

                    if (ActivityAddress.btnselect.getVisibility() == View.VISIBLE) {
                        ActivityAddress.btnselect.setBackgroundColor(Color.parseColor("#66BB6A"));
                    }

                    List<String> Lines = Arrays.asList(G.context.getResources().getStringArray(R.array.ostanha));
                    for (int i = 0; i < Lines.size(); i++) {
                        if (Lines.get(i).equals(mdataset.get(position).Ostan)) {
                            ActivityAddress.spnerostan.setSelection(i);
                            spinershahrha();
                            break;
                        }
                    }


                    for (int i = 0; i < ActivityAddress.adapter.getCount(); i++) {

                        if (ActivityAddress.adapter.getItem(i).toString().equals(mdataset.get(position).Shahr)) {
                            final int finalI = i;
                            G.HANDLER.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ActivityAddress.spnershahr.setSelection(finalI);
                                }
                            }, 300);

                            break;
                        }
                    }


                    ActivityAddress.btnsave.setText("ثبت تغییرات");
                    updateid = mdataset.get(position).Id;


                    ActivityAddress.btnselect.clearFocus();
                    ActivityAddress.btnselect.setFocusableInTouchMode(true);
                    ActivityAddress.btnselect.requestFocus();
                } else if (colors.get(position).equals(Color.TRANSPARENT)) {

                    colors.set(position, Color.WHITE);
                    holder.card.setBackgroundColor(colors.get(position));


                    ActivityAddress.edtaddress.setText("");
                    ActivityAddress.edtcodeposti.setText("");
                    ActivityAddress.edtname.setText("");
                    ActivityAddress.edthomenumber.setText("");
                    ActivityAddress.edtphonenumber.setText("");
                    if (ActivityAddress.btnselect.getVisibility() == View.VISIBLE) {
                        ActivityAddress.btnselect.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    }

                    ActivityAddress.spnerostan.setSelection(0);

                    spinershahrha();

                    ActivityAddress.btnsave.setText("اضافه کردن آدرس جدید");
                    updateid = null;
                }


                for (int i = 0; i < colors.size(); i++) {

                    if (i == position) {
                        continue;
                    }
                    colors.set(i, Color.WHITE);
                }

                notifyDataSetChanged();


            }
        });


    }


    @Override
    public int getItemCount() {
        return mdataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtname;
        public TextView txtphonenumber;
        public TextView txtaddress;
        public ImageView imgdelete;
        public LinearLayout layout;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            txtname = (TextView) itemView.findViewById(R.id.txtname);
            txtphonenumber = (TextView) itemView.findViewById(R.id.txtphonenumber);
            txtaddress = (TextView) itemView.findViewById(R.id.txtaddress);
            imgdelete = (ImageView) itemView.findViewById(R.id.imgdelete);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            card = (CardView) itemView.findViewById(R.id.card);

        }
    }
}
