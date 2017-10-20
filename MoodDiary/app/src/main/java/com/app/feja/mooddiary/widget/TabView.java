package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.base.BaseView;


public class TabView extends BaseView {

    private float touchX, touchY;
    private boolean pressDown;
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onClick(int item) {

        }
    };

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

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.reset();
        paint.setColor(themeColor);
        paint.setAntiAlias(true);
        canvas.drawLine(0, 0, this.width, 0, paint);

        if(pressDown){
            paint.reset();
            paint.setColor(Color.LTGRAY);
            for(Rect rect: tabRects){
                if(rect.contains((int)this.touchX, (int)this.touchY)){
                    // 绘制点击效果
                    canvas.drawRect(rect, paint);
                    break;
                }
            }
        }

        for(int i=0; i<tabNum; i++){
            Rect rect = this.tabRects[i];
            paint.reset();
            paint.setColor(themeColor);
            canvas.drawLine(this.width*i/this.tabNum, 0, this.width*i/this.tabNum, this.height, paint);
            switch (i){
                case 0:
                    this.drawBook(rect.centerX(), rect.centerY(), rect.height()*9/40, paint, canvas);
                    break;
                case 1:
                    this.drawPen(rect.centerX()+rect.width()/30, rect.centerY(), rect.height()/4, paint, canvas);
                    break;
                case 2:
                    this.drawSettings(rect.centerX(), rect.centerY(), rect.height()/4, paint, canvas);
                    break;
                default:
                    canvas.drawText("item"+ i, rect.left, rect.centerY(), paint);
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
        y -= r/5;
        paint.reset();
        paint.setColor(themeColor);
        paint.setAntiAlias(true);

        paint.setStrokeWidth(2.0f);
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


    private void drawBook(int x, int y, int r, Paint paint, Canvas canvas){
        paint.reset();
        paint.setColor(themeColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.0f);

        canvas.drawLine(x-r*4/5, y+r, x+r*4/5, y+r, paint);
        canvas.drawLine(x-r*4/5, y-r, x+r*4/5, y-r, paint);
        canvas.drawLine(x+r*4/5, y+r, x+r*4/5, y-r, paint);

        canvas.drawLine(x-r*4/5, y-r, x-r*4/5,y-r/2, paint);
        canvas.drawLine(x-r*4/5, y-r/6, x-r*4/5, y+r/6, paint);
        canvas.drawLine(x-r*4/5, y+r/2, x-r*4/5, y+r, paint);

        canvas.drawLine(x-r/2, y-r, x-r/2, y+r, paint);


        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(this.themeColor);
        canvas.drawRect(x-r, y-r/2, x-r*3/5, y-r/6, paint);
        canvas.drawRect(x-r, y+r/6, x-r*3/5, y+r/2, paint);
        canvas.drawRect(x, y-r*4/5, x+r*3/5, y-r/5, paint);
    }

    private void drawSettings(int x, int y, int r, Paint paint, Canvas canvas){
        int h = r*4/5;
        paint.reset();
        paint.setColor(themeColor);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);

        Rect rect = new Rect(x-r*4/5, y-h, x+r*4/5, y-h*3/5);
        canvas.drawRect(rect, paint);
        rect.offset(0, h*4/5);
        canvas.drawRect(rect, paint);
        rect.offset(0, h*4/5);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.touchX = event.getX();
        this.touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.pressDown = true;
                break;

            case MotionEvent.ACTION_UP:
                this.pressDown = false;
                for(int i=0; i<tabRects.length; i++){
                    if(tabRects[i].contains((int)this.touchX, (int)this.touchY)){
                        this.onItemClickListener.onClick(i);
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    public interface OnItemClickListener {
        void onClick(int item);
    }

}
