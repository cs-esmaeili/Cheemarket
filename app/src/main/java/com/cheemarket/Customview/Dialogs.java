package com.cheemarket.Customview;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheemarket.ActivityMain;
import com.cheemarket.Commands;
import com.cheemarket.G;
import com.cheemarket.R;


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


    public static void ShowRepairDialog() {
        G.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                final Dialog dialog = new Dialog(G.CurrentActivity);
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_repair);
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
                        dialog.dismiss();
                        System.exit(0);
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



}




