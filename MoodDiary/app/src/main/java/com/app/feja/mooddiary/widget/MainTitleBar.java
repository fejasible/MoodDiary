package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.base.BaseView;

public class MainTitleBar extends BaseView {

    private String titleString;
    private String searchString;

    private AnimationSet animationSet;

    private String today = "";

    private Paint paint;

    private float touchX, touchY;

    private boolean pressCategoryDown = false;
    private boolean pressCalendarDown = false;
    private boolean pressSearchDown = false;

    private boolean showDateTime = false;
    private boolean showSearch = false;

    private boolean enableCategory = true;
    private boolean enableCalendar = true;
    private boolean enableSearch = true;

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
        this.titleString = getResources().getString(R.string.all_sort);
        this.searchString = getResources().getString(R.string.search);
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

        animationSet = new AnimationSet(false);
        AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation2.setDuration(500);
        animationSet.addAnimation(alphaAnimation2);
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
        this.invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        if(this.showSearch){
//            this.drawSearch(rectSearch, paint, canvas);
            this.drawUpArrow(rectSearch, paint, canvas);
            this.drawSearchTitle(rectCategory, paint, canvas);
            return ;
        }
        if(this.showDateTime){
            this.drawDate(rectCategory.left, rectCategory.top, rectCategory.right, rectCategory.bottom, paint, canvas);
//            this.drawCalendarImage(rectCalendar, paint, canvas);
            this.drawLeftUpArrow(rectCalendar, paint, canvas);
            return ;
        }
        this.drawCalendarImage(rectCalendar, paint, canvas);
        this.drawCategory(rectCategory, paint, canvas);
        this.drawSearch(rectSearch, paint, canvas);
    }

    public void changeDate(long timestamp){
        this.today = new DateTime(timestamp).toString(DateTime.Format.READABLE_MONTH);
        this.enableSearch = false;
        this.enableCategory = false;
        this.showDateTime = true;
        this.invalidate();
        this.startAnimation(animationSet);
    }

    public void changeDate(){
        this.enableSearch = false;
        this.enableCategory = false;
        this.showDateTime = true;
        this.invalidate();
        this.startAnimation(animationSet);
    }

    public void changeTitle(){
        this.enableSearch = true;
        this.enableCategory = true;
        this.enableCalendar = true;
        this.showDateTime = false;
        this.showSearch = false;
        this.invalidate();
        this.startAnimation(animationSet);
    }

    public void changeSearch(){
        this.enableCalendar = false;
        this.enableCategory = false;
        this.showSearch = true;
        this.invalidate();
        this.startAnimation(animationSet);
    }

    private void drawDate(int left, int top, int right, int bottom, Paint paint, Canvas canvas){
        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(this.width/20);
        paint.setColor(Color.WHITE);
        drawCenterText(this.today, (left+right)/2, (top+bottom)/2, paint, canvas);
    }

    private void drawSearchTitle(Rect rect, Paint paint, Canvas canvas){
        paint.reset();
        int textSize = this.width/20;
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        drawCenterText(searchString, rect.centerX(), rect.centerY(), paint, canvas);
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
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(x, y, r, paint);
        canvas.drawLine(x+r, y+r, x+r/1.414f, y+r/1.414f, paint);
    }

    private void drawUpArrow(Rect rect, Paint paint, Canvas canvas){
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
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(x-r/2, y+r/3, x, y-r/3, paint);
        canvas.drawLine(x, y-r/3, x+r/2, y+r/3, paint);
    }

    private void drawLeftUpArrow(Rect rect, Paint paint, Canvas canvas){
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
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(x-r/2, y, x, y, paint);
        canvas.drawLine(x-r/2, y, x-r/2, y+r/2, paint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.touchX = event.getX();
        this.touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(enableCategory && center.contains((int)this.touchX, (int)this.touchY)){
                    this.pressCategoryDown = true;
                }else if(enableCalendar && left.contains((int)this.touchX, (int)this.touchY)){
                    this.pressCalendarDown = true;
                }else if(enableSearch && right.contains((int)this.touchX, (int)this.touchY)){
                    this.pressSearchDown = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                this.pressCalendarDown = false;
                this.pressCategoryDown = false;
                this.pressSearchDown = false;
                if(center.contains((int)this.touchX, (int)this.touchY) && enableCategory){
                    listener.onCategoryClick();
                }else if(left.contains((int)this.touchX, (int)this.touchY) && enableCalendar){
                    listener.onCalendarClick();
                }else if(right.contains((int)this.touchX, (int)this.touchY) && enableSearch){
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
