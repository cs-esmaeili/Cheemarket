package www.cheemarket.com.javadesmaeili.Customview;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.R;

/**
 * Created by user on 7/1/2018.
 */

public class Dialogs extends Application{

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

                Button btnOk = (Button) dialog.findViewById(R.id.exit);
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

                Button btnOk = (Button) dialog.findViewById(R.id.exit);
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

    public static void Checkpermissions() {

        String[] permissions = {
                Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_NETWORK_STATE

        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && G.context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(G.context, permission) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(G.CurrentActivity, permissions, 1);

                    break;
                }
            }
        }
    }


    public  static  void  yesnodialog(String text , final Activity actname){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        if(actname != null){
                            Intent intent = new Intent(G.CurrentActivity, actname.getClass());
                            G.CurrentActivity.startActivity(intent);

                        }

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(actname);
        builder.setMessage(text).setPositiveButton("بله", dialogClickListener)
                .setNegativeButton("خیر", dialogClickListener).show();



    }




}




