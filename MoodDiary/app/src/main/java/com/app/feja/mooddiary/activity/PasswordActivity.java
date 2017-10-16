package com.app.feja.mooddiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.PasswordBorder;

public class PasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        PasswordBorder passwordBorder = (PasswordBorder) findViewById(R.id.password_border);
        passwordBorder.setOnEnterFinishListener(new PasswordBorder.OnEnterFinishListener() {
            @Override
            public void enterFinish(String password) {
                Toast.makeText(getApplicationContext(), password, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        passwordBorder.setOnHelpClickListener(new PasswordBorder.OnHelpClickListener() {
            @Override
            public void onClick() {
                Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
