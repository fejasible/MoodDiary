package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.ui.view.ArticleEditView;

import java.util.Date;

public class ArticleEditPresenter {

    private ArticleEditView articleEditView;
    private DiaryDao diaryDao;

    public ArticleEditPresenter(ArticleEditView articleEditView){
        this.articleEditView = articleEditView;
        this.diaryDao = new DiaryDaoImpl();
    }

    public boolean editArticle(DiaryEntity diaryEntity) {
        if(diaryEntity == null) {
            return false;
        }else if(diaryEntity.getId() == null) {
            return diaryEntity.getId() == null && this.diaryDao.addDiary(diaryEntity) == 1;
        }
        else {
            return diaryEntity.getId() != null && this.diaryDao.editDiary(diaryEntity) == 1;
        }
    }

    public void loadArticle(DiaryEntity diaryEntity){
        articleEditView.onLoadArticle(diaryEntity);
    }
}
