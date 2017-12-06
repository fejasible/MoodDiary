package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.lang.reflect.Field;

public class BaseActivity extends Activity {

    private String tag = "undefine";

    private static final String FIRST_START = "first_start";


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

    public String getPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sharedPreferences == null){
            return "";
        }else{
            return sharedPreferences.getString(PasswordActivity.PASSWORD_KEY, "");
        }
    }

    public boolean savePassword(String password){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(PasswordActivity.PASSWORD_KEY, password).apply();
        return true;
    }

    public void clearPassword(){
        savePassword("");
    }

    public boolean isFirstStart(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        boolean firstStart = sharedPreferences.getBoolean(FIRST_START, true);
        if(firstStart){
            sharedPreferences.edit().putBoolean(FIRST_START, false).apply();
        }
        return firstStart;
    }
}
