package com.app.feja.mooddiary.widget.setting;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.app.feja.mooddiary.widget.base.BaseView;

public class ThemeSelectBar extends BaseView{

    private Paint textPaint;
    private Paint colorPaint;
    private Paint selectButtonPaint;
    private String text;

    private boolean select;

    public ThemeSelectBar(Context context) {
        super(context);
        this.init();
    }

    public ThemeSelectBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public ThemeSelectBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init(){

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setAntiAlias(true);

        colorPaint = new Paint();
        colorPaint.setAntiAlias(true);
        colorPaint.setColor(Color.BLACK);

        selectButtonPaint = new Paint();
        selectButtonPaint.setAntiAlias(true);
        selectButtonPaint.setStyle(Paint.Style.STROKE);
        selectButtonPaint.setColor(Color.BLACK);

        text = "————";
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(textPaint.getTextSize() != getHeight() >> 2){
            textPaint.setTextSize(getHeight() >> 2);
        }
        if(select){
            selectButtonPaint.setStyle(Paint.Style.FILL);
        }else{
            selectButtonPaint.setStyle(Paint.Style.STROKE);
        }
        this.drawSelectButton(0, 0, getHeight(), getHeight(), selectButtonPaint, canvas);
        this.drawCenterText(text, getWidth()>>1, getHeight()>>1, textPaint, canvas);
        if(rect == null){
            rect = new RectF((getWidth()*4-getHeight()*3)/4, getHeight()>>2, getWidth()-getHeight()/4, getHeight()*3/4);
        }
        this.drawColorIndicator(rect, colorPaint, canvas);
    }

    private void drawSelectButton(int left, int top, int right, int bottom, Paint paint, Canvas canvas){
        canvas.drawCircle((left+right)>>1, (top+bottom)>>1, top>bottom?top>>3:bottom>>3, paint);
    }

    private RectF rect;
    private void drawColorIndicator(RectF rect, Paint paint, Canvas canvas){
        canvas.drawRoundRect(rect, 7, 7, paint);
    }

    public int getColor() {
        return this.colorPaint.getColor();
    }

    public void setColor(int color) {
        this.colorPaint.setColor(color);
        this.selectButtonPaint.setColor(color);
        this.invalidate();
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
        this.invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();
    }
}

