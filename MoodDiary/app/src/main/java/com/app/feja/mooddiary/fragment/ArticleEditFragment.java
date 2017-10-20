package com.app.feja.mooddiary.fragment;


import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.widget.ArticleEditTitleBar;

import java.util.Date;

public class ArticleEditFragment extends Fragment {

    private ArticleEditTitleBar titleBar;
    private View view;
    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_edit, container, false);
        titleBar = (ArticleEditTitleBar) view.findViewById(R.id.article_edit_title_bar);
        editText = (EditText) view.findViewById(R.id.editText);

        titleBar.setOnTitleBarClickListener(new ArticleEditTitleBar.OnTitleBarClickListener() {
            @Override
            public void onCancelClick() {
                Toast.makeText(getActivity().getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCategoryClick() {
                Toast.makeText(getActivity().getApplicationContext(), "category", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSaveClick() {
                Toast.makeText(getActivity().getApplicationContext(), "save", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    public DiaryEntity getDiary(){
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setContent(editText.getText().toString());
        diaryEntity.setCreateTime(new Date());
        return diaryEntity;
    }

    public void setDiary(DiaryEntity diary){

    }
}
