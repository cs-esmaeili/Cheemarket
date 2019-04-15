package www.cheemarket.com.javadesmaeili;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import www.cheemarket.com.javadesmaeili.Customview.Dialogs;



public class Webservice {



    public    OndownloadListener datalistener;

    public Webservice setDatalistener(OndownloadListener datalistener) {
        this.datalistener = datalistener;
        return this;
    }

    long Starttime = 0;
    public Webservice downloaddata(final String url, final ArrayList<NameValuePair> params) {
     Log.i("LOG","url = " + url);



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Starttime = System.currentTimeMillis();
                    HttpClient client = new DefaultHttpClient();

                    HttpParams paramss = client.getParams();
                    HttpConnectionParams.setConnectionTimeout(paramss, 10000);
                    HttpConnectionParams.setSoTimeout(paramss, 10000);



                    HttpPost method = new HttpPost(url);

                    if (params != null) {
                        method.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
                        Log.i("LOG","params =" + params.toString());
                    }

                    HttpResponse response = client.execute(method);
                    InputStream stream = response.getEntity().getContent();
                    String result = convertInputStreamToString(stream);

                    Log.i("LOG", "result = " + result);

                    if(result.equals("close")){
                        new JSONArray(result);
                    }

                    if (datalistener != null) {
                        datalistener.Oncompelet(result);
                    }



                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                    handelerro();
                }
                catch (IOException e) {
                    e.printStackTrace();
                    handelerro();
                } catch (JSONException e) {
                    e.printStackTrace();
                    handelerro();
                }catch (Exception e){
                    e.printStackTrace();

                    handelerro();
                }


            }
        });
        thread.start();
        return this;
    }

    private  void handelerro(){

        Log.i("LOG", "start = " + Starttime);
        Log.i("LOG", "current = " + System.currentTimeMillis());


        if(!G.readNetworkStatus()){
            Intent intent = new Intent(G.CurrentActivity, Networkactivity.class);
            G.CurrentActivity.startActivity(intent);
        }else {

            if((System.currentTimeMillis() - Starttime) > 5000){

                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(G.CurrentActivity,"time out",Toast.LENGTH_LONG).show();
                    }
                });
            }else{
                Dialogs.ShowRepairDialog();
            }



        }


    }


    private static String convertInputStreamToString(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            return builder.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
