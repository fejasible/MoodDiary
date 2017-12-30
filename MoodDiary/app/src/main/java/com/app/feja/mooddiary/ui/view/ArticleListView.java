package com.app.feja.mooddiary.ui.view;


import android.support.annotation.Nullable;

import com.app.feja.mooddiary.model.entity.DiaryEntity;

import java.util.List;

/**
 * created by fejasible@163.com
 */
public interface ArticleListView extends BaseView{

    void onLoadArticles(List<DiaryEntity> diaryEntities);

}
