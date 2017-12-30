package com.app.feja.mooddiary.widget.base;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * created by fejasible@163.com
 */
public class BaseView extends View{

    protected int width, height;

    public BaseView(Context context) {
        super(context);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            int desire = size + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            int desire = size + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
        return height;
    }

    public final void drawCenterText(String text, int x, int y, Paint paint, Canvas canvas){
        paint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        canvas.drawText(text, x, y-(fontMetricsInt.top+fontMetricsInt.bottom)/2, paint);
    }

    public final void drawCenterText(String text, int x, int y, Paint paint, Canvas canvas, Paint.Align align){
        paint.setTextAlign(align);
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        canvas.drawText(text, x, y-(fontMetricsInt.top+fontMetricsInt.bottom)/2, paint);
    }

    public final void drawLeftArrow(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height() / 4;
        canvas.drawLine(x-r/2, y, x+r/2, y-r/2, paint);
        canvas.drawLine(x-r/2, y, x+r/2, y+r/2, paint);
    }

    public final void drawRightArrow(Rect rect, Paint paint, Canvas canvas){
        int x = rect.centerX();
        int y = rect.centerY();
        int r = rect.height() / 4;
        canvas.drawLine(x+r/2, y, x-r/2, y-r/2, paint);
        canvas.drawLine(x+r/2, y, x-r/2, y+r/2, paint);
    }

    public static void setMargins (View view, int l, int t, int r, int b) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.setMargins(l, t, r, b);
            view.requestLayout();
        }
    }

    public static void setMargins(View view, int m){
        setMargins(view, m, m, m, m);
    }

}
