package com.app.feja.mooddiary.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.presenter.ArticleBrowsePresenter;
import com.app.feja.mooddiary.ui.activity.ArticleEditActivity;
import com.app.feja.mooddiary.ui.view.ArticleView;
import com.app.feja.mooddiary.widget.ArticleBrowseTitleBar;

import java.io.Serializable;

public class ArticleBrowseFragment extends Fragment implements ArticleView{

    private View view;
    private ArticleBrowseTitleBar titleBar;
    private DiaryEntity diaryEntity;
    private TextView textView;
    private ArticleBrowsePresenter articleBrowsePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_browse, container, false);
        titleBar = (ArticleBrowseTitleBar) view.findViewById(R.id.article_browse_title_bar);
        textView = (TextView) view.findViewById(R.id.textView);
        this.articleBrowsePresenter = new ArticleBrowsePresenter(this);

        Serializable serializable = getActivity().getIntent().getSerializableExtra(DiaryEntity.BUNDLE_NAME);
        if(serializable == null){
            diaryEntity = new DiaryEntity();
        }else{
            diaryEntity = (DiaryEntity) serializable;
        }

        titleBar.setOnTitleBarClickListener(new ArticleBrowseTitleBar.OnTitleBarClickListener() {
            @Override
            public void onBackClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onCategoryClick() {
            }

            @Override
            public void onGarbageClick() {
                articleBrowsePresenter.deleteArticle(diaryEntity);
                getActivity().onBackPressed();
            }

            @Override
            public void onPenClick() {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(DiaryEntity.BUNDLE_NAME, diaryEntity);
                intent.putExtras(bundle);
                intent.setClass(ApplicationContext.getContext(), ArticleEditActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onLoadArticle(DiaryEntity diaryEntity) {
        titleBar.setDiaryEntity(diaryEntity);
        textView.setText(diaryEntity.getContent());
    }

    @Override
    public void onStart() {
        articleBrowsePresenter.loadArticle(diaryEntity);
        super.onStart();
    }
}
