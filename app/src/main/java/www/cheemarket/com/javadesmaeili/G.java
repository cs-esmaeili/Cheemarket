package www.cheemarket.com.javadesmaeili;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;

import java.util.ArrayList;

import www.cheemarket.com.javadesmaeili.Structure.sabad;

/**
 * Created by user on 7/1/2018.
 */

public class G extends Application {

    public static String Baseurl = "http://www.cheemarket.com/Store/";
    public static Activity CurrentActivity;
    public static Context context;
    public static  int IMAGES_HEIGHT = 0;
    public static  int IMAGES_WIDTH = 0;
    public static final Handler HANDLER = new Handler();

    public static ArrayList<sabad> mdatasetsabad = new ArrayList<sabad>();
    public static String Connectioncode = "";
    public static boolean comeback = false;


    public static final String  VERSIONNAME = "3.0";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Textconfig.setAssetManager(getAssets());
        Textconfig.setFontpath("vazir.ttf");
    }

    public static View.OnClickListener onClickListenersearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Intent intent = new Intent(G.CurrentActivity, SearchActivity.class);
           G.CurrentActivity.startActivity(intent);
        }
    };

    public static View.OnClickListener onClickListenersabadkharid = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (G.Connectioncode.equals("")) {
                Intent intent = new Intent(G.CurrentActivity, ActivityLogin.class);
                G.CurrentActivity.startActivity(intent);
            }else {
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
            return  false;
        }



    }


}
