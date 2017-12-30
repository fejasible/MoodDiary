package com.app.feja.mooddiary.factory;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;


/**
 * created by fejasible@163.com
 */
public class DiaryFactory implements Generator<DiaryEntity>{

    private int count;

    @Override
    public DiaryEntity next() {
        return next(new TypeEntity(TheApplication.getContext().getString(R.string.no_sort)));
    }

    public DiaryEntity next(TypeEntity typeEntity){
        return new DiaryEntity(typeEntity.getType() + ": 内容" + count++, typeEntity);
    }

}
