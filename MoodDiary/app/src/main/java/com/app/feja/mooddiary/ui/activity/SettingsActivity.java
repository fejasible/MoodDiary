package com.app.feja.mooddiary.ui.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.base.TouchListenView;
import com.app.feja.mooddiary.widget.setting.LinkRightBar;
import com.app.feja.mooddiary.widget.setting.SettingTitleBar;
import com.suke.widget.SwitchButton;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by fejasible@163.com
 */
public class SettingsActivity extends BaseActivity implements TouchListenView.OnItemTouchListener,
        SwitchButton.OnCheckedChangeListener {

    @BindView(R.id.id_settings_title)
    SettingTitleBar settingTitleBar;

    @BindView(R.id.id_settings_export)
    LinkRightBar exportDiaryBar;

    @BindView(R.id.id_settings_export_theme)
    LinkRightBar themeBar;

    @BindView(R.id.id_settings_export_about)
    LinkRightBar aboutBar;

    @BindView(R.id.id_text_view)
    TextView textView;
    @BindView(R.id.id_switch_button)
    SwitchButton switchButton;

    @BindString(R.string.enable_password)
    String enablePassword;

    @BindString(R.string.disable_password)
    String disablePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        this.initViews();

        this.initListener();
    }

    private void initViews() {
        this.themeBar.setString(getString(R.string.theme));
        this.themeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ThemeActivity.class);
                startActivity(intent);
            }
        });

        this.exportDiaryBar.setString(getString(R.string.export_diary));
        this.exportDiaryBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, ExportDiaryActivity.class);
                startActivity(intent);
            }
        });

        this.switchButton.setOnCheckedChangeListener(this);

        this.aboutBar.setString(getString(R.string.about_mood_diary));
        this.aboutBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initListener() {
        this.settingTitleBar.setOnItemTouchListener(this);
    }

    @Override
    public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
        switch (item) {
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
        this.settingTitleBar.setBackgroundColor(TheApplication.getThemeData().getColor());
        this.textView.setTextColor(TheApplication.getThemeData().getColor());
        if (getPassword().equals("")) {
            this.switchButton.setChecked(false);
            this.textView.setText(disablePassword);
        } else {
            this.switchButton.setChecked(true);
            this.textView.setText(enablePassword);
        }
    }

    @Override
    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
        if (isChecked) {
            Intent intent = new Intent(SettingsActivity.this, PasswordActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(PasswordActivity.ACTION_BUNDLE_NAME, PasswordActivity.ACTION.EDIT_ENTER);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            clearPassword();
            this.textView.setText(disablePassword);
        }
    }
}
