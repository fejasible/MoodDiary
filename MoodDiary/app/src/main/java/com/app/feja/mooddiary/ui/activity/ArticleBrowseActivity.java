package com.app.feja.mooddiary.ui.activity;

import android.os.Bundle;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.ui.fragment.ArticleBrowseFragment;

/**
 * created by fejasible@163.com
 */
public class ArticleBrowseActivity extends BaseActivity {

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
