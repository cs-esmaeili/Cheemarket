package com.cheemarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


import com.cheemarket.Customview.badgelogo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cheemarket.Start.pre;


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
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

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
                attemptLogin();
            }
        });

        sabtname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity,ActivitySabtnam.class);
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
                    recovery(mEmailView.getText().toString() , "recoveryemail");

                } else if (mEmailView.getText().toString().contains("09") && mEmailView.getText().toString().length() == 11){

                    recovery(mEmailView.getText().toString() , "recoveryphone");
                }else {
                    message.setText(getString(R.string.error_invalid_email));
                }
            }
        });
    }

    private void recovery(final String username, final String action) {

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "username";
        param1.value = username;
        array.add(param1);

        Webservice.request("recovery.php?action=" + action, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        recovery(username,action);
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String input = response.body().string();

                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        if (input.equals("Ok")) {
                            if(action.equals("recoveryemail")){
                                message.setText("لینک بازیابی پسورد به ایمیل شما ارسال شد");
                            }else {
                                message.setText("لینک بازیابی پسورد به شماره همراه شما ارسال شد");
                            }

                        } else if (input.equals("needActivate")) {
                            message.setText("این نام کاربری فعال نیست لینک فعال سازی برای شما ارسال شده است");
                        }else  if (input.equals("activesend")){
                            message.setText("این نام کاربری فعال نیست لینک فعال سازی برای شما ارسال شد");
                        } else  {
                            message.setText("این نام کاربری وجود ندارد");
                        }
                    }
                });

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

            if(!message.getText().toString().contains("\n" +  getString(R.string.error_field_required)))
            message.setText(message.getText().toString() +  "\n" +  getString(R.string.error_field_required));
            //mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            if(!message.getText().toString().contains(  "\n" + getString(R.string.error_invalid_email)))
            message.setText(message.getText().toString() +  "\n" + getString(R.string.error_invalid_email));
            // mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            message.setText(getString(R.string.error_invalid_password));
            //  mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {

            if(!message.getText().toString().contains(  "\n"  + "قسمت رمز ورود خالی است"))
                message.setText(message.getText().toString() +  "\n"  + "قسمت رمز ورود خالی است");
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

                if(mEmailView.getText().toString().contains("@gmail.com") || mEmailView.getText().toString().contains("@yahoo.com")){
                    Webservice.requestparameter param3 = new Webservice.requestparameter();
                    param3.key = "type";
                    param3.value = "email";
                    array.add(param3);

                }else if (mEmailView.getText().toString().contains("09") && mEmailView.getText().toString().length() == 11){
                    Webservice.requestparameter param3 = new Webservice.requestparameter();
                    param3.key = "type";
                    param3.value = "phonenumber";
                    array.add(param3);

                }


                Webservice.request("Store.php?action=checkuser", new Callback() {
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


                        if (input.equals("not")) {
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    //     mEmailView.setError("نام کاربری یا رمز عبور اشتباه است");
                                    message.setText("نام کاربری یا رمز عبور اشتباه است");
                                }
                            });

                        } else if (input.equals("needActivate")) {
                              G.HANDLER.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      message.setText("لینک تایید برای شما ارسال شده است");
                                  }
                              });
                        }else if (input.equals("Activesend")) {
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    message.setText("لینک تایید برای شما ارسال شد");
                                }
                            });
                        } else {
                            try {
                                usernametemp = mEmailView.getText().toString();
                                G.Connectioncode = input;
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("Username", usernametemp);
                                editor.putString("Connectioncode", G.Connectioncode);

                                editor.apply();

                                G.CurrentActivity.finish();

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //  mEmailView.setError("مشکلی در ارتباط به وجود امد دوباره سعی کنید");
                                        message.setText("مشکلی در ارتباط به وجود امد دوباره سعی کنید");
                                    }
                                });

                            }

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

