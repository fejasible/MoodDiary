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
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.widget.base.TouchListenView;

/**
 * created by fejasible@163.com
 */
public class ArticleEditTitleBar extends TouchListenView {

    private Paint paint;

    private String cancelString;
    private String saveString;
    private String noCategoryString;
    private String editString;

    private DiaryEntity diaryEntity;

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

    public DiaryEntity getDiaryEntity() {
        return diaryEntity;
    }

    public void setDiaryEntity(DiaryEntity diaryEntity) {
        this.invalidate();
        this.diaryEntity = diaryEntity;
    }

    private void init(){
        this.paint = new Paint();
        this.setBackgroundColor(TheApplication.getThemeData().getColor());
        this.cancelString = getResources().getString(R.string.cancel);
        this.saveString = getResources().getString(R.string.save);
        this.noCategoryString = getResources().getString(R.string.no_sort);
        this.editString = getResources().getString(R.string.edit);
        this.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                switch (item){
                    case 0:
                        onTitleBarClickListener.onCancelClick();
                        break;
                    case 1:
                        onTitleBarClickListener.onCategoryClick(diaryEntity);
                        break;
                    case 2:
                        onTitleBarClickListener.onSaveClick(diaryEntity);
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
        if(diaryEntity == null || diaryEntity.getType() == null
                || diaryEntity.getType().getType() == null){
            this.drawCenterText(noCategoryString, this.getRects()[1].centerX(),
                    this.getRects()[1].centerY(), paint, canvas);
        }else{
            this.drawCenterText(diaryEntity.getType().getType(), this.getRects()[1].centerX(),
                    this.getRects()[1].centerY(), paint, canvas);
        }
        paint.setStrokeWidth(2.0f);
        int h = this.getRects()[1].centerY()-textSize/2;
        canvas.drawLine(this.getRects()[1].centerX()-h/3, this.getRects()[1].centerY()+textSize/2+h/3,
                this.getRects()[1].centerX(), this.getRects()[1].bottom-h/3, paint);
        canvas.drawLine(this.getRects()[1].centerX()+h/3, this.getRects()[1].centerY()+textSize/2+h/3,
                this.getRects()[1].centerX(), this.getRects()[1].bottom-h/3, paint);
        paint.setStrokeWidth(1.0f);

        if(diaryEntity == null || diaryEntity.getId() == null){
            this.drawCenterText(this.saveString, this.getRects()[2].centerX(),
                    this.getRects()[2].centerY(), paint, canvas);
        }else{
            this.drawCenterText(this.editString, this.getRects()[2].centerX(),
                    this.getRects()[2].centerY(), paint, canvas);
        }
    }

    public interface OnTitleBarClickListener{
        void onCancelClick();
        void onCategoryClick(DiaryEntity diaryEntity);
        void onSaveClick(DiaryEntity diaryEntity);
    }
}
