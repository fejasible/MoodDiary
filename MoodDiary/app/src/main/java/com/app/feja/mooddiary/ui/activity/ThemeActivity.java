package com.app.feja.mooddiary.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.ThemeAdapter;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.base.TouchListenView;
import com.app.feja.mooddiary.widget.setting.SettingTitleBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.ColorPicker;

public class ThemeActivity extends BaseActivity implements TouchListenView.OnItemTouchListener,
        ThemeAdapter.OnItemClickListener, ColorPicker.OnColorPickListener {


    private ThemeAdapter themeAdapter;

    @BindView(R.id.id_settings_title)
    SettingTitleBar settingTitleBar;

    @BindView(R.id.id_theme_select_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);

        this.initView();
        this.initListener();
    }

    private void initView(){
        settingTitleBar.setSettingString(getString(R.string.theme));


        this.initAdapter();
        this.initRecycleView();
    }

    private void initAdapter(){
        // 声明adapter
        themeAdapter = new ThemeAdapter();
        // 声明datas
        List<ThemeAdapter.Data> datas = new ArrayList<>();

        // 添加内置数据
        boolean custom = true;
        for(int i=1; i<=10; i++){
            int colorId, stringId;
            colorId = getResId("theme_color_"+i, R.color.class);
            stringId = getResId("theme_color_name_"+i, R.string.class);
            if(colorId == -1 || stringId == -1){
                continue;
            }
            ThemeAdapter.Data data = new ThemeAdapter.Data(
                    ContextCompat.getColor(getApplicationContext(), colorId), getString(stringId));
            datas.add(data);
            if(TheApplication.getThemeData().equals(data)) {// 已存储的内置主题
                data.setSelect(true);
                custom = false;
            }
        }
        if(getCustomTheme() != null && datas.size() > 0) {
            datas.get(0).setColor(getCustomTheme().getColor());
        }
        if(custom){
            if(datas.size() > 0) {
                datas.get(0).setColor(TheApplication.getThemeData().getColor());
                datas.get(0).setSelect(true);
            }
        }
        // 设置数据
        themeAdapter.setData(datas);
        // 设置监听
        themeAdapter.setOnItemClickListener(this);
    }

    private void initRecycleView(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(themeAdapter);
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
    public void onItemClick(View view, int position) {
        final ThemeAdapter.Data data = themeAdapter.getData().get(position);
        TheApplication.setThemeData(data);
        saveTheme(data);

        for(int i=0; i<themeAdapter.getData().size(); i++){
            if(i!=position){
                themeAdapter.getData().get(i).setSelect(false);
            }else{
                themeAdapter.getData().get(i).setSelect(true);
            }
        }
        if(position == 0){
            ColorPicker colorPicker = new ColorPicker(this);
            colorPicker.setInitColor(themeAdapter.getData().get(position).getColor());
            colorPicker.setOnColorPickListener(this);
            colorPicker.show();
        }
        themeAdapter.notifyDataSetChanged();
        settingTitleBar.setBackgroundColor(TheApplication.getThemeData().getColor());
    }

    @Override
    protected void onResume() {
        super.onResume();
        settingTitleBar.setBackgroundColor(TheApplication.getThemeData().getColor());
    }

    private boolean saveTheme(ThemeAdapter.Data themeData){
        SharedPreferences sharedPreferences = getSharedPreferences(
                TheApplication.getSharedPreferencesName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(TheApplication.THEME_KEY, themeData.toString()).apply();
        return true;
    }

    private boolean saveCustomTheme(ThemeAdapter.Data themeData){
        SharedPreferences sharedPreferences = getSharedPreferences(
                TheApplication.getSharedPreferencesName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(TheApplication.CUSTOM_THEME_KEY, themeData.toString()).apply();
        return true;
    }

    private ThemeAdapter.Data getCustomTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(TheApplication.getSharedPreferencesName(), Context.MODE_PRIVATE);
        if(sharedPreferences == null){
            return null;
        }else{
            String themeString = sharedPreferences.getString(TheApplication.CUSTOM_THEME_KEY, "");
            try {
                return new Gson().fromJson(themeString, ThemeAdapter.Data.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void onColorPicked(@ColorInt int pickedColor) {
        themeAdapter.getData().get(0).setColor(pickedColor);
        themeAdapter.getData().get(0).setSelect(true);
        TheApplication.setThemeData(themeAdapter.getData().get(0));
        saveTheme(themeAdapter.getData().get(0));
        saveCustomTheme(themeAdapter.getData().get(0));
        themeAdapter.notifyDataSetChanged();
        settingTitleBar.setBackgroundColor(TheApplication.getThemeData().getColor());
    }
}
