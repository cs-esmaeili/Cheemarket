package com.cheemarket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.cheemarket.Customview.badgelogo;
import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import com.cheemarket.Structure.KalaStructure;

import okhttp3.Call;
import okhttp3.Response;

public class Commands {


    public static void convertinputdata(JSONObject jsonObject, KalaStructure kalaStructure, boolean kalaone) {

        if (kalaone) {
            try {
                kalaStructure.Name1 = jsonObject.getString("Name");
                kalaStructure.Weight1 = jsonObject.getString("Weight");
                kalaStructure.Price1 = jsonObject.getString("Price");
                kalaStructure.OldPrice1 = jsonObject.getString("OldPrice");
                kalaStructure.Image1 = jsonObject.getString("Image");
                kalaStructure.Status1 = jsonObject.getString("Status");
                kalaStructure.Datetime1 = jsonObject.getString("Datetime");
                kalaStructure.Id1 = jsonObject.getString("Id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                kalaStructure.Name2 = jsonObject.getString("Name");
                kalaStructure.Weight2 = jsonObject.getString("Weight");
                kalaStructure.Price2 = jsonObject.getString("Price");
                kalaStructure.OldPrice2 = jsonObject.getString("OldPrice");
                kalaStructure.Image2 = jsonObject.getString("Image");
                kalaStructure.Status2 = jsonObject.getString("Status");
                kalaStructure.Datetime2 = jsonObject.getString("Datetime");
                kalaStructure.Id2 = jsonObject.getString("Id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public static void openactivity(ArrayList<KalaStructure> mdataset, int position, boolean one, Class<? extends Activity> target) {


        Intent intent = new Intent(G.CurrentActivity, target);

        if (one) {
            intent.putExtra("Name", mdataset.get(position).Name1);
            intent.putExtra("Weight", mdataset.get(position).Weight1);
            intent.putExtra("Image", mdataset.get(position).Image1);
            intent.putExtra("Id", mdataset.get(position).Id1);
        } else {
            intent.putExtra("Name", mdataset.get(position).Name2);
            intent.putExtra("Weight", mdataset.get(position).Weight2);
            intent.putExtra("Image", mdataset.get(position).Image2);
            intent.putExtra("Id", mdataset.get(position).Id2);
        }

        G.CurrentActivity.startActivity(intent);


    }


    public static void openactivity(JSONObject jsonObject, Class<? extends Activity> target) {

        try {
            Intent intent = new Intent(G.CurrentActivity, target);
            intent.putExtra("Name", jsonObject.getString("Name"));
            intent.putExtra("Weight", jsonObject.getString("Weight"));
            intent.putExtra("Volume", jsonObject.getString("Volume"));
            intent.putExtra("Image", jsonObject.getString("Image"));
            intent.putExtra("Id", jsonObject.getString("Id"));

            G.CurrentActivity.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public static void showimage(@Nullable final String url, @Nullable final Integer src, final ImageView img, final boolean trytoload) {


        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {

                if (url != null) {


                    Picasso.get()
                            .load(url)
                            .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            // .memoryPolicy(MemoryPolicy.NO_CACHE)
                            //  .networkPolicy(NetworkPolicy.NO_CACHE)
                            .error(R.drawable.brokenimage)
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(img).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {


                                    if (trytoload) {
                                        //    showimage(url, src, img, false);
                                    }


                                }
                            });

                } else if (src != null) {
                    Picasso.get()
                            .load(src)
                            .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            //   .memoryPolicy(MemoryPolicy.NO_CACHE)
                            //  .networkPolicy(NetworkPolicy.NO_CACHE)
                            .error(R.drawable.brokenimage)
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(img).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {

                                    if (trytoload) {
                                        //   showimage(url, src, img, false);
                                    }

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
        }
    };

    public static View.OnClickListener onClickListenersabadkharid = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                G.CurrentActivity.startActivity(intent);
            } else {
                Intent intent = new Intent(G.CurrentActivity, ActivitySabad.class);
                G.CurrentActivity.startActivity(intent);
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

    public static  void addview(String location){


        ArrayList<Webservice.requestparameter> array = new ArrayList<>();
        Webservice.requestparameter object1 = new Webservice.requestparameter();
        object1.key = "location";
        object1.value = location;

        array.add(object1);


        Webservice.request("Store.php?action=view", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        },array);

    }

}

