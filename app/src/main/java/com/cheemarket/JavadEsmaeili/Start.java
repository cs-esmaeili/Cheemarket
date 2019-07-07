package com.cheemarket.JavadEsmaeili;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.SocketTimeoutException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.cheemarket.JavadEsmaeili.Customview.Dialogs;

public class Start extends AppCompatActivity {

    public static Uri appLinkData;
    public static SharedPreferences pre;
    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        G.CurrentActivity = this;


        pre = getSharedPreferences("Cheemarket", MODE_PRIVATE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        G.IMAGES_HEIGHT = (int) (Double.parseDouble(displayMetrics.heightPixels + "") / 1.5);
        G.IMAGES_WIDTH = (int) (Double.parseDouble(displayMetrics.widthPixels + "") / 1.5);

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        appLinkData = appLinkIntent.getData();


        check_Atelae();


    }

    private void check_Atelae() {
        Webservice.request("message.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (e instanceof SocketTimeoutException) {
                    e.printStackTrace();
                    Webservice.handelerro("timeout");
                } else {
                    e.printStackTrace();
                    Webservice.handelerro(null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (!input.equals("[]")) {
                    try {
                        JSONObject object = new JSONObject(input);



                        if(pre.contains("messageid") && !pre.getString("messageid", "Error").equals("Error") ){
                            if(pre.getString("messageid", "Error").equals(object.getString("messageid"))){
                                checkrunword();
                                return;
                            }
                        }


                        if (object.getString("type").endsWith("update")) {
                            if(G.VERSIONNAME .equals(object.getString("VERSIONNAME"))){
                                checkrunword();
                                return;
                            }else {
                                if(object.getString("cancansel").endsWith("yes")){

                                    if(object.getString("save").endsWith("yes")){
                                        SharedPreferences.Editor editor = pre.edit();
                                        editor.putString("messageid", object.getString("messageid"));
                                        editor.apply();
                                    }


                                    Dialogs.message(true,object.getString("btntext"),object.getString("matn") ,object.getString("Image"),object.getString("url"));

                                    //checkrunword();

                                }else  if(object.getString("cancansel").endsWith("no")) {
                                    Dialogs.message(false,object.getString("btntext"),object.getString("matn") ,object.getString("Image"),object.getString("url"));
                                }
                            }




                        }else {

                            if(object.getString("cancansel").endsWith("yes")){

                                if(object.getString("save").endsWith("yes")) {
                                    SharedPreferences.Editor editor = pre.edit();
                                    editor.putString("messageid", object.getString("messageid"));
                                    editor.apply();
                                }

                                Dialogs.message(true,object.getString("btntext"),object.getString("matn") ,object.getString("Image"),object.getString("url"));

                               // checkrunword();

                            }else  if(object.getString("cancansel").endsWith("no")) {
                                Dialogs.message(false,object.getString("btntext"),object.getString("matn") ,object.getString("Image"),object.getString("url"));
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null);

    }


    public static void checkrunword() {

        Webservice.request("server.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                if (e instanceof SocketTimeoutException) {
                    e.printStackTrace();
                    Webservice.handelerro("timeout");
                } else {
                    e.printStackTrace();
                    Webservice.handelerro(null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {
                    if (response.body().string().equals("run")) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(300);
                                    Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                                    G.CurrentActivity.startActivity(intent);
                                    G.CurrentActivity.finish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {

                        Dialogs.ShowRepairDialog();
                    }

                } catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    Webservice.handelerro("timeout");
                } catch (IOException e) {
                    e.printStackTrace();
                    Webservice.handelerro(null);
                }

            }

        }, null);

    }
}


