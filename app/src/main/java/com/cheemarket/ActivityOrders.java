package com.cheemarket;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.Listordersadapter;
import com.cheemarket.Customview.badgelogo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ActivityOrders extends AppCompatActivity {

    public class order {
        public String factor_id;
        public String sum;
        public String status;
        public String difference_status;
        public String price;
    }


    private static RecyclerView RecyclerViewList;
    private static RecyclerView.LayoutManager LayoutManagerList;
    private static RecyclerView.Adapter AdapterList;
    public static ArrayList<order> mdatasetList;
    public static TextView txtempty;
    private static ProgressBar progressBar;
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
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
        Webservice.request("factor", new Callback() {
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
                },G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String input = response.body().string();
                if(input.equals("[]")){
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
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
                        order.factor_id = object.getString("factor_id");
                        order.sum = object.getString("sum");
                        order.status = object.getString("status");
                        mdatasetList.add(order);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        AdapterList.notifyDataSetChanged();

                    }
                });

            }
        }, array);


    }
}
