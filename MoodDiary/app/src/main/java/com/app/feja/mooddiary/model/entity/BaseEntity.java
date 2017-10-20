package com.app.feja.mooddiary.model.entity;

import com.google.gson.Gson;

public class BaseEntity {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}