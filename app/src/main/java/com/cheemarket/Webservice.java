package com.cheemarket;


import android.content.Intent;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import com.cheemarket.Customview.Dialogs;


public class Webservice {


    private static OkHttpClient client = null;

    private static OkHttpClient getclient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .build();

        }
        return client;
    }


    public static class requestparameter {
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
        if (requestBody != null) {

            RequestBuilder.post(requestBody);
        }
        Request request = RequestBuilder.build();
        getclient().newCall(request).enqueue(callback);


    }


    public static void handelerro(Exception e, final Callable<Void> Method) {

        if (!Commands.readNetworkStatus()) {
            Intent intent = new Intent(G.CurrentActivity, ActivityNetwork.class);
            G.CurrentActivity.startActivity(intent);
            return;
        }

        if (e instanceof SocketTimeoutException || e instanceof TimeoutException) {
            e.printStackTrace();

            G.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    //   Toast.makeText(G.CurrentActivity, "اینترنت شما بسیار ضعیت است !", Toast.LENGTH_LONG).show();
                    try {
                        Method.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });

        } else {
            e.printStackTrace();
            Dialogs.ShowRepairDialog();
        }


    }

}
