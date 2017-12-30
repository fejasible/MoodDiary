package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.TypeDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.dao.impl.TypeDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.ui.view.ArticleView;

import java.util.List;

/**
 * created by fejasible@163.com
 */
public class ArticleBrowsePresenter {

    private ArticleView articleView;
    private DiaryDao diaryDao;
    private TypeDao typeDao;

    public ArticleBrowsePresenter(ArticleView articleView){
        this.articleView = articleView;
        this.diaryDao = new DiaryDaoImpl();
        this.typeDao = new TypeDaoImpl();
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

    public List<TypeEntity> getAllType(){
        return typeDao.getAllType();
    }

}
