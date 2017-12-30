package com.app.feja.mooddiary.model.dao;


import com.app.feja.mooddiary.model.entity.TypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * created by fejasible@163.com
 */
public interface TypeDao {

    int addType(TypeEntity typeEntity);

    int deleteType(TypeEntity typeEntity);

    int updateType(TypeEntity typeEntity);

    int updateType(String type);

    TypeEntity selectType(String typeString);

    List<TypeEntity> getAllType();

}
