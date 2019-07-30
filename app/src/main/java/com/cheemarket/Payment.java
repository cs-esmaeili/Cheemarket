package com.cheemarket;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

import okhttp3.Callback;
import okhttp3.Response;

public class Payment {

    static class OrderStructure {
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

    public static void openpaymentgate() {

        ArrayList<OrderStructure> array = new ArrayList<OrderStructure>();
        for (int i = 0; i < G.mdatasetsabad.size(); i++) {

            OrderStructure orderStructure = new OrderStructure();


            orderStructure.Price = G.mdatasetsabad.get(i).Price;
            orderStructure.OldPrice = G.mdatasetsabad.get(i).OldPrice;
            orderStructure.Tedad = G.mdatasetsabad.get(i).Tedad;
            orderStructure.kalaId = G.mdatasetsabad.get(i).Id;

            orderStructure.PersonName = Paymentstep.Address.Name;
            orderStructure.Homenumber = Paymentstep.Address.Homenumber;
            orderStructure.Phonenumber = Paymentstep.Address.Phonenumber;
            orderStructure.Ostan = Paymentstep.Address.Ostan;
            orderStructure.Shahr = Paymentstep.Address.Shahr;
            orderStructure.Codeposti = Paymentstep.Address.Codeposti;
            orderStructure.Address = Paymentstep.Address.Address;

            orderStructure.Connectioncode = G.Connectioncode;
            array.add(orderStructure);
        }



        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(array, new TypeToken<List<OrderStructure>>() {
        }.getType());

        if (!element.isJsonArray()) {
            //error
        }

        JsonArray jsonArray = element.getAsJsonArray();


        Webservice.requestparameter param = new Webservice.requestparameter();
        param.key = "action";
        param.value = "Paymentwork";

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "jsontext";
        param1.value = jsonArray.toString();


        Log.i("loG", jsonArray.toString());

        ArrayList<Webservice.requestparameter> arrayList = new ArrayList<>();
        arrayList.add(param);
        arrayList.add(param1);


        Webservice.request("Request.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(G.context, "مشکلی در ارتباط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();


                Log.i("loG", input);


                if (input.contains("https://www.zarinpal.com/pg/StartPay/") && input.contains("/ZarinGate")) {

                    Paymentstep.Address = null;
                    G.mdatasetsabad.clear();


                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(input));
                    G.CurrentActivity.startActivity(browserIntent);


                } else {

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(G.context, "مشکلی در ارتباط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        }, arrayList);


    }
}
