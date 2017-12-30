package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.factory.BubbleFactory;
import com.app.feja.mooddiary.widget.base.TouchListenView;
import com.app.feja.mooddiary.widget.setting.BubbleView;
import com.app.feja.mooddiary.widget.setting.SettingTitleBar;

import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by fejasible@163.com
 */
public class AboutActivity extends Activity implements TouchListenView.OnItemTouchListener {

    @BindView(R.id.id_settings_title)
    SettingTitleBar settingTitleBar;

    @BindView(R.id.id_bubble_view)
    BubbleView bubbleView;

    @BindView(R.id.id_sample_text_view)
    TextView textView;

    private ViewGroup.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        layoutParams = textView.getLayoutParams();
        settingTitleBar.setSettingString(getString(R.string.about_mood_diary));
        settingTitleBar.setBackgroundColor(TheApplication.getThemeData().getColor());
        settingTitleBar.setOnItemTouchListener(this);

        bubbleView.removeView(textView);
        Iterator<TextView> textViewIterator = new BubbleFactory(this);
        while (textViewIterator.hasNext()){
            TextView textView = textViewIterator.next();
            textView.setLayoutParams(layoutParams);
            bubbleView.addView(textView);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

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
}
