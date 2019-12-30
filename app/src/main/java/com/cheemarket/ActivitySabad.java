package com.cheemarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.SabadAdapter;
import com.cheemarket.Structure.sabad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
        add_card_server();


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

    public static void add_card_server() {
        if(G.token.equals("") || G.token == null){
            return;
        }
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        Webservice.requestparameter requestparameter1 = new Webservice.requestparameter();
        requestparameter1.key = "token";
        requestparameter1.value = G.token;


        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(G.mdatasetsabad, new TypeToken<List<sabad>>() {
        }.getType());

        if (!element.isJsonArray()) {
            //error
        }

        JsonArray jsonArray = element.getAsJsonArray();

        Webservice.requestparameter requestparameter2 = new Webservice.requestparameter();
        requestparameter2.key = "json_text";
        requestparameter2.value = jsonArray + "";

        array.add(requestparameter1);
        array.add(requestparameter2);

        Webservice.request("add_cart", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        }, array);
    }

    public static void check_card_server() {
        if(G.token.equals("") || G.token == null){
            return;
        }
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        Webservice.requestparameter requestparameter1 = new Webservice.requestparameter();
        requestparameter1.key = "token";
        requestparameter1.value = G.token;
        array.add(requestparameter1);

        Webservice.request("cart_products", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(input);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        sabad s = new sabad();
                        s.Name = jsonObject.getString("name");
                        s.Price = jsonObject.getString("price");
                        s.OldPrice = jsonObject.getString("old_price");
                        s.Image_thumbnail = jsonObject.getString("image_thumbnail");
                        s.Image_folder = jsonObject.getString("image_folder");
                        s.Tozihat = jsonObject.getString("description");
                        s.Ordernumber = jsonObject.getString("order_number");
                        s.Tedad = jsonObject.getString("number");
                        s.Status = jsonObject.getString("status");
                        s.Datetime = jsonObject.getString("datetime");
                        s.Id = jsonObject.getString("product_id");
                        G.mdatasetsabad.add(s);

                    }
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Commands.setbadgenumber(ActivityMain.badge);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, array);
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
                }, G.CurrentActivity);
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
                            } else if (G.mdatasetsabad.size() > j && G.mdatasetsabad.get(j).Id.equals(object.getString("product_id"))) {
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
