package com.cheemarket;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.ArrayList;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.Structure.AddressStructure;
import com.cheemarket.Structure.KalaStructure;
import com.cheemarket.Structure.sabad;

public class Paymentstep extends AppCompatActivity {


    private static RecyclerView.Adapter AdapterList1;
    private static ArrayList<KalaStructure> mdatasetList1;
    private static RecyclerView RecyclerViewList1;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    public static AddressStructure Address;
    private TextView btnpay;


    @Override
    protected void onResume() {
        super.onResume();

        if( Paymentstep.Address == null && G.mdatasetsabad.size()==0 ){
            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (Address != null) {
            btnpay.setBackgroundColor(Color.parseColor("#66BB6A"));
        } else {
            btnpay.setBackgroundColor(Color.parseColor("#D6D7D7"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentstep);

        G.CurrentActivity = this;
        RecyclerViewList1 = (RecyclerView) findViewById(R.id.List);

        Lineimage txtwhitoutoff = (Lineimage) findViewById(R.id.lineimage);
        TextView finalprice = (TextView) findViewById(R.id.finalprice);
        TextView txtoff = (TextView) findViewById(R.id.txtoff);
        TextView paykperice = (TextView) findViewById(R.id.paykperice);
        btnpay = (TextView) findViewById(R.id.btnpay);
        Button selectaddress = (Button) findViewById(R.id.selectaddress);

        selectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity, ActivityAddress.class);
                intent.putExtra("Select", "True");
                G.CurrentActivity.startActivity(intent);
            }
        });


        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Address == null){
                    Toast.makeText(G.context,"آدرس را انتخاب کنید", Toast.LENGTH_LONG).show();
                }else {

                    Payment.openpaymentgate();


                }

            }
        });


        mdatasetList1 = new ArrayList<KalaStructure>();

        for (sabad a : G.mdatasetsabad) {
            KalaStructure kalaStructure = new KalaStructure();
            kalaStructure.Name1 = a.Name;
            kalaStructure.Code1 = a.Code;
            kalaStructure.Weight1 = a.Weight;
            kalaStructure.Price1 = a.Price;
            kalaStructure.Volume1 = a.Volume;
            kalaStructure.Image1 = a.Image;
            kalaStructure.Tozihat1 = a.Tozihat;
            kalaStructure.Datetime1 = a.Datetime;
            kalaStructure.Ordernumber1 = a.Tedad;
            kalaStructure.OldPrice1 = a.OldPrice;
            kalaStructure.Status1 = a.Status;
            kalaStructure.Id1 = a.Id;


            mdatasetList1.add(kalaStructure);
        }


        RecyclerViewList1.setHasFixedSize(true);
        LayoutManagerList1 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewList1.setLayoutManager(LayoutManagerList1);
        AdapterList1 = new Adapter(null, mdatasetList1, R.layout.listone);
        RecyclerViewList1.setAdapter(AdapterList1);

        AdapterList1.notifyDataSetChanged();


        BigInteger temp1 = BigInteger.valueOf(0);
        BigInteger temp2 = BigInteger.valueOf(0);
        BigInteger temp3 = BigInteger.valueOf(0);
        for (sabad a : G.mdatasetsabad) {

            BigInteger multi = BigInteger.valueOf(0);
            multi = multi.add(BigInteger.valueOf(Integer.parseInt(a.Price)));
            multi = multi.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));

            temp1 = temp1.add(multi);


            BigInteger sumoff = BigInteger.valueOf(Integer.parseInt(a.OldPrice));
            sumoff = sumoff.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));
            temp2 = temp2.add(sumoff);
        }
        temp3 = temp2.subtract(temp1);
     //   finalprice.setText(temp1 + " " + "تومان");
        txtwhitoutoff.setText(temp2 + " " + "تومان");
        txtoff.setText(temp3 + " " + "تومان");

        BigInteger multi = BigInteger.valueOf(100000);
        if(temp1.compareTo(multi)== 1 || temp1.compareTo(multi)== 0){
            paykperice.setText("رایگان");
            paykperice.setBackgroundColor(Color.parseColor("#66BB6A"));
            finalprice.setText(temp1 + " " + "تومان");
        }else {
            paykperice.setText("3000"+" تومان");
            multi = BigInteger.valueOf(3000);
            temp1 = temp1.add(multi);
            finalprice.setText(temp1 + " " + "تومان");
        }


    }
}
