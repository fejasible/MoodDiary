package com.app.feja.mooddiary.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.view.CategoryView;
import com.app.feja.mooddiary.widget.CategoryTitleBar;
import com.app.feja.mooddiary.widget.ConfirmCancelButton;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.picker.SinglePicker;

public class CategoryActivity extends Activity implements CategoryView, CategoryTitleBar.OnTitleBarClickListener, View.OnClickListener {

    private ArticleListPresenter articleListPresenter;
    private LinearLayout category_list_container;
    private ViewGroup.LayoutParams categoryParams;
    private CategoryTitleBar categoryTitleBar;
    private CustomPopWindow customPopWindow;
    private LinearLayout categoryEditLayout;
    private ConfirmCancelButton confirmCancelButton;
    private EditText editText;
    private List<TypeEntity> typeEntities;
    private List<DiaryEntity> diaryEntities;
    private OptionPicker categoryPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category_list_container = (LinearLayout) findViewById(R.id.id_container_category);
        categoryEditLayout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.layout_category_edit_input, null);
        confirmCancelButton = (ConfirmCancelButton) categoryEditLayout.findViewById(R.id.id_confirm_cancel_button);
        editText = (EditText) categoryEditLayout.findViewById(R.id.editText);
        categoryTitleBar = (CategoryTitleBar) findViewById(R.id.id_category_title_bar);
        categoryParams = category_list_container.findViewById(R.id.id_category_view_sample).getLayoutParams();
        category_list_container.removeAllViews();

        articleListPresenter = new ArticleListPresenter(this);

        categoryTitleBar.setOnTitleBarClickListener(this);
        articleListPresenter.loadCategories();
    }

    @Override
    public void onLoadCategories(List<TypeEntity> typeEntities, List<DiaryEntity> diaryEntities) {
        this.typeEntities = typeEntities;
        this.diaryEntities = diaryEntities;
        Map<String, Integer> map = new HashMap<>();
        for(TypeEntity typeEntity: typeEntities){
            if(typeEntity.getType().equals(getString(R.string.no_sort))){
                continue;
            }
            map.put(typeEntity.getType(), 0);
        }
        for(DiaryEntity diaryEntity: diaryEntities){
            if(map.containsKey(diaryEntity.getType().getType())){
                map.put(diaryEntity.getType().getType(), map.get(diaryEntity.getType().getType()) + 1);
            }
        }
        category_list_container.removeAllViews();
        for(Map.Entry<String, Integer> entry: map.entrySet()){
            com.app.feja.mooddiary.widget.CategoryView categoryView =
                    new com.app.feja.mooddiary.widget.CategoryView(this);
            categoryView.setLayoutParams(categoryParams);
            categoryView.setBackgroundColor(Color.LTGRAY);
            categoryView.setCategoryString(entry.getKey());
            categoryView.setCategoryCount(entry.getValue());
            categoryView.setOnClickListener(this);
            category_list_container.addView(categoryView);
        }

    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }

    @Override
    public void onAddClick() {
        if(customPopWindow == null){
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(categoryEditLayout)
                    .size(ApplicationContext.getScreenWidth()*2/3, ApplicationContext.getScreenHeight()/6)
                    .enableBackgroundDark(true)
                    .enableOutsideTouchableDissmiss(false)
                    .setBgDarkAlpha(0.9f)
                    .create();
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean categoryIsExist = false;
                for(TypeEntity typeEntity: typeEntities){
                    if(s.toString().equals(typeEntity.getType())){
                        categoryIsExist = true;
                        break;
                    }
                }
                if(s.length() == 0 || categoryIsExist){
                    confirmCancelButton.setConfirmEnable(false);
                    confirmCancelButton.invalidate();
                }else{
                    confirmCancelButton.setConfirmEnable(true);
                    confirmCancelButton.invalidate();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmCancelButton.setConfirmEnable(false);
        confirmCancelButton.setOnButtonClickListener(new ConfirmCancelButton.OnButtonClickListener() {
            @Override
            public void onCancelClick() {
                customPopWindow.onDismiss();
                customPopWindow = null;
            }

            @Override
            public void onConfirmClick() {
                customPopWindow.onDismiss();
                customPopWindow = null;
                articleListPresenter.editTypes(editText.getText().toString().trim());
                articleListPresenter.loadCategories();
            }
        });

        customPopWindow.showAsDropDown(categoryTitleBar, ApplicationContext.getScreenWidth() / 6, 5);
    }

    @Override
    public void onClick(View v) {
        com.app.feja.mooddiary.widget.CategoryView categoryView =
                (com.app.feja.mooddiary.widget.CategoryView) v;
        if(categoryPicker == null){
            categoryPicker = new OptionPicker(this, new String[]{
                    getString(R.string.delete_category_only),
                    getString(R.string.delete_category_and_diary)
            });
        }
        categoryPicker.setTitleText(getString(R.string.delete_category_)+categoryView.getCategoryString());
        categoryPicker.setTitleTextColor(Color.GRAY);
        categoryPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                Toast.makeText(CategoryActivity.this, index+":"+item, Toast.LENGTH_SHORT).show();
                // TODO
            }
        });
        categoryPicker.show();
    }
}
