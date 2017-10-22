package com.app.feja.mooddiary.model.dao;


import com.app.feja.mooddiary.model.entity.TypeEntity;

import java.util.ArrayList;
import java.util.List;

public interface TypeDao {

    int addType(TypeEntity typeEntity);

    int deleteType(TypeEntity typeEntity);

    int updateType(TypeEntity typeEntity);

    TypeEntity selectType(String typeString);

    List<TypeEntity> getAllType();

}
