package com.cheemarket.Customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 10/20/2018.
 */

public class badgelogo extends View {

    private Paint paintcircle = new Paint();
    private Paint painttext = new Paint();

    private String textforshow = "";


    private void init() {

        paintcircle.setStyle(Paint.Style.FILL);
        paintcircle.setColor(Color.WHITE);
        paintcircle.setStrokeWidth(4);
        paintcircle.setAntiAlias(true);


        painttext.setColor(Color.RED);
        painttext.setTextAlign(Paint.Align.CENTER);
        painttext.setStrokeWidth(5);
        painttext.setAntiAlias(true);

    }


    public void setNumber(int number) {
        textforshow = "" + number;
        if(number > 0){
            this.setVisibility(VISIBLE);
        }else {
            this.setVisibility(INVISIBLE);
        }
        postInvalidate();
    }


    public badgelogo(Context context) {
        super(context);
        init();
    }

    public badgelogo(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public badgelogo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect bounds = new Rect();

        int x = getWidth();
        int y = getHeight();



        canvas.drawCircle(x / 2, y / 2, (y/2) - 5, paintcircle);

        Paint temp = new Paint();
        temp.setStyle(Paint.Style.STROKE);
        temp.setStrokeWidth(5);
        temp.setColor(Color.parseColor("#FF1744"));

        canvas.drawCircle(x / 2, y / 2, (y/2) -  5 , temp);


        painttext.setTextSize(y / 2);

        painttext.getTextBounds(textforshow, 0, textforshow.length(), bounds);


        canvas.drawText(textforshow,  x/2  , ((y  / 2 ) + (bounds.height() / 2)  ) , painttext);










    }

}