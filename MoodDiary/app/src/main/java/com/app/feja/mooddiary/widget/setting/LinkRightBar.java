package com.app.feja.mooddiary.widget.setting;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.base.BaseView;

/**
 * created by fejasible@163.com
 */
public class LinkRightBar extends BaseView{

    private Paint themePaint;
    private Paint backgroundPaint;
    private Paint whiteBackgroundPaint;
    private boolean pressed = false;
    private String string;
    private Rect arrowRect;
    private OnClickListener onClickListener;

    public LinkRightBar(Context context) {
        super(context);
        this.init();
    }

    public LinkRightBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public LinkRightBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init(){
        themePaint = new Paint();
        themePaint.setAntiAlias(true);
        themePaint.setColor(TheApplication.getThemeData().getColor());

        backgroundPaint = new Paint(themePaint);
        backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.lightGrey));

        whiteBackgroundPaint = new Paint(backgroundPaint);
        whiteBackgroundPaint.setColor(Color.WHITE);

        this.string = "Link Right Bar";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(arrowRect == null){
            arrowRect = new Rect(getWidth()-getHeight(), 0, getWidth(), getHeight());
        }
        if(themePaint.getTextSize() != getHeight() / 3){
            themePaint.setTextSize(getHeight() / 3);
        }
        if(themePaint.getColor() != TheApplication.getThemeData().getColor()){
            themePaint.setColor(TheApplication.getThemeData().getColor());
        }
        if(pressed){
            canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        }else{
            canvas.drawRect(0, 0, getWidth(), getHeight(), whiteBackgroundPaint);
        }
        this.drawCenterText(string, this.getHeight() / 2, this.getHeight() / 2, themePaint, canvas,
                Paint.Align.LEFT);
        this.drawRightArrow(arrowRect, themePaint, canvas);
        canvas.drawLine(0, 0, getWidth(), 0, themePaint);
        canvas.drawLine(0, getHeight(), getWidth(), getHeight(), themePaint);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
        this.invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.pressed = true;
                if(this.onClickListener != null){
                    this.onClickListener.onClick(this);
                }
                break;
            default:
                this.pressed = false;
                break;
        }
        invalidate();
        return true;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        this.onClickListener = l;
    }
}
