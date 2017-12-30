package com.app.feja.mooddiary.model.entity;

import com.google.gson.Gson;

/**
 * created by fejasible@163.com
 */
public class BaseEntity {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}