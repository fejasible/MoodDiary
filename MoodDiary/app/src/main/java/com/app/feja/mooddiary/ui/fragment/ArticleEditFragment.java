package com.app.feja.mooddiary.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleEditPresenter;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.activity.CategoryActivity;
import com.app.feja.mooddiary.ui.view.ArticleEditView;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.ArticleEditTitleBar;
import com.app.feja.mooddiary.widget.CategoryView;
import com.example.zhouwei.library.CustomPopWindow;

import java.io.Serializable;
import java.util.List;

public class ArticleEditFragment extends Fragment implements ArticleEditView,
        ArticleEditTitleBar.OnTitleBarClickListener, ArticleListView, View.OnClickListener{

    private ArticleEditTitleBar titleBar;
    private View view;
    private EditText editText;
    private ArticleEditPresenter articleEditPresenter;
    private ArticleListPresenter articleListPresenter;
    private LinearLayout popupLayout;
    private CustomPopWindow customPopWindow;
    private DiaryEntity diaryEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_edit, container, false);
        titleBar = (ArticleEditTitleBar) view.findViewById(R.id.article_edit_title_bar);
        editText = (EditText) view.findViewById(R.id.editText);
        articleEditPresenter = new ArticleEditPresenter(this);
        articleListPresenter = new ArticleListPresenter(this);

        Serializable s = getActivity().getIntent().getSerializableExtra(DiaryEntity.BUNDLE_NAME);
        if(s != null){
            this.diaryEntity = (DiaryEntity) s;
        }else{
            this.diaryEntity = new DiaryEntity();
        }

        articleEditPresenter.loadArticle(diaryEntity);
        titleBar.setOnTitleBarClickListener(this);
        return view;
    }

    public DiaryEntity getDiary(){
        return diaryEntity;
    }

    @Override
    public void onLoadArticle(DiaryEntity diaryEntity) {
        editText.setText(diaryEntity.getContent());
        editText.setSelection(diaryEntity.getContent().length());
        titleBar.setDiaryEntity(diaryEntity);
    }

    @Override
    public void onCancelClick() {
        getActivity().onBackPressed();
    }

    @Override
    public void onCategoryClick(DiaryEntity diaryEntity) {
        // 获取分类信息
        List<TypeEntity> typeEntities = articleListPresenter.getAllTypes();

        if(popupLayout == null){
            // 获取弹窗布局
            popupLayout = (LinearLayout) LayoutInflater.from(getActivity())
                    .inflate(R.layout.layout_category_popup_window, null);


            // 获取弹窗内容组件布局
            ViewGroup.LayoutParams params = popupLayout.findViewById(R.id.category_view).getLayoutParams();

            // 初始化弹窗布局内容
            popupLayout.removeAllViews();

            CategoryView categoryView;

            // 分类选项
            for(TypeEntity typeEntity: typeEntities){
                categoryView = new CategoryView(getActivity());
                categoryView.setOnClickListener(this);
                categoryView.setLayoutParams(params);
                categoryView.setCategoryString(typeEntity.getType());
                popupLayout.addView(categoryView);
            }

            // “编辑我的分类”选项
            categoryView = new CategoryView(getActivity());
            categoryView.setLayoutParams(params);
            categoryView.setCategoryString(getResources().getString(R.string.edit_my_category));
            categoryView.setOnClickListener(this);
            popupLayout.addView(categoryView);

        }

        if(customPopWindow == null){
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                    .setView(popupLayout)
                    .size(ApplicationContext.getScreenWidth() * 3 / 5, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create();
        }
        customPopWindow.showAsDropDown(titleBar, ApplicationContext.getScreenWidth() / 5, 3);
    }

    @Override
    public void onSaveClick(DiaryEntity diaryEntity) {
        diaryEntity.setType(this.diaryEntity.getType());
        diaryEntity.setContent(editText.getText().toString());
        articleEditPresenter.editArticle(diaryEntity);
        getActivity().onBackPressed();
    }

    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {

    }

    @Override
    public void onClick(View v) {
        customPopWindow.dissmiss();
        CategoryView categoryView = (CategoryView) v;
        if(categoryView.getCategoryString().equals(getString(R.string.edit_my_category))){
            Intent intent = new Intent();
            intent.setClass(this.getActivity(), CategoryActivity.class);
            startActivity(intent);
        }else{
            diaryEntity.setType(articleListPresenter.getType(categoryView.getCategoryString()));
            titleBar.setDiaryEntity(diaryEntity);
        }
    }
}
