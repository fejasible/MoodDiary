package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.view.CategoryView;
import com.app.feja.mooddiary.widget.CategoryTitleBar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends Activity implements CategoryView{

    private ArticleListPresenter articleListPresenter;
    private LinearLayout linearLayout;
    private ViewGroup.LayoutParams categoryParams;
    private CategoryTitleBar categoryTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        linearLayout = (LinearLayout) findViewById(R.id.id_container_category);
        categoryTitleBar = (CategoryTitleBar) findViewById(R.id.id_category_title_bar);
        categoryParams = linearLayout.findViewById(R.id.id_category_view_sample).getLayoutParams();
        linearLayout.removeAllViews();

        articleListPresenter = new ArticleListPresenter(this);

        categoryTitleBar.setOnTitleBarClickListener(new CategoryTitleBar.OnTitleBarClickListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }

            @Override
            public void onAddClick() {

            }
        });
        articleListPresenter.loadCategories();
    }

    @Override
    public void onLoadCategories(List<TypeEntity> typeEntities, List<DiaryEntity> diaryEntities) {

        Map<String, Integer> map = new HashMap<>();
        for(TypeEntity typeEntity: typeEntities){
            Toast.makeText(this, typeEntity.getType(), Toast.LENGTH_SHORT).show();
            map.put(typeEntity.getType(), 0);
        }
        for(DiaryEntity diaryEntity: diaryEntities){
            if(map.containsKey(diaryEntity.getType().getType())){
                map.put(diaryEntity.getType().getType(), map.get(diaryEntity.getType().getType()) + 1);
            }
        }
        linearLayout.removeAllViews();
        for(Map.Entry<String, Integer> entry: map.entrySet()){
            com.app.feja.mooddiary.widget.CategoryView categoryView =
                    new com.app.feja.mooddiary.widget.CategoryView(this);
            categoryView.setLayoutParams(categoryParams);
            categoryView.setShowDelete(true);
            categoryView.setCategoryString(entry.getKey());
            categoryView.setCategoryCount(entry.getValue());
            linearLayout.addView(categoryView);
        }

    }
}
