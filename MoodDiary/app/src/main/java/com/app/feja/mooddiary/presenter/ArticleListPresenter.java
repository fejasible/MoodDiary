package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.TypeDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.dao.impl.TypeDaoImpl;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.ui.view.CategoryView;

import java.util.List;

public class ArticleListPresenter {

    public static final String ALL_CATEGORY = ApplicationContext.getContext().getString(R.string.all_sort);
    public static final String NO_CATEGORY = ApplicationContext.getContext().getString(R.string.no_sort);

    private ArticleListView articleListView;
    private CategoryView categoryView;
    private DiaryDao diaryDao;
    private TypeDao typeDao;

    public ArticleListPresenter(ArticleListView articleListView){
        this.articleListView = articleListView;
        this.diaryDao = new DiaryDaoImpl();
        this.typeDao = new TypeDaoImpl();
    }

    public ArticleListPresenter(CategoryView categoryView){
        this.categoryView = categoryView;
        this.diaryDao = new DiaryDaoImpl();
        this.typeDao = new TypeDaoImpl();
    }


    public void loadArticles(){
        articleListView.onLoadArticles(diaryDao.getDiary(8L));
    }

    public void loadArticles(String category){
        if(category.equals(ALL_CATEGORY)){
            articleListView.onLoadArticles(this.diaryDao.getAllDiary());
            return ;
        }
        TypeEntity typeEntity = this.typeDao.selectType(category);
        if(typeEntity != null){
            articleListView.onLoadArticles(this.diaryDao.getDiary(typeEntity));
        }
    }


    public List<TypeEntity> getAllTypes(){
        return typeDao.getAllType();
    }


    public void loadCategories(){
        categoryView.onLoadCategories(this.typeDao.getAllType(), this.diaryDao.getAllDiary());
    }

}
