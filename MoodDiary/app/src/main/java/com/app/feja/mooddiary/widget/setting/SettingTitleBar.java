package com.app.feja.mooddiary.widget.setting;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.widget.base.TouchListenView;

public class SettingTitleBar extends TouchListenView{

    private Paint whitePaint;
    private String settingString;

    public SettingTitleBar(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public SettingTitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public SettingTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 3;
        this.rectPercents = new int[]{100, 520, 100};
        this.orientation = ORIENTATION.HORIZONTAL;
    }

    private void init(){
        this.whitePaint = new Paint();
        this.whitePaint.setColor(Color.WHITE);
        this.whitePaint.setAntiAlias(true);
        this.whitePaint.setStrokeWidth(2.0f);

        this.settingString = getResources().getString(R.string.setting);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.whitePaint.setTextSize(this.getHeight() / 3);
        this.drawLeftArrow(getRects()[0], whitePaint, canvas);
        this.drawCenterText(settingString, this.getWidth()/2, this.getHeight()/2, whitePaint, canvas);
    }

    public String getSettingString() {
        return settingString;
    }

    public void setSettingString(String settingString) {
        this.settingString = settingString;
    }
}
