package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.fragment.ArticleEditFragment;
import com.app.feja.mooddiary.widget.ArticleEditTitleBar;



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
