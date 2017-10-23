package com.app.feja.mooddiary.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.fragment.ArticleListFragment;
import com.app.feja.mooddiary.ui.fragment.SettingsFragment;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.CategoryView;
import com.app.feja.mooddiary.widget.MainTitleBar;
import com.app.feja.mooddiary.widget.TabView;
import com.example.zhouwei.library.CustomPopWindow;

import java.util.List;


public class MainActivity extends FragmentActivity implements TabView.OnTabClickListener,
        MainTitleBar.OnTitleBarClickListener, ArticleListView, View.OnClickListener{

    private ArticleListFragment articleListFragment;
    private SettingsFragment settingsFragment;
    private MainTitleBar mainTitleBar;
    private TabView tabView;
    private ArticleListPresenter presenter;
    private CustomPopWindow customPopWindow;
    private LinearLayout popupLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(articleListFragment == null){
            articleListFragment = new ArticleListFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, articleListFragment).commit();

        tabView = (TabView) this.findViewById(R.id.tab_view);
        mainTitleBar = (MainTitleBar) this.findViewById(R.id.main_title_bar);
        presenter = new ArticleListPresenter(this);

        tabView.setOnTabClickListener(this);
        mainTitleBar.setOnTitleBarClickListener(this);
    }

    /**
     * Tab被点击
     * @param item item
     */
    @Override
    public void onClick(int item) {
        if(articleListFragment == null){
            articleListFragment = new ArticleListFragment();
        }
        if(settingsFragment == null){
            settingsFragment = new SettingsFragment();
        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (item){
            case 0:
                fragmentTransaction.replace(R.id.fragment_container, articleListFragment);
                break;
            case 1:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ArticleEditActivity.class);
                startActivity(intent);
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 日历被点击
     */
    @Override
    public void onCalendarClick() {

    }

    /**
     * 分类被点击
     */
    @Override
    public void onCategoryClick() {
        // 获取分类信息
        List<TypeEntity> typeEntities = presenter.getAllTypes();

        if(popupLayout == null){
            // 获取弹窗布局
            popupLayout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.layout_category_popup_window, null);


            // 获取弹窗内容组件布局
            ViewGroup.LayoutParams params = popupLayout.findViewById(R.id.category_view).getLayoutParams();

            // 初始化弹窗布局内容
            popupLayout.removeAllViews();

            // “所有分类”选项
            CategoryView categoryView = new CategoryView(getApplicationContext());
            categoryView.setLayoutParams(params);
            categoryView.setCategoryString(getResources().getString(R.string.all_sort));
            categoryView.setOnClickListener(this);
            popupLayout.addView(categoryView);

            // 分类选项
            for(TypeEntity typeEntity: typeEntities){
                categoryView = new CategoryView(getApplicationContext());
                categoryView.setOnClickListener(this);
                categoryView.setLayoutParams(params);
                categoryView.setCategoryString(typeEntity.getType());
                popupLayout.addView(categoryView);
            }

            // “编辑我的分类”选项
            categoryView = new CategoryView(getApplicationContext());
            categoryView.setLayoutParams(params);
            categoryView.setCategoryString(getResources().getString(R.string.edit_my_category));
            categoryView.setOnClickListener(this);
            popupLayout.addView(categoryView);

        }

        if(customPopWindow == null){
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(popupLayout)
                    .size(ApplicationContext.getScreenWidth() * 3 / 5, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .create();
        }
        customPopWindow.showAsDropDown(mainTitleBar, ApplicationContext.getScreenWidth() / 5, 5);
    }

    /**
     * 搜索被点击
     */
    @Override
    public void onSearchClick() {

    }

    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {
        articleListFragment.onLoadArticles(diaryEntities);
    }


    /**
     * 分类被点击
     * @param v View
     */
    @Override
    public void onClick(View v) {
        CategoryView categoryView;
        try {
            categoryView = (CategoryView) v;
        }catch (ClassCastException e){
            e.printStackTrace();
            return ;
        }
        if(categoryView.getCategoryString().equals(getString(R.string.edit_my_category))){
            Intent intent = new Intent();
            intent.setClass(this, CategoryActivity.class);
            startActivity(intent);
        }else{
            presenter.loadArticles(categoryView.getCategoryString());
        }
        customPopWindow.dissmiss();
    }
}
