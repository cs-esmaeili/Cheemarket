package com.cheemarket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.cheemarket.Customview.Sliderimage;
import com.cheemarket.Structure.SliderStructure;

import java.util.ArrayList;

import static com.cheemarket.G.pre;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivitySplash extends AppCompatActivity {


    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private static ViewPager view_pager;
    private static me.relex.circleindicator.CircleIndicator circle;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        G.CurrentActivity = this;
        mContentView = findViewById(R.id.fullscreen_content);
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        circle = (me.relex.circleindicator.CircleIndicator) findViewById(R.id.circle);

        TextView txtskip = (TextView) findViewById(R.id.txtskip);
        txtskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(G.CurrentActivity, ActivityStart.class);
                G.CurrentActivity.startActivity(intent);
                G.CurrentActivity.finish();
            }
        });

        if (pre.contains("message_id") && !pre.getString("message_id", "Error").equals("Error")) {
            Intent intent = new Intent(G.CurrentActivity, ActivityStart.class);
            G.CurrentActivity.startActivity(intent);
            G.CurrentActivity.finish();
            ;
        }

        ViewPager.OnPageChangeListener lisener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 3) {
                    G.HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(G.CurrentActivity, ActivityStart.class);
                            G.CurrentActivity.startActivity(intent);
                            G.CurrentActivity.finish();
                        }
                    }, 2000);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        };

        view_pager.setOnPageChangeListener(lisener);

        SliderStructure a = new SliderStructure();
        a.Postimage = "" + Uri.parse("android.resource://com.cheemarket/" + R.drawable.page1);


        SliderStructure b = new SliderStructure();
        b.Postimage = "" + Uri.parse("android.resource://com.cheemarket/" + R.drawable.page2);



        SliderStructure c = new SliderStructure();
        c.Postimage = "" + Uri.parse("android.resource://com.cheemarket/" + R.drawable.page3);


        SliderStructure d = new SliderStructure();
        d.Postimage = "" + Uri.parse("android.resource://com.cheemarket/" + R.drawable.page4);


        array.add(a);
        array.add(b);
        array.add(c);
        array.add(d);
        setsliders();

        hide();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    static ArrayList<SliderStructure> array = new ArrayList<SliderStructure>();

    public static void setsliders() {
        view_pager.setOffscreenPageLimit(1);
        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                Sliderimage mysilder = new Sliderimage(G.context, view_pager, circle, R.layout.sliderlayout, R.id.image, ImageView.ScaleType.FIT_XY);
                mysilder.removealldata();
                for (int i = 0; i < array.size(); i++) {

                    mysilder.addsilder(array.get(i).Postimage);
                }

            }
        });


    }

}
