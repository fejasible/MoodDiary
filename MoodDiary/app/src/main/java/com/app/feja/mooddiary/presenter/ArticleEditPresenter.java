package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.TypeDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.dao.impl.TypeDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.ui.view.ArticleEditView;

import java.util.Date;

public class ArticleEditPresenter {

    private ArticleEditView articleEditView;
    private DiaryDao diaryDao;
    private TypeDao typeDao;

    public ArticleEditPresenter(ArticleEditView articleEditView){
        this.articleEditView = articleEditView;
        this.diaryDao = new DiaryDaoImpl();
        this.typeDao = new TypeDaoImpl();
    }

    public boolean editArticle(DiaryEntity diaryEntity) {
        if(diaryEntity.getId() == null) {
            TypeEntity typeEntity = this.typeDao.selectType(diaryEntity.getType().getType());
            if(typeEntity == null){
                typeEntity = this.typeDao.selectType(ApplicationContext.getContext().getString(R.string.no_sort));
                if(typeEntity == null){
                    typeEntity = new TypeEntity(ApplicationContext.getContext().getString(R.string.no_sort));
                    this.typeDao.addType(typeEntity);
                }
            }
            diaryEntity.setType(typeEntity);
            return this.diaryDao.addDiary(diaryEntity) == 1;
        }
        else {
            return this.diaryDao.editDiary(diaryEntity) == 1;
        }
    }

    public void loadArticle(DiaryEntity diaryEntity){
        articleEditView.onLoadArticle(diaryEntity);
    }
}
