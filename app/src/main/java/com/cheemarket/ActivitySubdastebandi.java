package com.cheemarket;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Adapter.SubdastebandiAdapter;
import com.cheemarket.Customview.Dialogs;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.PoductStructure;


public class ActivitySubdastebandi extends AppCompatActivity {

    private static String code = "";
    private static String codesubdastebandi = "";
    public static ArrayList<com.cheemarket.Structure.Subdastebandi> mdatasetListsubdastebandi;
    public static ArrayList<PoductStructure> mdatasetkalafortwokala;
    public static ArrayList<PoductStructure> mdatasetkalaforonekala;
    public static LinearLayoutManager layoutManager = null;


    public static RecyclerView.Adapter AdapterListsubcategory;
    public static Adapter AdapterListkala;

    public static RecyclerView List = null;
    public static long Listnumber = 0;
    public static boolean showsubdastebandi = true;
    static Button btnview;
    static Button btnsort;
    private static TextView pagetitle;
    private static boolean allownext = true;
    private static String sort = "Nothing";
    private boolean needtoclose = false;
    private static String toptitle = "";
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
        setContentView(R.layout.activity_subdastebandi);
        G.CurrentActivity = this;


        List = (RecyclerView) findViewById(R.id.List);
        btnview = (Button) findViewById(R.id.btnview);
        btnsort = (Button) findViewById(R.id.btnsort);
        pagetitle = (TextView) findViewById(R.id.txttitle);
        clearalldata();

        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);
        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            if (extras.containsKey("subdastebandistring")) {
                codesubdastebandi = extras.getString("subdastebandistring");
                namayeshsubdastebandi(codesubdastebandi);

                Commands.addview("دسته بندی " + codesubdastebandi +  " بازدید شد");

            } else if (extras.containsKey("subkala")) {
                code = extras.getString("subkala");
                toptitle = extras.getString("NameSubcategori");
                needtoclose = true;
                namayeshkalaha(code, toptitle);


            }
        }


        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdapterListkala != null) {

                    AdapterListkala.changelayout(List, AdapterListkala, layoutManager);
                }

            }
        });
        btnsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder b = new AlertDialog.Builder(G.CurrentActivity);
                b.setTitle("فیلتر");
                String[] types = {"بدون فیلتر", "قیمت از کم به زیاد", "قیمت از زیاد به کم"};
                b.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                sort = "Nothing";
                                namayeshkalaha(code, toptitle);
                                break;
                            case 1:
                                sort = "Ascending";
                                namayeshkalaha(code, toptitle);
                                break;
                            case 2:
                                sort = "Descending";
                                namayeshkalaha(code, toptitle);
                                break;
                        }
                    }
                });

                b.show();

            }
        });
    }

    private static void clearalldata() {



        if (mdatasetkalaforonekala != null) {
            mdatasetkalaforonekala.clear();
        }
        if (mdatasetkalafortwokala != null) {
            mdatasetkalafortwokala.clear();
        }
        if (mdatasetListsubdastebandi != null) {
            mdatasetListsubdastebandi.clear();
        }
        if (AdapterListsubcategory != null) {
            AdapterListsubcategory.notifyDataSetChanged();
        }
        if (AdapterListkala != null) {
            AdapterListkala.notifyDataSetChanged();
        }


        mdatasetListsubdastebandi = null;
        mdatasetkalafortwokala = null;
        mdatasetkalaforonekala = null;
        layoutManager = null;
        AdapterListsubcategory = null;
        AdapterListkala = null;

        Listnumber = 0;
        showsubdastebandi = false;
        toptitle = "";
        pagetitle.setVisibility(View.GONE);
        pagetitle.setText("");
        btnsort.setVisibility(View.GONE);
        btnview.setVisibility(View.GONE);
    }

    public static void namayeshsubdastebandi(final String code) throws NullPointerException {

        clearalldata();
        showsubdastebandi = true;
        mdatasetListsubdastebandi = new ArrayList<>();
        List.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(G.CurrentActivity);
        List.setLayoutManager(layoutManager);
        AdapterListsubcategory = new SubdastebandiAdapter(mdatasetListsubdastebandi);
        List.setAdapter(AdapterListsubcategory);



        Webservice.request("subCategory/" + code , new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        namayeshsubdastebandi(code);
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (input.equals("[]")) {
                    Dialogs.vizhegiayande();
                    return;
                }
                try {
                    JSONArray array = new JSONArray(input);
                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject object = array.getJSONObject(i);
                        com.cheemarket.Structure.Subdastebandi subdastebandi = new com.cheemarket.Structure.Subdastebandi();
                        subdastebandi.Subdastebandi = object.getString("category");
                        subdastebandi.Title = object.getString("title");
                        subdastebandi.Image = object.getString("image");
                        subdastebandi.Id = object.getString("sub_category_id");
                        mdatasetListsubdastebandi.add(subdastebandi);
                    }

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            AdapterListsubcategory.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, null);


    }

    static Callback mycall;
    public static void namayeshkalaha(final String mycode, String title) throws NullPointerException {

        clearalldata();
        showsubdastebandi = false;
        toptitle = title;
        pagetitle.setVisibility(View.VISIBLE);
        pagetitle.setText(title);
        btnsort.setVisibility(View.VISIBLE);
        btnview.setVisibility(View.VISIBLE);
        code = mycode;

        layoutManager = new LinearLayoutManager(G.CurrentActivity);
        mdatasetkalafortwokala = new ArrayList<PoductStructure>();
        mdatasetkalaforonekala = new ArrayList<PoductStructure>();
        AdapterListkala = new Adapter(mdatasetkalaforonekala, mdatasetkalafortwokala, R.layout.listtwo);

        List.setHasFixedSize(true);
        List.setLayoutManager(layoutManager);
        List.setAdapter(AdapterListkala);
        List.setNestedScrollingEnabled(false);

        Commands.addview("زیر دسته بندی " + code + " بازدید شد");

        mycall  = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
                        Webservice.requestparameter object1 = new Webservice.requestparameter();
                        object1.key = "value";
                        object1.value = code;
                        array1.add(object1);
                        Webservice.requestparameter object2 = new Webservice.requestparameter();
                        object2.key = "number";
                        object2.value = Listnumber + "";
                        array1.add(object2);
                        Webservice.requestparameter object3 = new Webservice.requestparameter();
                        object3.key = "sort";
                        object3.value = sort;
                        array1.add(object3);
                        Webservice.request("productList", mycall, array1);
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                allownext = false;

                if (input.equals("[]")) {
                    if (Listnumber == 0) {
                        Dialogs.vizhegiayande();
                    }
                    return;
                }
                try {
                    JSONArray array = new JSONArray(input);
                    PoductStructure kala = new PoductStructure();
                    boolean kalaone = true;
                    Listnumber += array.length();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        if (i == 0) {
                            if (mdatasetkalafortwokala.size() > 0) {

                                if (mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1).Id2 == null || mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1).Id2.equals("")) {
                                    Commands.convertinputdata(object, mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1), false);
                                }

                            }
                        }


                        PoductStructure temp = new PoductStructure();
                        Commands.convertinputdata(object, temp, true);
                        mdatasetkalaforonekala.add(temp);


                        if (kalaone) {
                            kala = new PoductStructure();
                            Commands.convertinputdata(object, kala, kalaone);
                            if (i == array.length() - 1) {
                                mdatasetkalafortwokala.add(kala);
                            }
                        } else {
                            Commands.convertinputdata(object, kala, kalaone);
                            mdatasetkalafortwokala.add(kala);
                        }
                        kalaone = !kalaone;
                    }


                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {

                            AdapterListkala.notifyItemInserted(AdapterListkala.getItemCount() + 1);
                        }
                    });
                    allownext = true;


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        };


        ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "value";
        object1.value = code;
        array1.add(object1);
        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "number";
        object2.value = Listnumber + "";
        array1.add(object2);
        Webservice.requestparameter object3 = new Webservice.requestparameter();
        object3.key = "sort";
        object3.value = sort;
        array1.add(object3);



        Webservice.request("productList", mycall, array1);


        List.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {

                if (showsubdastebandi) {
                    return;
                }
                if (mdatasetkalafortwokala.size() > 2) {
                    TextView txtname = (TextView) view.findViewById(R.id.txtnameone);
                    if (txtname.getText().toString().equals(mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 2).Name1) || txtname.getText().toString().equals(mdatasetkalafortwokala.get(mdatasetkalafortwokala.size() - 1).Name1)) {
                        if (layoutManager.findLastVisibleItemPosition() == (mdatasetkalafortwokala.size() - 1) - 1) {

                            if (allownext == false) {
                                return;
                            }
                            allownext = false;


                            ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
                            Webservice.requestparameter object1 = new Webservice.requestparameter();
                            object1.key = "value";
                            object1.value = code;
                            array1.add(object1);
                            Webservice.requestparameter object2 = new Webservice.requestparameter();
                            object2.key = "number";
                            object2.value = Listnumber + "";
                            array1.add(object2);
                            Webservice.requestparameter object3 = new Webservice.requestparameter();
                            object3.key = "sort";
                            object3.value = sort;
                            array1.add(object3);
                            Webservice.request("productList", mycall, array1);

                        }
                    }

                }

            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {


            }
        });

    }

    @Override
    public void onBackPressed() {

        if (needtoclose) {
            super.onBackPressed();
            return;
        }
        if (showsubdastebandi == false) {
            namayeshsubdastebandi(codesubdastebandi);
        } else if (showsubdastebandi) {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        clearalldata();


    }
}
