package com.app.feja.mooddiary.model.dao.impl;


import android.content.Context;
import android.widget.Toast;

import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.constant.WEATHER;
import com.app.feja.mooddiary.model.DatabaseHelper;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.util.DateTime;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiaryDaoImpl implements DiaryDao {

    private Dao<DiaryEntity, Integer> dao;

    public DiaryDaoImpl(){
        try {
            this.dao = DatabaseHelper.getHelper(ApplicationContext.getContext()).getDao(DiaryEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public DiaryDaoImpl(Context context){
        try {
            this.dao = DatabaseHelper.getHelper(context).getDao(DiaryEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DiaryEntity getDiary(DiaryEntity diaryEntity) {
        try {
            return dao.queryForId(diaryEntity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public DiaryEntity getDiaryById(Integer id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DiaryEntity> getDiary(DateTime dateTime) {
        return null;
    }

    @Override
    public List<DiaryEntity> getDiary(Long count) {
        try {
            return dao.queryBuilder().orderBy(DiaryEntity.COLUMN_NAME_CREATE_TIME, false)
                    .limit(count).where()
                    .eq(DiaryEntity.COLUMN_NAME_IS_DELETE, DiaryEntity.IS_NOT_DELETE).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<DiaryEntity> getDiaryByFace(int face) {
        return null;
    }

    @Override
    public List<DiaryEntity> getDiary(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public List<DiaryEntity> getDiaryByWeather(WEATHER weather) {
        return null;
    }

    @Override
    public List<DiaryEntity> getAllDiary() {
        try {
            return dao.queryBuilder().where()
                    .eq(DiaryEntity.COLUMN_NAME_IS_DELETE, DiaryEntity.IS_NOT_DELETE).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public int addDiary(DiaryEntity diaryEntity) {
        try {
            return dao.create(diaryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int editDiary(DiaryEntity diaryEntity) {
        try {
            return dao.update(diaryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteDiary(DiaryEntity diaryEntity) {
        diaryEntity.setIsDelete(DiaryEntity.IS_DELETE);
        try {
            return dao.update(diaryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}