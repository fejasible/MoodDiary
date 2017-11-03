package com.app.feja.mooddiary.widget.Search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.base.TouchListenView;

public class EditToolBar extends TouchListenView{

    private Paint themePaint;
    private Paint reflectPaint;
    private Paint backgroundPaint;

    public EditToolBar(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public EditToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public EditToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init(){
        this.initThemePaint();
        this.initBackgroundPaint();
        this.initReflectPaint();
    }

    private void initThemePaint(){
        this.themePaint = new Paint();
        this.themePaint.setStyle(Paint.Style.STROKE);
        this.themePaint.setAntiAlias(true);
        this.themePaint.setStrokeWidth(2.0f);
        this.themePaint.setColor(ContextCompat.getColor(getContext(), R.color.lightSkyBlue));
    }

    private void initBackgroundPaint(){
        this.backgroundPaint = new Paint();
        this.backgroundPaint.setStyle(Paint.Style.STROKE);
        this.backgroundPaint.setAntiAlias(true);
        this.backgroundPaint.setStrokeWidth(1.0f);
        this.backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.lightSkyBlue));
    }

    private void initReflectPaint(){
        this.reflectPaint = new Paint();
        this.reflectPaint.setStyle(Paint.Style.STROKE);
        this.reflectPaint.setAntiAlias(true);
        this.reflectPaint.setStrokeWidth(1.0f);
        this.reflectPaint.setColor(ContextCompat.getColor(getContext(), R.color.dark_gray));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.themePaint.setTextSize(this.height/2);
        this.backgroundPaint.setTextSize(this.height/2);
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 5;
        this.orientation = ORIENTATION.HORIZONTAL;
    }

    @Override
    public void onDraw(Canvas canvas){
        this.drawBackground(0, 1, width, 1, backgroundPaint, canvas);
        this.drawTools_1(getRects()[0], themePaint, canvas);
        this.drawTools_2(getRects()[1], themePaint, canvas);
        this.drawTools_3(getRects()[2], themePaint, canvas);
        this.drawTools_4(getRects()[3], themePaint, canvas);
        this.drawTools_5(getRects()[4], themePaint, canvas);
    }

    private void drawBackground(float x1, float y1, float x2, float y2, Paint paint, Canvas canvas){
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    private void drawTools_1(Rect rect, Paint paint, Canvas canvas){
        drawCenterText("Aa", rect.centerX(), rect.centerY(), paint, canvas);
    }

    private void drawTools_2(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height()/2;

        canvas.drawRect(x-r*2/3+r/12, y-r/2-r/12, x+r/2+r/12, y+r*2/3-r/12, paint);
        float textSize = paint.getTextSize();
        paint.setTextSize(textSize*2/3);
        drawCenterText("b", rect.centerX(), rect.centerY(), paint, canvas);
        paint.setTextSize(textSize);
    }

    private void drawTools_3(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height()/2;
        canvas.drawRect(x-r*2/3+r/12, y-r/2-r/12, x+r/2+r/12, y+r*2/3-r/12, paint);
        canvas.drawLine(x-r*2/3+r/12, y+r/3, x-r/3, y-r/3, paint);
        canvas.drawLine(x-r/3, y-r/3, x, y+r/12, paint);
        canvas.drawLine(x, y+r/12, x+r/3, y, paint);
        canvas.drawLine(x+r/3, y, x+r/2+r/12, y+r/3, paint);
        canvas.drawCircle(x+r/3, y-r/3, 3, paint);
    }

    private void drawTools_4(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height()/2;
        canvas.drawCircle(x, y, r/2, paint);
        canvas.drawLine(x, y-r/3, x, y, paint);
        canvas.drawLine(x, y, x+r/3, y, paint);
    }

    private void drawTools_5(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height()/2;
        canvas.drawCircle(x, y, r/4, paint);
        for(int i=0; i<8; i++){
            float theta = (float) (i*Math.PI/4);
            canvas.drawLine((int)((x*3+Math.cos(theta)*r)/3), (int)((y*3-Math.sin(theta)*r)/3),
                    (int)((x*2+Math.cos(theta)*r)/2), (int)((y*2-Math.sin(theta)*r)/2), paint);
        }
    }


}
