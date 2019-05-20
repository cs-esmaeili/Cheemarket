package www.cheemarket.com.javadesmaeili;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import www.cheemarket.com.javadesmaeili.Structure.KalaStructure;

public class Commands {


    public static void convertinputdata(JSONObject jsonObject, KalaStructure kalaStructure, boolean kalaone) {

        if (kalaone) {
            try {
                kalaStructure.Name1 = jsonObject.getString("Name");
                kalaStructure.Weight1 = jsonObject.getString("Weight");
                kalaStructure.Price1 = jsonObject.getString("Price");
                kalaStructure.OldPrice1 = jsonObject.getString("OldPrice");
                kalaStructure.Volume1 = jsonObject.getString("Volume");
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
                kalaStructure.Volume2 = jsonObject.getString("Volume");
                kalaStructure.Image2 = jsonObject.getString("Image");
                kalaStructure.Status2 = jsonObject.getString("Status");
                kalaStructure.Datetime2 = jsonObject.getString("Datetime");
                kalaStructure.Id2 = jsonObject.getString("Id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    public static void openactivity(ArrayList<KalaStructure> mdataset, int position, Class<? extends Activity> target) {


        Intent intent = new Intent(G.CurrentActivity, target);

        intent.putExtra("Name", mdataset.get(position).Name1);
        intent.putExtra("Weight", mdataset.get(position).Weight1);
        intent.putExtra("Volume", mdataset.get(position).Volume1);
        intent.putExtra("Image", mdataset.get(position).Image1);
        intent.putExtra("Id", mdataset.get(position).Id1);
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

                    Log.i("Image", "url =" + url);
                    Picasso.get()
                            .load(url)
                            .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .error(R.drawable.brokenimage)
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(img).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {

                                    /*
                                    if (trytoload) {
                                        showimage(url, src, img, false);
                                    }
                                    if (e instanceof SocketTimeoutException) {
                                        e.printStackTrace();
                                        Webservice.handelerro("timeout");
                                    } else {
                                        e.printStackTrace();
                                        Webservice.handelerro(null);F
                                    }
                                    */
                                }
                            });

                } else if (src != null) {
                    Picasso.get()
                            .load(src)
                            .resize(G.IMAGES_HEIGHT, G.IMAGES_WIDTH)
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .networkPolicy(NetworkPolicy.NO_CACHE)
                            .error(R.drawable.brokenimage)
                            .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    MaterialImageLoading.animate(img).setDuration(1000).start();
                                }

                                @Override
                                public void onError(Exception e) {
                                    /*

                                    if (trytoload) {
                                        showimage(url, src, img, false);
                                    }

                                    if (e instanceof SocketTimeoutException) {
                                        e.printStackTrace();
                                        Webservice.handelerro("timeout");
                                    } else {
                                        e.printStackTrace();
                                        Webservice.handelerro(null);
                                    }
                                    */
                                }
                            });
                }


            }
        });
    }

}

