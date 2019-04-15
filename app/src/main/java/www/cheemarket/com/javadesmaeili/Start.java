package www.cheemarket.com.javadesmaeili;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import org.json.JSONException;

import www.cheemarket.com.javadesmaeili.Customview.Dialogs;
import www.cheemarket.com.javadesmaeili.R;

public class Start extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        G.CurrentActivity = this;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        G.IMAGES_HEIGHT = (int) (Double.parseDouble(displayMetrics.heightPixels + "") / 1.5);
        G.IMAGES_WIDTH = (int) (Double.parseDouble(displayMetrics.widthPixels + "") / 1.5);


        Webservice webservice = new Webservice();
        OndownloadListener ondownloadListener = new OndownloadListener() {
            @Override
            public void Oncompelet(String input) throws JSONException {
                if (input.equals("run")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(300);
                                Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                                startActivity(intent);
                                finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {

                    Dialogs.ShowRepairDialog();
                }
            }

            @Override
            public void onProgressDownload(int percent) {

            }
        };
        webservice.setDatalistener(ondownloadListener);
        webservice.downloaddata(G.Baseurl + "server.php", null);


    }
}


