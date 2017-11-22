package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.widget.base.TouchListenView;
import com.app.feja.mooddiary.widget.setting.LinkRightBar;
import com.app.feja.mooddiary.widget.setting.SettingTitleBar;

public class SettingsActivity extends Activity implements TouchListenView.OnItemTouchListener {

    private SettingTitleBar settingTitleBar;
    private LinkRightBar exportDiaryBar;
    private LinkRightBar themeBar;
    private LinkRightBar privatePasswordBar;
    private LinkRightBar aboutBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.initViews();

        this.initListener();
    }

    private void initViews(){
        this.settingTitleBar = (SettingTitleBar) findViewById(R.id.id_settings_title);

        this.exportDiaryBar = (LinkRightBar) findViewById(R.id.id_settings_export_diary);
        this.exportDiaryBar.setString(getString(R.string.export_diary));
        this.exportDiaryBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ExportDiaryActivity.class);
                startActivity(intent);
            }
        });

        this.themeBar = (LinkRightBar) findViewById(R.id.id_settings_export_theme);
        this.themeBar.setString(getString(R.string.theme));
        this.themeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ThemeActivity.class);
                startActivity(intent);
            }
        });

        this.privatePasswordBar = (LinkRightBar) findViewById(R.id.id_settings_export_private_password);
        this.privatePasswordBar.setString(getString(R.string.private_password));
        this.privatePasswordBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, PasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PasswordActivity.ACTION_BUNDLE_NAME, PasswordActivity.ACTION.EDIT_ENTER);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        this.aboutBar = (LinkRightBar) findViewById(R.id.id_settings_export_about);
        this.aboutBar.setString(getString(R.string.about_mood_diary));
    }

    private void initListener(){
        this.settingTitleBar.setOnItemTouchListener(this);
    }

    @Override
    public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
        switch (item){
            case 0:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.settingTitleBar.setBackgroundColor(ApplicationContext.getThemeData().getColor());
    }
}
