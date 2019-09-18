package com.cheemarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.cheemarket.Adapter.Listordersadapter;
import com.cheemarket.Customview.badgelogo;


public class ActivityOrders extends AppCompatActivity {

    public class order {
        public String Category;
        public String sum;
        public String Vaziyat;
        public Float Rate;
    }


    private static RecyclerView RecyclerViewList;
    private static RecyclerView.LayoutManager LayoutManagerList;
    private static RecyclerView.Adapter AdapterList;
    public static ArrayList<order> mdatasetList;
    public static TextView txtempty;

    private badgelogo badge;
    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        Commands.setbadgenumber(badge);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        RecyclerViewList = (RecyclerView) findViewById(R.id.List);
        txtempty = (TextView) findViewById(R.id.txtempty);
        G.CurrentActivity = this;

        mdatasetList = new ArrayList<order>();


        RecyclerViewList.setHasFixedSize(true);
        LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
        RecyclerViewList.setLayoutManager(LayoutManagerList);
        AdapterList = new Listordersadapter(mdatasetList);
        RecyclerViewList.setAdapter(AdapterList);

        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);

        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "token";
        param1.value = G.token;
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        Webservice.request("Store.php?action=listorders", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context,"مشکلی در ارتیاط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String input = response.body().string();
                if(input.equals("[]")){
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            txtempty.setVisibility(View.VISIBLE);
                        }
                    });

                    return;
                }

                try {
                    JSONArray array = new JSONArray(input);

                    for (int i = array.length() - 1; i >= 0 ; i--) {
                        JSONObject object = array.getJSONObject(i);
                        order order = new order();
                        order.Category = object.getString("Category");
                        order.sum = object.getString("sum");
                        order.Rate = Float.parseFloat(object.getString("Rate").equals("null") ? "0.0" : object.getString("Rate"));
                        order.Vaziyat = object.getString("Vaziyat");
                        mdatasetList.add(order);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        AdapterList.notifyDataSetChanged();

                    }
                });

            }
        }, array);


    }
}
