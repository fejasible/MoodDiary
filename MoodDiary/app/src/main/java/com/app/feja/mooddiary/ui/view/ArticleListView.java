package com.app.feja.mooddiary.ui.view;


import com.app.feja.mooddiary.model.entity.DiaryEntity;

import java.util.List;

public interface ArticleListView extends BaseView{

    void onLoadArticles(List<DiaryEntity> diaryEntities);

    List<DiaryEntity> getArticles();

}
