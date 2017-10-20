package com.app.feja.mooddiary.model.dao.impl;


import com.app.feja.mooddiary.constant.FACE;
import com.app.feja.mooddiary.constant.WEATHER;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.util.DateTime;

import java.util.ArrayList;

public class DiaryDaoImpl implements DiaryDao {
    @Override
    public DiaryEntity getDiaryById(Integer id) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiary(DateTime dateTime) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiary(int number) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiary(FACE face) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiaryByTypeId(Integer typeId) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiaryByType(String type) {
        return null;
    }

    @Override
    public ArrayList<DiaryEntity> getDiaryByWeather(WEATHER weather) {
        return null;
    }

    @Override
    public int addDiary(DiaryEntity diaryEntity) {
        return 0;
    }

    @Override
    public int deleteDiary(DiaryEntity diaryEntity) {
        return 0;
    }
}
