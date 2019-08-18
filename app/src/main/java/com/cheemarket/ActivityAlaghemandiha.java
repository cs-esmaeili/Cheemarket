package com.cheemarket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cheemarket.Adapter.AlaghemandihaAdapter;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.KalaStructure;

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
    private static ArrayList<KalaStructure> mdatasetList;
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


        mdatasetList = new ArrayList<KalaStructure>();

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
        object1.key = "Connectioncode";
        object1.value = G.Connectioncode;

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
                        Webservice.request("Store.php?action=Listalaghemandiha", mycall, array);
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                allownext = false;

                if (input.equals("null")) {
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
                    KalaStructure kala = new KalaStructure();
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
                            kala = new KalaStructure();
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


        Webservice.request("Store.php?action=Listalaghemandiha", mycall, array);


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
                            object1.key = "Connectioncode";
                            object1.value = G.Connectioncode;

                            Webservice.requestparameter object2 = new Webservice.requestparameter();
                            object2.key = "number";
                            object2.value = Listnumber + "";

                            ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                            array.add(object1);
                            array.add(object2);

                            Webservice.request("Store.php?action=Listalaghemandiha", mycall, array);
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
        object1.key = "Connectioncode";
        object1.value = G.Connectioncode;

        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "Kalaid";
        object2.value = id + "";

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(object1);
        array.add(object2);


        Webservice.request("Store.php?action=addtolistalaghemandiha", new Callback() {
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
                String temp = response.body().string();
                if (temp.equals("Ok")) {
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
            }
        }, array);


    }

    public static void deletealaghemandiha(final String id) {
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "Connectioncode";
        object1.value = G.Connectioncode;

        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "Kalaid";
        object2.value = id + "";

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(object1);
        array.add(object2);


        Webservice.request("Store.php?action=deletefromlistalaghemandiha", new Callback() {
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
                final String temp = response.body().string();
                if (temp.equals("Ok")) {

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            boolean find = false;
                            for (int i = 0; i < mdatasetList.size(); i++) {


                                if (!find && mdatasetList.get(i).Id1.equals(id)) {

                                    mdatasetList.get(i).Id1 = null;
                                    find = true;

                                    if (mdatasetList.get(i).Id2 == null) {
                                        mdatasetList.remove(i);
                                        continue;
                                    }

                                } else if (!find && mdatasetList.get(i).Id2.equals(id)) {

                                    mdatasetList.get(i).Id2 = null;
                                    find = true;


                                }
                                // 3

                                if (find) {
                                    if (mdatasetList.get(i).Id1 == null) {

                                        if (mdatasetList.get(i).Id2 == null) {
                                            break;
                                        }
                                        mdatasetList.get(i).Id1 = mdatasetList.get(i).Id2;

                                        mdatasetList.get(i).Name1 = mdatasetList.get(i).Name2;
                                        mdatasetList.get(i).Code1 = mdatasetList.get(i).Code2;
                                        mdatasetList.get(i).Weight1 = mdatasetList.get(i).Weight2;
                                        mdatasetList.get(i).Price1 = mdatasetList.get(i).Price2;
                                        mdatasetList.get(i).OldPrice1 = mdatasetList.get(i).OldPrice2;
                                        mdatasetList.get(i).Image1 = mdatasetList.get(i).Image2;
                                        mdatasetList.get(i).Tozihat1 = mdatasetList.get(i).Tozihat2;
                                        mdatasetList.get(i).Ordernumber1 = mdatasetList.get(i).Ordernumber2;
                                        mdatasetList.get(i).Status1 = mdatasetList.get(i).Status2;
                                        mdatasetList.get(i).Datetime1 = mdatasetList.get(i).Datetime2;


                                        mdatasetList.get(i).Id2 = null;


                                    }
                                    if (mdatasetList.get(i).Id2 == null) {
                                        if ((i + 1) < mdatasetList.size()) {

                                            if (mdatasetList.get(i + 1).Id1 == null) {
                                                break;
                                            }

                                            mdatasetList.get(i).Id2 = mdatasetList.get(i + 1).Id1;
                                            mdatasetList.get(i).Name2 = mdatasetList.get(i + 1).Name1;
                                            mdatasetList.get(i).Code2 = mdatasetList.get(i + 1).Code1;
                                            mdatasetList.get(i).Weight2 = mdatasetList.get(i + 1).Weight1;
                                            mdatasetList.get(i).Price2 = mdatasetList.get(i + 1).Price1;
                                            mdatasetList.get(i).OldPrice2 = mdatasetList.get(i + 1).OldPrice1;
                                            mdatasetList.get(i).Image2 = mdatasetList.get(i + 1).Image1;
                                            mdatasetList.get(i).Tozihat2 = mdatasetList.get(i + 1).Tozihat1;
                                            mdatasetList.get(i).Ordernumber2 = mdatasetList.get(i + 1).Ordernumber1;
                                            mdatasetList.get(i).Status2 = mdatasetList.get(i + 1).Status1;
                                            mdatasetList.get(i).Datetime2 = mdatasetList.get(i + 1).Datetime1;

                                            mdatasetList.get(i + 1).Id1 = null;

                                            if (mdatasetList.get(i + 1).Id2 == null) {
                                                mdatasetList.remove(i + 1);
                                            }
                                        } else {
                                            break;
                                        }

                                    }


                                }


                            }

                            AdapterList.notifyDataSetChanged();
                            if (mdatasetList.size() == 0) {
                                txtempty.setVisibility(View.VISIBLE);
                            }


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
            }
        }, array);
    }
}
