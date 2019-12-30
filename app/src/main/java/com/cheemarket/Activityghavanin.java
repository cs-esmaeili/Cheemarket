package com.cheemarket;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class Activityghavanin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityghavanin);

        TextView txt = findViewById(R.id.txt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            txt.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        txt.setText(LoadData("law.txt"));


    }

    public String LoadData(String inFile) {
        String tContents = "";

        try {
            InputStream stream = getAssets().open(inFile);

            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();
            tContents = new String(buffer);
        } catch (IOException e) {
            // Handle exceptions here
        }

        return tContents;

    }

}
