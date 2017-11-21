package com.app.feja.mooddiary.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.adapter.DiaryAdapter;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.activity.ArticleBrowseActivity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.ArticleView;

import java.util.List;


public class ArticleListFragment extends Fragment implements ArticleListView, ArticleView.OnArticleViewClickListener {

    private View view;
    private LinearLayout linearLayout;
    private ArticleListPresenter articleListPresenter;
    private ViewGroup.LayoutParams layoutParams;
    private LayoutAnimationController layoutAnimationController;
    private ArticleView.OnArticleViewClickListener listener;
    private List<DiaryEntity> diaryEntities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.articleListPresenter = new ArticleListPresenter(this);
        this.initAnimation();
        this.initLayout(inflater, container);
        return view;
    }

    private void initAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);

        layoutAnimationController = new LayoutAnimationController(alphaAnimation, 0.3f);
    }

    private void initLayout(LayoutInflater inflater, ViewGroup container){
        view = inflater.inflate(R.layout.fragment_article_list, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.layout_article_list);
        linearLayout.setLayoutAnimation(layoutAnimationController);
        View articleViewLayout = inflater.inflate(R.layout.layout_article_view, container, false);
        ArticleView articleView = (ArticleView) articleViewLayout.findViewById(R.id.article_view);
        layoutParams = articleView.getLayoutParams();
    }


    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {
        if(diaryEntities == null){
            return ;
        }

        this.diaryEntities = diaryEntities;
        linearLayout.removeAllViews();

        if(listener == null){
            listener = this;
        }
        for(DiaryEntity diaryEntity: diaryEntities){
            ArticleView articleView = DiaryAdapter.getArticleView(diaryEntity, layoutParams, listener);
            linearLayout.addView(articleView);
        }
        linearLayout.startLayoutAnimation();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(diaryEntities != null){
            onLoadArticles(diaryEntities);
        }
    }

    @Override
    public void onFaceClick(ArticleView articleView) {

    }

    @Override
    public void onDateClick(ArticleView articleView) {

    }

    @Override
    public void onAbstractClick(ArticleView articleView) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DiaryEntity.BUNDLE_NAME, articleView.getDiaryEntity());
        intent.putExtras(bundle);
        intent.setClass(getActivity().getApplicationContext(), ArticleBrowseActivity.class);
        startActivity(intent);
    }
}
