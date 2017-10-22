package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.ui.fragment.ArticleEditFragment;


public class ArticleEditActivity extends Activity{


    ArticleEditFragment articleEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_edit);

        if(articleEditFragment == null) {
            articleEditFragment = new ArticleEditFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.id_container_article_edit, articleEditFragment)
                .commit();


    }
}
