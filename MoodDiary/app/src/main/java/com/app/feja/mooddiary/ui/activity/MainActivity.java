package com.app.feja.mooddiary.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.fragment.ArticleListFragment;
import com.app.feja.mooddiary.fragment.SettingsFragment;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.widget.TabView;

import java.util.Date;

public class MainActivity extends FragmentActivity {

    private ArticleListFragment articleListFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(articleListFragment == null){
            articleListFragment = new ArticleListFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, articleListFragment).commit();

        TabView tabView = (TabView) this.findViewById(R.id.tab_view);
        tabView.setOnItemClickListener(new TabView.OnItemClickListener() {
            @Override
            public void onClick(int item) {
                if(articleListFragment == null){
                    articleListFragment = new ArticleListFragment();
                }
                if(settingsFragment == null){
                    settingsFragment = new SettingsFragment();
                }

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                switch (item){
                    case 0:
                        fragmentTransaction.replace(R.id.fragment_container, articleListFragment);
                        break;
                    case 1:
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), ArticleEditActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }
}
