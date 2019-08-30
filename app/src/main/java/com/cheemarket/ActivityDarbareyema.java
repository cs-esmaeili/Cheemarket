package com.cheemarket;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

import com.cheemarket.Customview.badgelogo;

public class ActivityDarbareyema extends AppCompatActivity {

    private int count = 0;
    private  badgelogo  badge;
    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        Commands.setbadgenumber(badge);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_darbareyema);


        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);

        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);



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

        Commands.showimage(null,R.drawable.logo,img);


    }
}
