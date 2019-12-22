package com.cheemarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityResetPassword extends AppCompatActivity {

    private AutoCompleteTextView password1;
    private EditText password2;
    private TextView btn;
    private TextView error;
    String username;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        password1 = (AutoCompleteTextView) findViewById(R.id.username);
        password2 = (EditText) findViewById(R.id.password);
        btn = (TextView) findViewById(R.id.btn);
        error = (TextView) findViewById(R.id.error);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            username = extras.getString("username");
            token = extras.getString("token");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (password1.getText().toString().length() <= 4) {
                    Toast.makeText(G.CurrentActivity, "رمز عبور و تکرار رمز عبور یکسان نمی باشد", Toast.LENGTH_LONG).show();
                }else if (password1.getText().toString().equals(password2.getText().toString()) && password1.getText().toString().length() > 4) {
                    resetpassword();
                } else {
                    Toast.makeText(G.CurrentActivity, "رمز عبور و تکرار رمز عبور یکسان نمی باشد", Toast.LENGTH_LONG).show();
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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

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
                                error.setText("عملیات موفقیت آمیز نبود");
                            }
                        });

                    } else if (obj.getString("status").equals("notexist")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                error.setText("اکانت با این مشخصات وجود ندارد");
                            }
                        });

                    } else if (obj.getString("status").equals("ban")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                error.setText("اکانت شما محدود شده است");
                            }
                        });

                    } else if (obj.getString("status").equals("expired")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
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
}
