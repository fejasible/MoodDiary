package com.app.feja.mooddiary.model.dao;


import com.app.feja.mooddiary.constant.FACE;
import com.app.feja.mooddiary.constant.WEATHER;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.util.DateTime;

import java.util.ArrayList;

public interface DiaryDao {

    DiaryEntity getDiaryById(Integer id);
    ArrayList<DiaryEntity> getDiary(DateTime dateTime);

    /**
     * 获取指定数量的DiaryEntity，按时间先后顺序排序
     * @param number
     * @return
     */
    ArrayList<DiaryEntity> getDiary(int number);
    ArrayList<DiaryEntity> getDiary(FACE face);
    ArrayList<DiaryEntity> getDiaryByTypeId(Integer typeId);
    ArrayList<DiaryEntity> getDiaryByType(String type);
    ArrayList<DiaryEntity> getDiaryByWeather(WEATHER weather);

    int addDiary(DiaryEntity diaryEntity);

    int deleteDiary(DiaryEntity diaryEntity);
}
