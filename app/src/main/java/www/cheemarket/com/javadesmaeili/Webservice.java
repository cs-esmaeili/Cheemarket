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
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import www.cheemarket.com.javadesmaeili.Customview.Dialogs;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class Webservice {


    private static OkHttpClient client = null;

    private static OkHttpClient getclient()    {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(3,TimeUnit.SECONDS)
                    .writeTimeout(3,TimeUnit.SECONDS)
                    .readTimeout(3,TimeUnit.SECONDS)
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
        Log.i("LOG",request.toString());
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
