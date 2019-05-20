package www.cheemarket.com.javadesmaeili;

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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Response;
import www.cheemarket.com.javadesmaeili.Customview.Dialogs;
import www.cheemarket.com.javadesmaeili.Adapter.Adapter;
import www.cheemarket.com.javadesmaeili.Structure.KalaStructure;
import www.cheemarket.com.javadesmaeili.Structure.SliderStructure;

import static www.cheemarket.com.javadesmaeili.Start.appLinkData;

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
    public static SharedPreferences pre;
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


    @Override
    protected void onResume() {
        super.onResume();

        Slider.setsliders();

        G.CurrentActivity = this;
        if (pre.contains("Username") && pre.contains("Connectioncode")) {
            if (!pre.getString("Username", "Error").equals("Error") && !pre.getString("Connectioncode", "Error").equals("")) {
                Textconfig.settext(txtprofile, pre.getString("Username", "Error"));
                G.Connectioncode = pre.getString("Connectioncode", "Error");
            }
        }


        navigationView.getMenu().findItem(R.id.exit).setChecked(false);
        navigationView.getMenu().findItem(R.id.category).setChecked(false);
        navigationView.getMenu().findItem(R.id.sabadkharid).setChecked(false);
        navigationView.getMenu().findItem(R.id.address).setChecked(false);

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

        pre = getSharedPreferences("Cheemarket", MODE_PRIVATE);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        txtprofile = (TextView) header.findViewById(R.id.txtprofile);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

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


        scroll = (ScrollView) findViewById(R.id.scroll);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        searchlogo.setOnClickListener(G.onClickListenersearch);
        shoplogo.setOnClickListener(G.onClickListenersabadkharid);

        if (!G.readNetworkStatus()) {
            Intent intent = new Intent(G.CurrentActivity, activityNetwork.class);
            startActivity(intent);

        } else {
            // addview.add("App");
            applinkaction();
            pagework();
        }


    }

    private static void applinkaction() {
        if (appLinkData != null) {
            String productid = appLinkData.getLastPathSegment();
            if (productid.contains("productid=")) {
                productid = productid.substring(productid.indexOf("=") + 1);
                Log.i("LOG", "productid =" + productid);

                Intent intent = new Intent(G.CurrentActivity, ActivityAtelaatkala.class);
                intent.putExtra("Id", productid);
                G.CurrentActivity.startActivity(intent);
            }

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
        LayoutManagerList1 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewList1.setLayoutManager(LayoutManagerList1);
        AdapterList1 = new Adapter(null, mdatasetList1, R.layout.listone);
        RecyclerViewList1.setAdapter(AdapterList1);


        RecyclerViewList4.setHasFixedSize(true);
        LayoutManagerList4 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewList4.setLayoutManager(LayoutManagerList4);
        AdapterList4 = new Adapter(null, mdatasetList4, R.layout.listone);
        RecyclerViewList4.setAdapter(AdapterList4);


        RecyclerViewList6.setHasFixedSize(true);
        LayoutManagerList6 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewList6.setLayoutManager(LayoutManagerList6);
        AdapterList6 = new Adapter(null, mdatasetList6, R.layout.listone);
        RecyclerViewList6.setAdapter(AdapterList6);


        txtprofile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (G.Connectioncode.equals("")) {
                    Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                    G.CurrentActivity.startActivity(intent);
                }
            }
        });


        Webservice.request("Store.php?action=Firstpagedata", callback, null);

    }

    static int index = -1;


    static okhttp3.Callback callback = new okhttp3.Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            if (e instanceof SocketTimeoutException) {
                e.printStackTrace();
                Webservice.handelerro("timeout");
            } else {
                e.printStackTrace();
                Webservice.handelerro(null);
            }

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
                Log.i("LOG", "body =" + input);

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
                            Textconfig.settext(h, saat + "");
                            Textconfig.settext(m, daghighe + "");
                            Textconfig.settext(s, saniye + "");

                        }
                    });

                    return;
                }
                Log.i("LOG", "Date =" + Datetimeserver);
                int roz = Integer.parseInt(Datetimeserver.substring(Datetimeserver.lastIndexOf("-") + 1, Datetimeserver.lastIndexOf("-") + 3).trim());
                roz = roz * 24;
                saat = roz + Integer.parseInt(Datetimeserver.substring(Datetimeserver.indexOf(" ") + 1, Datetimeserver.indexOf(" ") + 3).replace(":", ""));///
                daghighe = Integer.parseInt(Datetimeserver.substring(Datetimeserver.indexOf(":") + 1, Datetimeserver.indexOf(":") + 3).replace(":", ""));

                saniye = Integer.parseInt(Datetimeserver.substring(Datetimeserver.lastIndexOf(":") + 1, Datetimeserver.length()));

                Log.i("LOG", "saat =" + saat + "//daghighe =" + daghighe + "//" + saniye);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            while (true) {

                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Textconfig.settext(h, saat + "");
                                        Textconfig.settext(m, daghighe + "");
                                        Textconfig.settext(s, saniye + "");

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
                Webservice.handelerro(null);
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                Webservice.handelerro("timeout");
            } catch (IOException e) {
                e.printStackTrace();
                Webservice.handelerro(null);
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
        } else if (id == R.id.exit) {
            G.Connectioncode = "";
            SharedPreferences.Editor editor = ActivityMain.pre.edit();
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
            Commands.showimage(G.Baseurl + "Listimages/" + jsonObject.getString("Postimage") + "/" + jsonObject.getString("Postimage") + ".png", null, img, true);
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

                                            Intent intent = new Intent(G.CurrentActivity, Subdastebandi.class);//Subdastebandi
                                            intent.putExtra("subkala", jsonObject.getString("Subcategori"));
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

                                Toast.makeText(G.context, "Width =" + img.getWidth() + " ?? Height =" + img.getHeight(), Toast.LENGTH_LONG).show();
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
}
