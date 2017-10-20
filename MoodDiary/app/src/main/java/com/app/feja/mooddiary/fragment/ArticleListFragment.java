package com.app.feja.mooddiary.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.adapter.DiaryAdapter;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.activity.ArticleBrowseActivity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.ArticleView;

import java.util.List;


public class ArticleListFragment extends Fragment implements ArticleListView{

    private View view;
    private LinearLayout linearLayout;
    private ArticleListPresenter articleListPresenter;
    private ViewGroup.LayoutParams layoutParams;
    private List<DiaryEntity> diaryEntities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.articleListPresenter = new ArticleListPresenter(this);
        view = inflater.inflate(R.layout.fragment_article_list, container, false);
        linearLayout = (LinearLayout) view.findViewById(R.id.layout_article_list);

        View articleViewLayout = inflater.inflate(R.layout.layout_article_view, container, false);
        ArticleView articleView = (ArticleView) articleViewLayout.findViewById(R.id.article_view);
        layoutParams = articleView.getLayoutParams();

        return view;
    }


    @Override
    public void onLoadArticles(final List<DiaryEntity> diaryEntities) {
        this.diaryEntities = diaryEntities;
        if(diaryEntities == null){
            return ;
        }
        linearLayout.removeAllViews();
        ArticleView.OnArticleViewClickListener listener = new ArticleView.OnArticleViewClickListener() {
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
                intent.setClass(ApplicationContext.getContext(), ArticleBrowseActivity.class);
                startActivity(intent);
            }
        };
        for(DiaryEntity diaryEntity: diaryEntities){
            ArticleView articleView = DiaryAdapter.getArticleView(diaryEntity, layoutParams, listener);
            linearLayout.addView(articleView);
        }

    }

    @Override
    public List<DiaryEntity> getArticles() {
        return this.diaryEntities;
    }


    @Override
    public void onStart() {
        super.onStart();
        articleListPresenter.loadArticles();
    }
}
