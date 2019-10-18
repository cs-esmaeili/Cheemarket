package com.cheemarket;

import android.content.Intent;
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

        boolean cancel = false;
        View focusView = null;


        if (!TextUtils.isEmpty(passwordtemp) && !isPasswordValid(passwordtemp)) {
            message.setText(getString(R.string.error_invalid_password));

            focusView = this.password;
            cancel = true;
        } else if (TextUtils.isEmpty(passwordtemp)) {

            if (!message.getText().toString().contains("\n" + "قسمت رمز ورود خالی است"))
                message.setText(message.getText().toString() + "\n" + "قسمت رمز ورود خالی است");

            focusView = username;
            cancel = true;
        }

        // Check for a valid usernametemp address.
        if (TextUtils.isEmpty(usernametemp)) {

            if (!message.getText().toString().contains("\n" + getString(R.string.error_field_required)))
                message.setText(message.getText().toString() + "\n" + getString(R.string.error_field_required));

            focusView = username;
            cancel = true;
        } else if (!isEmailValid(usernametemp)) {
            if (!message.getText().toString().contains("\n" + getString(R.string.error_invalid_email)))
                message.setText(message.getText().toString() + "\n" + getString(R.string.error_invalid_email));

            focusView = username;
            cancel = true;
        }

        if(username.getText().toString().length() < 5){
            if (!message.getText().toString().contains("\n" + getString(R.string.error_invalid_email)))
                message.setText(message.getText().toString() + "\n" + "رمز عبور شما باید از 4 کارکتر بیشتر باشد");

            focusView = username;
            cancel = true;
        }


        if (cancel) {

            focusView.requestFocus();
        } else {


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
                            intent.putExtra("username", username.getText().toString());
                            intent.putExtra("password", ActivitySabtnam.this.password.getText().toString());

                            if ((username.getText().toString().contains("09") || username.getText().toString().contains("۰۹") )&& username.getText().toString().length() == 11) {
                                intent.putExtra("type", "phonenumber");
                                G.CurrentActivity.startActivity(intent);
                                finish();
                            } else if (username.getText().toString().contains("@gmail.com") || username.getText().toString().contains("@yahoo.com")) {
                                intent.putExtra("type", "username");
                                G.CurrentActivity.startActivity(intent);
                                finish();
                            }


                        } else if (obj.has("status") && obj.getString("status").equals("ok")) {

                            Intent intent = new Intent(G.CurrentActivity, ActivityEnterCode.class);
                            intent.putExtra("m", 1);
                            intent.putExtra("s", 60);
                            intent.putExtra("username", username.getText().toString());
                            intent.putExtra("password", ActivitySabtnam.this.password.getText().toString());
                            if ((username.getText().toString().contains("09") || username.getText().toString().contains("۰۹") )&& username.getText().toString().length() == 11) {
                                intent.putExtra("type", "phonenumber");
                                G.CurrentActivity.startActivity(intent);
                                finish();
                            } else if (username.getText().toString().contains("@gmail.com") || username.getText().toString().contains("@yahoo.com")) {
                                intent.putExtra("type", "username");
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
                        } else if (obj.has("status") && obj.getString("status").equals("Account available")) {
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    message.setText("اکانتی با این مشخصات وجود دارد");
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


    private boolean isEmailValid(String email) {
        if (email.contains("09") && email.length() == 11 || email.contains("۰۹") && email.length() == 11) {
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

