package com.cheemarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.cheemarket.Adapter.SabadAdapter;
import com.cheemarket.Structure.sabad;

public class ActivitySabad extends AppCompatActivity {


    public static RecyclerView.Adapter Adapter;
    public static BigInteger temp;


    static TextView txt;
    public static TextView txtpyam;

    @Override
    protected void onResume() {
        super.onResume();

        G.CurrentActivity = this;

        setghaymat();
        if (G.comeback) {
            G.CurrentActivity.finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sabad);
        G.CurrentActivity = this;


        RecyclerView List = (RecyclerView) findViewById(R.id.Listsabad);
        TextView btnpay = (TextView) findViewById(R.id.btnpay);
        txt = (TextView) findViewById(R.id.txtjamkharid);
        txtpyam = (TextView) findViewById(R.id.txtpayam);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(G.CurrentActivity);
        Adapter = new SabadAdapter(G.mdatasetsabad);
        List.setHasFixedSize(true);
        List.setLayoutManager(layoutManager);
        List.setAdapter(Adapter);
        Adapter.notifyDataSetChanged();


        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (G.mdatasetsabad.size() > 0) {
                    ActivitySabad.pagework();
                }

            }
        });


    }

    public static void setghaymat() {
        temp = BigInteger.valueOf(0);
        for (sabad a : G.mdatasetsabad) {

            BigInteger multi = BigInteger.valueOf(0);
            multi = multi.add(BigInteger.valueOf(Integer.parseInt(a.Price)));
            multi = multi.multiply(BigInteger.valueOf(Integer.parseInt(a.Tedad)));

            temp = temp.add(multi);
        }
        txt.setText(temp + " " + "تومان");


        if (G.mdatasetsabad.size() == 0) {
            txtpyam.setVisibility(View.VISIBLE);
        } else {
            txtpyam.setVisibility(View.GONE);
        }


    }

    public static void pagework() {


        Gson gson = new GsonBuilder().create();
        JsonArray myCustomArray = gson.toJsonTree(G.mdatasetsabad).getAsJsonArray();


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();

        Webservice.requestparameter requestparameter = new Webservice.requestparameter();
        requestparameter.key = "jsontext";
        requestparameter.value = "" + myCustomArray;

        Webservice.requestparameter requestparameter1 = new Webservice.requestparameter();
        requestparameter1.key = "token";
        requestparameter1.value = G.token;


        array.add(requestparameter);
        array.add(requestparameter1);
        Webservice.request("checkCart", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "مشکلی در ارتیاط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();


                if (input.equals("[]") || input.equals("")) {

                    if (G.mdatasetsabad.size() > 0) {
                        Intent intent = new Intent(G.CurrentActivity, Paymentstep.class);
                        G.CurrentActivity.startActivity(intent);

                    }
                    return;
                }

                try {
                    JSONArray array = new JSONArray(input);
                    String text = "";
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);


                        for (int j = 0; j < G.mdatasetsabad.size(); j++) {

                            if (object.has("delete") && G.mdatasetsabad.get(j).Id.equals(object.getString("delete"))) {

                                G.mdatasetsabad.remove(j);
                                text = "بعضی از کالاها به دلیل عدم موجودی پاک شدند";
                                j = 0;
                            }else if (G.mdatasetsabad.size() > j && G.mdatasetsabad.get(j).Id.equals(object.getString("product_id"))) {
                                if (!text.contains("عوض")) {
                                    text += "\n" + "اطلاعات بعضی از کالا ها عوض شد";
                                }

                                G.mdatasetsabad.get(j).Name = object.getString("name");
                                G.mdatasetsabad.get(j).Price = object.getString("price");
                                G.mdatasetsabad.get(j).Image_thumbnail = object.getString("image_thumbnail");
                                G.mdatasetsabad.get(j).Ordernumber = object.getString("order_number");


                                if (Integer.parseInt(G.mdatasetsabad.get(j).Ordernumber) < Integer.parseInt(G.mdatasetsabad.get(j).Tedad)) {
                                    G.mdatasetsabad.get(j).Tedad = G.mdatasetsabad.get(j).Ordernumber;

                                }
                                /*
                                if (Integer.parseInt(object.getString("Tedad")) < Integer.parseInt(G.mdatasetsabad.get(j).Tedad)) {
                                    G.mdatasetsabad.get(j).Tedad = object.getString("Tedad");
                                }

*/
                            }

                        }
                    }

                    final String finalText = text;
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(G.context, finalText, Toast.LENGTH_LONG).show();
                            Adapter.notifyDataSetChanged();
                        }
                    });


                } catch (JSONException e) {

                }


            }
        }, array);

    }


}
