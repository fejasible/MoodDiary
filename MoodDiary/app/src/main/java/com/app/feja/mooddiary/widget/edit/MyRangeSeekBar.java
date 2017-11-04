package com.app.feja.mooddiary.widget.edit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.jaygoo.widget.RangeSeekBar;

public class MyRangeSeekBar extends RangeSeekBar{

    private Paint paint;
    private Paint.FontMetricsInt fontMetricsInt;

    public MyRangeSeekBar(Context context) {
        super(context);
        this.init();
    }

    public MyRangeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init(){
        this.paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setAntiAlias(true);
        paint.setColor(ContextCompat.getColor(getContext(), R.color.lightSkyBlue));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(getCurrentRange()[0] > 40){
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(getResources().getString(R.string.current_text_size)+":"
                    +(int)getCurrentRange()[0], 10, 40, paint);
        }else{
            paint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(getResources().getString(R.string.current_text_size)+":"
                    +(int)getCurrentRange()[0], getWidth()-10, 40, paint);
        }
    }
}
