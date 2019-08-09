package com.cheemarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.cheemarket.Customview.Dialogs;
import com.cheemarket.Structure.IntromanegmentStructure;

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
        G.IMAGES_HEIGHT = (int) (Double.parseDouble(displayMetrics.heightPixels + "") / 2);
        G.IMAGES_WIDTH = (int) (Double.parseDouble(displayMetrics.widthPixels + "") / 2);

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        appLinkData = appLinkIntent.getData();


        check_Atelae();


    }

    private void check_Atelae() {
        ArrayList<Webservice.requestparameter> params = new ArrayList<>();

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "VERSIONNAME";
        param1.value = G.VERSIONNAME;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "messageid";
        param2.value =  (pre.contains("messageid") && !pre.getString("messageid", "Error").equals("Error")?   pre.getString("messageid", "Error") : "-1" ) ;


        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "action";
        param3.value = "check";

        params.add(param1);
        params.add(param2);
        params.add(param3);

        Webservice.request("intromanegment.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        check_Atelae();
                        return null;
                    }
                });


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                Log.i("LOG" , input);
                if (!input.equals("[]")) {
                    try {
                        JSONObject object = new JSONObject(input);

                        if(object.length() == 1){
                            SharedPreferences.Editor editor = pre.edit();
                            editor.putString("messageid" , object.getString("messageid"));
                            editor.apply();
                            checkrunword();
                            return;
                        }

                        IntromanegmentStructure data = new IntromanegmentStructure();
                        data.type = object.getString("type");
                        data.btntext = object.getString("btntext");
                        data.canseltext = object.getString("canseltext");
                        data.btnvisi = object.getString("btnvisi");
                        data.canselvisi = object.getString("canselvisi");
                        data.matn = object.getString("matn");
                        data.Image = object.getString("Image");
                        data.url = object.getString("url");
                        data.messageid = object.getString("messageid");

                        data.cancansel = object.getString("cancansel");
                        data.save = object.getString("save");


                        if(data.type.equals("update")){
                            if(data.save.equals("yes")){
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("messageid", data.messageid );
                                editor.apply();
                            }

                            Dialogs.message(data.cancansel.equals("yes") , data.btntext,data.canseltext, data.btnvisi,data.canselvisi,data.matn,data.Image,data.url);


                        }else if(data.type.equals("info")){
                            Log.i("LOG" , "ok");
                            if(data.save.equals("yes")){
                                SharedPreferences.Editor editor = pre.edit();
                                editor.putString("messageid",data.messageid);
                                editor.apply();
                            }

                            Log.i("LOG" , "ok");
                            Dialogs.message(data.cancansel.equals("yes"), data.btntext,data.canseltext, data.btnvisi,data.canselvisi,data.matn,data.Image,data.url);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    checkrunword();
                }
            }
        }, params);

    }


    public static void checkrunword() {

        Webservice.request("server.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        checkrunword();
                        return null;
                    }
                });

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

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }, null);

    }
}


