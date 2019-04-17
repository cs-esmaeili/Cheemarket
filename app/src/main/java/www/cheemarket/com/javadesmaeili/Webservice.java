package www.cheemarket.com.javadesmaeili;


import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import www.cheemarket.com.javadesmaeili.Customview.Dialogs;


public class Webservice {


    private static OkHttpClient client = null;

    private static OkHttpClient getclient()    {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(1,TimeUnit.SECONDS)
                    .writeTimeout(1,TimeUnit.SECONDS)
                    .readTimeout(1,TimeUnit.SECONDS)
                    .build();

        }
        return client;
    }


    public class requestparameter {
        public String key;
        public String value;
    }

    public static void request(String url, Callback callback, ArrayList<requestparameter> array) {


        HttpUrl.Builder urlBuilder = HttpUrl.parse(G.Baseurl + url).newBuilder();

        if (array != null) {
            for (requestparameter parameter : array) {
                urlBuilder.addQueryParameter(parameter.key, parameter.value);
            }
        }


        url = urlBuilder.build().toString();


        Request request = new Request.Builder()
                .url(url)
                .build();




            getclient().newCall(request).enqueue(callback);




    }


    public static void handelerro(String check) {

        if (!G.readNetworkStatus()) {
            Intent intent = new Intent(G.CurrentActivity, Networkactivity.class);
            G.CurrentActivity.startActivity(intent);
        } else if(check != null && check.equals("timeout")){
            G.HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(G.CurrentActivity, "time out", Toast.LENGTH_LONG).show();
                }
            });

        }else {
            Dialogs.ShowRepairDialog();
        }


    }

}
