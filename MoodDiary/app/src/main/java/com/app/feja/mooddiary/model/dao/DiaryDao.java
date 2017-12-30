package com.app.feja.mooddiary.model.dao;


import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.util.DateTime;

import java.util.List;

/**
 * created by fejasible@163.com
 */
public interface DiaryDao {

    DiaryEntity getDiary(DiaryEntity diaryEntity);
    DiaryEntity getDiaryById(Integer id);
    List<DiaryEntity> getDiary(DateTime dateTime);

    /**
     * 获取指定数量的DiaryEntity，按时间先后顺序排序
     * @param count
     * @return
     */
    List<DiaryEntity> getDiary(Long count);
    List<DiaryEntity> getDiaryByFace(int face);
    List<DiaryEntity> getDiary(TypeEntity typeEntity);
    List<DiaryEntity> getDiaryByWeather(int weather);
    List<DiaryEntity> getAllDiary();
    List<DiaryEntity> getDiaryByKeyword(String keyword);

    int addDiary(DiaryEntity diaryEntity);

    int editDiary(DiaryEntity diaryEntity);

    int deleteDiary(DiaryEntity diaryEntity);

    int getAllDiaryCount();
}
