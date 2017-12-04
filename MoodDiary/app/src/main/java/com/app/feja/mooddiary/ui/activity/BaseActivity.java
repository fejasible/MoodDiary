package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import java.lang.reflect.Field;

public class BaseActivity extends Activity {

    private String tag = "undefine";


    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
