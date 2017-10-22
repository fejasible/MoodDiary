package com.app.feja.mooddiary.model.entity;


import com.j256.ormlite.field.ForeignCollectionField;

import java.util.Collection;

public class ForeignCollectionEntity {

    @ForeignCollectionField
    private Collection<DiaryEntity> diaryEntities;

    public Collection<DiaryEntity> getDiaryEntities() {
        return diaryEntities;
    }

    public void setDiaryEntities(Collection<DiaryEntity> diaryEntities) {
        this.diaryEntities = diaryEntities;
    }


}
