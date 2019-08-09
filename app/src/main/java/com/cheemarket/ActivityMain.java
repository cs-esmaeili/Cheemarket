package com.cheemarket;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Customview.Dialogs;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.KalaStructure;
import com.cheemarket.Structure.SliderStructure;

import static com.cheemarket.Start.appLinkData;
import static com.cheemarket.Start.pre;


public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static Activity thisactivity;


    private static RecyclerView RecyclerViewList1;
    private static RecyclerView RecyclerViewList4;
    private static RecyclerView RecyclerViewList6;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    private static RecyclerView.LayoutManager LayoutManagerList4;
    private static RecyclerView.LayoutManager LayoutManagerList6;
    public static RecyclerView.Adapter AdapterList1;
    public static RecyclerView.Adapter AdapterList4;
    public static RecyclerView.Adapter AdapterList6;
    public static ArrayList<KalaStructure> mdatasetList1;
    public static ArrayList<KalaStructure> mdatasetList4;
    public static ArrayList<KalaStructure> mdatasetList6;


    private static ImageView[] imgs = new ImageView[6];

    static ScrollView scroll;
    private static DrawerLayout drawer;
    private static TextView txtprofile;

    NavigationView navigationView;
    public static ViewPager viewPager;
    public static CircleIndicator circleIndicator;

    public static boolean needpagework = false;

    static String Datetimeserver;
    static TextView h;
    static TextView m;
    static TextView s;

    static int saat = 0;
    static int daghighe = 0;
    static int saniye = 0;

    private static CircleImageView imgkhoshbar;
    private static CircleImageView imgsayfijat;
    private static CircleImageView imgchaei;
    private static CircleImageView imgkhorma;
    private static CircleImageView imgasal;
    private static CircleImageView imgsabzijat;
    private static CircleImageView imgasasi;

    private static LinearLayout khoshbar;
    private static LinearLayout sayfijat;
    private static LinearLayout chaei;
    private static LinearLayout khorma;
    private static LinearLayout asal;
    private static LinearLayout sabzijat;
    private static LinearLayout asasi;
    private static LinearLayout layoutprofile;
    private static badgelogo badge;

    @Override
    protected void onResume() {
        super.onResume();

        Slider.setsliders();

        G.CurrentActivity = this;
        if (pre.contains("Username") && pre.contains("Connectioncode")) {
            if (!pre.getString("Username", "Error").equals("Error") && !pre.getString("Connectioncode", "Error").equals("")) {
                // Textconfig.settext(txtprofile, pre.getString("Username", "Error"));
                txtprofile.setText(pre.getString("Username", "Error"));
                G.Connectioncode = pre.getString("Connectioncode", "Error");
            }
        }


        navigationView.getMenu().findItem(R.id.btn).setChecked(false);
        navigationView.getMenu().findItem(R.id.category).setChecked(false);
        navigationView.getMenu().findItem(R.id.sabadkharid).setChecked(false);
        navigationView.getMenu().findItem(R.id.address).setChecked(false);
        navigationView.getMenu().findItem(R.id.alaghemandiha).setChecked(false);
        navigationView.getMenu().findItem(R.id.yourorders).setChecked(false);
        navigationView.getMenu().findItem(R.id.problems).setChecked(false);
        navigationView.getMenu().findItem(R.id.darbareyema).setChecked(false);
        Commands.setbadgenumber(badge);
        if (needpagework) {
            needpagework = false;
            pagework();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        thisactivity = this;
        G.CurrentActivity = this;


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        layoutprofile = (LinearLayout) header.findViewById(R.id.layoutprofile);
        txtprofile = (TextView) header.findViewById(R.id.txtprofile);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);
        badge = (badgelogo) findViewById(R.id.badgelogo);


        Commands.setbadgenumber(badge);


        RecyclerViewList1 = (RecyclerView) findViewById(R.id.List1);
        RecyclerViewList4 = (RecyclerView) findViewById(R.id.List4);
        RecyclerViewList6 = (RecyclerView) findViewById(R.id.List6);
        RecyclerViewList1.setFocusable(false);
        RecyclerViewList4.setFocusable(false);
        RecyclerViewList6.setFocusable(false);



        imgs[0] = (ImageView) findViewById(R.id.post2);
        imgs[1] = (ImageView) findViewById(R.id.post3);
        imgs[2] = (ImageView) findViewById(R.id.post4);
        imgs[3] = (ImageView) findViewById(R.id.post5);
        imgs[4] = (ImageView) findViewById(R.id.post6);
        imgs[5] = (ImageView) findViewById(R.id.post8);

        circleIndicator = findViewById(R.id.circle);
        viewPager = findViewById(R.id.view_pager);

        h = (TextView) findViewById(R.id.h);
        m = (TextView) findViewById(R.id.m);
        s = (TextView) findViewById(R.id.s);


        imgkhoshbar = (CircleImageView) findViewById(R.id.imgkhoshbar);
        imgsayfijat = (CircleImageView) findViewById(R.id.imgsayfijat);
        imgchaei = (CircleImageView) findViewById(R.id.imgchaei);
        imgkhorma = (CircleImageView) findViewById(R.id.imgkhorma);
        imgasal = (CircleImageView) findViewById(R.id.imgasal);
        imgasasi = (CircleImageView) findViewById(R.id.imgasasi);
        imgsabzijat = (CircleImageView) findViewById(R.id.imgsabzijat);


        khoshbar = (LinearLayout) findViewById(R.id.khoshbar);
        sayfijat = (LinearLayout) findViewById(R.id.sayfijat);
        chaei = (LinearLayout) findViewById(R.id.chaei);
        khorma = (LinearLayout) findViewById(R.id.khorma);
        asal = (LinearLayout) findViewById(R.id.asal);
        asasi = (LinearLayout) findViewById(R.id.asasi);
        sabzijat = (LinearLayout) findViewById(R.id.sabzijat);


        scroll = (ScrollView) findViewById(R.id.scroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(G.CurrentActivity, Subdastebandi.class);
                intent.putExtra("subdastebandistring", "" + v.getTag());
                startActivity(intent);
            }
        };




        khoshbar.setTag("dastebandi_khoshkbar");
        sayfijat.setTag("dastebandi_seyfijat");
        chaei.setTag("dastebandi_chay");
        khorma.setTag("dastebandi_khorma");
        asal.setTag("dastebandi_asal");
        asasi.setTag("dastebandi_kalahayeasasi");
        sabzijat.setTag("dastebandi_sabzijat");
        
        khoshbar.setOnClickListener(onClickListener);
        sayfijat.setOnClickListener(onClickListener);
        chaei.setOnClickListener(onClickListener);
        khorma.setOnClickListener(onClickListener);
        asal.setOnClickListener(onClickListener);
        asasi.setOnClickListener(onClickListener);
        sabzijat.setOnClickListener(onClickListener);


        layoutprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (G.Connectioncode.equals("")) {
                    Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                    G.CurrentActivity.startActivity(intent);
                }
            }
        });

        if (!Commands.readNetworkStatus()) {
            Intent intent = new Intent(G.CurrentActivity, activityNetwork.class);
            startActivity(intent);

        } else {
            // addview.add("App");
            applinkaction();
            getimageurls();
            pagework();
        }


    }

    private static void applinkaction() {
        if(appLinkData != null){
            String productid = appLinkData.toString();

            if(productid.contains("productid=")){
                productid = productid.substring(productid.indexOf("=") + 1);

            }
            Intent intent = new Intent(G.CurrentActivity,ActivityAtelaatkala.class);
            intent.putExtra("Id" , productid);
            G.CurrentActivity.startActivity(intent);
        }

    }


    public static void pagework() {

        if (pre.contains("Username") && pre.contains("Id")) {
            if (!pre.getString("Username", "Error").equals("Error") && !pre.getString("Connectioncode", "Error").equals("")) {
                Textconfig.settext(txtprofile, pre.getString("Username", "Error"));
                G.Connectioncode = pre.getString("Connectioncode", "Error");
            }
        }
        index = -1;

        Dialogs.Checkpermissions();

        mdatasetList1 = new ArrayList<KalaStructure>();
        mdatasetList4 = new ArrayList<KalaStructure>();
        mdatasetList6 = new ArrayList<KalaStructure>();


        RecyclerViewList1.setHasFixedSize(true);
        LayoutManagerList1 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, true);
        RecyclerViewList1.setLayoutManager(LayoutManagerList1);
        AdapterList1 = new Adapter(null, mdatasetList1, R.layout.listone);
        RecyclerViewList1.setAdapter(AdapterList1);


        RecyclerViewList4.setHasFixedSize(true);
        LayoutManagerList4 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, true);
        RecyclerViewList4.setLayoutManager(LayoutManagerList4);
        AdapterList4 = new Adapter(null, mdatasetList4, R.layout.listone);
        RecyclerViewList4.setAdapter(AdapterList4);


        RecyclerViewList6.setHasFixedSize(true);
        LayoutManagerList6 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, true);
        RecyclerViewList6.setLayoutManager(LayoutManagerList6);
        AdapterList6 = new Adapter(null, mdatasetList6, R.layout.listone);
        RecyclerViewList6.setAdapter(AdapterList6);





        Webservice.request("Store.php?action=Firstpagedata", callback, null);

    }

    static int index = -1;


    static okhttp3.Callback callback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Webservice.handelerro(e, new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    Webservice.request("Store.php?action=Firstpagedata", callback, null);
                    return null;
                }
            });


        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            try {
                index = -1;
                mdatasetList1.clear();
                mdatasetList4.clear();
                mdatasetList6.clear();
                Slider.array.clear();

                String input = response.body().string();
                JSONArray array = new JSONArray(input);


                for (int i = 0; i < array.length(); i++) {
                    final JSONObject jsonObject = array.getJSONObject(i);

                    if (jsonObject.has("Name") && jsonObject.getString("Postimage").equals("")) {

                        KalaStructure kalaStructure = new KalaStructure();

                        Commands.convertinputdata(jsonObject, kalaStructure, true);

                        if (jsonObject.getString("Location").equals("1")) {
                            mdatasetList1.add(kalaStructure);
                        } else if (jsonObject.getString("Location").equals("7")) {
                            mdatasetList4.add(kalaStructure);
                        } else if (jsonObject.getString("Location").equals("9")) {
                            mdatasetList6.add(kalaStructure);
                        }

                    } else {

                        if (jsonObject.getString("Location").equals("2")) {
                            index = 0;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("Location").equals("3")) {
                            index = 1;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("Location").equals("4")) {
                            index = 2;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("Location").equals("5")) {
                            index = 3;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("Location").equals("6")) {
                            index = 4;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("Location").equals("8")) {
                            index = 5;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("Location").equals("10")) {
                            SliderStructure sliderStructure = new SliderStructure();
                            sliderStructure.Postimage = jsonObject.getString("Postimage");
                            sliderStructure.Caption = "";
                            Slider.array.add(sliderStructure);
                        }


                    }


                }

                Slider.setsliders();


                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        AdapterList1.notifyDataSetChanged();
                        AdapterList4.notifyDataSetChanged();
                        AdapterList6.notifyDataSetChanged();
                    }
                });
                Datetimeserver = mdatasetList1.get(0).Datetime1;
                if (Datetimeserver.equals("0000-00-00 00:00:00")) {
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            h.setTypeface(Textconfig.gettypeface());
                            m.setTypeface(Textconfig.gettypeface());
                            s.setTypeface(Textconfig.gettypeface());
                            h.setText(saat + "");
                            m.setText(daghighe + "");
                            s.setText(saniye + "");


                        }
                    });

                    return;
                }

                int roz = Integer.parseInt(Datetimeserver.substring(Datetimeserver.lastIndexOf("-") + 1, Datetimeserver.lastIndexOf("-") + 3).trim());
                roz = roz * 24;
                saat = roz + Integer.parseInt(Datetimeserver.substring(Datetimeserver.indexOf(" ") + 1, Datetimeserver.indexOf(" ") + 3).replace(":", ""));///
                daghighe = Integer.parseInt(Datetimeserver.substring(Datetimeserver.indexOf(":") + 1, Datetimeserver.indexOf(":") + 3).replace(":", ""));

                saniye = Integer.parseInt(Datetimeserver.substring(Datetimeserver.lastIndexOf(":") + 1, Datetimeserver.length()));


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            while (true) {

                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        h.setTypeface(Textconfig.gettypeface());
                                        m.setTypeface(Textconfig.gettypeface());
                                        s.setTypeface(Textconfig.gettypeface());
                                        h.setText(saat + "");
                                        m.setText(daghighe + "");
                                        s.setText(saniye + "");


                                    }
                                });

                                if (saat == 0 && daghighe == 0 && saniye == 0) {

                                    break;
                                }
                                Thread.sleep(1000);
                                if (saniye > 0) {
                                    saniye--;
                                } else if (daghighe > 0) {
                                    daghighe--;
                                    saniye = 60;
                                } else if (saat > 0) {
                                    saat--;
                                    daghighe = 60;

                                }


                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            drawer.openDrawer(GravityCompat.END);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.category) {
            Intent intent = new Intent(G.CurrentActivity, Dastebandimahsolat.class);
            startActivity(intent);
        } else if (id == R.id.sabadkharid) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                startActivity(intent);
            }

        } else if (id == R.id.alaghemandiha) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(G.CurrentActivity, Alaghemandiha.class);
                startActivity(intent);
            }

        } else if (id == R.id.address) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivityAddress.class);
                startActivity(intent);
            }

        } else if (id == R.id.yourorders) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(G.CurrentActivity, Orders.class);
                startActivity(intent);
            }
        } else if (id == R.id.problems) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(G.CurrentActivity, Activityproblems.class);
                startActivity(intent);
            }
        }else if (id == R.id.darbareyema) {
                Intent intent = new Intent(G.CurrentActivity, ActivityDarbareyema.class);
                startActivity(intent);
        } else if (id == R.id.btn) {
            G.Connectioncode = "";
            SharedPreferences.Editor editor = pre.edit();
            editor.putString("Username", "");
            editor.putString("Connectioncode", "");
            editor.apply();
            txtprofile.setText("وارد شوید / ثبت نام کنید");

        }


        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(G.context, permission) != PackageManager.PERMISSION_GRANTED) {

                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

                alertDialog.setTitle("دسترسی ها");
                alertDialog.setMessage("شما درسترسی های زیر  را رد کردید این برنامه بدون این درسترسی ها کار نمیکند" +
                        "\n" + permission);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "تلاش دوباره", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                        alertDialog.dismiss();
                        Dialogs.Checkpermissions();

                    }
                });
                alertDialog.show();

                break;
            }
        }

    }

    private static void showimage(final ImageView img, final JSONObject jsonObject) {

        try {
            Commands.showimage(G.Baseurl + "Listimages/" + jsonObject.getString("Postimage") + "/" + jsonObject.getString("Postimage") + ".jpg", null, img, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void setonclicks(final ImageView img, final JSONObject jsonObject) {


        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                try {


                    if (jsonObject.has("Subcategori") && !jsonObject.getString("Subcategori").equals("")) {

                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {

                                            Intent intent = new Intent(G.CurrentActivity, Subdastebandi.class);
                                            intent.putExtra("subkala", jsonObject.getString("Subcategori"));
                                            intent.putExtra("NameSubcategori", jsonObject.getString("NameSubcategori"));
                                            G.CurrentActivity.startActivity(intent);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });


                            }
                        });

                    } else if (jsonObject.has("Name") && !jsonObject.getString("Name").equals("")) {
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Commands.openactivity(jsonObject, ActivityAtelaatkala.class);


                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void getimageurls() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                final ImageView[] imageViews = new ImageView[]{imgasasi, imgkhoshbar, imgkhorma, imgsayfijat, imgsabzijat, imgchaei, imgasal};
                Webservice.request("Dastebandi_Images/indexs.php", new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        Webservice.handelerro(e, new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                getimageurls();
                                return null;
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String input = response.body().string();

                        if (input != "") {
                            try {
                                final JSONArray array = new JSONArray(input);
                                int temp = 0;
                                for (int i = 0; i < 7; i++) {


                                    if (i == 1) {
                                        temp = 1;
                                    } else if (i == 2) {
                                        temp = 3;
                                    } else if (i == 3) {
                                        temp = 12;
                                    } else if (i == 4) {
                                        temp = 13;
                                    } else if (i == 5) {
                                        temp = 14;
                                    } else if (i == 6) {
                                        temp = 15;
                                    }


                                    final int finalTemp = temp;
                                    final int finalI = i;
                                    G.HANDLER.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            try {
                                                Commands.showimage(array.get(finalTemp).toString(), null, imageViews[finalI], true);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, null);

            }
        }).start();

    }
}
