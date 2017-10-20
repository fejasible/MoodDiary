package com.app.feja.mooddiary.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.fragment.MainFragment;
import com.app.feja.mooddiary.fragment.SettingsFragment;
import com.app.feja.mooddiary.widget.TabView;

public class MainActivity extends FragmentActivity {

    private MainFragment mainFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(mainFragment == null){
            mainFragment = new MainFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment).commit();

        TabView tabView = (TabView) this.findViewById(R.id.tab_view);
        tabView.setOnItemClickListener(new TabView.OnItemClickListener() {
            @Override
            public void onClick(int item) {
                if(mainFragment == null){
                    mainFragment = new MainFragment();
                }
                if(settingsFragment == null){
                    settingsFragment = new SettingsFragment();
                }

                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                switch (item){
                    case 0:
                        fragmentTransaction.replace(R.id.fragment_container, mainFragment);
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
