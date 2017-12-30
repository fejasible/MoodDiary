package com.app.feja.mooddiary.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.app.feja.mooddiary.model.dao.DiaryDao;
import com.app.feja.mooddiary.model.dao.impl.DiaryDaoImpl;
import com.app.feja.mooddiary.model.entity.DiaryEntity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * created by fejasible@163.com
 */
@RunWith(AndroidJUnit4.class)
public class DiaryDaoTest{

    private DiaryDao diaryDao;
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Before
    public void getUp() throws Exception{
        diaryDao = new DiaryDaoImpl(appContext);
    }

    @After
    public void tearDown() throws Exception{
        diaryDao = null;
    }

    @Test
    public void diary_test_001(){
        List<DiaryEntity> diaryEntities = diaryDao.getAllDiary();
        for(DiaryEntity diaryEntity: diaryEntities){
            Log.i("diary_test_002", diaryEntity.toString());
        }
    }

    @Test
    public void diary_test_002(){
        List<DiaryEntity> diaryEntities = diaryDao.getDiaryByKeyword("分类");
        for(DiaryEntity diaryEntity: diaryEntities){
            Log.i("diary_test_002", diaryEntity.toString());
        }
    }
}
