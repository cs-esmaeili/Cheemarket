package com.cheemarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cheemarket.ActivitySabad.check_card_server;
import static com.cheemarket.G.pre;

public class ActivityResetPassword extends AppCompatActivity {

    private AutoCompleteTextView password1;
    private EditText password2;
    private TextView btn;
    private TextView error;
    String username;
    String token;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        G.CurrentActivity = this;
        password1 = (AutoCompleteTextView) findViewById(R.id.username);
        password2 = (EditText) findViewById(R.id.password);
        btn = (TextView) findViewById(R.id.btn);
        error = (TextView) findViewById(R.id.error);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            token = extras.getString("token");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                error.setText("");
                btn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                if (!password1.getText().toString().equals(password2.getText().toString())) {
                    Toast.makeText(G.CurrentActivity, "رمز ورود و تکرار رمز عبور یکسان نمی باشد", Toast.LENGTH_LONG).show();
                } else if ((password1.getText().toString().length() <= 4) || (password1.getText().toString().length() <= 4)) {
                    Toast.makeText(G.CurrentActivity, "رمز ورود باید بیشتر از 4 کارکتر باشد", Toast.LENGTH_LONG).show();
                } else {
                    resetpassword();
                }
            }
        });
    }

    private void resetpassword() {

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "token";
        param2.value = token;

        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "password";
        param3.value = password1.getText().toString();


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);
        array.add(param3);

        Webservice.request("resetPasswordAction", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                resetpassword();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                Log.i("LOG", input);
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.getString("status").equals("ok")) {
                        mini_login(username, password1.getText().toString());

                    } else if (obj.getString("status").equals("fail")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                error.setText("عملیات موفقیت آمیز نبود");
                            }
                        });

                    } else if (obj.getString("status").equals("notexist")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                error.setText("اکانت با این مشخصات وجود ندارد");
                            }
                        });

                    } else if (obj.getString("status").equals("ban")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                error.setText("اکانت شما محدود شده است");
                            }
                        });

                    } else if (obj.getString("status").equals("expired")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                btn.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                error.setText("کد تایید منقضی شده است");
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, array);
    }

    private void mini_login(final String strusername, final String strpassword) {
        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = strusername;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "password";
        param2.value = strpassword;

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);


        Webservice.request("login", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        mini_login(strusername, strpassword);
                        return null;
                    }
                }, G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.has("status") && obj.getString("status").equals("ok") && obj.has("token")) {
                        G.token = obj.getString("token");
                        SharedPreferences.Editor editor = pre.edit();
                        editor.putString("Username", strusername);
                        editor.putString("token", G.token);
                        editor.apply();
                        check_card_server();
                        Commands.setbadgenumber(ActivityMain.badge);
                        G.CurrentActivity.finish();
                    } else {
                        Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                        G.CurrentActivity.startActivity(intent);
                        G.CurrentActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);
    }
}
