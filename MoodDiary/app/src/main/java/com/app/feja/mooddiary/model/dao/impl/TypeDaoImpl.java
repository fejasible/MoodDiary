package com.app.feja.mooddiary.model.dao.impl;


import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.DatabaseHelper;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.model.dao.TypeDao;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * created by fejasible@163.com
 */
public class TypeDaoImpl implements TypeDao {
    private Dao<TypeEntity, Integer> dao;

    public TypeDaoImpl(){
        try {
            dao = DatabaseHelper.getHelper(TheApplication.getContext()).getDao(TypeEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int addType(TypeEntity typeEntity) {
        try {
            return dao.create(typeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deleteType(TypeEntity typeEntity) {
        try {
            return dao.delete(typeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateType(TypeEntity typeEntity) {
        try {
            return dao.update(typeEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateType(String type) {
        try {
            List<TypeEntity> typeEntities = dao.queryBuilder().where().eq(TypeEntity.COLUMN_TYPE, type).query();
            if(typeEntities == null || typeEntities.size() != 1){
                TypeEntity typeEntity = new TypeEntity(type);
                dao.create(typeEntity);
                return typeEntity.getId();
            }else{
                dao.update(typeEntities.get(0));
                return typeEntities.get(0).getId();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public TypeEntity selectType(String typeString) {
        try {
            List<TypeEntity> typeEntities = dao.queryBuilder().where().eq(TypeEntity.COLUMN_TYPE, typeString).query();
            if(typeEntities == null || typeEntities.size() == 0){
                return null;
            }else{
                return typeEntities.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TypeEntity> getAllType() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
