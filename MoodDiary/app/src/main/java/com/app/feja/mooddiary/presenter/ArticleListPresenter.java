package com.app.feja.mooddiary.presenter;


import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.TypeDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.dao.impl.TypeDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.ui.view.CategoryView;
import com.app.feja.mooddiary.util.DateTime;

import java.util.Date;
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

    public void loadArticles(Date date){
        articleListView.onLoadArticles(this.diaryDao.getDiary(new DateTime(date)));
    }

    public List<DiaryEntity> getAllArticles(){
        return this.diaryDao.getAllDiary();
    }


    public List<TypeEntity> getAllTypes(){
        return typeDao.getAllType();
    }

    public TypeEntity getType(String type){
        return this.typeDao.selectType(type);
    }


    public void loadCategories(){
        categoryView.onLoadCategories(this.typeDao.getAllType(), this.diaryDao.getAllDiary());
    }

    public void editTypes(String string){
        this.typeDao.updateType(string);
    }

    public void deleteTypeOnly(String type){
        Toast.makeText(ApplicationContext.getContext(), type, Toast.LENGTH_SHORT).show();
        TypeEntity typeEntity = typeDao.selectType(type);
        List<DiaryEntity> diaryEntities = diaryDao.getDiary(typeEntity);
        if(diaryEntities == null || diaryEntities.size() == 0){
            typeDao.deleteType(typeEntity);
        }else{
            for(DiaryEntity diaryEntity: diaryEntities){
                diaryEntity.setType(typeDao.selectType(NO_CATEGORY));
                diaryDao.editDiary(diaryEntity);
            }
            typeDao.deleteType(typeEntity);
        }
    }

    public void deleteTypeAndDiary(String type){
        TypeEntity typeEntity = typeDao.selectType(type);
        List<DiaryEntity> diaryEntities = diaryDao.getDiary(typeEntity);
        for(DiaryEntity diaryEntity: diaryEntities){
            diaryDao.deleteDiary(diaryEntity);
        }
        typeDao.deleteType(typeEntity);
    }

    public void loadArticlesByKeyWord(String keyword){
        articleListView.onLoadArticles(diaryDao.getDiaryByKeyword(keyword));
    }

}
