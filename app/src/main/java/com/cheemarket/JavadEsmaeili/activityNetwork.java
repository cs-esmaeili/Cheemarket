package com.cheemarket.JavadEsmaeili;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class activityNetwork extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networkactivity);

        G.CurrentActivity.finish();
        G.CurrentActivity = this;

        TextView txttry = (TextView) findViewById(R.id.txttry);

        txttry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(G.readNetworkStatus() == true){
                    Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    G.CurrentActivity.startActivity(intent);
                    finish();
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        return;
    }
}
