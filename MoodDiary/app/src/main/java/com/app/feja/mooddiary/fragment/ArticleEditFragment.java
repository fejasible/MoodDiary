package com.app.feja.mooddiary.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.presenter.ArticleEditPresenter;
import com.app.feja.mooddiary.ui.view.ArticleEditView;
import com.app.feja.mooddiary.widget.ArticleEditTitleBar;

import java.io.Serializable;

public class ArticleEditFragment extends Fragment implements ArticleEditView{

    private ArticleEditTitleBar titleBar;
    private View view;
    private EditText editText;
    private ArticleEditPresenter articleEditPresenter;
    private DiaryEntity diaryEntity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_edit, container, false);
        titleBar = (ArticleEditTitleBar) view.findViewById(R.id.article_edit_title_bar);
        editText = (EditText) view.findViewById(R.id.editText);
        articleEditPresenter = new ArticleEditPresenter(this);
        Serializable s = getActivity().getIntent().getSerializableExtra(DiaryEntity.BUNDLE_NAME);
        if(s != null){
            this.diaryEntity = (DiaryEntity) s;
        }else{
            this.diaryEntity = new DiaryEntity();
        }

        articleEditPresenter.loadArticle(diaryEntity);
        titleBar.setOnTitleBarClickListener(new ArticleEditTitleBar.OnTitleBarClickListener() {
            @Override
            public void onCancelClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onCategoryClick(DiaryEntity diaryEntity) {
                Toast.makeText(getActivity().getApplicationContext(), "category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSaveClick(DiaryEntity diaryEntity) {
                diaryEntity.setContent(editText.getText().toString());
                articleEditPresenter.editArticle(diaryEntity);
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    public DiaryEntity getDiary(){
        return diaryEntity;
    }

    @Override
    public void onLoadArticle(DiaryEntity diaryEntity) {
        editText.setText(diaryEntity.getContent());
        editText.setSelection(diaryEntity.getContent() == null ? 0 :diaryEntity.getContent().length());
        titleBar.setDiaryEntity(diaryEntity);
    }
}
