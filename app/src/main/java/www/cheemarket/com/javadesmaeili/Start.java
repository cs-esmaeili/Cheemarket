package www.cheemarket.com.javadesmaeili;

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
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import www.cheemarket.com.javadesmaeili.Customview.Dialogs;
import www.cheemarket.com.javadesmaeili.R;

import static www.cheemarket.com.javadesmaeili.ActivityMain.pre;

public class Start extends AppCompatActivity {

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

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (!input.equals("[]")) {
                    try {
                        JSONObject object = new JSONObject(input);


                        if (object.getString("type").endsWith("update")) {

                            if(G.VERSIONNAME == object.getString("VERSIONNAME")){
                                return;
                            }

                            if(object.getString("cancansel").endsWith("yes")){
                                // zakhire
                            }else  if(object.getString("cancansel").endsWith("no")) {

                            }


                        }else {

                            if(object.getString("cancansel").endsWith("yes")){
                                // zakhire
                            }else  if(object.getString("cancansel").endsWith("no")) {

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null);

    }


    private void checkrunword() {

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
                                    startActivity(intent);
                                    finish();
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


