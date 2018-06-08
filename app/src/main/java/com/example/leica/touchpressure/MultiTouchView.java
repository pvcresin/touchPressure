package com.example.leica.touchpressure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


// http://pr.cei.uec.ac.jp/kobo2015/index.php?Android%2FTouch
public class MultiTouchView extends View {
    TextView tv;
    Paint paint = new Paint();
    int bgColor = Color.BLACK;

    float radius = 100;

    boolean touched;
    int type;
    float pressure;
    int pointer_count;
    float size;

    int x, y;

    public MultiTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        x = -999;
        y = -999;

        setBackgroundColor(Color.GRAY);
        paint.setTextSize(40);
    }

    public void setTextView(TextView _tv){
        tv = _tv;
    }

    @Override
    protected void onDraw(Canvas canvas) {  // = onResume loop
        super.onDraw(canvas);

        setBackgroundColor(bgColor);

        if (touched) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setARGB(255, 255, 255, 255);  // white line

            canvas.drawCircle(x, y, radius, paint);

            String s = "X: " + x + ", Y: " + y;
            canvas.drawText(s, x - 300, y - 2 * radius, paint);
            touched = false;
        }
        //invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        radius = 4 * size * 1000;   // real size = 2 * size * 1000 == about 100

        x = (int)event.getX();
        y = (int)event.getY();

        type = event.getActionMasked();
        pressure = event.getPressure();
        size = event.getSize();
        pointer_count = event.getPointerCount();

        touched = true;

        if (pointer_count == 1 && type == MotionEvent.ACTION_UP){ // all fingers released
            touched = false;
            pointer_count = 0;
            pressure = 0;

            bgColor = Color.BLACK;
        }

        // send process here

        String s = "TouchX:" + x + ",\nTouchY:" + y + ",\nSize:" + size + ",\nPressure:" + pressure;

        tv.setText(s);

        double thresh1 = 0.045, thresh2 = 0.050;

        if (size <= thresh1) {
            bgColor = Color.BLACK;
        } else if (thresh1 < size && size <= thresh2) {
            double depth = (size - thresh1) / (thresh2 - thresh1);
            bgColor = Color.rgb(0, 0, (int)(depth * 255));

            //bgColor = Color.BLUE;

        } else if (thresh2 < size) bgColor = Color.RED;

        invalidate();   // refresh view = onDraw()

        return true;    // do not transmit touch event
    }

}