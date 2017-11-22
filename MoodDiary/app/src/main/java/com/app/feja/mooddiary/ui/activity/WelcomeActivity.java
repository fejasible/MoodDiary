package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends Activity {

    @BindView(R.id.id_welcome_text_view)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ButterKnife.bind(this);

        textView.setTextColor(ApplicationContext.getThemeData().getColor());

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent();
                intent.setClass(WelcomeActivity.this, PasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PasswordActivity.ACTION_BUNDLE_NAME, PasswordActivity.ACTION.FIRST_ENTER);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
