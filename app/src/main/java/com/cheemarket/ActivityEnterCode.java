package com.cheemarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityEnterCode extends AppCompatActivity {

    TextView m;
    TextView s;
    TextView txt;
    TextView txtretry;
    int daghighe;
    int saniye;
    String type;
    String username;
    String password;

    EditText edt1;
    EditText edt2;
    EditText edt3;
    EditText edt4;
    EditText edt5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        G.CurrentActivity = this;

        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);


        m = (TextView) findViewById(R.id.m);
        s = (TextView) findViewById(R.id.s);
        txt = (TextView) findViewById(R.id.txt);
        txtretry = (TextView) findViewById(R.id.txtretry);
        edt1 = (EditText) findViewById(R.id.edt1);
        edt2 = (EditText) findViewById(R.id.edt2);
        edt3 = (EditText) findViewById(R.id.edt3);
        edt4 = (EditText) findViewById(R.id.edt4);
        edt5 = (EditText) findViewById(R.id.edt5);

        shoplogo.setVisibility(View.GONE);
        searchlogo.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            daghighe = extras.getInt("m");
            saniye = extras.getInt("s");
            type = extras.getString("type");
            username = extras.getString("username");
            password = extras.getString("password");
            if (type.equals("phonenumber")) {
                txt.setText("کد تایید به شماره " + username + " ارسال شد");
            } else if (type.equals("email")) {
                txt.setText("کد تایید به ایمیل " + username + " ارسال شد");
            }
            if (password.equals("reset")) {
                setTime();
                setCodereset();
            } else {
                setTime();
                setCode();
            }

        }

        txtretry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtretry.setVisibility(View.INVISIBLE);
                edt1.setText("");
                edt2.setText("");
                edt3.setText("");
                edt4.setText("");
                edt5.setText("");
                edt1.requestFocus();
                if (password.equals("reset")) {

                    resetagain();
                } else {
                    signupagain();
                }

            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtretry.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (edt5.hasFocus() && edt5.getText().toString().length() == 0) {
                    edt4.requestFocus();
                } else if (edt4.hasFocus() && edt4.getText().toString().length() == 0) {
                    edt3.requestFocus();
                } else if (edt3.hasFocus() && edt3.getText().toString().length() == 0) {
                    edt2.requestFocus();
                } else if (edt2.hasFocus() && edt2.getText().toString().length() == 0) {
                    edt1.requestFocus();
                } else if (edt1.hasFocus() && edt1.getText().toString().length() == 0) {

                } else {
                    if (edt1.hasFocus()) {
                        edt2.requestFocus();
                    } else if (edt2.hasFocus()) {
                        edt3.requestFocus();
                    } else if (edt3.hasFocus()) {
                        edt4.requestFocus();
                    } else if (edt4.hasFocus()) {
                        edt5.requestFocus();
                    }

                }

                if (edt5.getText().toString().length() == 1 && edt4.getText().toString().length() == 1 && edt3.getText().toString().length() == 1 && edt2.getText().toString().length() == 1 && edt1.getText().toString().length() == 1) {
                    if (password.equals("reset")) {
                        verifyreset(edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString());
                    } else {
                        verify(edt1.getText().toString() + edt2.getText().toString() + edt3.getText().toString() + edt4.getText().toString() + edt5.getText().toString());
                    }

                }

            }
        };

        edt1.addTextChangedListener(watcher);
        edt2.addTextChangedListener(watcher);
        edt3.addTextChangedListener(watcher);
        edt4.addTextChangedListener(watcher);
        edt5.addTextChangedListener(watcher);


    }

    private void verify(String token) {

        Log.i("LOG", "verify");
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "token";
        param2.value = token;

        Log.i("LOG", username);
        Log.i("LOG", token);

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);

        Webservice.request("signupVerify", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                Log.i("LOG", "verify=" + input);
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.getString("status").equals("ok")) {
                        Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                        G.CurrentActivity.startActivity(intent);
                        G.CurrentActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    } else if (obj.getString("status").equals("fail")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                //  txtretry.setText("کد تایید اشتباه است");
                                //   txtretry.setVisibility(View.VISIBLE);
                                Toast.makeText(G.CurrentActivity, "کد تایید اشتباه است", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else if (obj.getString("status").equals("expired")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.CurrentActivity, "کد تایید منقضی شده است", Toast.LENGTH_LONG).show();
                                //  txtretry.setText("کد تایید منقضی شده است");
                                //  txtretry.setVisibility(View.VISIBLE);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, array);
    }

    private void setCode() {

        Log.i("LOG", "username=" + username);
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);


        Webservice.request("signupCodeSender", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        }, array);
    }

    private void setTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    while (true) {

                        if (daghighe == 0 && saniye == 0) {

                            break;
                        }
                        Thread.sleep(1000);
                        if (saniye > 0) {
                            saniye--;
                        } else if (daghighe > 0) {
                            daghighe--;
                            saniye = 60;
                        }

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {


                                m.setTypeface(Textconfig.gettypeface());
                                s.setTypeface(Textconfig.gettypeface());

                                m.setText(daghighe + "");
                                s.setText(saniye + "");

                                if (daghighe == 0 && saniye == 0) {
                                    txtretry.setVisibility(View.VISIBLE);
                                }
                            }
                        });


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void signupagain() {
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "password";
        param2.value = password;

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);


        Webservice.request("signup", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.has("status") && obj.getString("status").equals("ok")) {
                        daghighe = 1;
                        saniye = 60;
                        setTime();
                        setCode();
                    } else if (obj.has("status") && obj.getString("status").equals("ban")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.CurrentActivity, "تعداد تلاش بیتشر از حد مجاز!", Toast.LENGTH_LONG).show();
                                txtretry.setClickable(false);
                            }
                        });

                    } else if (obj.has("status") && obj.getString("status").equals("Account available")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.CurrentActivity, "اکانتی با این مشخصات وجود دارد", Toast.LENGTH_LONG).show();
                                txtretry.setClickable(false);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);
    }


    private void verifyreset(String token) {
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "token";
        param2.value = token;


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);

        Webservice.request("resetPasswordVerify", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.getString("status").equals("ok")) {
                        Intent intent = new Intent(G.CurrentActivity, ActivityResetPassword.class);
                        intent.putExtra("username", username);
                        intent.putExtra("token", obj.getString("token"));
                        G.CurrentActivity.startActivity(intent);
                        G.CurrentActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    } else if (obj.getString("status").equals("fail")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                txtretry.setVisibility(View.VISIBLE);
                            }
                        });

                    } else if (obj.getString("status").equals("notexist")) {
                        txtretry.setText("اکانت با این مشخصات وجود ندارد");
                        txtretry.setVisibility(View.VISIBLE);
                    } else if (obj.getString("status").equals("ban")) {
                        txtretry.setText("تعداد تلاش بیتشر از حد مجاز!");
                        txtretry.setVisibility(View.VISIBLE);
                    } else if (obj.getString("status").equals("expired")) {
                        txtretry.setText("کد تایید شما منقضی شده است");
                        txtretry.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, array);
    }

    private void setCodereset() {

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);


        Webservice.request("resetPasswordCodeSender", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        }, array);
    }

    private void resetagain() {
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);


        Webservice.request("resetPassword", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.has("status") && obj.getString("status").equals("ok")) {
                        daghighe = 1;
                        saniye = 60;
                        setTime();
                        setCode();
                    } else if (obj.has("status") && obj.getString("status").equals("ban")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.CurrentActivity, "تعداد تلاش بیتشر از حد مجاز!", Toast.LENGTH_LONG).show();
                                txtretry.setClickable(false);
                            }
                        });

                    } else if (obj.has("status") && obj.getString("status").equals("notexist")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.CurrentActivity, "اکانت با این مشخصات وجود ندارد", Toast.LENGTH_LONG).show();
                                txtretry.setClickable(false);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);
    }
}
