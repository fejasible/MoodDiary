package com.app.feja.mooddiary.widget.setting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.base.TouchListenView;

/**
 * created by fejasible@163.com
 */
public class ExportTitleBar extends TouchListenView{

    private Paint paint;
    private String titleString;
    private String nextString;
    private OnTitleBarClickListener onTitleBarClickListener;

    public ExportTitleBar(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public ExportTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ExportTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 3;
        this.rectPercents = new int[]{100, 520, 100};
        this.orientation = ORIENTATION.HORIZONTAL;
    }


    private void init(){
        paint = new Paint();
        this.setBackgroundColor(TheApplication.getThemeData().getColor());
        this.titleString = getContext().getString(R.string.export_diary);
        this.nextString = getContext().getString(R.string.export_history);
        this.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                switch (item){
                    case 0:
                        if(onTitleBarClickListener != null){
                            onTitleBarClickListener.onBackClick();
                        }
                        break;
                    case 2:
                        if(onTitleBarClickListener != null){
                            onTitleBarClickListener.onHistoryClick();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        this.drawBackArrow(getRects()[0].centerX(), getRects()[0].centerY(),
                getRects()[0].height()/8, paint, canvas);


        this.drawTitleText(this.width/2, this.height/2, paint, canvas);

        this.drawNext(getRects()[2].centerX(), getRects()[2].centerY(),
                getRects()[2].height()/4, paint, canvas);
    }

    private void drawBackArrow(int x, int y, int r, Paint paint, Canvas canvas){
        paint.reset();
        if(getPressRects()[0]) {
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(Color.WHITE);
        }
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.0f);

        canvas.drawLine(x-r, y, x+r, y-r, paint);
        canvas.drawLine(x-r, y, x+r, y+r, paint);
    }

    private void drawTitleText(int x, int y, Paint paint, Canvas canvas){
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(this.width/20);
        this.drawCenterText(titleString, x, y, paint, canvas);
    }

    private void drawNext(int x, int y, int r, Paint paint, Canvas canvas){
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(this.width/20);
        this.drawCenterText(nextString, x, y, paint, canvas);
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    public interface OnTitleBarClickListener{
        void onBackClick();
        void onHistoryClick();
    }
}
