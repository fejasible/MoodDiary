package com.app.feja.mooddiary.model.dao.impl;


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
import java.util.Date;
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
    public ArrayList<DiaryEntity> getDiary(DateTime dateTime) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiary(int count) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiaryByFace(int face) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiary(TypeEntity typeEntity) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiaryByWeather(WEATHER weather) {
        return null;
    }

    @Override
    public List<DiaryEntity> getAllDiary() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        List<DiaryEntity> diaryEntities = new ArrayList<>();
//        TypeEntity typeEntity = new TypeEntity();
//        typeEntity.setType("梦境");
//        for(int i=0; i<10; i++){
//            DiaryEntity diaryEntityTmp = new DiaryEntity();
//            diaryEntityTmp.setId(i*4);
//            diaryEntityTmp.setType(typeEntity);
//            diaryEntityTmp.setCreateTime(new Date());
//            diaryEntityTmp.setMood(DiaryEntity.MIRTHFUL);
//            diaryEntityTmp.setContent("心情日记"+i);
//            diaryEntities.add(diaryEntityTmp);
//        }

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
        try {
            return dao.delete(diaryEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
