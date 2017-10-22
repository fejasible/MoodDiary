package com.app.feja.mooddiary.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.base.BaseView;

public class CategoryView extends BaseView{

    private Paint paint;
    private String categoryString;
    private int textColor = Color.WHITE;
    private boolean showDelete = false;
    private int categoryCount = 0;

    public CategoryView(Context context) {
        super(context);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CategoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        this.paint = new Paint();
        this.categoryString = getResources().getString(R.string.no_sort);
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightSkyBlue));
        this.setAlpha(0.9f);
    }

    public String getCategoryString() {
        return categoryString;
    }

    public void setCategoryString(String categoryString) {
        this.categoryString = categoryString;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public boolean isShowDelete() {
        return showDelete;
    }

    public void setShowDelete(boolean showDelete) {
        this.showDelete = showDelete;
    }

    public int getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(int categoryCount) {
        this.categoryCount = categoryCount;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.reset();
        int textSize = this.height/2;
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);
        paint.setColor(textColor);

        if(showDelete){
            this.drawDeleteButton(this.width-this.height/2, this.height/2, this.height/4, paint, canvas);
            drawCenterText(categoryString+"("+categoryCount+")", textSize, height/2, paint, canvas, Paint.Align.LEFT);
        }else {
            drawCenterText(categoryString, textSize, height / 2, paint, canvas, Paint.Align.LEFT);
        }
    }


    private void drawDeleteButton(int x, int y, int r, Paint paint, Canvas canvas){
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, r, paint);
        canvas.drawLine(x-r/3, y-r/3, x+r/3, y+r/3, paint);
        canvas.drawLine(x+r/3, y-r/3, x-r/3, y+r/3, paint);
    }
}
