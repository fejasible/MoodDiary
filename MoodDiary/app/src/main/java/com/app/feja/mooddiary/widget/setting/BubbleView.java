package com.app.feja.mooddiary.widget.setting;


import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * created by fejasible@163.com
 */
public class BubbleView extends LinearLayout {

    private BubbleViewController bubbleViewController;

    public BubbleView(@NonNull Context context) {
        this(context,null);
    }

    public BubbleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        bubbleViewController = new BubbleViewController(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bubbleViewController.onSizeChanged(w,h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        bubbleViewController.onLayout(changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bubbleViewController.onDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!bubbleViewController.getEnable()){
                    bubbleViewController.setEnable(true);
                }
                bubbleViewController.onViewClick();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public BubbleViewController getBubbleViewController(){
        return this.bubbleViewController;
    }
}