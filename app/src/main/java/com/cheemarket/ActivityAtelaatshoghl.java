package com.cheemarket;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.CollapsingToolbarLayout;


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