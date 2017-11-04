package com.app.feja.mooddiary.widget.edit;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.widget.base.TouchListenView;

public class FaceChooseView extends TouchListenView{

    private Paint themePaint;
    private Paint backgroundPaint;
    private int themeColor;

    public FaceChooseView(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public FaceChooseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public FaceChooseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public void init(){
        this.themeColor = ContextCompat.getColor(getContext(), R.color.lightSkyBlue);

        themePaint = new Paint();
        themePaint.setAntiAlias(true);
        themePaint.setStyle(Paint.Style.STROKE);
        themePaint.setColor(themeColor);
        themePaint.setStrokeWidth(2.0f);

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(Color.LTGRAY);
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 5;
        this.orientation = ORIENTATION.HORIZONTAL;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(int i=0; i<5; i++){
            if(getPressRects()[i]){
                canvas.drawRect(getRects()[i], backgroundPaint);
            }
            this.drawFace(
                    getRects()[i].centerX(),
                    getRects()[i].centerY(),
                    getRects()[i].height() > getRects()[i].width() ? getRects()[i].width()/4 : getRects()[i].height()/4,
                    i, themePaint, canvas);
        }
    }


    private void drawFace(int x, int y, int rad, int face, Paint paint, Canvas canvas){
        RectF rectLeft = new RectF(x-rad*2/3, y-rad/2, x-rad/3, y);
        RectF rectRight = new RectF(x+rad/3, y-rad/2, x+rad*2/3, y);
        RectF rectBottom = new RectF(x-rad/3, y, x+rad/3, y+rad/2);

        canvas.drawCircle(x, y, rad, paint);
        switch(face){
            case DiaryEntity.MIRTHFUL:
                canvas.drawArc(rectLeft, 180, 180, false, paint);
                canvas.drawArc(rectRight, 180, 180, false, paint);
                canvas.drawArc(rectBottom, 0, 180, false, paint);
                break;
            case DiaryEntity.SMILING:
                canvas.drawLine(x-rad*2/3, y-rad/4, x-rad/3, y-rad/4, paint);
                canvas.drawLine(x+rad/3, y-rad/4, x+rad*2/3, y-rad/4, paint);
                canvas.drawArc(rectBottom, 0, 180, false, paint);
                break;
            case DiaryEntity.CALM:
                canvas.drawLine(x-rad*2/3, y-rad/4, x-rad/3, y-rad/4, paint);
                canvas.drawLine(x+rad/3, y-rad/4, x+rad*2/3, y-rad/4, paint);
                canvas.drawLine(x-rad/4, y+rad/3, x+rad/4, y+rad/3, paint);
                break;
            case DiaryEntity.DISAPPOINTED:
                canvas.drawArc(rectLeft, 0, 180, false, paint);
                canvas.drawArc(rectRight, 0, 180, false, paint);
                canvas.drawLine(x-rad/4, y+rad/2, x+rad/4, y+rad/2, paint);
                break;
            case DiaryEntity.SAD:
                rectLeft.offset(0, -rad/8);
                rectRight.offset(0, -rad/8);
                rectBottom.offset(0, rad/4);
                canvas.drawArc(rectLeft, 0, 180, false, paint);
                canvas.drawArc(rectRight, 0, 180, false, paint);
                canvas.drawArc(rectBottom, 180, 180, false, paint);
                break;
        }

    }


}
