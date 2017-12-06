package com.app.feja.mooddiary.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.ui.fragment.ArticleEditFragment;


public class ArticleEditActivity extends BaseActivity{


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

    @Override
    public void onBackPressed() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        super.onBackPressed();
    }
}
