package com.cheemarket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cheemarket.Customview.badgelogo;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Activityproblems extends AppCompatActivity {

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
        setContentView(R.layout.activity_activityproblems);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button)findViewById(R.id.button);
        ImageView shoplogo = (ImageView) findViewById(R.id.shoplogo);
        ImageView searchlogo = (ImageView) findViewById(R.id.searchlogo);

        searchlogo.setOnClickListener(Commands.onClickListenersearch);
        shoplogo.setOnClickListener(Commands.onClickListenersabadkharid);

        badge = (badgelogo) findViewById(R.id.badgelogo);
        Commands.setbadgenumber(badge);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() > 0){

                    Webservice.requestparameter param = new Webservice.requestparameter();
                    param.key = "Text";
                    param.value = editText.getText().toString() + "";

                    Webservice.requestparameter param1 = new Webservice.requestparameter();
                    param1.key = "Connectioncode";
                    param1.value = G.Connectioncode;



                    ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                    array.add(param);
                    array.add(param1);

                    Webservice.request("Store.php?action=problems", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            G.HANDLER.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(G.context,"مشکلی در ارتیاط با سرور پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String input = response.body().string();
                            if(input.equals("Ok")){
                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        editText.setText("");
                                        Toast.makeText(G.context,"پیام شما ثبت شد متشکریم برای کمک شما به بهبود عملکر برنامه",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else {
                                G.HANDLER.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(G.context,"در ثبت پیام شما مشکلی پیش آمد دوباره تلاش کنید",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        }
                    },array);
                }
            }
        });
    }
}
