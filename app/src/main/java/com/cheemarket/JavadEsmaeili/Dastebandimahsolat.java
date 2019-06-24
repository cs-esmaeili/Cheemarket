package com.cheemarket.JavadEsmaeili;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Dastebandimahsolat extends AppCompatActivity {


    ArrayList<dastebandi_sakhtar> data = new ArrayList<>();


    class dastebandi_sakhtar {
        private RelativeLayout layout;
        private ImageView imgview;
        private String url;
        private String tag;

        public dastebandi_sakhtar setLayout(RelativeLayout layout) {
            this.layout = layout;
            return this;
        }

        public dastebandi_sakhtar setImgview(ImageView imgview) {
            this.imgview = imgview;
            return this;
        }

        public dastebandi_sakhtar setUrl(String url) {
            this.url = url;
            return this;
        }

        public dastebandi_sakhtar setTag(String tag) {
            this.tag = tag;
            return this;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        if (data.get(0).imgview != null) {
            setimages();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dastebandimahsolat);

        G.CurrentActivity = this;


        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img1)).setLayout((RelativeLayout) findViewById(R.id.cat1)).setUrl("").setTag("dastebandi_kalahayeasasi"));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img2)).setLayout((RelativeLayout) findViewById(R.id.cat2)).setUrl("").setTag("dastebandi_khoshkbar"));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img3)).setLayout((RelativeLayout) findViewById(R.id.cat3)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img4)).setLayout((RelativeLayout) findViewById(R.id.cat4)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img5)).setLayout((RelativeLayout) findViewById(R.id.cat5)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img6)).setLayout((RelativeLayout) findViewById(R.id.cat6)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img7)).setLayout((RelativeLayout) findViewById(R.id.cat7)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img8)).setLayout((RelativeLayout) findViewById(R.id.cat8)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img9)).setLayout((RelativeLayout) findViewById(R.id.cat9)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img10)).setLayout((RelativeLayout) findViewById(R.id.cat10)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img11)).setLayout((RelativeLayout) findViewById(R.id.cat11)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img12)).setLayout((RelativeLayout) findViewById(R.id.cat12)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img13)).setLayout((RelativeLayout) findViewById(R.id.cat13)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img14)).setLayout((RelativeLayout) findViewById(R.id.cat14)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img15)).setLayout((RelativeLayout) findViewById(R.id.cat15)).setUrl("").setTag(""));
        data.add(new dastebandi_sakhtar().setImgview((ImageView) findViewById(R.id.img16)).setLayout((RelativeLayout) findViewById(R.id.cat16)).setUrl("").setTag(""));


        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(G.onClickListenersearch);
        shoplogo.setOnClickListener(G.onClickListenersabadkharid);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearimages();
                Intent intent = new Intent(G.CurrentActivity, Subdastebandi.class);
                intent.putExtra("subdastebandistring", "" + v.getTag());
                startActivity(intent);
            }
        };


        for (dastebandi_sakhtar onedata : data) {
            onedata.layout.setOnClickListener(onClickListener);
            onedata.layout.setTag(onedata.tag);
        }


        getimageurls();

    }

    private void getimageurls() {

        Webservice.request("Dastebandi_Images/indexs.php", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String input = response.body().string();

                if (input != "") {
                    try {
                        JSONArray array = new JSONArray(input);
                        for (int i = 0; i < array.length(); i++) {
                            if (data.size() == i) {
                                return;
                            } else {
                                data.get(i).url = array.get(i).toString();
                            }

                        }

                        G.HANDLER.post(new Runnable() {
                            @Override
                            public void run() {

                                setimages();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, null);
    }

    private void clearimages() {

        for (dastebandi_sakhtar img : data) {
            img.imgview.setImageResource(0);
        }
    }

    private void setimages() {


        for (int i = 0; i < data.size(); i++) {
            final int finalI = i;
            if(data.get(finalI).url == "" || data.get(finalI).url == null){
                continue;
            }
            Commands.showimage(data.get(finalI).url, null, data.get(finalI).imgview, true);

        }

    }

    @Override
    public void onBackPressed() {
        clearimages();
        super.onBackPressed();

    }
}
