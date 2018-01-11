package com.app.feja.mooddiary.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.ArticleAdapter;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.adapter.DiaryAdapter;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.ui.activity.ArticleBrowseActivity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.ArticleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * created by fejasible@163.com
 */
public class ArticleListFragment extends Fragment implements ArticleListView,
        ArticleView.OnArticleViewClickListener, SwipeRefreshLayout.OnRefreshListener, Runnable {

    private LinearLayout view;
//    private LinearLayout linearLayout;
//    private ViewGroup.LayoutParams layoutParams;
    private LayoutAnimationController layoutAnimationController;
//    private ArticleView.OnArticleViewClickListener listener;
    private List<DiaryEntity> diaryEntities;

    private ArticleAdapter articleAdapter;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Handler handler;

    private Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.initAnimation();
        this.initLayout(inflater, container);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.setLayoutManager(layoutManager);

        articleAdapter = new ArticleAdapter(getActivity());
        articleAdapter.setData(new ArrayList<DiaryEntity>());
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    private void initAnimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(500);

//        AnimationSet animationSet = new AnimationSet(true);
//        animationSet.addAnimation(alphaAnimation);

        layoutAnimationController = new LayoutAnimationController(alphaAnimation, 0);
    }

    private void initLayout(LayoutInflater inflater, ViewGroup container){
        view = (LinearLayout) inflater.inflate(R.layout.fragment_article_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.id_article_list_swipe_refresh_layout);
        recyclerView = (RecyclerView) view.findViewById(R.id.id_article_list_recycler_view);
//        linearLayout = (LinearLayout) view.findViewById(R.id.layout_article_list);
//        linearLayout.setLayoutAnimation(layoutAnimationController);
//        View articleViewLayout = inflater.inflate(R.layout.list_item_article_view, container, false);
//        ArticleView articleView = (ArticleView) articleViewLayout.findViewById(R.id.article_view);
//        layoutParams = articleView.getLayoutParams();
    }


    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {
        if(diaryEntities == null){
            diaryEntities = new ArrayList<>();
        }
        this.diaryEntities = diaryEntities;
        articleAdapter.setData(diaryEntities);
        articleAdapter.notifyDataSetChanged();
//        linearLayout.removeAllViews();
//
//        if(listener == null){
//            listener = this;
//        }
//        for(DiaryEntity diaryEntity: diaryEntities){
//            ArticleView articleView = DiaryAdapter.getArticleView(diaryEntity, layoutParams, listener);
//            articleView.setThemeColor(TheApplication.getThemeData().getColor());
//            linearLayout.addView(articleView);
//        }
//        linearLayout.startLayoutAnimation();
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

    @Override
    public void onRefresh() {
        if(handler == null){
            handler = new Handler();
        }
        handler.postDelayed(this, random.nextInt(1000)+300);
    }

    @Override
    public void run() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
