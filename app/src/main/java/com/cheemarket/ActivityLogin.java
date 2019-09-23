package com.cheemarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cheemarket.G.pre;


public class ActivityLogin extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView resetpass;
    private TextView sabtname;
    private TextView message;


    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        G.CurrentActivity = this;

        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setVisibility(View.GONE);
        shoplogo.setVisibility(View.GONE);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.password1);
        mPasswordView = (EditText) findViewById(R.id.password2);

        resetpass = (TextView) findViewById(R.id.resetpass);
        sabtname = (TextView) findViewById(R.id.sabtname);
        message = (TextView) findViewById(R.id.message);


        final TextView mEmailSignInButton = (TextView) findViewById(R.id.email_sign_in_button);


        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
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

        sabtname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity, ActivitySabtnam.class);
                G.CurrentActivity.startActivity(intent);
                finish();
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
        mEmailView.addTextChangedListener(watcher);
        mPasswordView.addTextChangedListener(watcher);

        resetpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setText("");
                if (mEmailView.getText().toString().contains("@gmail.com") || mEmailView.getText().toString().contains("@yahoo.com")) {
                    message.setText("");
                    recovery(mEmailView.getText().toString());

                } else if (mEmailView.getText().toString().contains("09") && mEmailView.getText().toString().length() == 11) {

                    recovery(mEmailView.getText().toString());
                } else {
                    message.setText(getString(R.string.error_invalid_email));
                }
            }
        });
    }

    private void recovery(final String usernam) {

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = mEmailView.getText().toString();


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);


        Webservice.request("resetPassword", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        attemptLogin();
                        return null;
                    }
                });
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
                        intent.putExtra("username", mEmailView.getText().toString());
                        intent.putExtra("password", "reset");

                        if (mEmailView.getText().toString().contains("09") && mEmailView.getText().toString().length() == 11) {
                            intent.putExtra("type", "phonenumber");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        } else if (mEmailView.getText().toString().contains("@gmail.com") || mEmailView.getText().toString().contains("@yahoo.com")) {
                            intent.putExtra("type", "email");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        }


                    } else if (obj.has("status") && obj.getString("status").equals("ok")) {
                        Intent intent = new Intent(G.CurrentActivity, ActivityEnterCode.class);
                        intent.putExtra("m", 1);
                        intent.putExtra("s", 60);
                        intent.putExtra("username", mEmailView.getText().toString());
                        intent.putExtra("password", "reset");
                        if (mEmailView.getText().toString().contains("09") && mEmailView.getText().toString().length() == 11) {
                            intent.putExtra("type", "phonenumber");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        } else if (mEmailView.getText().toString().contains("@gmail.com") || mEmailView.getText().toString().contains("@yahoo.com")) {
                            intent.putExtra("type", "email");
                            G.CurrentActivity.startActivity(intent);
                            finish();
                        }

                    } else if (obj.has("status") && obj.getString("status").equals("ban")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                message.setText("تعداد تلاش بیتشر از حد مجاز!");

                            }
                        });
                    } else if (obj.has("status") && obj.getString("status").equals("notexist")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                message.setText("نام کاربری یا رمز عبور اشتباه است");
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);
    }

    String usernametemp = "";

    private void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {

            if (!message.getText().toString().contains("\n" + getString(R.string.error_field_required)))
                message.setText(message.getText().toString() + "\n" + getString(R.string.error_field_required));
            //mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            if (!message.getText().toString().contains("\n" + getString(R.string.error_invalid_email)))
                message.setText(message.getText().toString() + "\n" + getString(R.string.error_invalid_email));
            // mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            message.setText(getString(R.string.error_invalid_password));
            //  mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {

            if (!message.getText().toString().contains("\n" + "قسمت رمز ورود خالی است"))
                message.setText(message.getText().toString() + "\n" + "قسمت رمز ورود خالی است");
            //mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            Webservice.requestparameter param1 = new Webservice.requestparameter();
            param1.key = "username";
            param1.value = mEmailView.getText().toString();

            Webservice.requestparameter param2 = new Webservice.requestparameter();
            param2.key = "password";
            param2.value = mPasswordView.getText().toString();

            ArrayList<Webservice.requestparameter> array = new ArrayList<>();
            array.add(param1);
            array.add(param2);


            Webservice.request("login", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Webservice.handelerro(e, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            attemptLogin();
                            return null;
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String input = response.body().string();
                    try {
                        JSONObject obj = new JSONObject(input);
                        if (obj.has("status") && obj.getString("status").equals("ok") && obj.has("token")) {

                            usernametemp = mEmailView.getText().toString();
                            G.token = obj.getString("token");
                            SharedPreferences.Editor editor = pre.edit();
                            editor.putString("Username", usernametemp);
                            editor.putString("token", G.token);
                            editor.apply();
                            G.CurrentActivity.finish();

                        } else if (obj.has("status") && obj.getString("status").equals("ban")) {
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    message.setText("اکانت شما محدود است با پشتیبانی تماس بگیرید");
                                }
                            });
                        } else if (obj.has("status") && obj.getString("status").equals("notexist")) {
                            message.setText("نام کاربری یا رمز عبور اشتباه است");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, array);


        }
    }


    private boolean isEmailValid(String email) {
        if (email.contains("09") && email.length() == 11) {
            return true;
        } else if (email.contains("@gmail.com") || email.contains("@yahoo.com")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }


}

