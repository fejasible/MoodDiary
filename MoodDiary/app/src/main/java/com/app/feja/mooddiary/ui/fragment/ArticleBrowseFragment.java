package com.app.feja.mooddiary.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleBrowsePresenter;
import com.app.feja.mooddiary.ui.activity.ArticleEditActivity;
import com.app.feja.mooddiary.ui.view.ArticleView;
import com.app.feja.mooddiary.widget.ArticleBrowseTitleBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

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
                List<TypeEntity> typeEntities = articleBrowsePresenter.getAllType();
                List<String> items = new ArrayList<>();
                for(TypeEntity typeEntity: typeEntities){
                    items.add(typeEntity.getType());
                }
                SinglePicker<String> singlePicker = new SinglePicker<>(getActivity(), items);
                singlePicker.setOnItemPickListener(new SinglePicker.OnItemPickListener<String>() {
                    @Override
                    public void onItemPicked(int index, String item) {

                    }
                });
                singlePicker.show();
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
                intent.setClass(getActivity().getApplicationContext(), ArticleEditActivity.class);
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