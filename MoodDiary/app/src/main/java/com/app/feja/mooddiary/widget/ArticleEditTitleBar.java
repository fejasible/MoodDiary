package com.app.feja.mooddiary.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.base.TouchListenView;

public class ArticleEditTitleBar extends TouchListenView {

    private Paint paint;

    private String categoryString = "未分类";

    private String cancelString;
    private String saveString;

    private OnTitleBarClickListener onTitleBarClickListener;


    public ArticleEditTitleBar(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public ArticleEditTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ArticleEditTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public OnTitleBarClickListener getOnTitleBarClickListener() {
        return onTitleBarClickListener;
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    private void init(){
        this.paint = new Paint();
        this.cancelString = getResources().getString(R.string.cancel);
        this.saveString = getResources().getString(R.string.save);
        this.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                switch (item){
                    case 0:
                        onTitleBarClickListener.onCancelClick();
                        break;
                    case 1:
                        onTitleBarClickListener.onCategoryClick();
                        break;
                    case 2:
                        onTitleBarClickListener.onSaveClick();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 3;
        this.rectPercents = new int[]{100, 520, 100};
        this.orientation = ORIENTATION.HORIZONTAL;
    }

    @Override
    public void onDraw(Canvas canvas){
        int textSize = this.width/20;
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        this.drawCenterText(this.cancelString, this.getRects()[0].centerX(),
                this.getRects()[0].centerY(), paint, canvas);
        this.drawCenterText(this.categoryString, this.getRects()[1].centerX(),
                this.getRects()[1].centerY(), paint, canvas);
        this.drawCenterText(this.saveString, this.getRects()[2].centerX(),
                this.getRects()[2].centerY(), paint, canvas);
    }

    public interface OnTitleBarClickListener{
        void onCancelClick();
        void onCategoryClick();
        void onSaveClick();
    }

}
