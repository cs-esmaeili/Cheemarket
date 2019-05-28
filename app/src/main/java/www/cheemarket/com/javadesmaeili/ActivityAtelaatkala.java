package www.cheemarket.com.javadesmaeili;

import android.content.Intent;
import android.graphics.Color;
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
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import www.cheemarket.com.javadesmaeili.Customview.Lineimage;
import www.cheemarket.com.javadesmaeili.Customview.Sliderimage;
import www.cheemarket.com.javadesmaeili.Structure.sabad;


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

    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
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
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
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
        if (extras != null) {

            if (extras.containsKey("Name") && extras.getString("Name") != null && !extras.getString("Name").equals("") && !extras.getString("Name").equals("null")) {
                mysabad.Name = extras.getString("Name");
            } else {
                mysabad.Name = "";
            }
            if (extras.containsKey("Weight") && extras.getString("Weight") != null && !extras.getString("Weight").equals("") && !extras.getString("Weight").equals("null")) {
                mysabad.Weight = extras.getString("Weight");
            } else {
                mysabad.Weight = "";
            }
            if (extras.containsKey("Volume") && extras.getString("Volume") != null && !extras.getString("Volume").equals("") && !extras.getString("Volume").equals("null")) {

                mysabad.Volume = extras.getString("Volume");
            } else {
                mysabad.Volume = "";
            }
            if (extras.containsKey("Image") && extras.getString("Image") != null && !extras.getString("Image").equals("") && !extras.getString("Image").equals("null")) {
                mysabad.Image = extras.getString("Image");
            } else {
                mysabad.Image = "";
            }

            if (extras.containsKey("Id") && extras.getString("Id") != null && !extras.getString("Id").equals("") && !extras.getString("Id").equals("null")) {
                mysabad.Id = extras.getString("Id");
            }


        }


        if (mysabad.Name == null || mysabad.Name.equals("")) {
            setkaladata(true);
        } else {
            settextdata();
            showimage();
            setkaladata(false);
        }


    }

    private void settextdata() {

        txtafzodan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean temp = false;
                for (int i = 0; i < G.mdatasetsabad.size(); i++) {
                    if (G.mdatasetsabad.get(i).Id.equals(mysabad.Id)) {
                        G.mdatasetsabad.get(i).Tedad = (Integer.parseInt(G.mdatasetsabad.get(i).Tedad) + 1) + "";
                        Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                        G.CurrentActivity.startActivity(intent);
                        temp = true;
                        return;
                    }
                }
                if(temp == false){
                    G.mdatasetsabad.add(mysabad);
                    Log.i("LOG", mysabad.Id + " ?? " +G.mdatasetsabad.get(0).Id);
                    Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                    G.CurrentActivity.startActivity(intent);
                }

            }
        });


        imgsheare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = "www.cheemarket.com/product/?productid=" + mysabad.Id;
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Cheemarket"));
            }
        });

        imgalaghemandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alaghemandiha.addtoalaghemandiha(mysabad.Id);
            }
        });

        Textconfig.settext(txtcode, "کد کالا: " + mysabad.Id);

        if (mysabad.Name != null && !mysabad.Name.equals("")) {
            colaps.setTitle(mysabad.Name);
            Textconfig.settext(txtname, "نام کالا : " + mysabad.Name);
        }

        if (mysabad.Weight == null || mysabad.Weight.equals("")) {
            txt1.setVisibility(View.GONE);
        } else {
            Textconfig.settext(txt1, "وزن کالا : " + mysabad.Weight);
        }

        if (mysabad.Volume == null || mysabad.Volume.equals("")) {
            txt2.setVisibility(View.GONE);
        } else {
            Textconfig.settext(txt2, "حجم کالا : " + mysabad.Volume);
        }

    }

    private void setkaladata(final boolean alldata) {

        ArrayList<Webservice.requestparameter> list = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "kalaid";
        object1.value = mysabad.Id;

        if (alldata) {
            Webservice.requestparameter object2 = new Webservice.requestparameter();
            object2.key = "Alldata";
            object2.value = "true";
            list.add(object2);
        }

        list.add(object1);
        Webservice.request("Store.php?action=onekaladata", new Callback() {
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
                String input = response.body().string();
                try {
                    JSONArray array = new JSONArray(input);

                    JSONObject object = array.getJSONObject(0);
                    mysabad.Code = object.getString("Code");
                    mysabad.Price = object.getString("Price");
                    mysabad.OldPrice = object.getString("OldPrice");
                    mysabad.Tozihat = object.getString("Tozihat").equals("null") ? "ندارد" : object.getString("Tozihat");
                    mysabad.Ordernumber = object.getString("Ordernumber");
                    mysabad.Status = object.getString("Status");
                    mysabad.Tedad = "1";

                    if (alldata) {
                        mysabad.Name = object.getString("Name");
                        mysabad.Weight = object.getString("Weight");
                        mysabad.Volume = object.getString("Volume");
                        mysabad.Image = object.getString("Image");
                    }

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Textconfig.settext(txt3, "بارکد درج شده بر روی کالا : " + mysabad.Code);
                            Textconfig.settext(txttozihat, mysabad.Tozihat);
                            Textconfig.settext(txtprice, "" + mysabad.Price);
                            txtoff.setText("" + mysabad.OldPrice);

                            if (mysabad.OldPrice.equals("0")) {
                                txtoff.setVisibility(View.GONE);
                            }


                            if (mysabad.Status.equals("1")) {
                                talaei.setVisibility(View.GONE);
                                txtafzodan.setClickable(true);
                            } else if (mysabad.Status.equals("2")) {
                                talaei.setVisibility(View.VISIBLE);
                                setdateandtime();
                            } else if (mysabad.Status.equals("3")) {
                                talaei.setVisibility(View.GONE);
                                txtafzodan.setText("ناموجود");
                                txtafzodan.setBackgroundColor(Color.RED);
                                txtafzodan.setClickable(false);
                            }

                            if (alldata) {
                                settextdata();
                                showimage();
                            }


                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, list);


    }

    private void showimage() {


        ArrayList<Webservice.requestparameter> array1 = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "Foldername";
        object1.value = mysabad.Image + "";
        array1.add(object1);
        Webservice.request("Store.php?action=images", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                String input = response.body().string();


                if (input == null || input.equals("")) {
                    return;
                }


                try {
                    final JSONArray jsonArray = new JSONArray(input);

                    final Sliderimage mysilder = new Sliderimage(G.context, ActivityAtelaatkala.viewPager, ActivityAtelaatkala.circleIndicator, R.layout.sliderlayout, R.id.image, ImageView.ScaleType.CENTER_CROP);
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
                            final Sliderimage mysilder = new Sliderimage(G.context, ActivityAtelaatkala.viewPager, ActivityAtelaatkala.circleIndicator, R.layout.sliderlayout, R.id.image, ImageView.ScaleType.CENTER_CROP);
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
        }, array1);

    }

    private void setdateandtime() {

        ArrayList<Webservice.requestparameter> array2 = new ArrayList<>();
        Webservice.requestparameter object2 = new Webservice.requestparameter();
        object2.key = "kalaid";
        object2.value = mysabad.Id;
        array2.add(object2);
        Webservice.request("Store.php?action=gettime", new Callback() {
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

                Datetimeserver = response.body().string();
                G.HANDLER.post(new Runnable() {
                    @Override
                    public void run() {
                        txtafzodan.setClickable(true);
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
                                            h.setText(saat + "");
                                            m.setText(daghighe + "");
                                            s.setText(saniye + "");

                                        }
                                    });

                                    if (saat == 0 && daghighe == 0 && saniye == 0) {

                                        setkaladata(false);
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
        }, array2);

    }
}