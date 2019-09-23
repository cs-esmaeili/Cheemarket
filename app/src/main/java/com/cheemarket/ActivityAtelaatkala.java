package com.cheemarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import com.cheemarket.Customview.Lineimage;
import com.cheemarket.Customview.Sliderimage;
import com.cheemarket.Structure.sabad;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class ActivityAtelaatkala extends AppCompatActivity {

    String Datetimeserver;
    sabad mysabad;
    TextView h;
    TextView m;
    TextView s;
    int saat;
    int daghighe;
    int saniye;
    public static ViewPager viewPager;
    public static CircleIndicator circleIndicator;


    private TextView txttozihat;
    private TextView txtafzodan;
    private TextView txtprice;
    private Lineimage txtoff;
    private ImageView imgalaghemandi;
    private LinearLayout talaei;
    private ImageView imgsheare;

    private TextView txtname;
    private TextView txtcode;

    private CollapsingToolbarLayout colaps;

    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        if (G.comeback) {
            G.comeback = false;
            G.CurrentActivity.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atelaatkala);
        G.CurrentActivity = this;

        colaps = (CollapsingToolbarLayout) findViewById(R.id.colaps);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtname = (TextView) findViewById(R.id.txtname);
        txtcode = (TextView) findViewById(R.id.txtcode);
        txttozihat = (TextView) findViewById(R.id.txttozihat);
        txtafzodan = (TextView) findViewById(R.id.txtafzodan);
        txtprice = (TextView) findViewById(R.id.txtprice);
        txtoff = (Lineimage) findViewById(R.id.txtoff);
        imgalaghemandi = (ImageView) findViewById(R.id.imgalaghemandi);
        talaei = (LinearLayout) findViewById(R.id.talaei);
        imgsheare = (ImageView) findViewById(R.id.imgsheare);


        h = (TextView) findViewById(R.id.h);
        m = (TextView) findViewById(R.id.m);
        s = (TextView) findViewById(R.id.s);
        circleIndicator = findViewById(R.id.circle);
        viewPager = findViewById(R.id.view_pager);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        colaps.setCollapsedTitleTextColor(Color.WHITE);
        colaps.setExpandedTitleColor(Color.TRANSPARENT);


        mysabad = new sabad();

        Bundle extras = getIntent().getExtras();
        Log.i("LOG", "=" + extras.getString("Name"));
        Log.i("LOG", "=" + extras.getString("Image"));
        Log.i("LOG", "=" + extras.getString("Id"));
        if (extras != null) {

            if (extras.containsKey("Name") && extras.getString("Name") != null && !extras.getString("Name").equals("") && !extras.getString("Name").equals("null")) {
                mysabad.Name = extras.getString("Name");
            } else {
                mysabad.Name = "";
            }

            if (extras.containsKey("Image_folder") && extras.getString("Image_folder") != null && !extras.getString("Image_folder").equals("") && !extras.getString("Image_folder").equals("null")) {
                mysabad.Image_folder = extras.getString("Image_folder");
            } else {
                mysabad.Image_folder = "";
            }

            if (extras.containsKey("Image_thumbnail") && extras.getString("Image_thumbnail") != null && !extras.getString("Image_thumbnail").equals("") && !extras.getString("Image_thumbnail").equals("null")) {
                mysabad.Image_thumbnail = extras.getString("Image_thumbnail");
            } else {
                mysabad.Image_thumbnail = "";
            }

            if (extras.containsKey("Id") && extras.getString("Id") != null && !extras.getString("Id").equals("") && !extras.getString("Id").equals("null")) {
                mysabad.Id = extras.getString("Id");
            }


        }

        Commands.addview("کالای " + mysabad.Id + " بازدید شد");


        settextdata();
        showimage();
        setkaladata();


    }

    private void settextdata() {

        txtafzodan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (G.token.equals("")) {
                    Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                    startActivity(intent);
                    return;
                }

                boolean temp = false;
                for (int i = 0; i < G.mdatasetsabad.size(); i++) {
                    if (G.mdatasetsabad.get(i).Id.equals(mysabad.Id)) {

                        G.mdatasetsabad.get(i).Ordernumber = mysabad.Ordernumber;
                        if (Integer.parseInt(G.mdatasetsabad.get(i).Tedad) > Integer.parseInt(mysabad.Ordernumber)) {
                            G.mdatasetsabad.set(i, mysabad);
                        }

                        if ((Integer.parseInt(G.mdatasetsabad.get(i).Tedad) + 1) <= Integer.parseInt(G.mdatasetsabad.get(i).Ordernumber)) {
                            G.mdatasetsabad.get(i).Tedad = (Integer.parseInt(G.mdatasetsabad.get(i).Tedad) + 1) + "";
                        }


                        Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                        G.CurrentActivity.startActivity(intent);
                        temp = true;
                        return;
                    }
                }
                if (temp == false) {
                    G.mdatasetsabad.add(mysabad);

                    Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                    G.CurrentActivity.startActivity(intent);
                }

            }
        });


        imgsheare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "https://www.cheemarket.com/product/?productid=" + mysabad.Id;
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Cheemarket"));
            }
        });

        imgalaghemandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (G.token.equals("")) {
                    Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                } else {
                    ActivityAlaghemandiha.addtoalaghemandiha(mysabad.Id);
                }

            }
        });

        Textconfig.settext(txtcode, "کد کالا: " + mysabad.Id);

        if (mysabad.Name != null && !mysabad.Name.equals("")) {
            colaps.setTitle(mysabad.Name);
            Textconfig.settext(txtname, "نام کالا : " + mysabad.Name);
        }


    }

    private void setkaladata() {


        Webservice.request("product/" + mysabad.Id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONObject object = new JSONObject(input);
                    mysabad.Price = object.getString("price");
                    mysabad.OldPrice = object.getString("old_price");
                    mysabad.Tozihat = object.getString("description").equals("null") ? "ندارد" : object.getString("description");
                    mysabad.Ordernumber = object.getString("order_number");
                    mysabad.Status = object.getString("status");
                    mysabad.Tedad = "1";


                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Textconfig.settext(txttozihat, mysabad.Tozihat);
                            Textconfig.settext(txtprice, "" + mysabad.Price);
                            txtoff.setText("" + mysabad.OldPrice);

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                txttozihat.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                            }

                            if (mysabad.OldPrice.equals("0")) {
                                txtoff.setVisibility(View.GONE);
                            }


                            if (mysabad.Status.equals("1")) {
                                talaei.setVisibility(View.GONE);
                                txtafzodan.setEnabled(true);
                            } else if (mysabad.Status.equals("2")) {
                                talaei.setVisibility(View.VISIBLE);
                                setdateandtime();
                            } else if (mysabad.Status.equals("3")) {
                                talaei.setVisibility(View.GONE);
                                txtafzodan.setText("ناموجود");
                                txtafzodan.setBackgroundColor(Color.RED);
                                txtafzodan.setEnabled(false);
                            }


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, null);


    }

    private void showimage() {


        Webservice.request("product/images/" + mysabad.Image_folder, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String input = response.body().string();


                if (input == null || input.equals("")) {
                    return;
                }


                try {
                    final JSONArray jsonArray = new JSONArray(input);

                    final Sliderimage mysilder = new Sliderimage(G.context, ActivityAtelaatkala.viewPager, ActivityAtelaatkala.circleIndicator, R.layout.sliderlayout, R.id.image, ImageView.ScaleType.FIT_XY);
                    mysilder.removealldata();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        final int finalI = i;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    mysilder.addsilder(jsonArray.getString(finalI));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            final Sliderimage mysilder = new Sliderimage(G.context, ActivityAtelaatkala.viewPager, ActivityAtelaatkala.circleIndicator, R.layout.sliderlayout, R.id.image, ImageView.ScaleType.FIT_XY);
                            mysilder.removealldata();
                            mysilder.addsilder("www.test.com");

                        }
                    });


                    // Webservice.handelerro(null);
                }/* catch (SocketTimeoutException e) {
                    e.printStackTrace();
                    Webservice.handelerro("timeout");
                } catch (IOException e) {
                    e.printStackTrace();
                    Webservice.handelerro(null);
                }*/


            }
        }, null);

    }

    private void setdateandtime() {


        Webservice.request("getTime/" + mysabad.Id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Datetimeserver = response.body().string();
                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        txtafzodan.setEnabled(true);
                    }
                });

                if (Datetimeserver.equals("0000-00-00 00:00:00")) {
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            talaei.setVisibility(View.GONE);
                        }
                    });

                } else {


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

                                        setkaladata();
                                        showimage();

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

            }
        }, null);

    }

    @Override
    protected void onDestroy() {
        mysabad = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}