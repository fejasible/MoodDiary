package com.app.feja.mooddiary.widget.base;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;


/**
 * 将View按垂直或水平方向分为指定份数，每份区域通过设定监听器以获得不同效果
 */
public class TouchListenView extends BaseView{

    /** 点击位置 */
    private float touchX, touchY;

    /** 将view分割的份数 */
    protected int touchZoneNum = 3;

    /** 分割方向 */
    protected ORIENTATION orientation = ORIENTATION.HORIZONTAL;

    /** 分割区域 */
    private Rect[] rects;

    protected static final int FUNDAMENTAL_NUMBER = 720;

    /** 指定分割区域占百分比，基数为FUNDAMENTAL_NUMBER */
    protected int[] rectPercents;

    private boolean[] pressRects;

    private OnItemTouchListener listener;

    /**
     *
     * @param context context
     * @param touchZoneNum 分割区域数量 如3
     * @param rectPercents 分割区在总区域的占比,和必须等于FUNDAMENTAL_NUMBER
     */
    public TouchListenView(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context);
        this.init(touchZoneNum, rectPercents);
    }

    public TouchListenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.defaultSettings();
        this.init(this.touchZoneNum, this.rectPercents);
    }

    public TouchListenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.defaultSettings();
        this.init(this.touchZoneNum, this.rectPercents);
    }

    public ORIENTATION getOrientation() {
        return orientation;
    }

    public void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
    }

    public Rect[] getRects() {
        return rects;
    }

    public int[] getRectPercents() {
        return rectPercents;
    }

    public boolean[] getPressRects() {
        return pressRects;
    }

    public OnItemTouchListener getOnItemTouchListener() {
        return listener;
    }

    public void setOnItemTouchListener(OnItemTouchListener listener) {
        this.listener = listener;
    }

    protected void defaultSettings(){
        this.touchZoneNum = 3;
        this.rectPercents = new int[]{100, 520, 100};
        this.orientation = ORIENTATION.HORIZONTAL;
    }

    private void init(int touchZoneNum, @Nullable int[] rectPercents){
        if(touchZoneNum > 0){// 校验分割区域数量
            this.touchZoneNum = touchZoneNum;
        }else{
            throw new IllegalArgumentException("touchZoneNum must be a positive number");
        }
        if(rectPercents == null){// 校验分割区域百分比是否为空
            this.rectPercents = new int[this.touchZoneNum];// 初始化分割区域百分比
            int sum = 0;
            for(int i=0; i<this.rectPercents.length; i++){
                this.rectPercents[i] = FUNDAMENTAL_NUMBER/this.rectPercents.length;
                sum += this.rectPercents[i];
            }
            if(sum != FUNDAMENTAL_NUMBER){
                this.rectPercents[this.rectPercents.length-1] += FUNDAMENTAL_NUMBER-sum;
            }
        }else if(rectPercents.length <= 0){// 校验分割区域百分比长度
            throw new IllegalArgumentException("rectPercents size must be a positive number");
        }else{// 校验分割区域百分比总和是否为FUNDAMENTAL_NUMBER
            int sum = 0;
            for(int i=0; i<rectPercents.length; i++){
                sum += rectPercents[i];
            }
            if(Math.abs(sum - FUNDAMENTAL_NUMBER) > 2){
                throw  new IllegalArgumentException("rectPercents sum must be "+FUNDAMENTAL_NUMBER);
            }else{
                this.rectPercents = rectPercents;// 初始化分割区域百分比
            }
        }
        this.rects = new Rect[this.touchZoneNum];// 初始化分割区域
        this.pressRects = new boolean[this.rects.length]; // 初始化按下事件
        for(int i=0; i<rects.length; i++){
            rects[i] = new Rect();
            pressRects[i] = false;
        }
        listener = new OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        Toast.makeText(getContext(), "item"+item+":"+touchRect.toShortString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int left, top, right, bottom;
        switch (this.orientation){// 重设分块区域
            case HORIZONTAL:
                top = 0;
                right = 0;
                bottom = this.height;
                for(int i=0; i<this.rects.length; i++){
                    right = right + this.rectPercents[i] * this.width / FUNDAMENTAL_NUMBER;
                    left = right - this.rectPercents[i] * this.width / FUNDAMENTAL_NUMBER;
                    rects[i].set(left, top, right, bottom);
                }
                break;
            case VERTICAL:
                left = 0;
                right = this.width;
                bottom = 0;
                for(int i=0; i<this.rects.length; i++){
                    bottom = bottom + this.rectPercents[i] * this.height / FUNDAMENTAL_NUMBER;
                    top = bottom - this.rectPercents[i] * this.height / FUNDAMENTAL_NUMBER;
                    rects[i].set(left, top, right, bottom);
                }
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.touchX = event.getX();
        this.touchY = event.getY();
        for(int i=0; i<rects.length; i++){
            if(rects[i].contains((int) this.touchX, (int) this.touchY)){
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    pressRects[i] = true;
                }else{
                    pressRects[i] = false;
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    listener.onItemTouch(i, rects[i], event);
                    break;
                }
            }
            if(event.getAction() != MotionEvent.ACTION_DOWN){
                pressRects[i] = false;
            }
        }
        invalidate();
        return true;
    }

    public interface OnItemTouchListener{
        void onItemTouch(int item, Rect touchRect, MotionEvent event);
    }

    public enum ORIENTATION{
        VERTICAL, HORIZONTAL
    }

}
