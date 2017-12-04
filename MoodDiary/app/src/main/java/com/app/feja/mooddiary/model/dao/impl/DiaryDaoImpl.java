package com.app.feja.mooddiary.model.dao.impl;


import android.content.Context;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
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
            this.dao = DatabaseHelper.getHelper(TheApplication.getContext()).getDao(DiaryEntity.class);
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
        DateTime dateTime1 = dateTime.clone();
        dateTime1.toZeroTime();
        DateTime dateTime2 = dateTime1.clone();
        dateTime2.addDay(1);
        try {
            return dao.queryBuilder()
                    .orderBy(DiaryEntity.COLUMN_NAME_CREATE_TIME, false)
                    .where()
                    .eq(DiaryEntity.COLUMN_NAME_IS_DELETE, DiaryEntity.IS_NOT_DELETE)
                    .and()
                    .between(DiaryEntity.COLUMN_NAME_CREATE_TIME, dateTime1.toDate(), dateTime2.toDate())
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<DiaryEntity> getDiary(Long count) {
        try {
            return dao.queryBuilder()
                    .orderBy(DiaryEntity.COLUMN_NAME_CREATE_TIME, false)
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
        try {
            return dao.queryBuilder()
                    .orderBy(DiaryEntity.COLUMN_NAME_CREATE_TIME, false)
                    .where()
                    .eq(DiaryEntity.COLUMN_NAME_TYPE, typeEntity)
                    .and()
                    .eq(DiaryEntity.COLUMN_NAME_IS_DELETE, DiaryEntity.IS_NOT_DELETE)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<DiaryEntity> getDiaryByWeather(int weather) {
        return null;
    }

    @Override
    public List<DiaryEntity> getAllDiary() {
        try {
            return dao.queryBuilder()
                    .orderBy(DiaryEntity.COLUMN_NAME_CREATE_TIME, false)
                    .where()
                    .eq(DiaryEntity.COLUMN_NAME_IS_DELETE, DiaryEntity.IS_NOT_DELETE).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<DiaryEntity> getDiaryByKeyword(String keyword) {
        try {
            return dao.queryBuilder()
                    .orderBy(DiaryEntity.COLUMN_NAME_CREATE_TIME, false)
                    .where()
                    .eq(DiaryEntity.COLUMN_NAME_IS_DELETE, DiaryEntity.IS_NOT_DELETE)
                    .and()
                    .like(DiaryEntity.COLUMN_NAME_CONTENT, "%"+keyword+"%").query();
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
        diaryEntity.setType(new TypeDaoImpl().selectType(TheApplication.getContext().getString(R.string.no_sort)));
        try {
            return dao.update(diaryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
