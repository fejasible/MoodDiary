package com.app.feja.mooddiary.model.dao;


import com.app.feja.mooddiary.model.entity.TypeEntity;

import java.util.ArrayList;

public interface TypeDao {

    int addType(TypeEntity typeEntity);

    int deleteType(TypeEntity typeEntity);

    int updateType(TypeEntity typeEntity);

    ArrayList<TypeEntity> getAllType();

}
