package com.app.feja.mooddiary.factory;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.entity.TypeEntity;

import java.util.Random;

/**
 * created by fejasible@163.com
 */
public class TypeFactory implements Generator<TypeEntity> {


    private Random random = new Random();

    @Override
    public TypeEntity next() {
        return new TypeEntity("测试分类" + random.nextInt(10));
    }

    public TypeEntity nextStandard(){
        return new TypeEntity(TheApplication.getContext().getString(R.string.no_sort));
    }
}
