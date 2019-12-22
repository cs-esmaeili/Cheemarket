package com.cheemarket;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Textconfig {
//salam
    private static String Fontpath = "";
    private static AssetManager assetManager = null;

    public static void setFontpath(String fontpath) {
        Fontpath = fontpath;
    }

    public static void setAssetManager(AssetManager assetManager) {
        Textconfig.assetManager = assetManager;
    }

    public static String getFontpath() {
        return Fontpath;
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }


    public static Typeface gettypeface(){
        return Typeface.createFromAsset(assetManager,Fontpath);
    }

    private static void changefont(TextView textView){

        if(!Fontpath.equals("") && assetManager !=null){
            Typeface typeface = Typeface.createFromAsset(assetManager,Fontpath);
            textView.setTypeface(typeface);
        }

    }

    public static String formattext(String text){
        String orginaltext = text;

        if(orginaltext.contains("تومان")){
            text = text.replace("تومان","");


            if (TextUtils.isDigitsOnly(text) && !text.equals("")){
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                return formatter.format(Long.parseLong(text)) + " تومان";
            }

                text = text + "تومان";


        }else {
            if (TextUtils.isDigitsOnly(text) && !text.equals("")){
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                return formatter.format(Long.parseLong(text));
            }
        }
        return text;
    }

    public static void settext(TextView textView, String text){
        changefont(textView);
        textView.setText(formattext(text));

    }
}
