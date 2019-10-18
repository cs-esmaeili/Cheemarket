package com.cheemarket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cheemarket.Adapter.Maindastebandiadapter;
import com.cheemarket.Adapter.Maindastebandifirstpageadapter;
import com.cheemarket.Customview.badgelogo;
import com.cheemarket.Structure.Maindastebandi;
import com.cheemarket.Structure.Maindastebandifistpage;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.cheemarket.Structure.PoductStructure;

import okhttp3.Call;
import okhttp3.Response;

public class Commands {


    public static void convertinputdata(JSONObject jsonObject, PoductStructure poductStructure, boolean kalaone) {

        if (kalaone) {
            try {
                poductStructure.Name1 = jsonObject.getString("name");
                poductStructure.Price1 = jsonObject.getString("price");
                poductStructure.OldPrice1 = jsonObject.getString("old_price");
                poductStructure.Image_thumbnail1 = jsonObject.getString("image_thumbnail");
                poductStructure.Image_folder1 = jsonObject.getString("image_folder");
                poductStructure.Status1 = jsonObject.getString("status");
                poductStructure.Datetime1 = jsonObject.getString("datetime");
                poductStructure.Id1 = jsonObject.getString("product_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                poductStructure.Name2 = jsonObject.getString("name");
                poductStructure.Price2 = jsonObject.getString("price");
                poductStructure.OldPrice2 = jsonObject.getString("old_price");
                poductStructure.Image_thumbnail2 = jsonObject.getString("image_thumbnail");
                poductStructure.Image_folder2 = jsonObject.getString("image_folder");
                poductStructure.Status2 = jsonObject.getString("status");
                poductStructure.Datetime2 = jsonObject.getString("datetime");
                poductStructure.Id2 = jsonObject.getString("product_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public static void openactivity(ArrayList<PoductStructure> mdataset, int position, boolean one, Class<? extends Activity> target) {


        Intent intent = new Intent(G.CurrentActivity, target);
        if (one) {
            intent.putExtra("Name", mdataset.get(position).Name1);
            intent.putExtra("Image_folder", mdataset.get(position).Image_folder1);
            intent.putExtra("Image_thumbnail", mdataset.get(position).Image_thumbnail1);
            intent.putExtra("Id", mdataset.get(position).Id1);
        } else {
            intent.putExtra("Name", mdataset.get(position).Name2);
            intent.putExtra("Image_folder", mdataset.get(position).Image_folder2);
            intent.putExtra("Image_thumbnail", mdataset.get(position).Image_thumbnail2);
            intent.putExtra("Id", mdataset.get(position).Id2);
        }

        G.CurrentActivity.startActivity(intent);
        G.CurrentActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

    }


    public static void openactivity(JSONObject jsonObject, Class<? extends Activity> target) {

        try {
            Intent intent = new Intent(G.CurrentActivity, target);
            intent.putExtra("Name", jsonObject.getString("name"));
            intent.putExtra("Image", jsonObject.getString("image"));
            intent.putExtra("Id", jsonObject.getString("product_id"));

            G.CurrentActivity.startActivity(intent);
            G.CurrentActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public static void showimage(@Nullable final String url, @Nullable final Integer src, final ImageView img) {


        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {

                if (url != null) {


                    Picasso.get()
                            .load(url)
                            // .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            .fit().centerInside()
                            .error(R.drawable.brokenimage)
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(img).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {


                                }
                            });

                } else if (src != null) {
                    Picasso.get()
                            .load(src)
                            //  .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            .fit().centerInside()
                            .error(R.drawable.brokenimage)
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(img).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {


                                }
                            });
                }


            }
        });
    }

    public static View.OnClickListener onClickListenersearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(G.CurrentActivity, ActivitySearch.class);
            G.CurrentActivity.startActivity(intent);
            G.CurrentActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        }
    };

    public static View.OnClickListener onClickListenersabadkharid = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (G.token.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                G.CurrentActivity.startActivity(intent);
                G.CurrentActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                G.CurrentActivity.startActivity(intent);
                G.CurrentActivity.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }

        }
    };


    public static boolean readNetworkStatus() {
        ConnectivityManager connectivityManager = (ConnectivityManager) G.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        }

        boolean isConnected = networkInfo.isConnected();


        if (isConnected) {
            return true;

        } else {
            return false;
        }


    }

    public static void setbadgenumber(badgelogo badge) {
        badge.setNumber(G.mdatasetsabad.size());
    }

    public static void addview(String location) {


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "location";
        object1.value = location;

        array.add(object1);


        Webservice.request("view", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        }, array);

    }


    public static void getMaindastebandi(final String page, final RecyclerView List) {

        Webservice.requestparameter param = new Webservice.requestparameter();
        param.key = "page";
        param.value = page;
        final ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        array.add(param);

        final ArrayList<Maindastebandifistpage> dastebandihafirstpage = new ArrayList<>();
        final ArrayList<Maindastebandi> dastebandiha = new ArrayList<>();
        Webservice.request("mainCategory", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (!input.equals("[]")) {
                    try {
                        JSONArray array1 = new JSONArray(input);
                        int index = 1;
                        for (int i = 0; i < array1.length(); i++) {

                            for (int j = 0; j < array1.length(); j++) {
                                JSONObject obj = array1.getJSONObject(j);


                                if (page.equals("first") && Integer.parseInt(obj.getString("firstpage_index")) != index) {
                                    continue;
                                }

                                if (page.equals("main") && Integer.parseInt(obj.getString("category_index")) != index) {
                                    continue;
                                }

                                index++;


                                if (page.equals("first")) {
                                    Maindastebandifistpage maindastebandifistpage = new Maindastebandifistpage();
                                    maindastebandifistpage.Title = obj.getString("title");
                                    maindastebandifistpage.Image = obj.getString("image");

                                    maindastebandifistpage.Id = obj.getString("main_category_id");
                                    dastebandihafirstpage.add(maindastebandifistpage);
                                }

                                if (page.equals("main")) {


                                    if (dastebandiha.size() > 0 && dastebandiha.get(dastebandiha.size() - 1).Id2 == null) {
                                        dastebandiha.get(dastebandiha.size() - 1).Title2 = obj.getString("title");
                                        dastebandiha.get(dastebandiha.size() - 1).Image2 = obj.getString("image");
                                        dastebandiha.get(dastebandiha.size() - 1).Id2 = obj.getString("main_category_id");
                                    } else {
                                        Maindastebandi maindastebandifistpage = new Maindastebandi();
                                        maindastebandifistpage.Title1 = obj.getString("title");
                                        maindastebandifistpage.Image1 = obj.getString("image");
                                        maindastebandifistpage.Id1 = obj.getString("main_category_id");
                                        dastebandiha.add(maindastebandifistpage);
                                    }

                                }

                            }


                        }

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {

                                if (page.equals("first")) {
                                    List.setHasFixedSize(true);
                                    RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.HORIZONTAL, true);
                                    List.setLayoutManager(LayoutManagerList);
                                    RecyclerView.Adapter AdapterList = new Maindastebandifirstpageadapter(dastebandihafirstpage);
                                    List.setAdapter(AdapterList);
                                } else {
                                    List.setHasFixedSize(true);
                                    RecyclerView.LayoutManager LayoutManagerList = new LinearLayoutManager(G.CurrentActivity, LinearLayoutManager.VERTICAL, false);
                                    List.setLayoutManager(LayoutManagerList);
                                    RecyclerView.Adapter AdapterList = new Maindastebandiadapter(dastebandiha);
                                    List.setAdapter(AdapterList);
                                }

                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, array);


    }

}

