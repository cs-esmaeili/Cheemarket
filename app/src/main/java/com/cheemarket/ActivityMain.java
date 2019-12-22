package com.cheemarket;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cheemarket.Adapter.Adapter;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.PoductStructure;
import com.cheemarket.Structure.SliderStructure;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.cheemarket.ActivityStart.appLinkData;
import static com.cheemarket.G.pre;


public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static Activity thisactivity;


    private static RecyclerView List;
    private static RecyclerView RecyclerViewList1;
    private static RecyclerView RecyclerViewList4;
    private static RecyclerView RecyclerViewList6;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    private static RecyclerView.LayoutManager LayoutManagerList4;
    private static RecyclerView.LayoutManager LayoutManagerList6;
    public static RecyclerView.Adapter AdapterList1;
    public static RecyclerView.Adapter AdapterList4;
    public static RecyclerView.Adapter AdapterList6;
    public static ArrayList<PoductStructure> mdatasetList1;
    public static ArrayList<PoductStructure> mdatasetList4;
    public static ArrayList<PoductStructure> mdatasetList6;


    private static ImageView[] imgs = new ImageView[6];

    static ScrollView scroll;
    private static DrawerLayout drawer;
    private static TextView txtprofile;

    NavigationView navigationView;
    public static ViewPager viewPager;
    public static RelativeLayout silderparent;
    public static CircleIndicator circleIndicator;

    public static boolean needpagework = false;

    static String Datetimeserver;
    static TextView h;
    static TextView m;
    static TextView s;

    static int saat = 0;
    static int daghighe = 0;
    static int saniye = 0;


    private static LinearLayout layoutprofile;
    private static badgelogo badge;

    @Override
    protected void onResume() {
        super.onResume();

        Slider.setsliders();

        G.CurrentActivity = this;

        if (pre.contains("Username") && pre.contains("token")) {
            if (!pre.getString("Username", "Error").equals("Error") && !pre.getString("token", "Error").equals("")) {
                // Textconfig.settext(txtprofile, pre.getString("Username", "Error"));
                txtprofile.setText(pre.getString("Username", "Error"));
                G.token = pre.getString("token", "Error");
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

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        G.IMAGES_HEIGHT = (int) (Double.parseDouble(displayMetrics.heightPixels + "") / 2);
        G.IMAGES_WIDTH = (int) (Double.parseDouble(displayMetrics.widthPixels + "") / 2);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        layoutprofile = (LinearLayout) header.findViewById(R.id.layoutprofile);
        txtprofile = (TextView) header.findViewById(R.id.txtprofile);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);
        badge = (badgelogo) findViewById(R.id.badgelogo);


        Commands.setbadgenumber(badge);

        List = (RecyclerView) findViewById(R.id.List);
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
        silderparent = findViewById(R.id.silderparent);

        h = (TextView) findViewById(R.id.h);
        m = (TextView) findViewById(R.id.m);
        s = (TextView) findViewById(R.id.s);


        scroll = (ScrollView) findViewById(R.id.scroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);


        layoutprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (G.token.equals("")) {
                    Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                    G.CurrentActivity.startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        if (!Commands.readNetworkStatus()) {
            Intent intent = new Intent(G.CurrentActivity, ActivityNetwork.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        } else {

            applinkaction();
            Commands.getMaindastebandi("first", List);
            pagework();
        }


    }

    private void applinkaction() {
        if (appLinkData != null) {
            String productid = appLinkData.toString();

            if (productid.contains("productid=")) {
                productid = productid.substring(productid.indexOf("=") + 1);

            }
            Intent intent = new Intent(G.CurrentActivity, ActivityAtelaatkala.class);

            intent.putExtra("Id", productid);
            G.CurrentActivity.startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

    }


    public static void pagework() {

        if (pre.contains("Username") && pre.contains("Id")) {
            if (!pre.getString("Username", "Error").equals("Error") && !pre.getString("token", "Error").equals("")) {
                Textconfig.settext(txtprofile, pre.getString("Username", "Error"));
                G.token = pre.getString("token", "Error");
            }
        }
        index = -1;


        mdatasetList1 = new ArrayList<PoductStructure>();
        mdatasetList4 = new ArrayList<PoductStructure>();
        mdatasetList6 = new ArrayList<PoductStructure>();


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


        Webservice.request("firstPage", callback, null);
        Commands.addview("صفحه اصلی");

    }

    static int index = -1;


    static Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Webservice.handelerro(e, new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    Webservice.request("firstPage", callback, null);
                    return null;
                }
            }, G.CurrentActivity);


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

                    if (jsonObject.has("name") && jsonObject.getString("post_image").equals("")) {

                        PoductStructure poductStructure = new PoductStructure();

                        Commands.convertinputdata(jsonObject, poductStructure, true);

                        if (jsonObject.getString("location").equals("1")) {
                            mdatasetList1.add(poductStructure);
                        } else if (jsonObject.getString("location").equals("7")) {
                            mdatasetList4.add(poductStructure);
                        } else if (jsonObject.getString("location").equals("9")) {
                            mdatasetList6.add(poductStructure);
                        }

                    } else {

                        if (jsonObject.getString("location").equals("2")) {
                            index = 0;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("location").equals("3")) {
                            index = 1;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("location").equals("4")) {
                            index = 2;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("location").equals("5")) {
                            index = 3;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("location").equals("6")) {
                            index = 4;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("location").equals("8")) {
                            index = 5;
                            showimage(imgs[index], jsonObject);
                            setonclicks(imgs[index], jsonObject);
                        } else if (jsonObject.getString("location").equals("10")) {
                            SliderStructure sliderStructure = new SliderStructure();
                            sliderStructure.Postimage = jsonObject.getString("post_image");
                            sliderStructure.name_subcategori = jsonObject.getString("name_subcategori");
                            sliderStructure.sub_categori = jsonObject.getString("sub_categori");
                            sliderStructure.url = jsonObject.getString("url");
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
                if (mdatasetList1.size() > 0) {
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
                }

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
            Intent intent = new Intent(G.CurrentActivity, ActivityDastebandimahsolat.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.sabadkharid) {
            if (G.token.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        } else if (id == R.id.alaghemandiha) {
            if (G.token.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivityAlaghemandiha.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        } else if (id == R.id.address) {
            if (G.token.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivityAddress.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        } else if (id == R.id.yourorders) {
            if (G.token.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivityOrders.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        } else if (id == R.id.problems) {
            if (G.token.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            } else {
                Intent intent = new Intent(G.CurrentActivity, Activityproblems.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        } else if (id == R.id.darbareyema) {
            Intent intent = new Intent(G.CurrentActivity, ActivityDarbareyema.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.tamasbama) {
            Intent intent = new Intent(G.CurrentActivity, ActivityTamasbama.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.ghavanin) {
            Intent intent = new Intent(G.CurrentActivity, Activityghavanin.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.btn) {
            G.token = "";
            SharedPreferences.Editor editor = pre.edit();
            editor.putString("Username", "");
            editor.putString("token", "");
            editor.apply();
            txtprofile.setText("وارد شوید / ثبت نام کنید");

        }


        G.HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawer.closeDrawer(GravityCompat.END);
            }
        }, 2000);

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


                    }
                });
                alertDialog.show();

                break;
            }
        }

    }

    private static void showimage(final ImageView img, final JSONObject jsonObject) {

        try {
            Commands.showimage(jsonObject.getString("post_image"), null, img);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static void setonclicks(final ImageView img, final JSONObject jsonObject) {


        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                try {


                    if (jsonObject.has("sub_categori") && !jsonObject.getString("sub_categori").equals("")) {

                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        try {

                                            Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                                            intent.putExtra("subkala", jsonObject.getString("sub_categori"));
                                            intent.putExtra("NameSubcategori", jsonObject.getString("name_subcategori"));
                                            G.CurrentActivity.startActivity(intent);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });


                            }
                        });

                    } else if (jsonObject.has("name") && !jsonObject.getString("name").equals("")) {
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Commands.openactivity(jsonObject, ActivityAtelaatkala.class);
                            }
                        });
                    } else if (jsonObject.has("url") && !jsonObject.getString("url").equals("")) {
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try {
                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(jsonObject.getString("url")));
                                    G.CurrentActivity.startActivity(browserIntent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });
                    } else if (jsonObject.has("main_category") && !jsonObject.getString("main_category").equals("")) {
                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                                try {
                                    intent.putExtra("subdastebandistring", jsonObject.getString("main_category"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                G.CurrentActivity.startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
