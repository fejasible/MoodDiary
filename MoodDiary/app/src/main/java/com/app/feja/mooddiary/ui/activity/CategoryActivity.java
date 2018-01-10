package com.app.feja.mooddiary.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.CategoryAdapter;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.view.CategoryView;
import com.app.feja.mooddiary.widget.CategoryTitleBar;
import com.app.feja.mooddiary.widget.ConfirmCancelButton;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;
import com.beloo.widget.chipslayoutmanager.gravity.IChildGravityResolver;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * created by fejasible@163.com
 */
public class CategoryActivity extends BaseActivity implements CategoryView,
        CategoryTitleBar.OnTitleBarClickListener{

    private ArticleListPresenter articleListPresenter;
//    private LinearLayout category_list_container;
//    private ViewGroup.LayoutParams categoryParams;
    private CustomPopWindow customPopWindow;
    private LinearLayout categoryEditLayout;
    private ConfirmCancelButton confirmCancelButton;
    private EditText editText;
    private List<TypeEntity> typeEntities;
//    private List<DiaryEntity> diaryEntities;
    private OptionPicker categoryPicker;

    private CategoryAdapter categoryAdapter;

    @BindView(R.id.id_category_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.id_category_title_bar)
    CategoryTitleBar categoryTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ButterKnife.bind(this);
        articleListPresenter = new ArticleListPresenter(this);

        categoryEditLayout = (LinearLayout) LayoutInflater.from(this)
                .inflate(R.layout.item_category_edit_input, null);
        confirmCancelButton = (ConfirmCancelButton) categoryEditLayout.findViewById(R.id.id_confirm_cancel_button);
        editText = (EditText) categoryEditLayout.findViewById(R.id.editText);

        categoryAdapter = new CategoryAdapter(articleListPresenter, this);
        categoryAdapter.setDiaryEntities(new ArrayList<DiaryEntity>());
        categoryAdapter.setTypeEntities(new ArrayList<TypeEntity>());

        ChipsLayoutManager layoutManager = ChipsLayoutManager.newBuilder(getApplicationContext())
                .setChildGravity(Gravity.START)
                .setScrollingEnabled(true)
                .setGravityResolver(new IChildGravityResolver() {
                    @Override
                    public int getItemGravity(int position) {
                        return Gravity.CENTER;
                    }
                })
                .setOrientation(ChipsLayoutManager.HORIZONTAL)
                .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                .withLastRow(true)
                .build();
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(categoryAdapter);

        categoryAdapter.notifyDataSetChanged();


        categoryTitleBar.setOnTitleBarClickListener(this);
        articleListPresenter.loadCategories();
    }

    /**
     * 加载分类
     * @param typeEntities 分类队列
     * @param diaryEntities 日记队列
     */
    @Override
    public void onLoadCategories(List<TypeEntity> typeEntities, List<DiaryEntity> diaryEntities) {
        this.typeEntities = typeEntities;
        categoryAdapter.setTypeEntities(typeEntities);
        categoryAdapter.setDiaryEntities(diaryEntities);
        categoryAdapter.notifyDataSetChanged();
    }

    /**
     * 返回按钮响应
     */
    @Override
    public void onBackClick() {
        onBackPressed();
    }

    /**
     * 添加按钮响应
     */
    @Override
    public void onAddClick() {
        if(customPopWindow == null){
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(categoryEditLayout)
                    .size(TheApplication.getScreenWidth()*2/3, TheApplication.getScreenHeight()/6)
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
                articleListPresenter.editType(editText.getText().toString().trim());
                articleListPresenter.loadCategories();
            }
        });

        customPopWindow.showAsDropDown(categoryTitleBar, TheApplication.getScreenWidth() / 6, 5);
    }
}
