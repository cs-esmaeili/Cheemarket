package com.cheemarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Adapter.AddressAdapter;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Structure.sabad;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;

import static com.cheemarket.ActivityAddress.address;
import static com.cheemarket.ActivityAddress.getAddress;
import static com.cheemarket.ActivityAddress.needtorealod;

public class Paymentstep extends AppCompatActivity {


    private static RecyclerView.Adapter AdapterList1;
    private static ArrayList<PoductStructure> mdatasetList1;
    private static RecyclerView RecyclerViewList1;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    public static TextView btnpay;
    public static RecyclerView.Adapter AdapterList;
    private Spinner spinnershift;
    private Spinner spinnerdate;
    private LinearLayout backspiner;
    private CheckBox fori;
    private CheckBox zaman;
    private badgelogo badge;
    private CheckBox payment;
    private CheckBox paymentoff;
    private static TextView txtempty;
    public static String Addressid;
    public static String Date = null;
    public static boolean paymentway = true;
    public static boolean close = false;

    @Override
    protected void onResume() {
        super.onResume();

        G.CurrentActivity = this;
        Commands.setbadgenumber(badge);

        if(txtempty != null){
            txtempty.setVisibility(View.GONE);
        }
        if (needtorealod) {
            needtorealod = false;
            getAddress(address, AdapterList ,txtempty);
        }

        if(close){
            Paymentstep.btnpay.setEnabled(true);
            close = false;
            Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            G.CurrentActivity.startActivity(intent);
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
        zaman = (CheckBox) findViewById(R.id.zaman);
        backspiner = (LinearLayout) findViewById(R.id.backspiner);
        RecyclerView Listaddress = (RecyclerView) findViewById(R.id.Listaddress);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);
        badge = (badgelogo) findViewById(R.id.badgelogo);
        payment = (CheckBox) findViewById(R.id.payment);
        paymentoff = (CheckBox) findViewById(R.id.paymentoff);
        txtempty = (TextView) findViewById(R.id.txtempty);
        Listaddress.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
        Listaddress.setLayoutManager(LayoutManagerList);
        AdapterList = new AddressAdapter(address, true, -1 , txtempty);
        Listaddress.setAdapter(AdapterList);
        getAddress(address, AdapterList ,txtempty);


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
        setspineers();

        fori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fori.isChecked()) {
                    zaman.setChecked(false);
                    spinnerdate.setEnabled(false);
                    spinnershift.setEnabled(false);
                }
            }
        });

        zaman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (zaman.isChecked()) {
                    fori.setChecked(false);
                    spinnerdate.setEnabled(true);
                    spinnershift.setEnabled(true);
                }
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment.isChecked()) {
                    paymentway = true;
                    paymentoff.setChecked(false);
                }
            }
        });

        paymentoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (paymentoff.isChecked()) {
                    paymentway = false;
                    payment.setChecked(false);
                }
            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Addressid == null) {
                    Toast.makeText(G.context, "آدرس را انتخاب کنید", Toast.LENGTH_LONG).show();
                } else if (fori.isChecked() == false && zaman.isChecked() == false && (spinnershift.getSelectedItemPosition() == 0 || spinnerdate.getSelectedItemPosition() == 0)) {
                    Toast.makeText(G.context, "زمان تحویل سفارش را انتخاب کنید", Toast.LENGTH_LONG).show();
                } else {
                    if ((zaman.isChecked() == false && fori.isChecked() == true) && (spinnerdate.getSelectedItemPosition() == 0 || spinnerdate.getSelectedItemPosition() == 0)) {
                        Date = "فوری";
                    } else {
                        Date = spinnerdate.getSelectedItem().toString() + "  " + spinnershift.getSelectedItem().toString();
                    }

                    btnpay.setEnabled(false);
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


        BigInteger ghymat = BigInteger.valueOf(0);
        BigInteger ghymatoff = BigInteger.valueOf(0);
        BigInteger temp3 = BigInteger.valueOf(0);


        for (sabad a : G.mdatasetsabad) {

            BigInteger multi = BigInteger.valueOf(0);
            multi = multi.add(BigInteger.valueOf(Integer.parseInt(a.Price)));
            multi = multi.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));

            ghymat = ghymat.add(multi);


            if (Integer.parseInt(a.OldPrice) == 0) {
                BigInteger sumoff = BigInteger.valueOf(Integer.parseInt(a.Price));
                sumoff = sumoff.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));
                ghymatoff = ghymatoff.add(sumoff);
            } else {
                BigInteger sumoff = BigInteger.valueOf(Integer.parseInt(a.OldPrice));
                sumoff = sumoff.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));
                ghymatoff = ghymatoff.add(sumoff);
            }

        }
        //temp3 = ghymatoff.subtract(ghymat);


        txtwhitoutoff.setText(ghymatoff + " " + "تومان");
        txtoff.setText(ghymat + " " + "تومان");

        BigInteger multi = BigInteger.valueOf(75000);
        if (ghymat.compareTo(multi) == 1 || ghymat.compareTo(multi) == 0) {
            paykperice.setText("رایگان");
            paykperice.setBackgroundColor(Color.parseColor("#66BB6A"));
            finalprice.setText(ghymat + " " + "تومان");
        } else {
            paykperice.setText("5000" + " تومان");
            multi = BigInteger.valueOf(5000);
            ghymat = ghymat.add(multi);
            finalprice.setText(ghymat + " " + "تومان");
        }


    }

    private void setspineers() {

        Calendar c = Calendar.getInstance();
        final int h = c.get(Calendar.HOUR_OF_DAY);


        ArrayList<String> dates = new ArrayList<>();


        for (int i = (h >= 20) ? 1 : 0; i < 7; i++) {

            c = Calendar.getInstance();

            c.add(Calendar.DATE, i);

            DateConverter converter = new DateConverter();

            converter.gregorianToPersian(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
            String finaldate = converter.getYear() + "/" + converter.getMonth() + "/" + converter.getDay();
            dates.add(finaldate);

        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdate.setAdapter(spinnerArrayAdapter);


        AdapterView.OnItemSelectedListener lisener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerdate.getSelectedItemPosition() == 0 && spinnerdate.getCount() == 7) {

                    ArrayList<String> shift = new ArrayList<>();

                    if (h < 9) {
                        shift.add("9-11");
                    }
                    if (h < 11) {
                        shift.add("11-13");
                    }

                    if (h < 13) {
                        shift.add("13-15");
                    }
                    if (h < 15) {
                        shift.add("15-17");
                    }

                    if (h < 17) {
                        shift.add("17-19");
                    }
                    if (h < 19) {
                        shift.add("19-21");
                    }
                    if(h < 22){
                        shift.add("22");
                    }



                    if (h >= 20 || h < 9) {
                        fori.setChecked(false);
                        fori.setEnabled(false);
                        zaman.setEnabled(true);
                        zaman.setChecked(true);
                    }

                    ArrayAdapter<String> spinnerArrayAdapterr = new ArrayAdapter<String>(G.CurrentActivity, android.R.layout.simple_spinner_item, shift);
                    spinnerArrayAdapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnershift.setAdapter(spinnerArrayAdapterr);


                } else {

                    ArrayList<String> shift = new ArrayList<>();
                    shift.add("9-11");
                    shift.add("11-13");
                    shift.add("13-15");
                    shift.add("15-17");
                    shift.add("17-19");
                    shift.add("19-21");
                    shift.add("22");
                    fori.setChecked(false);
                    fori.setEnabled(false);
                    zaman.setEnabled(true);
                    zaman.setChecked(true);

                    ArrayAdapter<String> spinnerArrayAdapterr = new ArrayAdapter<String>(G.CurrentActivity, android.R.layout.simple_spinner_item, shift);
                    spinnerArrayAdapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnershift.setAdapter(spinnerArrayAdapterr);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerdate.setOnItemSelectedListener(lisener);


    }
}
