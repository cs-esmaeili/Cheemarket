package com.cheemarket;


import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cheemarket.Customview.badgelogo;


public class ActivityDastebandimahsolat extends AppCompatActivity {


    private static RecyclerView List;
    private badgelogo badge;

    @Override
    protected void onResume() {
        super.onResume();
        G.CurrentActivity = this;
        Commands.setbadgenumber(badge);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dastebandimahsolat);

        G.CurrentActivity = this;





        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);
        List = (RecyclerView) findViewById(R.id.List);

        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);

        Commands.getMaindastebandi("main",List);



    }




    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}
