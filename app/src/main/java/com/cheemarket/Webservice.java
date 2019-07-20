package com.cheemarket;


import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import com.cheemarket.Customview.Dialogs;


public class Webservice {


    private static OkHttpClient client = null;

    private static OkHttpClient getclient()    {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10,TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .readTimeout(10,TimeUnit.SECONDS)
                    .build();

        }
        return client;
    }


    public static  class requestparameter {
        public String key;
        public String value;
    }

    public static void request(String url, Callback callback, ArrayList<requestparameter> array) {



        MultipartBody.Builder MultipartBodyBuilder = null;
        if (array != null) {

            MultipartBodyBuilder = new MultipartBody.Builder();
            MultipartBodyBuilder.setType(MultipartBody.FORM);

            for (requestparameter parameter : array) {
                MultipartBodyBuilder.addFormDataPart(parameter.key, parameter.value);
            }
        }


        RequestBody requestBody = (MultipartBodyBuilder != null) ? MultipartBodyBuilder.build() : null;




        Request.Builder RequestBuilder = new Request.Builder();
        RequestBuilder.url(G.Baseurl + url);
        if(requestBody != null){

            RequestBuilder.post(requestBody);
        }
        Request request = RequestBuilder.build();
        getclient().newCall(request).enqueue(callback);




    }


    public static void handelerro(String check) {


        if (!G.readNetworkStatus()) {
            Intent intent = new Intent(G.CurrentActivity, activityNetwork.class);
            G.CurrentActivity.startActivity(intent);
        } else if(check != null && check.equals("timeout")){
            G.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(G.CurrentActivity, "اینترنت شما بسیار ضعیت است !", Toast.LENGTH_LONG).show();
                }
            });

        }else {
            Dialogs.ShowRepairDialog();
        }


    }

}
