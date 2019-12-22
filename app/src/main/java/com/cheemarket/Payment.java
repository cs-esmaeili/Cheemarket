package com.cheemarket;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Payment {

    static class OrderStructure {
        public String Tedad;
        public String kalaId;
    }

    public static void openpaymentgate() {

        ArrayList<OrderStructure> array = new ArrayList<OrderStructure>();
        for (int i = 0; i < G.mdatasetsabad.size(); i++) {

            OrderStructure orderStructure = new OrderStructure();
            orderStructure.kalaId = G.mdatasetsabad.get(i).Id;
            orderStructure.Tedad = G.mdatasetsabad.get(i).Tedad;
            array.add(orderStructure);
        }


        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(array, new TypeToken<List<OrderStructure>>() {
        }.getType());

        if (!element.isJsonArray()) {
            //error
        }

        JsonArray jsonArray = element.getAsJsonArray();


        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "products";
        param1.value = jsonArray.toString();


        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "address_id";
        param2.value = Paymentstep.Addressid;


        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "date";
        param3.value = Paymentstep.Date;

        Webservice.requestparameter param4 = new Webservice.requestparameter();
        param4.key = "token";
        param4.value = G.token;


        Webservice.requestparameter param5 = new Webservice.requestparameter();
        param5.key = "payment";
        param5.value = Paymentstep.paymentway + "";


        ArrayList<Webservice.requestparameter> arrayList = new ArrayList<>();
        arrayList.add(param1);
        arrayList.add(param2);
        arrayList.add(param3);
        arrayList.add(param4);
        arrayList.add(param5);



        Webservice.request("payment/start", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {


                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        Paymentstep.btnpay.setEnabled(true);
                        Toast.makeText(G.context, "مشکلی در ارتباط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (input.contains("https://")) {

                    Paymentstep.Addressid = null;
                    Paymentstep.Date = null;
                    Paymentstep.close = true;
                    G.mdatasetsabad.clear();


                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(input));
                        G.CurrentActivity.startActivity(browserIntent);

                    } catch (Exception e) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Paymentstep.btnpay.setEnabled(true);
                                Toast.makeText(G.context, "مشکلی در ارتباط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                } else {

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Paymentstep.btnpay.setEnabled(true);
                            Toast.makeText(G.context, "مشکلی در ارتباط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        }, arrayList);


    }


}
