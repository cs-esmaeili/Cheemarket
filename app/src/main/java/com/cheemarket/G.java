package com.cheemarket;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.cheemarket.Structure.sabad;

import java.util.ArrayList;

/**
 * Created by user on 7/1/2018.
 */

public class G extends Application {

    public static String Baseurl = "https://www.cheemarket.com/api/user/";
    public static Activity CurrentActivity;
    public static Context context;
    public static  int IMAGES_HEIGHT = 0;
    public static  int IMAGES_WIDTH = 0;
    public static final Handler HANDLER = new Handler();

    public static ArrayList<sabad> mdatasetsabad = new ArrayList<sabad>();
    public static String token = "";
    public static boolean comeback = false;
    public static SharedPreferences pre;

    public static final String VERSIONNAME = "1.0.0";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Textconfig.setAssetManager(getAssets());
        Textconfig.setFontpath("vazir.ttf");

        pre = getSharedPreferences("Cheemarket", MODE_PRIVATE);
    }






}
