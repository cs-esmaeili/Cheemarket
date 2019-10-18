package com.cheemarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityEdite extends AppCompatActivity {

    public static EditText edtname;
    public static EditText edthomenumber;
    public static EditText edtphonenumber;
    public static EditText edtcodeposti;
    public static EditText edtaddress;

    public static Spinner spnershahr;
    public static Spinner spnerostan;
    public static TextView btnsave;

    private static int position = -1;
    public static ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite);
        G.CurrentActivity = this;

        edtname = (EditText) findViewById(R.id.edtname);
        edthomenumber = (EditText) findViewById(R.id.edthomenumber);
        edtphonenumber = (EditText) findViewById(R.id.edtphonenumber);
        edtcodeposti = (EditText) findViewById(R.id.edtcodeposti);
        edtaddress = (EditText) findViewById(R.id.edtaddress);
        spnershahr = (Spinner) findViewById(R.id.spnershahr);
        spnerostan = (Spinner) findViewById(R.id.spnerostan);
        btnsave = (TextView) findViewById(R.id.btnsave);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position");


            List<String> Lines = Arrays.asList(G.context.getResources().getStringArray(R.array.ostanha));
            for (int i = 0; i < Lines.size(); i++) {
                if (Lines.get(i).equals(ActivityAddress.address.get(position).Ostan)) {
                    spnerostan.setSelection(i);
                    spinershahrha();
                    break;
                }
            }

            setdata();

            for (int i = 0; i < adapter.getCount(); i++) {

                if (adapter.getItem(i).toString().equals(ActivityAddress.address.get(position).Shahr)) {
                    final int finalI = i;
                    G.HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            spnershahr.setSelection(finalI);
                        }
                    }, 300);

                    break;
                }
            }


        }

        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ostanha, android.R.layout.simple_spinner_item);
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
            public void onClick(View view) {
                save();
            }
        });

        spnerostan.setEnabled(false);
        spnershahr.setEnabled(false);
    }

    private void setdata() {
        edtname.setText(ActivityAddress.address.get(position).Name);
        edthomenumber.setText(ActivityAddress.address.get(position).Homenumber);
        edtphonenumber.setText(ActivityAddress.address.get(position).Phonenumber);
        edtcodeposti.setText(ActivityAddress.address.get(position).Codeposti);
        edtaddress.setText(ActivityAddress.address.get(position).Address);

        edthomenumber.setSelection(edthomenumber.getText().length());
        edtphonenumber.setSelection(edtphonenumber.getText().length());
        edtcodeposti.setSelection(edtcodeposti.getText().length());

    }


    public static void save() {


        final ArrayList<Webservice.requestparameter> array = new ArrayList<>();


        boolean temp = false;
        if(edtname.getText().length() == 0){
            edtname.setError("نام و نام خانوادگی را وارد کنید");
            temp = true;
        }
        if(edthomenumber.getText().length() == 0){
            edthomenumber.setError("شماره ثابت را وارد کنید");
            temp = true;
        }else if(edthomenumber.getText().length() < 11){
            edthomenumber.setError("شماره ثابت صحیح نمی باشد");
            temp = true;
        }

        if(edtphonenumber.getText().length() == 0){
            edtphonenumber.setError("شماره همراه را وارد کنید");
            temp = true;
        }else if(edtphonenumber.getText().length() < 11){
            edtphonenumber.setError("شماره همراه کوتاه است");
            temp = true;
        }else if(!edtphonenumber.getText().toString().contains("09") && !edtphonenumber.getText().toString().contains("۰۹")){
            edtphonenumber.setError("شماره همراه صحیح نمی باشد");
            temp = true;
        }

        if(edtaddress.getText().length() == 0){
            edtaddress.setError("آدرس  را وارد کنید");
            temp = true;
        }
        if(temp){
            return;
        }
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "name";
        param1.value = edtname.getText() + "";
        array.add(param1);

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "home_number";
        param2.value = edthomenumber.getText() + "";
        array.add(param2);

        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "phone_number";
        param3.value = edtphonenumber.getText() + "";
        array.add(param3);

        Webservice.requestparameter param4 = new Webservice.requestparameter();
        param4.key = "state";
        param4.value = spnerostan.getSelectedItem().toString();
        array.add(param4);

        Webservice.requestparameter param5 = new Webservice.requestparameter();
        param5.key = "city";
        param5.value = spnershahr.getSelectedItem().toString();
        array.add(param5);

        Webservice.requestparameter param6 = new Webservice.requestparameter();
        param6.key = "postal_code";
        param6.value = (edtcodeposti.getText().length() == 0)? "0" : edtcodeposti.getText() + "";
        array.add(param6);

        Webservice.requestparameter param7 = new Webservice.requestparameter();
        param7.key = "address";
        param7.value = edtaddress.getText() + "";
        array.add(param7);

        Webservice.requestparameter param8 = new Webservice.requestparameter();
        param8.key = "token";
        param8.value = G.token;
        array.add(param8);

        if(position != -1){
            Webservice.requestparameter param9 = new Webservice.requestparameter();
            param9.key = "user_address_id";
            param9.value = ActivityAddress.address.get(position).Id;
            array.add(param9);
        }
;



        Webservice.request( (position == -1)? "addressAdd" : "addressEdite", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.has("status") && obj.getString("status").equals("ok")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.CurrentActivity,"عملیات با موفقیت انجام شد",Toast.LENGTH_LONG).show();
                                ActivityAddress.needtorealod = true;
                                G.CurrentActivity.finish();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, array);

    }


    public static void spinershahrha() {
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
