package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.fragment.ArticleBrowseFragment;

public class ArticleBrowseActivity extends Activity {

    private ArticleBrowseFragment articleBrowseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_browse);
        if(articleBrowseFragment == null){
            articleBrowseFragment = new ArticleBrowseFragment();
        }
        getFragmentManager().beginTransaction()
                    .replace(R.id.id_container_article_browse, articleBrowseFragment).commit();
    }

}
