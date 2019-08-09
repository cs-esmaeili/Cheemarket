package com.cheemarket;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cheemarket.Customview.badgelogo;
import com.shuhart.stepview.StepView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import com.cheemarket.Adapter.Listpaymentadapter;
import com.cheemarket.Structure.KalaStructure;

public class Orderinformation extends AppCompatActivity {


    private static RecyclerView.Adapter AdapterList1;
    private static ArrayList<KalaStructure> mdatasetList1;
    private static RecyclerView RecyclerViewList1;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    private static RatingBar ratingBar;

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
        setContentView(R.layout.activity_orderinformation);
        G.CurrentActivity = this;

        final TextView txtname = (TextView) findViewById(R.id.txtname);
        final TextView txtphonenumber = (TextView) findViewById(R.id.txtphonenumber);
        final TextView txthomenumber = (TextView) findViewById(R.id.txthomenumber);
        final TextView txtcodeposti = (TextView) findViewById(R.id.txtcodeposti);
        final TextView txtaddress = (TextView) findViewById(R.id.txtaddress);
        final TextView txtostan = (TextView) findViewById(R.id.txtostan);
        final TextView txtshahr = (TextView) findViewById(R.id.txtshahr);
        RecyclerViewList1 = (RecyclerView) findViewById(R.id.List);
        final StepView stepView = (StepView) findViewById(R.id.step_view);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);

        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);

        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(G.CurrentActivity, R.color.colorAccent))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(G.CurrentActivity, R.color.colorAccent))
                .selectedCircleRadius(30)
                .selectedStepNumberColor(ContextCompat.getColor(G.CurrentActivity, R.color.colorPrimary))
                .doneTextColor(Color.BLACK)
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(new ArrayList<String>() {{
                    add("          پردازش");
                    add("      ارجاع به انبار");
                    add("    تحول به پیک ");
                    add(" تحویل سفارش ");
                }})
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(4)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .stepLineWidth(2)
                .textSize(40)
                .stepNumberTextSize(40)
                .commit();


        int position = -1;
        float Rate = 0.0f;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            position = extras.getInt("position");
            Rate = extras.getFloat("Rate");


        }
        ratingBar.setRating(Rate);
        if (Rate > 0.0) {
            ratingBar.setEnabled(false);
        }
        int currentvaziyat = Integer.parseInt(Orders.mdatasetList.get(position).Vaziyat);
        switch (currentvaziyat) {
            case 1:
                stepView.go(0, true);
                break;
            case 2:
                stepView.go(1, true);
                break;
            case 3:
                stepView.go(2, true);
                break;
            case 4:
                stepView.go(3, true);
                stepView.done(true);
                ratingBar.setRating(Orders.mdatasetList.get(position).Rate);
                break;
        }

        final int finalPosition = position;
        final float finalRate = Rate;
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if (fromUser && stepView.getCurrentStep() != 3) {
                    Toast.makeText(G.CurrentActivity, "بعد از تحویل کالا میتوانید نظر بدهید", Toast.LENGTH_LONG).show();
                    ratingBar.setRating(finalRate);
                } else if (fromUser) {
                    setrating(ratingBar.getRating(), finalPosition);
                    ratingBar.setEnabled(false);
                }
            }
        });

        mdatasetList1 = new ArrayList<KalaStructure>();


        RecyclerViewList1.setHasFixedSize(true);
        LayoutManagerList1 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewList1.setLayoutManager(LayoutManagerList1);
        AdapterList1 = new Listpaymentadapter(mdatasetList1);
        RecyclerViewList1.setAdapter(AdapterList1);

        AdapterList1.notifyDataSetChanged();


        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "Connectioncode";
        param1.value = G.Connectioncode;
        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "Category";
        param2.value = Orders.mdatasetList.get(position).Category + "";
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);
        Webservice.request("Store.php?action=informationorders", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context,"مشکلی در ارتیاط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                            }
                        });
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();


                try {
                    JSONArray array = new JSONArray(input);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        KalaStructure kalaStructure = new KalaStructure();

                        Commands.convertinputdata(object, kalaStructure, true);
                        kalaStructure.Ordernumber1 = object.getString("Tedad");

                        mdatasetList1.add(kalaStructure);

                    }
                    final JSONObject object = array.getJSONObject(0);
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                txtname.setText("" + object.getString("personnames"));
                                txtphonenumber.setText("" + object.getString("phonenumber"));
                                txthomenumber.setText("" + object.getString("homenumber"));
                                txtcodeposti.setText("" + object.getString("Codeposti"));
                                txtaddress.setText("" + object.getString("address"));
                                txtostan.setText("" + object.getString("Ostan"));
                                txtshahr.setText("" + object.getString("Shar"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            AdapterList1.notifyDataSetChanged();
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, array);
    }

    private void setrating(final float rate, final int position) {

        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "Connectioncode";
        param1.value = G.Connectioncode;
        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "Category";
        param2.value = Orders.mdatasetList.get(position).Category + "";

        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "rate";

        param3.value = rate + "";

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);
        array.add(param3);

        Webservice.request("Store.php?action=setrating", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context,"مشکلی در ارتیاط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                                ratingBar.setRating(0.0f);
                                ratingBar.setEnabled(true);
                            }
                        });
                        return null;
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                if (input.equals("Ok")) {
                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(G.context, "امتیاز شما با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }, array);

    }
}
