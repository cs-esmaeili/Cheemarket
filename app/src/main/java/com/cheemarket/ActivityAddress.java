package com.cheemarket;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Adapter.AddressAdapter;
import com.cheemarket.Structure.AddressStructure;
import com.cheemarket.Structure.PoductStructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityAddress extends AppCompatActivity {


    public static RecyclerView.Adapter AdapterList;
    public static ArrayList<AddressStructure> address = new ArrayList<>();
    public static boolean needtorealod = false;


    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        if (needtorealod) {
            needtorealod = false;
            getAddress(address, AdapterList);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        G.CurrentActivity = this;
        AdapterList = null;
        address = new ArrayList<>();

        RecyclerView List = (RecyclerView) findViewById(R.id.List);
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);

        List.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
        List.setLayoutManager(LayoutManagerList);
        AdapterList = new AddressAdapter(address, false, -1);
        List.setAdapter(AdapterList);

        getAddress(address, AdapterList);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(G.CurrentActivity, ActivityEdite.class);
                G.CurrentActivity.startActivity(intent);
            }
        });

    }

    public static void getAddress(final ArrayList<AddressStructure> save, final RecyclerView.Adapter AdapterList) {

        address.clear();
        AdapterList.notifyDataSetChanged();

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "token";
        param1.value = G.token;


        final ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);

        Webservice.request("address", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (!input.equals("[]")) {
                    try {
                        JSONArray array1 = new JSONArray(input);
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject obj = array1.getJSONObject(i);
                            AddressStructure address = new AddressStructure();
                            address.Name = obj.getString("name");
                            address.Homenumber = obj.getString("home_number");
                            address.Phonenumber = obj.getString("phone_number");
                            address.Ostan = obj.getString("state");
                            address.Shahr = obj.getString("city");
                            address.Codeposti = obj.getString("postal_code");
                            address.Address = obj.getString("address");
                            address.Id = obj.getString("user_address_id");
                            save.add(address);

                        }

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                AdapterList.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, array);

    }

    public static void deleteaddress(final String addressid, final ArrayList<AddressStructure> deleteas, final RecyclerView.Adapter AdapterList) {

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "token";
        param1.value = G.token;

        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "user_address_id";
        param2.value = addressid;

        final ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);

        Webservice.request("addressDelete", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.has("status") && obj.getString("status").equals("ok")) {
                        for (int i = 0; i < deleteas.size(); i++) {
                            if (deleteas.get(i).Id.equals(addressid)) {
                                deleteas.remove(i);
                                break;
                            }
                        }
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                AdapterList.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, array);

    }

}
