package com.app.feja.mooddiary.widget;


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
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.widget.base.TouchListenView;

public class ArticleBrowseTitleBar extends TouchListenView{

    private Paint paint;
    private DiaryEntity diaryEntity;
    private int themeColor;
    private String noCategoryString;
    private OnTitleBarClickListener onTitleBarClickListener;


    public ArticleBrowseTitleBar(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public ArticleBrowseTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ArticleBrowseTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public OnTitleBarClickListener getOnTitleBarClickListener() {
        return onTitleBarClickListener;
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }

    public DiaryEntity getDiaryEntity() {
        return diaryEntity;
    }

    public void setDiaryEntity(DiaryEntity diaryEntity) {
        this.diaryEntity = diaryEntity;
    }

    public void init(){
        this.paint = new Paint();
        this.themeColor = ContextCompat.getColor(getContext(), R.color.lightSkyBlue);
        this.noCategoryString = getResources().getString(R.string.no_sort);
        this.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                switch (item){
                    case 0:
                        onTitleBarClickListener.onBackClick();
                        break;
                    case 1:
                        onTitleBarClickListener.onCategoryClick();
                        break;
                    case 2:
                        onTitleBarClickListener.onGarbageClick();
                        break;
                    case 3:
                        onTitleBarClickListener.onPenClick();
                        break;
                }
            }
        });
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 4;
        this.rectPercents = new int[]{100, 420, 100, 100};
        this.orientation = ORIENTATION.HORIZONTAL;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        this.drawBackArrow(getRects()[0].centerX(), getRects()[0].centerY(),
                getRects()[0].height()/8, paint, canvas);
        if(diaryEntity == null || diaryEntity.getType() == null
                || diaryEntity.getType().getType() == null){
            this.drawTitle(noCategoryString, this.width/2, this.height/2, paint, canvas);
        }else{
            this.drawTitle(diaryEntity.getType().getType(), this.width/2, this.height/2, paint, canvas);
        }
        this.drawGarbage(getRects()[2].centerX(), getRects()[2].centerY(), this.height/5, paint, canvas);
        this.drawPen(getRects()[3].centerX(), getRects()[3].centerY(),
                getRects()[3].height()/4, paint, canvas);

    }


    private void drawBackArrow(int x, int y, int r, Paint paint, Canvas canvas){
        paint.reset();
        if(getPressRects()[0]) {
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(Color.WHITE);
        }
        paint.setAntiAlias(true);
        paint.setStrokeWidth(2.0f);

        canvas.drawLine(x-r, y, x+r, y-r, paint);
        canvas.drawLine(x-r, y, x+r, y+r, paint);
    }

    private void drawTitle(String title, int x, int y, Paint paint, Canvas canvas){
        int textSize = this.width/20;
        paint.reset();
        paint.setAntiAlias(true);
        if(getPressRects()[1]) {
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(Color.WHITE);
        }
        paint.setTextSize(textSize);
        this.drawCenterText(title, x, y, paint, canvas);
    }


    private void drawGarbage(int x, int y, int r, Paint paint, Canvas canvas){
        paint.reset();
        paint.setAntiAlias(true);
        if(getPressRects()[2]) {
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(Color.WHITE);
        }
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.0f);

        canvas.drawLine(x-r, y-r*3/4, x+r, y-r*3/4, paint);
        canvas.drawRect(x-r/4, y-r, x+r/4, y-r*3/4, paint);
        canvas.drawLine(x-r*3/4, y-r*3/4, x-r/2, y+r, paint);
        canvas.drawLine(x+r*3/4, y-r*3/4, x+r/2, y+r, paint);
        canvas.drawLine(x-r/2, y+r, x+r/2, y+r, paint);
        canvas.drawLine(x-r/3, y-r/2, x-r/4, y+r*3/4, paint);
        canvas.drawLine(x, y-r/2, x, y+r*3/4, paint);
        canvas.drawLine(x+r/3, y-r/2, x+r/4, y+r*3/4, paint);
    }

    private void drawPen(int x, int y, int r, Paint paint, Canvas canvas){
        y -= r/5;
        paint.reset();
        if(getPressRects()[3]) {
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(Color.WHITE);
        }
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


    public interface OnTitleBarClickListener{
        void onBackClick();
        void onCategoryClick();
        void onGarbageClick();
        void onPenClick();
    }
}
