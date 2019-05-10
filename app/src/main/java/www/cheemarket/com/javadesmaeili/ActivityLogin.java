package www.cheemarket.com.javadesmaeili;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;


import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ActivityLogin extends AppCompatActivity {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private TextView resetpass;
    private TextView sabtname;
    private boolean s = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        G.CurrentActivity = this;

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        resetpass = (TextView) findViewById(R.id.resetpass);
        sabtname = (TextView) findViewById(R.id.sabtname);


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
                if(!s){
                    sabtname.setText("ورود به چی مارکت");
                    sabtname.setTextColor(Color.parseColor("#1E88E5"));
                    mEmailSignInButton.setText("ثبت نام در چی مارکت");
                    mEmailSignInButton.setBackgroundColor(Color.parseColor("#66BB6A"));

                }else {
                    sabtname.setText("ثبت نام در چی مارکت");
                    sabtname.setTextColor(Color.parseColor("#66BB6A"));
                    mEmailSignInButton.setText("ورود به چی مارکت");
                    mEmailSignInButton.setBackgroundColor(Color.parseColor("#1E88E5"));
                }

                s = !s;

            }
        });


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
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            if (s) {




                Webservice.requestparameter param1 = new Webservice.requestparameter();
                param1.key = "username";
                param1.value = mEmailView.getText().toString();

                Webservice.requestparameter param2 = new Webservice.requestparameter();
                param2.key = "password";
                param2.value = mPasswordView.getText().toString();



                ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                array.add(param1);
                array.add(param2);


                Webservice.request("Store.php?action=adduser", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String input = response.body().string();

                        if (input.equals("not")) {
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    mEmailView.setError("این نام کاربری وجود دارد");

                                }
                            });

                        } else {


                            try {

                                usernametemp = mEmailView.getText().toString();
                                G.Connectioncode = input;
                                SharedPreferences.Editor editor = ActivityMain.pre.edit();
                                editor.putString("Username", usernametemp);
                                editor.putString("Connectioncode", G.Connectioncode);
                                editor.apply();

                                G.CurrentActivity.finish();

                            } catch (NumberFormatException e) {
                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mEmailView.setError("مشکلی در ساخت اکانت به وجود آمد لطفا دوباره سعی کنید");
                                    }
                                });

                                e.printStackTrace();
                            }


                        }

                    }
                },array);

            }else{



                Webservice.requestparameter param1 = new Webservice.requestparameter();
                param1.key = "username";
                param1.value = mEmailView.getText().toString();

                Webservice.requestparameter param2 = new Webservice.requestparameter();
                param2.key = "password";
                param2.value = mPasswordView.getText().toString();

                ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                array.add(param1);
                array.add(param2);


                Webservice.request("Store.php?action=checkuser", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String input = response.body().string();


                        if(input.equals("not")){
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    mEmailView.setError("نام کاربری یا رمز عبور اشتباه است");
                                }
                            });

                        }else {
                            try {
                                usernametemp = mEmailView.getText().toString();
                                G.Connectioncode=input;
                                SharedPreferences.Editor editor = ActivityMain.pre.edit();
                                editor.putString("Username", usernametemp);
                                editor.putString("Connectioncode",  G.Connectioncode);

                                editor.apply();

                                G.CurrentActivity.finish();

                            }catch (NumberFormatException e){
                                e.printStackTrace();
                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mEmailView.setError("مشکلی در ارتباط به وجود امد دوباره سعی کنید");
                                    }
                                });

                            }

                        }


                    }
                },array);


            }

        }
    }



    private boolean isEmailValid(String email) {
       if(email.contains("09")){
           return true;
       }else if(email.contains("@") && email.contains(".com")){
           return true;
       }else {
           return false;
       }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }



}

