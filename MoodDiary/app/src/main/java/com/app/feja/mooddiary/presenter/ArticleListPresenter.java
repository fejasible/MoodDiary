package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.ui.view.ArticleListView;

public class ArticleListPresenter {

    private ArticleListView articleListView;
    private DiaryDao diaryDao;

    public ArticleListPresenter(ArticleListView articleListView){
        this.articleListView = articleListView;
        this.diaryDao = new DiaryDaoImpl();
    }

    public void loadArticles(){
        articleListView.onLoadArticles(diaryDao.getAllDiary());
    }

}
