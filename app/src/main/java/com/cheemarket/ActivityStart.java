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

import static com.cheemarket.G.pre;

public class ActivityStart extends AppCompatActivity {

    public static Uri appLinkData;


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



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        G.IMAGES_HEIGHT = (int) (Double.parseDouble(displayMetrics.heightPixels + "") / 2);
        G.IMAGES_WIDTH = (int) (Double.parseDouble(displayMetrics.widthPixels + "") / 2);

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        appLinkData = appLinkIntent.getData();



        check_Atelae();

    }

    private static void check_Atelae() {
        ArrayList<Webservice.requestparameter> params = new ArrayList<>();

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "VERSIONNAME";
        param1.value = G.VERSIONNAME;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "messageid";
        param2.value =  (pre.contains("messageid") && !pre.getString("messageid", "Error").equals("Error")?   pre.getString("messageid", "Error") : "-1" ) ;

        Log.i("LOG" ,    param2.value );

        params.add(param1);
        params.add(param2);

        Webservice.request("versionControl", new Callback() {
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
                Log.i("LOG" , "=" + input);
                if (!input.equals("[]")) {
                    try {
                        JSONObject object = new JSONObject(input);

                        if(object.length() == 1){
                            SharedPreferences.Editor editor = pre.edit();
                            editor.putString("messageid" , object.getString("messageid"));
                            editor.apply();


                            Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                            G.CurrentActivity.startActivity(intent);
                            G.CurrentActivity.finish();


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
                    Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                    G.CurrentActivity.startActivity(intent);
                    G.CurrentActivity.finish();

                }
            }
        }, params);

    }
}


