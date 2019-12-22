package com.cheemarket;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Adapter.Listpaymentadapter;
import com.cheemarket.Customview.Dialogs;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.PoductStructure;
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

public class ActivityOrderinformation extends AppCompatActivity {


    private static RecyclerView.Adapter AdapterList1;
    private static ArrayList<PoductStructure> mdatasetList1;
    private static RecyclerView RecyclerViewList1;
    private static RecyclerView.LayoutManager LayoutManagerList1;
    private static RatingBar ratingBar;

    private badgelogo badge;
    float Rate = 0.0f;
    int position = -1;

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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            position = extras.getInt("position");
        }


        mdatasetList1 = new ArrayList<PoductStructure>();


        RecyclerViewList1.setHasFixedSize(true);
        LayoutManagerList1 = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerViewList1.setLayoutManager(LayoutManagerList1);
        AdapterList1 = new Listpaymentadapter(mdatasetList1);
        RecyclerViewList1.setAdapter(AdapterList1);

        AdapterList1.notifyDataSetChanged();


        Webservice.requestparameter param1 = new Webservice.requestparameter();
        param1.key = "token";
        param1.value = G.token;
        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "factor_id";
        param2.value = ActivityOrders.mdatasetList.get(position).factor_id + "";
        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);
        Webservice.request("factorInformation", new Callback() {
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
                }, G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();


                try {
                    JSONArray array = new JSONArray(input);

                    for (int i = 0; i < array.length(); i++) {
                        final JSONObject object = array.getJSONObject(i);

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    txtname.setText("" + object.getString("name"));
                                    txtphonenumber.setText("" + object.getString("phone_number"));
                                    txthomenumber.setText("" + object.getString("home_number"));
                                    txtcodeposti.setText("" + object.getString("postal_code"));
                                    txtaddress.setText("" + object.getString("address"));
                                    txtostan.setText("" + object.getString("state"));
                                    txtshahr.setText("" + object.getString("city"));
                                    Rate = Float.parseFloat(object.getString("rate"));

                                    ActivityOrders.mdatasetList.get(position).status = object.getString("status");
                                    ActivityOrders.mdatasetList.get(position).difference_status = object.getString("difference_status");
                                    ActivityOrders.mdatasetList.get(position).price = object.getString("price");
                                    ratingBar.setRating(Rate);
                                    if (Rate > 0.0) {
                                        ratingBar.setEnabled(false);
                                    }
                                    int currentvaziyat = Integer.parseInt(ActivityOrders.mdatasetList.get(position).status);
                                    switch (currentvaziyat) {
                                        case 1:
                                            stepView.go(0, true);
                                            break;
                                        case 2:
                                            stepView.go(1, true);
                                            break;
                                        case 3:
                                            stepView.go(1, true);
                                            break;
                                        case 7:
                                            stepView.go(2, true);
                                            break;
                                        case 8:
                                            stepView.go(3, true);
                                            stepView.done(true);
                                            ratingBar.setRating(Rate);
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

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                        JSONArray products = object.getJSONArray("products");
                        for (int j = 0; j < products.length(); j++) {
                            final JSONObject product = products.getJSONObject(j);
                            PoductStructure poductStructure = new PoductStructure();
                            poductStructure.Ordernumber1 = product.getString("number");
                            poductStructure.Name1 = product.getString("name");
                            poductStructure.Price1 = product.getString("price");
                            poductStructure.OldPrice1 = product.getString("old_price");
                            poductStructure.Image_thumbnail1 = product.getString("image_thumbnail");
                            mdatasetList1.add(poductStructure);
                        }


                    }

                    G.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            AdapterList1.notifyDataSetChanged();

                            if ((Integer.parseInt(ActivityOrders.mdatasetList.get(position).status) == 4) && (ActivityOrders.mdatasetList.get(position).difference_status.equals("give_gate"))) {
                                Dialogs.gate(true, ActivityOrders.mdatasetList.get(position).price ,  ActivityOrders.mdatasetList.get(position).factor_id + "");
                            } else if ((Integer.parseInt(ActivityOrders.mdatasetList.get(position).status) == 4) && (ActivityOrders.mdatasetList.get(position).difference_status.equals("send_card"))) {
                                Dialogs.gate(false, ActivityOrders.mdatasetList.get(position).price ,  ActivityOrders.mdatasetList.get(position).factor_id + "");
                            }

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
        param1.key = "token";
        param1.value = G.token;
        Webservice.requestparameter param2 = new Webservice.requestparameter();
        param2.key = "factor_id";
        param2.value = ActivityOrders.mdatasetList.get(position).factor_id + "";

        Webservice.requestparameter param3 = new Webservice.requestparameter();
        param3.key = "rate";

        param3.value = rate + "";

        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param1);
        array.add(param2);
        array.add(param3);

        Webservice.request("factorRating", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Webservice.handelerro(e, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "مشکلی در ارتیاط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                                ratingBar.setRating(0.0f);
                                ratingBar.setEnabled(true);
                            }
                        });
                        return null;
                    }
                }, G.CurrentActivity);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();
                try {
                    JSONObject obj = new JSONObject(input);
                    if (obj.getString("status").equals("ok")) {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "امتیاز شما با موفقیت ثبت شد", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(G.context, "شما فقط یک بار میتوانید رای بدهید", Toast.LENGTH_LONG).show();
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
