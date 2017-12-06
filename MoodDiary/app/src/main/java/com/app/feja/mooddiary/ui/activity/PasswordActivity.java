package com.app.feja.mooddiary.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.PasswordView;

import java.io.Serializable;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordActivity extends BaseActivity implements PasswordView.OnHelpClickListener {

    public static final String PASSWORD_KEY = "password";
    public static final String ACTION_BUNDLE_NAME = "action";
    public static final String CONFIRM_PASSWORD_BUNDLE_NAME = "confirm_password";

    @Override
    public void onClick() {
        Toast.makeText(getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
    }

    public enum ACTION implements Serializable{
        FIRST_ENTER("first_enter"), AGAIN_ENTER("again_enter"), EDIT_ENTER("edit_enter"),
        EDIT_ENTER_2("edit_enter_2"), EDIT_ENTER_3("edit_enter_3");

        private String s;

        ACTION(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return this.s;
        }
    }

    @BindView(R.id.password_border)
    PasswordView passwordView;

    @BindView(R.id.id_password_title)
    RelativeLayout passwordTitle;

    @BindString(R.string.password_error)
    String passwordErrorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null){
            final Bundle bundle = intent.getExtras();
            if(bundle != null){
                ACTION action = null;
                final String confirm_password = bundle.getString(CONFIRM_PASSWORD_BUNDLE_NAME, "");
                try {
                    action = (ACTION) bundle.getSerializable(ACTION_BUNDLE_NAME);
                }catch (Exception e){
                    e.printStackTrace();
                }
                if(action != null){
                    passwordView.setOnHelpClickListener(this);
                    switch (action){
                        case FIRST_ENTER:
                            if(getPassword().equals("")){
                                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent1);
                                finish();
                                break;
                            }
                            passwordView.setEnterString(getString(R.string.enter_password));
                            passwordView.setOnEnterFinishListener(new PasswordView.OnEnterFinishListener() {
                                @Override
                                public void enterFinish(String password) {
                                    if(getPassword().equals(password)){
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(PasswordActivity.this, passwordErrorString, Toast.LENGTH_SHORT).show();
                                        passwordView.clearPassword();
                                    }
                                }
                            });
                            break;
                        case AGAIN_ENTER:
                            passwordView.setEnterString(getString(R.string.enter_password));
                            passwordView.setOnEnterFinishListener(new PasswordView.OnEnterFinishListener() {
                                @Override
                                public void enterFinish(String password) {
                                    if(getPassword().equals(password)){
                                        onBackPressed();
                                        finish();
                                    }else{
                                        Toast.makeText(PasswordActivity.this, passwordErrorString, Toast.LENGTH_SHORT).show();
                                        passwordView.clearPassword();
                                    }
                                }
                            });
                            break;
                        case EDIT_ENTER:
                            if(getPassword().equals("")){
                                Intent intent2 = new Intent(getApplicationContext(), PasswordActivity.class);
                                Bundle bundle1 = new Bundle();
                                bundle1.putSerializable(ACTION_BUNDLE_NAME, ACTION.EDIT_ENTER_2);
                                intent2.putExtras(bundle1);
                                startActivity(intent2);
                                finish();
                                break;
                            }
                            passwordView.setEnterString(getString(R.string.enter_old_password));
                            passwordView.setOnEnterFinishListener(new PasswordView.OnEnterFinishListener() {
                                @Override
                                public void enterFinish(String password) {
                                    if(getPassword().equals(password)) {
                                        Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putSerializable(ACTION_BUNDLE_NAME, ACTION.EDIT_ENTER_2);
                                        intent.putExtras(bundle1);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(PasswordActivity.this, passwordErrorString, Toast.LENGTH_SHORT).show();
                                        passwordView.clearPassword();
                                    }
                                }
                            });
                            break;
                        case EDIT_ENTER_2:
                            passwordView.setEnterString(getString(R.string.edit_password));
                            passwordView.setOnEnterFinishListener(new PasswordView.OnEnterFinishListener() {
                                @Override
                                public void enterFinish(String password) {
                                    Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                                    Bundle bundle1 = new Bundle();
                                    bundle1.putSerializable(ACTION_BUNDLE_NAME, ACTION.EDIT_ENTER_3);
                                    bundle1.putString(CONFIRM_PASSWORD_BUNDLE_NAME, password);
                                    intent.putExtras(bundle1);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                            break;
                        case EDIT_ENTER_3:
                            passwordView.setEnterString(getString(R.string.edit_confirm_password));
                            passwordView.setOnEnterFinishListener(new PasswordView.OnEnterFinishListener() {
                                @Override
                                public void enterFinish(String password) {
                                    if(confirm_password != null && confirm_password.equals(password)){
                                        savePassword(password);
                                        Toast.makeText(PasswordActivity.this,
                                                getString(R.string.edit_password_success), Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(PasswordActivity.this,
                                                getString(R.string.not_the_same_password), Toast.LENGTH_SHORT).show();
                                    }
                                    onBackPressed();
                                    finish();
                                }
                            });
                            break;
                    }
                }
            }
        }

        this.initTheme();
    }

    private void initTheme(){
        passwordTitle.setBackgroundColor(TheApplication.getThemeData().getColor());
        passwordView.setThemeColor(TheApplication.getThemeData().getColor());
    }

    @Override
    public boolean savePassword(String password){
        if(password.length() != this.passwordView.getNumberCount()){
            return false;
        }
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(PASSWORD_KEY, password).apply();
        return true;
    }
}
