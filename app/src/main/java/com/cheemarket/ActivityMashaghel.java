package com.cheemarket;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.AdapterDastebandimashaghel;
import com.cheemarket.Adapter.Adaptermashaghel;
import com.cheemarket.Structure.Mashaghel;
import com.cheemarket.Structure.Mashagheldastebandi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityMashaghel extends AppCompatActivity {


    public static ArrayList<Mashagheldastebandi> dastebandiha = new ArrayList<>();
    public static ArrayList<Mashaghel> shoghlha = new ArrayList<>();
    private static RecyclerView Listdastebandi;
    private static RecyclerView Listshoghlha;
    public static String id = "0";
    public static TextView pagetitle;

    public static SearchView searchView = null;
    public static RecyclerView.Adapter AdapterList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mashaghel);

        G.CurrentActivity = this;
        Listdastebandi = (RecyclerView) findViewById(R.id.Listdastebandi);
        Listshoghlha = (RecyclerView) findViewById(R.id.Listshoghlha);
        searchView  = (SearchView) findViewById(R.id.searchView);
        pagetitle  = (TextView) findViewById(R.id.pagetitle);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        pagetitle.setText("همه شغل ها");
        getdastebandi();
        search("");

    }


    private void getdastebandi() {
        dastebandiha.clear();
        Webservice.request("Store.php?action=mashagheldastebandi", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                try {
                    JSONArray array = new JSONArray(input);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        Mashagheldastebandi mashagheldastebandi = new Mashagheldastebandi();
                        mashagheldastebandi.Name = object.getString("Name");
                        mashagheldastebandi.Id = object.getString("Id");
                        dastebandiha.add(mashagheldastebandi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        Listdastebandi.setHasFixedSize(true);
                        RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, true);
                        Listdastebandi.setLayoutManager(LayoutManagerList);
                        RecyclerView.Adapter AdapterList = new AdapterDastebandimashaghel(dastebandiha);
                        Listdastebandi.setAdapter(AdapterList);
                    }
                });


            }
        }, null);
    }


    public static void search(String matn) {
        shoghlha.clear();
        if(AdapterList != null){
            AdapterList.notifyDataSetChanged();
        }

        Webservice.requestparameter param = new Webservice.requestparameter();
        param.key = "id";
        param.value = id;

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "matnn";
        param1.value = matn;

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param);
        array.add(param1);

        Webservice.request("Store.php?action=searchmashaghel", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                try {
                    JSONArray array1 = new JSONArray(input);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject object = array1.getJSONObject(i);
                        Mashaghel mashaghel = new Mashaghel();
                        mashaghel.Title = object.getString("Title");
                        mashaghel.Image = object.getString("Image");
                        mashaghel.Tozihat = object.getString("Tozihat");
                        mashaghel.Dastebandi = object.getString("Dastebandi");
                        mashaghel.Id = object.getString("Id");

                        shoghlha.add(mashaghel);

                    }

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Listshoghlha.setHasFixedSize(true);
                            RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
                            Listshoghlha.setLayoutManager(LayoutManagerList);
                            AdapterList = new Adaptermashaghel(shoghlha);
                            Listshoghlha.setAdapter(AdapterList);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },array);


    }

}
