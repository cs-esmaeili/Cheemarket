package com.cheemarket;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheemarket.Customview.Lineimage;
import com.cheemarket.Customview.Sliderimage;
import com.cheemarket.Structure.sabad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;


public class ActivityAtelaatshoghl extends AppCompatActivity {




    private CollapsingToolbarLayout colaps;

    public static String tozihat;
    public static String title;
    public static String img;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atelaatshoghl);
        G.CurrentActivity = this;

        colaps = (CollapsingToolbarLayout) findViewById(R.id.colaps);


        colaps.setCollapsedTitleTextColor(Color.WHITE);
        colaps.setExpandedTitleColor(Color.TRANSPARENT);


        TextView Title = (TextView)findViewById(R.id.title);
        TextView Tozihat = (TextView)findViewById(R.id.tozihat);
        ImageView Img = (ImageView)findViewById(R.id.img);

        Title.setText(title);
        Tozihat.setText(tozihat);
        Commands.showimage(img,null,Img);

    }
}