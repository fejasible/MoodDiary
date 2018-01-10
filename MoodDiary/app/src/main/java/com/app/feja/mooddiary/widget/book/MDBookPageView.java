package com.app.feja.mooddiary.widget.book;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.app.feja.mooddiary.model.entity.DiaryEntity;

/**
 * Created by fejasible@163.com
 */
public class MDBookPageView extends BookPageView{

    private DiaryEntity diaryEntity;

    public MDBookPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DiaryEntity getDiaryEntity() {
        return diaryEntity;
    }

    public void setDiaryEntity(DiaryEntity diaryEntity) {
        this.diaryEntity = diaryEntity;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
