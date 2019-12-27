package com.cheemarket.Customview;

import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.cheemarket.ActivityMain;
import com.cheemarket.ActivityOrders;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.R;
import com.cheemarket.Webservice;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by user on 7/1/2018.
 */

public class Dialogs extends Application {


    public static void message(final boolean cancansel, final String btntext, final String canseltext, final String btnvisi, final String canselvisi, final String matn, final String Image, final String url) {
        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(G.CurrentActivity);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_atelae);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                dialog.setCancelable(cancansel);

                Button btnOk = (Button) dialog.findViewById(R.id.btn);
                Button btncansel = (Button) dialog.findViewById(R.id.cansel);
                ImageView img = (ImageView) dialog.findViewById(R.id.img);
                TextView txt = (TextView) dialog.findViewById(R.id.txt);

                btnOk.setText(btntext);
                btncansel.setText(canseltext);

                btnOk.setVisibility((btnvisi.equals("yes") ? View.VISIBLE : View.GONE));
                btncansel.setVisibility((canselvisi.equals("yes") ? View.VISIBLE : View.GONE));

                txt.setText(matn);
                Commands.showimage(Image, null, img);


                btnOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        if (!url.equals("")) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            G.CurrentActivity.startActivity(browserIntent);

                        } else {
                            if (url.equals("") && cancansel == false) {
                                System.exit(0);
                                return;
                            }
                            Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                            G.CurrentActivity.startActivity(intent);
                            G.CurrentActivity.finish();

                        }

                    }
                });


                btncansel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                        G.CurrentActivity.startActivity(intent);
                        G.CurrentActivity.finish();

                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {


                        Intent intent = new Intent(G.CurrentActivity, ActivityMain.class);
                        G.CurrentActivity.startActivity(intent);
                        G.CurrentActivity.finish();


                    }
                });

                dialog.show();
            }
        });

    }


    public static void gate(final boolean isgate, @Nullable final String price, final String factor_id) {
        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(G.CurrentActivity);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_gate);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                dialog.setCancelable(true);

                final Button btn = (Button) dialog.findViewById(R.id.btn);
                TextView txt = (TextView) dialog.findViewById(R.id.txt);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                LinearLayout card = (LinearLayout) dialog.findViewById(R.id.card);

                final EditText name = (EditText) dialog.findViewById(R.id.name);
                final EditText card1 = (EditText) dialog.findViewById(R.id.card1);
                final EditText card2 = (EditText) dialog.findViewById(R.id.card2);
                final EditText card3 = (EditText) dialog.findViewById(R.id.card3);
                final EditText card4 = (EditText) dialog.findViewById(R.id.card4);

                if (isgate) {
                    card.setVisibility(View.GONE);
                    title.setText("پرداخت مانده سفارش");
                    txt.setText(price + " تومان");
                    btn.setText("پرداخت");
                } else {
                    txt.setVisibility(View.GONE);
                    card.setVisibility(View.VISIBLE);
                    title.setText("اطلاعات مورد نیاز را جهت واریز مانده سفارش وارد کنید");
                    btn.setText("ثبت شماره کارت");
                }


                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        if (isgate) {

                            Webservice.requestparameter param1 = new Webservice.requestparameter();
                            param1.key = "token";
                            param1.value = G.token;
                            Webservice.requestparameter param2 = new Webservice.requestparameter();
                            param2.key = "factor_id";
                            param2.value = factor_id;
                            ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                            array.add(param1);
                            array.add(param2);

                            Webservice.request("open_gate", new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    G.HANDLER.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(G.CurrentActivity, "مشکلی  پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String output = response.body().string();

                                    try {
                                        Uri url = Uri.parse(output);
                                        if (!url.equals("")) {

                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, url);
                                            G.CurrentActivity.startActivity(browserIntent);

                                        } else {
                                            G.HANDLER.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(G.CurrentActivity, "مشکلی  پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            }, array);


                        } else {

                            Webservice.requestparameter param1 = new Webservice.requestparameter();
                            param1.key = "token";
                            param1.value = G.token;
                            Webservice.requestparameter param2 = new Webservice.requestparameter();
                            param2.key = "card";
                            param2.value = card1.getText().toString() + "/" + card2.getText().toString() + "/" + card3.getText().toString() + "/" + card4.getText().toString();
                            Webservice.requestparameter param3 = new Webservice.requestparameter();
                            param3.key = "name";
                            param3.value = name.getText().toString();
                            Webservice.requestparameter param4 = new Webservice.requestparameter();
                            param4.key = "factor_id";
                            param4.value = factor_id;
                            ArrayList<Webservice.requestparameter> array = new ArrayList<>();
                            array.add(param1);
                            array.add(param2);
                            array.add(param3);
                            array.add(param4);
                            Webservice.request("save_card", new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {


                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String output = response.body().string();
                                    if (output.equals("ok")) {
                                        dialog.dismiss();
                                    } else {
                                        G.HANDLER.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(G.CurrentActivity, "مشکلی در ثبت اطلاعات پیش آمد دوباره سعی کنید", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            }, array);
                        }


                    }
                });

                dialog.show();
            }
        });

    }

    public static void vizhegiayande() {
        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(G.CurrentActivity);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_vizhegiayande);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);
                dialog.setCancelable(false);

                Button btnOk = (Button) dialog.findViewById(R.id.btn);
                btnOk.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        G.CurrentActivity.finish();
                    }
                });
                dialog.show();
            }
        });

    }


    public static void message_dialog( String message){
        AlertDialog alertDialog = new AlertDialog.Builder(G.CurrentActivity).create();
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "فهمیدم",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

}




