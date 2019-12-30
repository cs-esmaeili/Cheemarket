package com.cheemarket;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ActivitySabtnam extends AppCompatActivity {

    private AutoCompleteTextView username;
    private EditText password;
    private TextView message;


    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabtnam);
        G.CurrentActivity = this;


        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setVisibility(View.GONE);
        shoplogo.setVisibility(View.GONE);


        username = (AutoCompleteTextView) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        message = (TextView) findViewById(R.id.message);


        final TextView mEmailSignInButton = (TextView) findViewById(R.id.email_sign_in_button);


        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText("");
                attemptLogin();
            }
        });

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                message.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                message.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                message.setText("");
            }
        };
        username.addTextChangedListener(watcher);
        password.addTextChangedListener(watcher);


    }


    private void attemptLogin() {


        username.setError(null);
        password.setError(null);


        String usernametemp = username.getText().toString();
        String passwordtemp = this.password.getText().toString();


        if (TextUtils.isEmpty(usernametemp)) {

            message.setText(getString(R.string.username_required));
            return;

        } else if (!isEmailValid(usernametemp) && !isPhonenumberValid(usernametemp)) {
            message.setText(getString(R.string.error_invalid_email));
            return;
        }

        if (TextUtils.isEmpty(passwordtemp)) {
            message.setText(getString(R.string.password_required));
            return;

        } else if (!isPasswordValid(passwordtemp)) {
            message.setText(getString(R.string.error_invalid_password));
            return;
        }


        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username.getText().toString();

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "password";
        param2.value = password.getText().toString();

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);


        Webservice.request("signup", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        attemptLogin();
                        return null;
                    }
                }, G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.has("remaining_time") && obj.has("status") && obj.getString("status").equals("ok")) {

                        Intent intent = new Intent(G.CurrentActivity, ActivityEnterCode.class);
                        int m = (Integer.parseInt(obj.getString("remaining_time")) / 60) >= 1 ? Integer.parseInt(obj.getString("remaining_time")) / 60 : 0;
                        int s = Integer.parseInt(obj.getString("remaining_time")) - (m * 60);
                        intent.putExtra("m", m);
                        intent.putExtra("s", s);
                        intent.putExtra("username", username.getText().toString());
                        intent.putExtra("password", ActivitySabtnam.this.password.getText().toString());

                        if (isPhonenumberValid(username.getText().toString())) {
                            intent.putExtra("type", "phonenumber");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        } else if (isEmailValid(username.getText().toString())) {
                            intent.putExtra("type", "email");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        }


                    } else if (obj.has("status") && obj.getString("status").equals("ok")) {

                        Intent intent = new Intent(G.CurrentActivity, ActivityEnterCode.class);
                        intent.putExtra("m", 1);
                        intent.putExtra("s", 60);
                        intent.putExtra("username", username.getText().toString());
                        intent.putExtra("password", ActivitySabtnam.this.password.getText().toString());
                        if (isPhonenumberValid(username.getText().toString())) {
                            intent.putExtra("type", "phonenumber");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        } else if (isEmailValid(username.getText().toString())) {
                            intent.putExtra("type", "email");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        }

                    } else if (obj.has("status") && obj.getString("status").equals("ban")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                message.setText("تعداد تلاش بیشتر از حد مجاز!");
                            }
                        });
                    } else if (obj.has("status") && obj.getString("status").equals("Account available")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                message.setText("حساب کاربری با این مشخصات وجود دارد");
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);


    }


    private boolean isEmailValid(String email) {
        if (email.contains("@") && email.substring(email.length() - 4).equals(".com") && (!email.contains("09") || !email.contains("۰۹"))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPhonenumberValid(String phonenumber) {
        if ((phonenumber.contains("09") || phonenumber.contains("۰۹")) && phonenumber.length() == 11 && (!phonenumber.contains(".com") || !phonenumber.contains("@"))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

}

