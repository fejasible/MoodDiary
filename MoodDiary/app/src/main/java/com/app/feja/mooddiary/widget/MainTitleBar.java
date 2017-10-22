package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.app.feja.mooddiary.widget.base.BaseView;

public class MainTitleBar extends BaseView {

    private String titleString = "所有分类";

    private Paint paint;

    private float touchX, touchY;

    private boolean pressCategoryDown = false;
    private boolean pressCalendarDown = false;
    private boolean pressSearchDown = false;

    private Rect rectCategory = new Rect();
    private Rect rectCalendar = new Rect();
    private Rect rectSearch = new Rect();

    private Rect left = new Rect();
    private Rect center = new Rect();
    private Rect right = new Rect();

    private OnTitleBarClickListener listener;



    public MainTitleBar(Context context) {
        super(context);
        this.init();
    }

    public MainTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public MainTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public void init(){
        this.paint = new Paint();
        listener = new OnTitleBarClickListener() {
            @Override
            public void onCalendarClick() {
                Toast.makeText(getContext(), "calendar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCategoryClick() {
                Toast.makeText(getContext(), "category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchClick() {
                Toast.makeText(getContext(), "search", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectCategory.set(this.width/2-this.height/2, 0, this.width/2+this.height/2, this.height);
        rectCalendar.set(this.height/4, this.height*3/16, this.height*3/4, this.height*11/16);
        rectSearch.set(this.width-this.height*7/10, this.height*3/10, this.width-this.height*3/10, this.height*7/10);

        left.set(0, 0, this.height, this.height);
        center.set(this.width/3, 0, this.width*2/3, this.height);
        right.set(this.width-this.height, 0, this.width, this.height);
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener listener){
        this.listener = listener;
    }

    public OnTitleBarClickListener getOnTitleBarClickListener(){
        return listener;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    @Override
    public void onDraw(Canvas canvas){

        this.drawCategory(rectCategory, paint, canvas);
        this.drawCalendarImage(rectCalendar, paint, canvas);
        this.drawSearch(rectSearch, paint, canvas);
    }

    private void drawCategory(Rect rect, Paint paint, Canvas canvas){
        paint.reset();
        int textSize = this.width/20;
        paint.setAntiAlias(true);
        if(pressCategoryDown){
            paint.setColor(Color.GRAY);
        }else {
            paint.setColor(Color.WHITE);
        }
        paint.setTextSize(textSize);
        drawCenterText(titleString, rect.centerX(), rect.centerY(), paint, canvas);
    }

    private void drawCalendarImage(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.width()/2;
        paint.reset();
        if(pressCalendarDown){
            paint.setColor(Color.GRAY);
        }else {
            paint.setColor(Color.WHITE);
        }
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawLine(x-r, y-r/2, x-r, y+r, paint);
        canvas.drawLine(x+r, y-r/2, x+r, y+r, paint);
        canvas.drawLine(x-r, y+r, x+r, y+r, paint);

        canvas.drawLine(x-r, y-r/2, x-r*2/3, y-r/2, paint);
        canvas.drawLine(x-r/3, y-r/2, x+r/3, y-r/2, paint);
        canvas.drawLine(x+r*2/3, y-r/2, x+r, y-r/2, paint);

        Rect rectSmall = new Rect(x-r*2/3, y-r*2/3, x-r/3, y-r/4);
        canvas.drawRect(rectSmall, paint);
        rectSmall.offset(r, 0);
        canvas.drawRect(rectSmall, paint);
        canvas.drawLine(x-r, y-r/8, x+r, y-r/8, paint);

        int dx = r*2/7, dy = r*3/8;
        for(int i=0; i<6; i+=2) canvas.drawLine(x-r+dx+i*dx, y+r-2*dy, x-r+2*dx+i*dx, y+r-2*dy, paint);
        for(int i=0; i<6; i+=2) canvas.drawLine(x-r+dx+i*dx, y+r-dy, x-r+2*dx+i*dx, y+r-dy, paint);
    }

    private void drawSearch(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.width()/2;

        paint.reset();
        if(pressSearchDown){
            paint.setColor(Color.GRAY);
        }else {
            paint.setColor(Color.WHITE);
        }
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(x, y, r, paint);
        canvas.drawLine(x+r, y+r, x+r/1.414f, y+r/1.414f, paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.touchX = event.getX();
        this.touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(center.contains((int)this.touchX, (int)this.touchY)){
                    this.pressCategoryDown = true;
                }else if(left.contains((int)this.touchX, (int)this.touchY)){
                    this.pressCalendarDown = true;
                }else if(right.contains((int)this.touchX, (int)this.touchY)){
                    this.pressSearchDown = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                this.pressCalendarDown = false;
                this.pressCategoryDown = false;
                this.pressSearchDown = false;
                if(center.contains((int)this.touchX, (int)this.touchY)){
                    listener.onCategoryClick();
                }else if(left.contains((int)this.touchX, (int)this.touchY)){
                    listener.onCalendarClick();
                }else if(right.contains((int)this.touchX, (int)this.touchY)){
                    listener.onSearchClick();
                }
                break;
        }
        invalidate();
        return true;
    }

    public interface OnTitleBarClickListener{
        void onCalendarClick();
        void onCategoryClick();
        void onSearchClick();
    }
}
