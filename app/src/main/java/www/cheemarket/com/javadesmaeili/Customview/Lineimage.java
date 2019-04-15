package www.cheemarket.com.javadesmaeili.Customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import www.cheemarket.com.javadesmaeili.G;
import www.cheemarket.com.javadesmaeili.Textconfig;

/**
 * Created by user on 10/20/2018.
 */

public class Lineimage extends View {

    private Paint paintline = new Paint();
    private Paint painttext = new Paint();

    private String textforshow = "TEST TEXT";
    private float size = 50;



    private void init() {
        paintline.setColor(Color.RED);
        paintline.setStrokeWidth(size - 45);

        painttext.setColor(Color.BLACK);
        painttext.setTextSize(dpToSp(size, G.context));
        painttext.setTextAlign(Paint.Align.CENTER);

    }

    private static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    private static int dpToSp(float dp, Context context) {
        return (int) (dpToPx(dp, context) / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public void setTextSize(int size) {
        this.size = size;
        postInvalidate();
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
        paintline.setColor(Color.RED);
        paintline.setStrokeWidth(size - 45);

        painttext.setColor(Color.BLACK);
        painttext.setTextSize(size); // 127
        painttext.setTextAlign(Paint.Align.CENTER);
        painttext.setTypeface(Textconfig.gettypeface());

        canvas.drawText(Textconfig.formattext(textforshow), this.getWidth() / 2, this.getHeight() / 2 + (size - 32), painttext);

        canvas.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2, paintline);


    }

}