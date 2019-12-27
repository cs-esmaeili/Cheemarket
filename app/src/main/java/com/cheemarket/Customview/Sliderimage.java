package com.cheemarket.Customview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cheemarket.ActivitySubdastebandi;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.Structure.SliderStructure;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class Sliderimage {


    private ArrayList<SliderStructure> SliderData = new ArrayList<>();
    private Context context;
    private int layoutid;
    private int imageviewid;
    private ImageView.ScaleType scaleType;
    private MyPager instantadapter = new MyPager();
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;


    public class MyPager extends PagerAdapter {


        /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = LayoutInflater.from(context).inflate(layoutid, null);
            final ImageView imageView = (ImageView) view.findViewById(imageviewid);

            imageView.setScaleType(scaleType);

            Commands.showimage(SliderData.get(position).Postimage, null, imageView);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!SliderData.get(position).url.equals("")) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(SliderData.get(position).url));
                        G.CurrentActivity.startActivity(browserIntent);
                    } else if (!SliderData.get(position).sub_categori.equals("")) {
                        Intent intent = new Intent(G.CurrentActivity, ActivitySubdastebandi.class);
                        intent.putExtra("subkala", SliderData.get(position).sub_categori);
                        intent.putExtra("NameSubcategori", SliderData.get(position).name_subcategori);
                        G.CurrentActivity.startActivity(intent);
                    }
                }
            });

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
            return SliderData.size();
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


    public Sliderimage(final Context context, final ViewPager viewPager, CircleIndicator circleIndicator, int layoutid, int imageviewid, ImageView.ScaleType scaleType) {
        this.context = context;
        this.layoutid = layoutid;
        this.imageviewid = imageviewid;
        this.viewPager = viewPager;
        this.circleIndicator = circleIndicator;
        this.scaleType = scaleType;
        instantadapter.notifyDataSetChanged();
    }


    public void addsilder(SliderStructure Structure) {
        this.SliderData.add(Structure);
        this.viewPager.setAdapter(instantadapter);
        circleIndicator.setViewPager(viewPager);
        instantadapter.notifyDataSetChanged();
    }

    public void addsilder(String imgurl) {
        SliderStructure temp = new SliderStructure();
        temp.Postimage = imgurl;
        temp.sub_categori = "";
        temp.url = "";
        temp.name_subcategori = "";
        this.SliderData.add(temp);
        this.viewPager.setAdapter(instantadapter);
        circleIndicator.setViewPager(viewPager);
        instantadapter.notifyDataSetChanged();
    }

    public void removealldata() {
        this.SliderData.clear();
        instantadapter.notifyDataSetChanged();
    }


}
