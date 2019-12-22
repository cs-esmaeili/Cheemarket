package com.cheemarket;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cheemarket.Customview.Sliderimage;
import com.cheemarket.Structure.SliderStructure;

import java.util.ArrayList;

/**
 * Created by user on 8/16/2018.
 */

public class Slider {

    static ArrayList<SliderStructure> array = new ArrayList<SliderStructure>();

    public static void setsliders() {
        ActivityMain.viewPager.setOffscreenPageLimit(2);
        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Sliderimage mysilder = new Sliderimage(G.context, ActivityMain.viewPager, ActivityMain.circleIndicator, R.layout.sliderlayout, R.id.image, ImageView.ScaleType.FIT_XY);
                mysilder.removealldata();
                for (int i = 0; i < array.size(); i++) {
                    mysilder.addsilder(array.get(i));
                }


                ActivityMain.silderparent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        if (!array.get(ActivityMain.viewPager.getCurrentItem()).url.equals("")) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(array.get(ActivityMain.viewPager.getCurrentItem()).url));
                            G.CurrentActivity.startActivity(browserIntent);
                        } else if (!array.get(ActivityMain.viewPager.getCurrentItem()).sub_categori.equals("")) {
                            Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                            intent.putExtra("subkala", array.get(ActivityMain.viewPager.getCurrentItem()).sub_categori);
                            intent.putExtra("NameSubcategori", array.get(ActivityMain.viewPager.getCurrentItem()).name_subcategori);
                            G.CurrentActivity.startActivity(intent);
                        }

                    }
                });
            }
        });
    }
}