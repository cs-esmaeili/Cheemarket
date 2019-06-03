package www.cheemarket.com.javadesmaeili;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.florent37.materialimageloading.MaterialImageLoading;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class Dastebandimahsolat extends AppCompatActivity {


    ArrayList<RelativeLayout> layouts = new ArrayList<>();
    ArrayList<ImageView> Images = new ArrayList<>();
    ArrayList<Integer> aksha = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        if (Images.get(0) != null) {
            setimages();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dastebandimahsolat);

        G.CurrentActivity = this;

        layouts.add((RelativeLayout) findViewById(R.id.cat1));
        layouts.add((RelativeLayout) findViewById(R.id.cat2));
        layouts.add((RelativeLayout) findViewById(R.id.cat3));
        layouts.add((RelativeLayout) findViewById(R.id.cat4));
        layouts.add((RelativeLayout) findViewById(R.id.cat5));
        layouts.add((RelativeLayout) findViewById(R.id.cat6));
        layouts.add((RelativeLayout) findViewById(R.id.cat7));
        layouts.add((RelativeLayout) findViewById(R.id.cat8));
        layouts.add((RelativeLayout) findViewById(R.id.cat9));
        layouts.add((RelativeLayout) findViewById(R.id.cat10));
        layouts.add((RelativeLayout) findViewById(R.id.cat11));
        layouts.add((RelativeLayout) findViewById(R.id.cat12));


        Images.add((ImageView) findViewById(R.id.img1));
        Images.add((ImageView) findViewById(R.id.img2));
        Images.add((ImageView) findViewById(R.id.img3));
        Images.add((ImageView) findViewById(R.id.img4));
        Images.add((ImageView) findViewById(R.id.img5));
        Images.add((ImageView) findViewById(R.id.img6));
        Images.add((ImageView) findViewById(R.id.img7));
        Images.add((ImageView) findViewById(R.id.img8));
        Images.add((ImageView) findViewById(R.id.img9));
        Images.add((ImageView) findViewById(R.id.img10));
        Images.add((ImageView) findViewById(R.id.img11));
        Images.add((ImageView) findViewById(R.id.img12));

        aksha.add(R.drawable.dastebandi_kalahayeasasi);
        aksha.add(R.drawable.dastebandi_khoshkbar);
        aksha.add(R.drawable.datebandi_mavadshoyande);
        aksha.add(R.drawable.dastebandi_khorma);
        aksha.add(R.drawable.dastebandi_labaniyat);
        aksha.add(R.drawable.dastebandi_sobhane);
        aksha.add(R.drawable.dastebandi_konserv);
        aksha.add(R.drawable.dastebandi_tanagholat);
        aksha.add(R.drawable.dastebandi_noshidani);
        aksha.add(R.drawable.dastebandi_shokolat);
        aksha.add(R.drawable.dastebandi_protoein);
        aksha.add(R.drawable.dastebandi_arayeshibehdashti);


     //   layouts.get(0).setTag("dastebandi_tanagholat");

     //   layouts.get(2).setTag("datebandi_mavadshoyande");
    //    layouts.get(3).setTag("dastebandi_khorma");
     //   layouts.get(4).setTag("dastebandi_labaniyat");
     //   layouts.get(5).setTag("dastebandi_sobhane");
      //  layouts.get(6).setTag("dastebandi_konserv");
        layouts.get(0).setTag("dastebandi_kalahayeasasi");
        layouts.get(1).setTag("dastebandi_khoshkbar");
      //  layouts.get(8).setTag("dastebandi_noshidani");
      //  layouts.get(9).setTag("dastebandi_shokolat");
      //  layouts.get(10).setTag("dastebandi_protoein");
      //  layouts.get(11).setTag("dastebandi_arayeshibehdashti");


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


        for (RelativeLayout layout : layouts) {
            layout.setOnClickListener(onClickListener);
        }


        setimages();

    }

    private void clearimages() {
        for (ImageView img : Images) {
            img.setImageResource(0);
        }
    }

    private void setimages() {


        for (int i = 0; i < Images.size(); i++) {
            final int finalI = i;

            Commands.showimage(null, aksha.get(finalI), Images.get(finalI),true);

        }


    }

    @Override
    public void onBackPressed() {
        clearimages();
        super.onBackPressed();

    }
}
