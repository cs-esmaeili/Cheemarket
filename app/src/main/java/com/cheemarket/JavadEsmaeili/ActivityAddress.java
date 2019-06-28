package com.cheemarket.JavadEsmaeili;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.cheemarket.JavadEsmaeili.R;

import com.cheemarket.JavadEsmaeili.Adapter.AddressAdapter;
import com.cheemarket.JavadEsmaeili.Structure.AddressStructure;

public class ActivityAddress extends AppCompatActivity {

    private static ArrayList<AddressStructure> mdatasetList;
    private static RecyclerView.Adapter AdapterList;
    private static ArrayList<Integer> colors = new ArrayList<>();
    public static EditText edtname;
    public static EditText edthomenumber;
    public static EditText edtphonenumber;
    public static EditText edtcodeposti;
    public static EditText edtaddress;

    public static Spinner spnershahr;
    public static Spinner spnerostan;
    public static Button btnsave;


    public static     Button btnselect;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        edtname = (EditText) findViewById(R.id.edtname);
        edthomenumber = (EditText) findViewById(R.id.edthomenumber);
        edtphonenumber = (EditText) findViewById(R.id.edtphonenumber);
        edtcodeposti = (EditText) findViewById(R.id.edtcodeposti);
        edtaddress = (EditText) findViewById(R.id.edtaddress);
        spnershahr = (Spinner) findViewById(R.id.spnershahr);
        spnerostan = (Spinner) findViewById(R.id.spnerostan);
        btnsave = (Button) findViewById(R.id.btnsave);
        RecyclerView List = (RecyclerView) findViewById(R.id.List);
        mdatasetList = new ArrayList<AddressStructure>();

        btnselect = (Button) findViewById(R.id.btnselect);

        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(G.onClickListenersearch);
        shoplogo.setOnClickListener(G.onClickListenersabadkharid);

        spnershahr.setEnabled(false);
        spnershahr.setClickable(false);
        spnerostan.setEnabled(false);
        spnerostan.setClickable(false);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            if (extras.containsKey("Select") && extras.getString("Select") != null && extras.getString("Select").equals("True")) {


                btnselect.setVisibility(View.VISIBLE);

                btnselect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(AddressAdapter.colors.indexOf(Color.TRANSPARENT) == -1){
                            Toast.makeText(G.context,"لطفا آدرسی را انتخاب کنید" , Toast.LENGTH_LONG).show();
                        }else {
                            Paymentstep.Address = mdatasetList.get(AddressAdapter.colors.indexOf(Color.TRANSPARENT));
                            finish();

                        }

                    }
                });
            }

        }


        List.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        List.setLayoutManager(LayoutManagerList);
        AdapterList = new AddressAdapter(mdatasetList , colors);
        List.setAdapter(AdapterList);

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.ostanha, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnerostan.setAdapter(adapter);


        spnerostan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinershahrha();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btnsave.getText().toString().equals("اضافه کردن آدرس جدید")) {
                    if(mdatasetList.size() < 5) {
                        if(edtname.getText().toString().length() > 0 && edthomenumber.getText().toString().length() > 0
                        && edtphonenumber.getText().toString().length() > 0 && edtaddress.getText().toString().length() > 0){

                            if(edtcodeposti.getText().toString().length() == 0){
                                edtcodeposti.setText("0");
                            }

                            btnsaveaction("-1");
                            btnselect.setBackgroundColor(Color.parseColor("#D6D7D7"));
                        }else{
                            Toast.makeText(G.context,"اطلاعات را به صورت کامل و درست وارد کنید !" , Toast.LENGTH_LONG).show();
                        }


                    }else{
                        Toast.makeText(G.context,"حداکثر تعداد آدرس 5 عدد میباشد" , Toast.LENGTH_LONG).show();


                        ActivityAddress.edtaddress.setText("");
                        ActivityAddress.edtcodeposti.setText("");
                        ActivityAddress.edtname.setText("");
                        ActivityAddress.edthomenumber.setText("");
                        ActivityAddress.edtphonenumber.setText("");

                        ActivityAddress.spnerostan.setSelection(0);

                        spinershahrha();

                        ActivityAddress.btnsave.setText("اضافه کردن آدرس جدید");
                        AddressAdapter.updateid = null;
                        btnselect.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    }
                } else {
                    btnselect.setBackgroundColor(Color.parseColor("#D6D7D7"));
                    btnsaveaction(AddressAdapter.updateid);

                }



            }
        });

        pagework();
    }

    public static void pagework() {


        btnsave.setText("اضافه کردن آدرس جدید");
        edtname.setText("");
        edthomenumber.setText("");
        edtphonenumber.setText("");
        edtcodeposti.setText("");
        edtaddress.setText("");


        Webservice.requestparameter param = new Webservice.requestparameter();
        param.key = "Connectioncode";
        param.value = G.Connectioncode;
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param);
        Webservice.request("Store.php?action=address", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                mdatasetList.clear();
                colors.clear();

                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        AdapterList.notifyDataSetChanged();
                    }
                });

                String input = response.body().string();
                if (input != null && !input.equals("[]")) {
                    try {
                        JSONArray temparray = new JSONArray(input);
                        for (int i = 0; i < temparray.length(); i++) {
                            JSONObject object = temparray.getJSONObject(i);
                            AddressStructure address = new AddressStructure();
                            address.Name = object.getString("Name");
                            address.Homenumber = object.getString("Homenumber");
                            address.Phonenumber = object.getString("Phonenumber");
                            address.Ostan = object.getString("Ostan");
                            address.Shahr = object.getString("Shahr");
                            address.Codeposti = object.getString("Codeposti");
                            address.Address = object.getString("Address");
                            address.Id = object.getString("Id");
                            mdatasetList.add(address);
                        }
                        for (int i = 0; i < mdatasetList.size(); i++) {
                            colors.add(Color.WHITE);
                        }

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                AdapterList.notifyDataSetChanged();
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, array);

    }

    public static ArrayList<Webservice.requestparameter> data(String updateid) {
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();


        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "Name";
        param1.value = edtname.getText() + "";
        array.add(param1);

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "Homenumber";
        param2.value = edthomenumber.getText() + "";
        array.add(param2);

        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "Phonenumber";
        param3.value = edtphonenumber.getText() + "";
        array.add(param3);

        Webservice.requestparameter param4 = new Webservice.requestparameter();
        param4.key = "Ostan";
        param4.value = spnerostan.getSelectedItem().toString();
        array.add(param4);

        Webservice.requestparameter param5 = new Webservice.requestparameter();
        param5.key = "Shahr";
        param5.value = spnershahr.getSelectedItem().toString();
        array.add(param5);

        Webservice.requestparameter param6 = new Webservice.requestparameter();
        param6.key = "Codeposti";
        param6.value = edtcodeposti.getText() + "";
        array.add(param6);
        Webservice.requestparameter param7 = new Webservice.requestparameter();
        param7.key = "Address";
        param7.value = edtaddress.getText() + "";
        array.add(param7);

        Webservice.requestparameter param8 = new Webservice.requestparameter();
        param8.key = "Connectioncode";
        param8.value = G.Connectioncode;
        array.add(param8);

        if (updateid != null && !updateid.equals("-1")) {
            Webservice.requestparameter param9 = new Webservice.requestparameter();
            param9.key = "Id";
            param9.value = updateid + "";
            array.add(param9);
        }

        return array;
    }

    public static void btnsaveaction(final String updateid) {


        ArrayList<Webservice.requestparameter> array = data(updateid);


        Webservice.request("Store.php?action=addaddress", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                Log.i("LOG", "input =" + input);
                if (input != null && !input.equals("[]")) {
                    if (input.equals("Ok")) {

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                if (updateid != null && !updateid.equals("-1")) {


                                    Toast.makeText(G.context, "آدرس شما تغییر کرد", Toast.LENGTH_LONG).show();

                                } else {
                                    Toast.makeText(G.context, "آدرس اضافه شد", Toast.LENGTH_LONG).show();

                                }

                                pagework();
                            }
                        });
                    }

                }

            }
        }, array);


    }
    public static ArrayAdapter<CharSequence> adapter;
    public static void spinershahrha(){
        String text = spnerostan.getSelectedItem().toString();


        if (text.equals("اصفهان")) {
            adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.esfahan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("آذربایجان\u200Cشرقی")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.azarbaijansharghi, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("آذربایجان\u200Cغربی")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.azarbaijangharbi, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("اردبیل")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.ardebil, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("البرز")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.alborz, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("ایلام")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.eilam, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("بوشهر")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.boshehr, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("تهران")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.tehran, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("چهارمحال\u200Cو\u200Cبختیاری")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.charmahalbakhtiyari, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("خراسان\u200Cجنوبی")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.khorasanjonobi, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("خراسان\u200Cرضوی")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.khorasanrazavi, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("خراسان\u200Cشمالی")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.khorasanshomali, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("خوزستان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.khozestan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("زنجان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.zanjan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("سمنان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.semnan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("سیستان\u200Cو\u200Cبلوچستان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.sistanvabalochestan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("فارس")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.fars, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("قزوین")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.ghazvin, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("قم")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.ghom, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("كردستان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.kordestan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("كرمان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.kerman, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("كرمانشاه")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.kermanshah, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("کهگیلویه\u200Cو\u200Cبویراحمد")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.kohkiloyevaboyerahmad, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("گلستان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.golestan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("گیلان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.gilan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("لرستان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.lorstan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("مازندران")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.mazandaran, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("مركزی")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.markazi, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);
        } else if (text.equals("هرمزگان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.hormozgan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);

        } else if (text.equals("همدان")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.hamedan, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);

        } else if (text.equals("یزد")) {
             adapter = ArrayAdapter.createFromResource(G.CurrentActivity, R.array.yazd, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnershahr.setAdapter(adapter);

        }

    }
}
