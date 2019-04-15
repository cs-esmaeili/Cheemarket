package www.cheemarket.com.javadesmaeili;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.view.View;

/**
 * Created by user on 7/1/2018.
 */

public class G extends Application {

    public static String Baseurl = "http://www.cheemarket.com/Store/";
    public static Activity CurrentActivity;
    public static Context context;
    public static final int DOWNLOAD_BUFFER_SIZE = 8 * 1024;
    public static  int IMAGES_HEIGHT = 0;
    public static  int IMAGES_WIDTH = 0;
    public static final Handler HANDLER = new Handler();

   // public static ArrayList<sabad> mdatasetsabad = new ArrayList<sabad>();
   // public static ArrayList<Data> tempdata = new ArrayList<Data>();
    public static long userid = -1;
    public static boolean comeback = false;
    static int result = -1;
    static String temp = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Textconfig.setAssetManager(getAssets());
        Textconfig.setFontpath("vazir.ttf");
    }

    public static View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        //    Intent intent = new Intent(G.CurrentActivity, SearchActivity.class);
       //     G.CurrentActivity.startActivity(intent);
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
