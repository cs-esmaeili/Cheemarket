package com.cheemarket.JavadEsmaeili.Customview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;
import com.cheemarket.JavadEsmaeili.Commands;
import com.cheemarket.JavadEsmaeili.R;

public class Sliderimage {


    private ArrayList<String> imageurlandscaletype =  new ArrayList<>();
    private Context context;
    private int layoutid;
    private int imageviewid;
    private ImageView.ScaleType scaleType;
    private MyPager instantadapter = new MyPager();
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;


    public class MyPager  extends PagerAdapter {


        /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(context).inflate(layoutid, null);
            final ImageView imageView = (ImageView) view.findViewById(imageviewid);

            imageView.setScaleType(scaleType);

            Commands.showimage(imageurlandscaletype.get(position),null,imageView,false);

            container.addView(view);
            return view;
        }
        /*
        This callback is responsible for destroying a page. Since we are using view only as the
        object key we just directly remove the view from parent container
        */
        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }
        /*
        Returns the count of the total pages
        */
        @Override
        public int getCount() {
            return imageurlandscaletype.size();
        }
        /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }



    }


    public Sliderimage(final Context context, final ViewPager viewPager , CircleIndicator circleIndicator, int layoutid , int imageviewid , ImageView.ScaleType scaleType) {
        this.context = context;
        this.layoutid = layoutid;
        this.imageviewid = imageviewid;
        this.viewPager = viewPager;
        this.circleIndicator = circleIndicator;
        this.scaleType = scaleType;
        instantadapter.notifyDataSetChanged();
    }


    public void addsilder(String imageurl){

        this.imageurlandscaletype.add(imageurl);
        this.viewPager.setAdapter(instantadapter);
        circleIndicator.setViewPager(viewPager);
        instantadapter.notifyDataSetChanged();
    }

    public void removealldata(){
        this.imageurlandscaletype.clear();
        instantadapter.notifyDataSetChanged();
    }


}
