package com.cheemarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Adapter.AddressAdapter;
import com.cheemarket.Customview.Dialogs;
import com.cheemarket.Customview.Lineimage;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Structure.header;
import com.cheemarket.Structure.peyment_step_structure;
import com.cheemarket.Structure.sabad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private static ProgressBar progressbar;
    private static TextView max_price_text;
    private static ScrollView main;
    private static TextView finalprice;
    private static TextView txtoff;
    private static TextView txtwhitoff;
    private static TextView paykperice;
    private static Lineimage txtwhitoutoff;

    @Override
    protected void onResume() {
        super.onResume();

        G.CurrentActivity = this;
        Commands.setbadgenumber(badge);

        if (txtempty != null) {
            txtempty.setVisibility(View.GONE);
        }
        if (needtorealod) {
            needtorealod = false;
            getAddress(address, AdapterList, txtempty);
        }

        if (close) {
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

        txtwhitoutoff = (Lineimage) findViewById(R.id.whitoutoff);
        finalprice = (TextView) findViewById(R.id.finalprice);
        txtoff = (TextView) findViewById(R.id.txtoff);
        txtwhitoff = (TextView) findViewById(R.id.txtwhitoff);
        paykperice = (TextView) findViewById(R.id.paykperice);
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
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        max_price_text = (TextView) findViewById(R.id.max_price_text);
        main = (ScrollView) findViewById(R.id.main);
        final EditText desc = (EditText) findViewById(R.id.desc);
        Listaddress.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
        Listaddress.setLayoutManager(LayoutManagerList);
        AdapterList = new AddressAdapter(address, true, -1, txtempty);
        Listaddress.setAdapter(AdapterList);
        getAddress(address, AdapterList, txtempty);


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
        peyment_step();

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
                    Payment.openpaymentgate(desc.getText().toString());
                }

            }
        });


    }

    private void setspineers() {

        final ArrayList<String> dates = new ArrayList<>();
        for (int i = 0; i < date_times.size(); i++) {
            dates.add(date_times.get(i).header_obj.date);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerdate.setAdapter(spinnerArrayAdapter);


        AdapterView.OnItemSelectedListener lisener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (date_times.get(spinnerdate.getSelectedItemPosition()).header_obj.message != null && !date_times.get(spinnerdate.getSelectedItemPosition()).header_obj.message.equals("")) {
                    Dialogs.message_dialog(date_times.get(spinnerdate.getSelectedItemPosition()).header_obj.message);
                }
                ArrayAdapter<String> spinnerArrayAdapterr = new ArrayAdapter<String>(G.CurrentActivity, android.R.layout.simple_spinner_item, date_times.get(spinnerdate.getSelectedItemPosition()).times);
                spinnerArrayAdapterr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnershift.setAdapter(spinnerArrayAdapterr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        spinnerdate.setOnItemSelectedListener(lisener);


    }

    List<peyment_step_structure> date_times = new ArrayList<>();

    private void peyment_step() {
        date_times.clear();
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "token";
        param1.value = G.token;

        final ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);


        Webservice.request("peyment_step", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                peyment_step();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String input = response.body().string();


                try {
                    final JSONObject obj = new JSONObject(input);

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisibility(View.GONE);
                            main.setVisibility(View.VISIBLE);
                            try {
                                set_prices(obj.getString("courier_price"), obj.getString("max_price"));

                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {
                                            if (obj.getString("fast").equals("no")) {
                                                fori.setChecked(false);
                                                fori.setEnabled(false);

                                                zaman.setEnabled(true);
                                                zaman.setChecked(true);
                                                spinnerdate.setEnabled(true);
                                                spinnershift.setEnabled(true);
                                            } else {
                                                fori.setChecked(true);
                                                fori.setEnabled(true);

                                                zaman.setEnabled(true);
                                                spinnerdate.setEnabled(false);
                                                spinnershift.setEnabled(false);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


                    max_price_text.setText(obj.getString("max_price_text"));
                    JSONArray dates_array = obj.getJSONArray("dates");
                    for (int i = 0; i < dates_array.length(); i++) {

                        JSONArray inner_array = dates_array.getJSONArray(i);

                        peyment_step_structure temp = new peyment_step_structure();
                        header header_obj = new header();
                        header_obj.date = inner_array.getJSONObject(0).getString("date");
                        header_obj.message = inner_array.getJSONObject(0).getString("message");
                        temp.header_obj = header_obj;

                        JSONArray times = inner_array.getJSONArray(1);
                        ArrayList<String> string_times = new ArrayList<>();
                        for (int j = 0; j < times.length(); j++) {
                            string_times.add(times.getString(j));
                        }
                        temp.times = string_times;

                        date_times.add(temp);

                    }

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            setspineers();
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);

    }

    private void set_prices(String courier_price, String max_price) {

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


        BigInteger ghymatbedonetakhfif = BigInteger.valueOf(0);
        BigInteger ghymatbebatakhfif = BigInteger.valueOf(0);
        BigInteger ghymatoff = BigInteger.valueOf(0);


        for (sabad a : G.mdatasetsabad) {
            BigInteger sum = BigInteger.valueOf(Integer.parseInt(a.Price));
            sum = sum.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));
            ghymatbebatakhfif = ghymatbebatakhfif.add(sum);


            if (Integer.parseInt(a.OldPrice) == 0) {
                BigInteger sumoff = BigInteger.valueOf(Integer.parseInt(a.Price));
                sumoff = sumoff.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));
                ghymatbedonetakhfif = ghymatbedonetakhfif.add(sumoff);
            } else {
                BigInteger sumoff = BigInteger.valueOf(Integer.parseInt(a.OldPrice));
                sumoff = sumoff.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));
                ghymatbedonetakhfif = ghymatbedonetakhfif.add(sumoff);
            }

        }
        ghymatoff = ghymatbedonetakhfif.subtract(ghymatbebatakhfif);


        txtwhitoutoff.setText(ghymatbedonetakhfif + " " + "تومان");
        txtoff.setText(ghymatoff + " " + "تومان");
        txtwhitoff.setText(ghymatbebatakhfif + " " + "تومان");

        BigInteger max = new BigInteger(max_price);


        if (ghymatbebatakhfif.compareTo(max) == 1) {
            paykperice.setText("رایگان");
            paykperice.setBackgroundColor(Color.parseColor("#026202"));
            finalprice.setText(ghymatbebatakhfif + " " + "تومان");
        } else {
            paykperice.setText(courier_price + " " + " تومان");
            BigInteger courier = new BigInteger(courier_price);
            ghymatbebatakhfif = ghymatbebatakhfif.add(courier);
            finalprice.setText(ghymatbebatakhfif + " " + "تومان");
        }
        max = new BigInteger("1000");
        if (ghymatbebatakhfif.compareTo(max) == -1) {
            payment.setChecked(false);
            payment.setEnabled(false);
            paymentoff.setChecked(true);
        }


    }
}
