package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.ui.view.ArticleView;

public class ArticleBrowsePresenter {

    private ArticleView articleView;
    private DiaryDao diaryDao;

    public ArticleBrowsePresenter(ArticleView articleView){
        this.articleView = articleView;
        this.diaryDao = new DiaryDaoImpl();
    }

    public void loadArticle(DiaryEntity diaryEntity){
        if(diaryEntity != null && diaryEntity.getId() != null){
            DiaryEntity diaryEntityTmp = diaryDao.getDiary(diaryEntity);
            if(diaryEntityTmp != null){
                diaryEntity = diaryEntityTmp;
            }
        }
        articleView.onLoadArticle(diaryEntity);
    }

    public void deleteArticle(DiaryEntity diaryEntity){
        this.diaryDao.deleteDiary(diaryEntity);
    }

}
