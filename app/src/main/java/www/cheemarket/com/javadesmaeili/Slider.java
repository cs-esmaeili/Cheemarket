package www.cheemarket.com.javadesmaeili;

import android.widget.ImageView;

import java.util.ArrayList;

import www.cheemarket.com.javadesmaeili.Customview.Sliderimage;
import www.cheemarket.com.javadesmaeili.Structure.SliderStructure;

/**
 * Created by user on 8/16/2018.
 */

public class Slider {

    static ArrayList<SliderStructure> array = new  ArrayList<SliderStructure>() ;

    public static void setsliders() {

        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Sliderimage mysilder = new Sliderimage(G.context, ActivityMain.viewPager , ActivityMain.circleIndicator ,R.layout.sliderlayout,R.id.image);
                mysilder.removealldata();
                for (int i = 0; i < array.size(); i++) {

                        mysilder.addsilder(G.Baseurl + "Listimages/" + array.get(i).Postimage + "/" + array.get(i).Postimage + ".png");


                }

            }
        });


    }
}