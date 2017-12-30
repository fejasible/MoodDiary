package com.app.feja.mooddiary.ui.view;


import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;

import java.util.List;

/**
 * created by fejasible@163.com
 */
public interface CategoryView extends BaseView{

    void onLoadCategories(List<TypeEntity> typeEntities, List<DiaryEntity> diaryEntities);


}
