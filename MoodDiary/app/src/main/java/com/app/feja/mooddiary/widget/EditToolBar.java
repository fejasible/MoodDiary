package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.widget.base.TouchListenView;

public class EditToolBar extends TouchListenView{

    public static final int UP = 0;
    public static final int DOWN = 1;

    private Paint themePaint;
    private Paint reflectPaint;
    private Paint backgroundPaint;
    private int status = UP;
    private int face = DiaryEntity.CALM;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        this.invalidate();
    }

    public int getFace() {
        return face;
    }

    public void setFace(int face) {
        this.face = face;
        this.invalidate();
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
        this.touchZoneNum = 6;
        this.orientation = ORIENTATION.HORIZONTAL;
    }

    @Override
    public void onDraw(Canvas canvas){
        this.drawBackground(backgroundPaint, canvas);
        this.drawTools_1(getRects()[0], themePaint, canvas);
        this.drawTools_2(getRects()[1], themePaint, canvas);
        this.drawTools_3(getRects()[2], themePaint, canvas);
        this.drawTools_4(getRects()[3], themePaint, canvas);
        this.drawTools_5(getRects()[4], themePaint, canvas);
        this.drawTools_6(getRects()[5], themePaint, canvas);
    }


    private void drawBackground(Paint paint, Canvas canvas){
        canvas.drawLine(0, 1, width, 1, paint);
        canvas.drawLine(0, height, width, height, paint);
    }

    private void drawTools_1(Rect rect, Paint paint, Canvas canvas){
        drawCenterText("Aa", rect.centerX(), rect.centerY(), paint, canvas);
    }

    private void drawTools_2(Rect rect, Paint paint, Canvas canvas){
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

    private void drawTools_3(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height()/2;
        canvas.drawCircle(x, y, r/2, paint);
        canvas.drawLine(x, y-r/3, x, y, paint);
        canvas.drawLine(x, y, x+r/3, y, paint);
    }

    private void drawTools_4(Rect rect, Paint paint, Canvas canvas){
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

    private void drawTools_5(Rect rect, Paint paint, Canvas canvas) {
        this.drawFace(rect.centerX(), rect.centerY(), rect.height()/4, face, paint, canvas);
    }

    private void drawTools_6(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height()/2;
        if(status == UP){
            canvas.drawLine(x-r/2, y-r/3, x, y+r/3, paint);
            canvas.drawLine(x, y+r/3, x+r/2, y-r/3, paint);
        }else if(status == DOWN){
            canvas.drawLine(x-r/2, y+r/3, x, y-r/3, paint);
            canvas.drawLine(x, y-r/3, x+r/2, y+r/3, paint);
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
