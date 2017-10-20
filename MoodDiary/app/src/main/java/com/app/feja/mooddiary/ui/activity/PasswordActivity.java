package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.PasswordView;

public class PasswordActivity extends Activity{

    private PasswordView passwordView;

    private String passwordErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        init();
        this.setListener();

    }

    private void init(){
        this.passwordView = (PasswordView) findViewById(R.id.password_border);
        this.passwordErrorString = getResources().getString(R.string.password_error);
    }

    private void setListener(){
        passwordView.setOnEnterFinishListener(new PasswordView.OnEnterFinishListener() {
            @Override
            public void enterFinish(String password) {
                if(getPassword().equals("")){
                    savePassword(password);
                }
                if(getPassword().equals(password)){
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(PasswordActivity.this, passwordErrorString, Toast.LENGTH_SHORT).show();
                    passwordView.clearPassword();
                }
            }
        });
        passwordView.setOnHelpClickListener(new PasswordView.OnHelpClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean savePassword(String password){
        if(password.length() != this.passwordView.getNumberCount()){
            return false;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("password", password).apply();
        return true;
    }

    private String getPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sharedPreferences == null){
            return "";
        }else{
            return sharedPreferences.getString("password", "");
        }
    }

}
