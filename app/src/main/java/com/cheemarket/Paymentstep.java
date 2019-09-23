package com.cheemarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Adapter.AddressAdapter;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.AddressStructure;
import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Structure.sabad;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.cheemarket.ActivityAddress.address;
import static com.cheemarket.ActivityAddress.getAddress;
import static com.cheemarket.ActivityAddress.needtorealod;

public class Paymentstep extends AppCompatActivity {


    private static RecyclerView.Adapter AdapterList1;
    private static ArrayList<PoductStructure> mdatasetList1;
    private static RecyclerView RecyclerViewList1;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    private TextView btnpay;
    public static RecyclerView.Adapter AdapterList;
    private Spinner spinnershift;
    private Spinner spinnerdate;
    private LinearLayout backspiner;
    private CheckBox fori;
    private badgelogo badge;


    public static String Addressid ;
    public static String Date = null;

    @Override
    protected void onResume() {
        super.onResume();

        G.CurrentActivity = this;
        Commands.setbadgenumber(badge);


        if (needtorealod) {
            needtorealod = false;
            getAddress(address, AdapterList);
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
        spinnershift = (Spinner) findViewById(R.id.spinnershift);
        spinnerdate = (Spinner) findViewById(R.id.spinnerdate);
        fori = (CheckBox) findViewById(R.id.fori);
        backspiner = (LinearLayout) findViewById(R.id.backspiner);
        RecyclerView Listaddress = (RecyclerView) findViewById(R.id.Listaddress);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);
        badge = (badgelogo) findViewById(R.id.badgelogo);


        Listaddress.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
        Listaddress.setLayoutManager(LayoutManagerList);
        AdapterList = new AddressAdapter(address, true, -1);
        Listaddress.setAdapter(AdapterList);
        getAddress(address, AdapterList);

        selectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity, ActivityEdite.class);
                G.CurrentActivity.startActivity(intent);
            }
        });
        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);


        Commands.setbadgenumber(badge);
        backspiner.setBackgroundColor(Color.parseColor("#BFC0C0"));
        setspineers();

        fori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fori.isChecked()) {
                    backspiner.setBackgroundColor(Color.parseColor("#BFC0C0"));
                } else {
                    backspiner.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
            }
        });

        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Addressid == null) {
                    Toast.makeText(G.context, "آدرس را انتخاب کنید", Toast.LENGTH_LONG).show();
                } else if (fori.isChecked() == false && (spinnershift.getSelectedItemPosition() == 0 || spinnerdate.getSelectedItemPosition() == 0)) {
                    Toast.makeText(G.context, "زمان تحویل سفارش را انتخاب کنید", Toast.LENGTH_LONG).show();
                } else {
                    if( spinnerdate.getSelectedItemPosition() == 0 || spinnerdate.getSelectedItemPosition() == 0){
                        Date = "فوری";
                    }else {
                        Date = spinnerdate.getSelectedItem().toString() + "  " + spinnershift.getSelectedItem().toString();
                    }

                    Payment.openpaymentgate();
                }

            }
        });


        mdatasetList1 = new ArrayList<PoductStructure>();

        for (sabad a : G.mdatasetsabad) {
            PoductStructure PoductStructure = new PoductStructure();
            PoductStructure.Name1 = a.Name;
            PoductStructure.Price1 = a.Price;
            PoductStructure.Image_folder1 = a.Image_folder;
            PoductStructure.Image_thumbnail1 = a.Image_thumbnail;
            PoductStructure.Description1 = a.Tozihat;
            PoductStructure.Datetime1 = a.Datetime;
            PoductStructure.Ordernumber1 = a.Tedad;
            PoductStructure.OldPrice1 = a.OldPrice;
            PoductStructure.Status1 = a.Status;
            PoductStructure.Id1 = a.Id;


            mdatasetList1.add(PoductStructure);
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
        if (temp1.compareTo(multi) == 1 || temp1.compareTo(multi) == 0) {
            paykperice.setText("رایگان");
            paykperice.setBackgroundColor(Color.parseColor("#66BB6A"));
            finalprice.setText(temp1 + " " + "تومان");
        } else {
            paykperice.setText("3000" + " تومان");
            multi = BigInteger.valueOf(3000);
            temp1 = temp1.add(multi);
            finalprice.setText(temp1 + " " + "تومان");
        }


    }

    private void setspineers() {


        ArrayList<String> dates = new ArrayList<>();
        dates.add("");

        for (int i = 1; i <= 7; i++) {

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, i);


            DateConverter converter = new DateConverter();
            converter.gregorianToPersian(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            String finaldate = converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay();
            dates.add(finaldate);

        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdate.setAdapter(spinnerArrayAdapter);


        ArrayList<String> shift = new ArrayList<>();
        shift.add("");
        shift.add("صبح");
        shift.add("عصر");
        ArrayAdapter<String> spinnerArrayAdapterr = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shift);
        spinnerArrayAdapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnershift.setAdapter(spinnerArrayAdapterr);


        AdapterView.OnItemSelectedListener lisener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (fori.isChecked()) {
                    fori.setChecked(false);
                }
                backspiner.setBackgroundColor(Color.parseColor("#FFFFFF"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerdate.setOnItemSelectedListener(lisener);
        spinnershift.setOnItemSelectedListener(lisener);


    }
}
