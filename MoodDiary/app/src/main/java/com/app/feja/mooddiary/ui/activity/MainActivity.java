package com.app.feja.mooddiary.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.fragment.ArticleListFragment;
import com.app.feja.mooddiary.ui.fragment.SettingsFragment;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.ArticleView;
import com.app.feja.mooddiary.widget.CategoryView;
import com.app.feja.mooddiary.widget.MainTitleBar;
import com.app.feja.mooddiary.widget.Search.DataHelper;
import com.app.feja.mooddiary.widget.Search.DiarySuggestion;
import com.app.feja.mooddiary.widget.TabView;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends FragmentActivity implements TabView.OnTabClickListener,
        MainTitleBar.OnTitleBarClickListener, ArticleListView, View.OnClickListener{

    private ArticleListFragment articleListFragment;
    private SettingsFragment settingsFragment;
    private MainTitleBar mainTitleBar;
    private TabView tabView;
    private ArticleListPresenter presenter;
    private CustomPopWindow customPopWindow;
    private LinearLayout mainLayout;
    private LinearLayout popupLayout;
    private ViewGroup.LayoutParams params;
    private CompactCalendarView compactCalendarView;
    private Date selectDate;
    private FloatingSearchView floatingSearchView;
    public static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

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
        mainLayout = (LinearLayout) this.findViewById(R.id.id_container_main);
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


    private boolean isCalendarShowing = false;
    /**
     * 日历被点击
     */
    @Override
    public void onCalendarClick() {
        if(compactCalendarView == null){
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.activity_calendar, null, false);
            compactCalendarView = (CompactCalendarView) linearLayout.findViewById(R.id.compactcalendar_view);
            linearLayout.removeView(compactCalendarView);
            linearLayout = null;
            mainLayout.addView(compactCalendarView, 1);
            compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
            compactCalendarView.setLocale(TimeZone.getDefault(), Locale.CHINESE);
            compactCalendarView.setUseThreeLetterAbbreviation(true);
            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    presenter.loadArticles(dateClicked);
                    selectDate = dateClicked;
                }

                @Override
                public void onMonthScroll(Date firstDayOfNewMonth) {
                    mainTitleBar.changeDate(firstDayOfNewMonth.getTime());
                    selectDate = firstDayOfNewMonth;
                    presenter.loadArticles(selectDate);
                }
            });
            selectDate = new DateTime().toDate();
            mainTitleBar.changeDate(selectDate.getTime());
            compactCalendarView.showCalendar();
            presenter.loadArticles(selectDate);
            isCalendarShowing = true;
            this.refreshCalendarEvent();
        }else if(!compactCalendarView.isAnimating()){
            if(!isCalendarShowing){
                isCalendarShowing = true;
                this.refreshCalendarEvent();
                presenter.loadArticles(selectDate);
                mainTitleBar.changeDate();
                compactCalendarView.showCalendarWithAnimation();
            }else{
                isCalendarShowing = false;
                presenter.loadArticles(mainTitleBar.getTitleString());
                mainTitleBar.changeTitle();
                compactCalendarView.hideCalendarWithAnimation();
            }
        }
    }

    private void refreshCalendarEvent(){
        compactCalendarView.removeAllEvents();
        List<DiaryEntity> diaryEntities = presenter.getAllArticles();
        List<Event> events = new ArrayList<>();
        for(DiaryEntity diaryEntity: diaryEntities){
            events.add(new Event(ContextCompat.getColor(this, R.color.lightSkyBlue_deeper),
                    diaryEntity.getCreateTime().getTime()));
        }
        compactCalendarView.addEvents(events);
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
            params = popupLayout.findViewById(R.id.category_view).getLayoutParams();

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

        }else{
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

    private boolean isSearchBarShowing = false;
    /**
     * 搜索被点击
     */
    @Override
    public void onSearchClick() {
//        if(floatingSearchView == null){
//            LinearLayout linearLayout = (LinearLayout) LayoutInflater
//                    .from(this)
//                    .inflate(R.layout.layout_search_bar, null, false);
//            floatingSearchView = (FloatingSearchView) linearLayout.findViewById(R.id.floating_search_view);
//            linearLayout.removeView(floatingSearchView);
//            mainLayout.addView(floatingSearchView, 1);
//            isSearchBarShowing = true;
//            mainTitleBar.changeSearch();
//            floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
//                @Override
//                public void onSearchTextChanged(String oldQuery, String newQuery) {
//                    if (!oldQuery.equals("") && newQuery.equals("")) {
//                        floatingSearchView.clearSuggestions();
//                    } else {
//                        floatingSearchView.showProgress();
//                        DataHelper.findSuggestions(MainActivity.this, newQuery, 5,
//                                FIND_SUGGESTION_SIMULATED_DELAY, new DataHelper.OnFindSuggestionsListener() {
//                                    @Override
//                                    public void onResults(List<DiarySuggestion> results) {
//                                        floatingSearchView.swapSuggestions(results);
//                                        floatingSearchView.hideProgress();
//                                    }
//                                });
//                    }
//                }
//            });
//
//            floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
//                @Override
//                public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
//                    Toast.makeText(MainActivity.this, searchSuggestion.getBody(), Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onSearchAction(String currentQuery) {
//
//                }
//            });
//
//        }else if(isSearchBarShowing){
//            isSearchBarShowing = false;
//            mainTitleBar.changeTitle();
//            mainLayout.removeView(floatingSearchView);
//        }else{
//            isSearchBarShowing = true;
//            mainTitleBar.changeSearch();
//            mainLayout.addView(floatingSearchView, 1);
//        }



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
            mainTitleBar.setTitleString(categoryView.getCategoryString());
            mainTitleBar.invalidate();
            presenter.loadArticles(categoryView.getCategoryString());
        }
        customPopWindow.dissmiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isCalendarShowing){
            presenter.loadArticles(selectDate);
        }else{
            presenter.loadArticles(mainTitleBar.getTitleString());
        }
    }
}
