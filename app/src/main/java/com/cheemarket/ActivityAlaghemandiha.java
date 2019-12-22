package com.cheemarket;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.AlaghemandihaAdapter;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.PoductStructure;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActivityAlaghemandiha extends AppCompatActivity {

    private static RecyclerView RecyclerViewList;

    private static LinearLayoutManager LayoutManagerList;
    private static RecyclerView.Adapter AdapterList;
    private static ArrayList<PoductStructure> mdatasetList;
    private static TextView txtempty;

    static long Listnumber = 0;
    static boolean allownext = false;

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
        setContentView(R.layout.activity_alaghemandiha);

        G.CurrentActivity = this;

        RecyclerViewList = (RecyclerView) findViewById(R.id.List);
        RecyclerViewList.setFocusable(false);


        mdatasetList = new ArrayList<PoductStructure>();

        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);

        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);

        RecyclerViewList.setHasFixedSize(true);
        LayoutManagerList = new LinearLayoutManager(G.CurrentActivity);
        RecyclerViewList.setLayoutManager(LayoutManagerList);
        AdapterList = new AlaghemandihaAdapter(mdatasetList);
        RecyclerViewList.setAdapter(AdapterList);
        txtempty = (TextView) findViewById(R.id.txtempty);

        pagework();


    }

    static Callback mycall;

    private static void pagework() {

        mdatasetList.clear();
        AdapterList.notifyDataSetChanged();
        Listnumber = 0;
        allownext = false;


        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "token";
        object1.value = G.token;

        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "number";
        object2.value = Listnumber + "";

        final ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(object1);
        array.add(object2);


        mycall = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        Webservice.request("Favorites", mycall, array);
                        return null;
                    }
                },G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                allownext = false;

                if (input.equals("[]")) {
                    if (Listnumber == 0) {


                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                txtempty.setVisibility(View.VISIBLE);
                            }
                        });

                        return;
                    }
                }
                try {
                    JSONArray array = new JSONArray(input);
                    PoductStructure kala = new PoductStructure();
                    boolean kalaone = true;
                    Listnumber += array.length();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        if (i == 0) {
                            if (mdatasetList.size() > 0) {

                                if (mdatasetList.get(mdatasetList.size() - 1).Id2 == null || mdatasetList.get(mdatasetList.size() - 1).Id2.equals("")) {
                                    Commands.convertinputdata(object, mdatasetList.get(mdatasetList.size() - 1), false);
                                }

                            }
                        }


                        if (kalaone) {
                            kala = new PoductStructure();
                            Commands.convertinputdata(object, kala, kalaone);
                            if (i == array.length() - 1) {
                                mdatasetList.add(kala);
                            }
                        } else {
                            Commands.convertinputdata(object, kala, kalaone);
                            mdatasetList.add(kala);
                        }
                        kalaone = !kalaone;
                    }


                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {

                            AdapterList.notifyItemInserted(AdapterList.getItemCount() + 1);
                        }
                    });
                    allownext = true;


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };


        Webservice.request("Favorites", mycall, array);


        RecyclerViewList.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {


                if (mdatasetList.size() > 2) {
                    TextView txtname = (TextView) view.findViewById(R.id.txtnameone);
                    if (txtname.getText().toString().equals(mdatasetList.get(mdatasetList.size() - 2).Name1) || txtname.getText().toString().equals(mdatasetList.get(mdatasetList.size() - 1).Name1)) {
                        if (LayoutManagerList.findLastVisibleItemPosition() == (mdatasetList.size() - 1) - 1) {

                            if (allownext == false) {
                                return;
                            }
                            allownext = false;


                            Webservice.requestparameter object1 = new Webservice.requestparameter();
                            object1.key = "token";
                            object1.value = G.token;

                            Webservice.requestparameter object2 = new Webservice.requestparameter();
                            object2.key = "number";
                            object2.value = Listnumber + "";

                            ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                            array.add(object1);
                            array.add(object2);

                            Webservice.request("Favorites", mycall, array);
                        }
                    }

                }

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {


            }
        });

    }

    public static void addtoalaghemandiha(String id) {

        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "token";
        object1.value = G.token;

        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "product_id";
        object2.value = id + "";

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(object1);
        array.add(object2);


        Webservice.request("addFavorite", new Callback() {
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
                },G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String temp = response.body().string();
                try {
                    JSONObject obj = new JSONObject(temp);


                    if (obj.getString("status").equals("ok") || obj.getString("status").equals("fail")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "به علاقه مندی های شما اضافه شد", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "مشکلی در اضافه شدن پیش آمد !", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, array);


    }

    public static void deletealaghemandiha(final String id) {
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "token";
        object1.value = G.token;

        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "product_id";
        object2.value = id + "";

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(object1);
        array.add(object2);


        Webservice.request("deleteFavorite", new Callback() {
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
                },G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String temp = response.body().string();
                try {
                    JSONObject obj = new JSONObject(temp);
                    if (obj.getString("status").equals("ok")) {

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {

                                pagework();

                            }
                        });


                    } else {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "مشکلی در حذف پیش آمد !", Toast.LENGTH_LONG).show();
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
