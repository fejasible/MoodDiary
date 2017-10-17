package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;


public class TabView extends BaseView {

    private Paint paint;
    private int themeColor;

    private int tabNum = 3;
    private Rect[] tabRects;

    public TabView(Context context) {
        super(context);
        init();
    }

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.tabRects = this.calculateTabRect(this.tabNum);
    }

    private void init(){
        this.paint = new Paint();
        this.themeColor =  ContextCompat.getColor(getContext(), R.color.lightSkyBlue);
    }

    public int getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(int themeColor) {
        this.themeColor = themeColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.reset();
        paint.setColor(themeColor);
        paint.setAntiAlias(true);

        canvas.drawLine(0, 0, this.width, 0, paint);
        for(int i=0; i<tabNum; i++){
            Rect rect = this.tabRects[i];
            paint.setColor(themeColor);
            canvas.drawLine(this.width*i/this.tabNum, 0, this.width*i/this.tabNum, this.height, paint);
            switch (i){
                case 0:
                    break;
                case 1:
                    this.drawPen(rect.centerX()+rect.width()/30, rect.centerY(), rect.height()/4, paint, canvas);
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    }

    private Rect[] calculateTabRect(int tabNum){
        Rect[] rectTemples = new Rect[tabNum];
        for(int i=0; i<rectTemples.length; i++){
            rectTemples[i] = new Rect(this.width*i/tabNum, 0, this.width*(i+1)/tabNum, this.height);
        }
        return rectTemples;
    }

    private void drawPen(int x, int y, int r, Paint paint, Canvas canvas){
        paint.reset();
        paint.setColor(themeColor);
        paint.setAntiAlias(true);

        canvas.drawLine(x-r, y+r, x-r, y-r/2, paint);
        canvas.drawLine(x-r, y+r, x+r/2, y+r, paint);
        canvas.drawLine(x, y-r/2, x-r, y-r/2, paint);
        canvas.drawLine(x+r/2, y+r, x+r/2, y, paint);

        canvas.drawLine(x-r/2, y+r/2, x-r/3, y, paint);
        canvas.drawLine(x-r/2, y+r/2, x, y+r/3, paint);
        canvas.drawLine(x-r/3, y, x, y+r/3, paint);
        canvas.drawLine(x-r/3, y, x+r/2, y-r*5/6, paint);
        canvas.drawLine(x, y+r/3, x+r*5/6, y-r/2, paint);
        canvas.drawLine(x+r/2-r/7, y-r*5/6+r/7, x+r*5/6-r/7, y-r/2+r/7, paint);
        canvas.drawLine(x+r/2, y-r*5/6, x+r*5/6, y-r/2, paint);
    }
}
