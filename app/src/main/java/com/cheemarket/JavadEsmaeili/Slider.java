package com.cheemarket.JavadEsmaeili;

import android.widget.ImageView;

import java.util.ArrayList;

import com.cheemarket.JavadEsmaeili.Customview.Sliderimage;
import com.cheemarket.JavadEsmaeili.Structure.SliderStructure;

/**
 * Created by user on 8/16/2018.
 */

public class Slider {

    static ArrayList<SliderStructure> array = new  ArrayList<SliderStructure>() ;

    public static void setsliders() {

        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Sliderimage mysilder = new Sliderimage(G.context, ActivityMain.viewPager , ActivityMain.circleIndicator ,R.layout.sliderlayout,R.id.image , ImageView.ScaleType.FIT_XY);
                mysilder.removealldata();
                for (int i = 0; i < array.size(); i++) {

                        mysilder.addsilder(G.Baseurl + "Listimages/" + array.get(i).Postimage + "/" + array.get(i).Postimage + ".jpg");


                }

            }
        });


    }
}