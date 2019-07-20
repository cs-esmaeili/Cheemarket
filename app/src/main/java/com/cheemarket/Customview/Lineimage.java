package com.cheemarket.Customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import com.cheemarket.Textconfig;

/**
 * Created by user on 10/20/2018.
 */

public class Lineimage extends android.support.v7.widget.AppCompatTextView {

    private Paint paintline = new Paint();
    private Paint painttext = new Paint();

    private String textforshow = "Null";


    private void init() {

        paintline.setColor(Color.RED);
        paintline.setStrokeWidth(3);

        painttext.setColor(Color.RED);
        painttext.setTextAlign(Paint.Align.CENTER);

    }


    public void setText(String Text) {
        textforshow = Text;
        postInvalidate();
    }


    public Lineimage(Context context) {
        super(context);
        init();
    }

    public Lineimage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Lineimage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    @Override
    public void onDraw(Canvas canvas) {


        Rect bounds = new Rect();


        paintline.setColor(Color.RED);
        paintline.setStrokeWidth(3);
        paintline.setTextAlign(Paint.Align.CENTER);

        painttext.setColor(Color.RED);
        painttext.setTextSize(this.getTextSize());
        painttext.setTextAlign(Paint.Align.CENTER);
        painttext.setTypeface(Textconfig.gettypeface());


        painttext.getTextBounds(Textconfig.formattext(textforshow), 0, Textconfig.formattext(textforshow).length(), bounds);


        // bounds.offset(0, -bounds.top);


        canvas.drawText(Textconfig.formattext(textforshow), this.getWidth() / 2, this.getHeight() / 2, painttext);


        //   paintline.getTextBounds(textforshow , 0 , textforshow.length(), bounds);


        canvas.drawLine(((this.getWidth() / 2) - (bounds.width() / 2)), (this.getHeight() / 2) - (bounds.height() / 2) + 6, ((this.getWidth() / 2) + (bounds.width() / 2)), (this.getHeight() / 2) - (bounds.height() / 2) + 6, paintline);


    }

}