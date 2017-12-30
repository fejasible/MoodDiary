package com.app.feja.mooddiary.http.model;


import com.google.gson.Gson;

/**
 * created by fejasible@163.com
 */
public class BaseModel {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
