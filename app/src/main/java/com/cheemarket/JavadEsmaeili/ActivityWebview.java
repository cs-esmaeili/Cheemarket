package com.cheemarket.JavadEsmaeili;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class ActivityWebview extends AppCompatActivity {

    WebView webView = null;
    String refcode = "";
    class OrderStructure{
        public String PersonName;
        public String Homenumber;
        public String Phonenumber;
        public String Ostan;
        public String Shahr;
        public String Codeposti;
        public String Address;



        public String Price;
        public String OldPrice;
        public String Tedad;
        public String kalaId;

        public String Connectioncode;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);


        webView = (WebView) findViewById(R.id.webview);
        final TextView txt = (TextView) findViewById(R.id.txt);
        final TextView ref = (TextView) findViewById(R.id.ref);



        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


         WebViewClient mdWebViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("LOG", url);
                if(url.contains(G.Baseurl+"successful.html")){
                    webView.setVisibility(View.GONE);
                    txt.setVisibility(View.VISIBLE);
                    ref.setVisibility(View.VISIBLE);
                    refcode = url.substring(url.indexOf("=") + 1 );
                    ref.setText("شماره تراکنش شما : " + refcode);


                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("RefCode", refcode);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(G.context,"شماره تراکنش کپی شد" , Toast.LENGTH_LONG).show();

                }else if(url.equals(G.Baseurl+"unsuccessful.html")){
                    webView.setVisibility(View.GONE);
                    txt.setText("پرداخت موفقیت آمیز نبود");
                    txt.setVisibility(View.VISIBLE);
                }


            }


        };
        ref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ref.getVisibility() == View.VISIBLE){
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("RefCode", refcode);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(G.context,"شماره تراکنش کپی شد" , Toast.LENGTH_LONG).show();
                }
            }
        });

        webView.setWebViewClient(mdWebViewClient);

        ArrayList<OrderStructure> array = new ArrayList<OrderStructure>();
        for(int i = 0 ; i < G.mdatasetsabad.size(); i++){
            OrderStructure orderStructure = new OrderStructure();


            orderStructure.Price = G.mdatasetsabad.get(i).Price;
            orderStructure.OldPrice = G.mdatasetsabad.get(i).OldPrice;
            orderStructure.Tedad = G.mdatasetsabad.get(i).Tedad;
            orderStructure.kalaId = G.mdatasetsabad.get(i).Id;

            orderStructure.PersonName = Paymentstep.Address.Name;
            orderStructure.Homenumber = Paymentstep.Address.Homenumber;
            orderStructure.Phonenumber = Paymentstep.Address.Phonenumber;
            orderStructure.Ostan = Paymentstep.Address.Ostan;
            orderStructure.Shahr  = Paymentstep.Address.Shahr;
            orderStructure.Codeposti = Paymentstep.Address.Codeposti;
            orderStructure.Address  = Paymentstep.Address.Address;

            orderStructure.Connectioncode = G.Connectioncode;
            array.add(orderStructure);
        }

        Paymentstep.Address = null;


        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(array, new TypeToken<List<OrderStructure>>() {}.getType());

        if (! element.isJsonArray()) {
            //error
        }

        JsonArray jsonArray = element.getAsJsonArray();

        Log.e("Array", jsonArray.toString());
        String postData = null;
        try {

            postData = "action=" + URLEncoder.encode( "Paymentwork" ,"UTF-8") + "&jsontext=" + URLEncoder.encode( jsonArray.toString() ,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        G.mdatasetsabad.clear();
         webView.bringToFront();
         webView.setVisibility(View.VISIBLE);
         txt.setVisibility(View.GONE);
         Log.i("LOG","postData=" + jsonArray.toString());

         webView.postUrl(G.Baseurl + "Request.php", postData.getBytes());

    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
