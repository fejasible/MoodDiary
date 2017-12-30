package com.app.feja.mooddiary.widget.browse;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.base.BaseView;

/**
 * created by fejasible@163.com
 */
public class ArticleInformationView extends BaseView{

    private DiaryEntity diaryEntity;
    private Paint themePaint;
    private Paint strokePaint;
    private String dateString;

    public ArticleInformationView(Context context) {
        super(context);
        this.init();
    }

    public ArticleInformationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public ArticleInformationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public DiaryEntity getDiaryEntity() {
        return diaryEntity;
    }

    public void setDiaryEntity(DiaryEntity diaryEntity) {
        this.diaryEntity = diaryEntity;
        dateString = new DateTime(diaryEntity.getCreateTime()).toString(DateTime.Format.DATETIME);
    }

    private void init(){
        themePaint = new Paint();
        themePaint.setAntiAlias(true);
        themePaint.setStyle(Paint.Style.STROKE);
        themePaint.setColor(TheApplication.getThemeData().getColor());

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(2.0f);
        strokePaint.setColor(TheApplication.getThemeData().getColor());

        this.setDiaryEntity(new DiaryEntity());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(themePaint.getTextSize() != this.height / 2){
            themePaint.setTextSize(this.height / 2);
        }

        this.drawFace(this.height / 2, this.height / 2, this.height / 4,
                diaryEntity.getMood(), strokePaint, canvas);

        //noinspection SuspiciousNameCombination
        this.drawCenterText(dateString, this.height, this.height / 2,
                themePaint, canvas, Paint.Align.LEFT);

        canvas.drawLine(0, this.height, this.width, this.height, strokePaint);
    }


    private void drawFace(int x, int y, int rad, int face, Paint paint, Canvas canvas){

        RectF rectLeft = new RectF(x-rad*2/3, y-rad/2, x-rad/3, y);
        RectF rectRight = new RectF(x+rad/3, y-rad/2, x+rad*2/3, y);
        RectF rectBottom = new RectF(x-rad/3, y, x+rad/3, y+rad/2);

        canvas.drawCircle(x, y, rad, paint);
        switch(face){
            case DiaryEntity.MIRTHFUL:
                canvas.drawArc(rectLeft, 180, 180, false, paint);
                canvas.drawArc(rectRight, 180, 180, false, paint);
                canvas.drawArc(rectBottom, 0, 180, false, paint);
                break;
            case DiaryEntity.SMILING:
                canvas.drawLine(x-rad*2/3, y-rad/4, x-rad/3, y-rad/4, paint);
                canvas.drawLine(x+rad/3, y-rad/4, x+rad*2/3, y-rad/4, paint);
                canvas.drawArc(rectBottom, 0, 180, false, paint);
                break;
            case DiaryEntity.CALM:
                canvas.drawLine(x-rad*2/3, y-rad/4, x-rad/3, y-rad/4, paint);
                canvas.drawLine(x+rad/3, y-rad/4, x+rad*2/3, y-rad/4, paint);
                canvas.drawLine(x-rad/4, y+rad/3, x+rad/4, y+rad/3, paint);
                break;
            case DiaryEntity.DISAPPOINTED:
                canvas.drawArc(rectLeft, 0, 180, false, paint);
                canvas.drawArc(rectRight, 0, 180, false, paint);
                canvas.drawLine(x-rad/4, y+rad/2, x+rad/4, y+rad/2, paint);
                break;
            case DiaryEntity.SAD:
                rectLeft.offset(0, -rad/8);
                rectRight.offset(0, -rad/8);
                rectBottom.offset(0, rad/4);
                canvas.drawArc(rectLeft, 0, 180, false, paint);
                canvas.drawArc(rectRight, 0, 180, false, paint);
                canvas.drawArc(rectBottom, 180, 180, false, paint);
                break;
        }

    }
}
