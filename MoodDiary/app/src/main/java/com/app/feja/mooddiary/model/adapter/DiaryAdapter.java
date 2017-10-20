package com.app.feja.mooddiary.model.adapter;


import android.view.ViewGroup;

import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.widget.ArticleView;

public class DiaryAdapter extends BaseAdapter{

    public static ArticleView getArticleView(DiaryEntity diaryEntity, ViewGroup.LayoutParams layoutParams, ArticleView.OnArticleViewClickListener listener){
        ArticleView articleView = new ArticleView(ApplicationContext.getContext());
        articleView.setLayoutParams(layoutParams);
        articleView.setDiaryEntity(diaryEntity);
        articleView.setListener(listener);
        return articleView;
    }

}
