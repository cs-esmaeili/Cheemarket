package com.cheemarket.JavadEsmaeili;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

public class ActivityDarbareyema extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darbareyema);



        LinearLayout spe = (LinearLayout) findViewById(R.id.spe);
        ImageView img = (ImageView) findViewById(R.id.img);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Toolbar te = (Toolbar)spe.findViewById(R.id.toolbar);

            te.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count++;

                    if(count == 25){
                        Intent intent = new Intent(ActivityDarbareyema.this,Activityprogrammer.class);
                        ActivityDarbareyema.this.startActivity(intent);
                    }
                }
            });
        }

        Commands.showimage(null,R.drawable.logo,img,false);


    }
}
