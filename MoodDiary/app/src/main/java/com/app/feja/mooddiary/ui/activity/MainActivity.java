package com.app.feja.mooddiary.ui.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.PopupWindowAdapter;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.presenter.WeatherPresenter;
import com.app.feja.mooddiary.ui.fragment.ArticleListFragment;
import com.app.feja.mooddiary.ui.fragment.SettingsFragment;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.ui.view.WeatherView;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.CategoryView;
import com.app.feja.mooddiary.widget.MainTitleBar;
import com.app.feja.mooddiary.widget.SearchView;
import com.app.feja.mooddiary.widget.TabView;
import com.arlib.floatingsearchview.FloatingSearchView;
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
        MainTitleBar.OnTitleBarClickListener, ArticleListView, View.OnClickListener, TextWatcher,
        SearchView.OnAnimationListener, WeatherView, PopupWindowAdapter.OnPopupWindowItemClickListener {

    private ArticleListFragment articleListFragment;
    private SettingsFragment settingsFragment;
    private MainTitleBar mainTitleBar;
    private TabView tabView;
    private ArticleListPresenter presenter;
    private WeatherPresenter weatherPresenter;
    private PopupWindowAdapter popupWindowAdapter;
    private CustomPopWindow customPopWindow;
    private LinearLayout mainLayout;
    private LinearLayout popupLayout;
    private ViewGroup.LayoutParams params;
    private CompactCalendarView compactCalendarView;
    private Date selectDate;
    private FloatingSearchView floatingSearchView;
    private SearchView searchView;
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
        weatherPresenter = new WeatherPresenter(this);

        tabView.setOnTabClickListener(this);
        mainTitleBar.setOnTitleBarClickListener(this);

//        weatherPresenter.getWeather();
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
//                fragmentTransaction.replace(R.id.fragment_container, settingsFragment);
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
            popupLayout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.layout_category_popup_window, null);

            //所有分类
            CategoryView totalView = (CategoryView) popupLayout.findViewById(R.id.id_popup_all_category_view);
            totalView.setCategoryString(getResources().getString(R.string.all_sort));
            totalView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String category = ((CategoryView) v).getCategoryString();
                    presenter.loadArticles(category);
                    customPopWindow.dissmiss();
                    mainTitleBar.setTitleString(category);
                }
            });

            //编辑我的分类
            CategoryView editView = (CategoryView) popupLayout.findViewById(R.id.id_popup_edit_category_view);
            editView.setCategoryString(getResources().getString(R.string.edit_my_category));
            editView.setOnClickListener(this);

            RecyclerView recyclerView = (RecyclerView) popupLayout.findViewById(R.id.id_popup_recycler_view);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            popupWindowAdapter = new PopupWindowAdapter();
            popupWindowAdapter.setOnPopupWindowItemClickListener(this);
            recyclerView.setAdapter(popupWindowAdapter);
        }

        popupWindowAdapter.setData(typeEntities);
        popupWindowAdapter.notifyDataSetChanged();

        if(customPopWindow == null){
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(popupLayout)
                    .size(ApplicationContext.getScreenWidth() * 3 / 5, ApplicationContext.getScreenHeight() / 3)
                    .create();
        }
        customPopWindow.showAsDropDown(mainTitleBar, ApplicationContext.getScreenWidth() / 5, 5);
    }

//    private boolean isSearchBarShowing = false;
//    /**
//     * 搜索被点击
//     */
//    @Override
//    public void onSearchClick() {
//        if(floatingSearchView == null){
//            LinearLayout linearLayout = (LinearLayout) LayoutInflater
//                    .from(this)
//                    .inflate(R.layout.layout_search_bar, null, false);
//
//
//
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
//    }

    private boolean isSearchBarShowing = false;
    /**
     * 搜索被点击
     */
    @Override
    public void onSearchClick() {
        if(searchView == null){
            LinearLayout linearLayout = (LinearLayout) LayoutInflater
                    .from(this)
                    .inflate(R.layout.layout_search_bar, null, false);
            searchView = (SearchView) linearLayout.findViewById(R.id.search_view);
            linearLayout.removeView(searchView);
            linearLayout = null;
            mainLayout.addView(searchView, 1);
            isSearchBarShowing = true;
            mainTitleBar.changeSearch();
            searchView.addTextChangedListener(this);
            searchView.setOnAnimationListener(this);
            searchView.showWithAnimation();
        }else if(isSearchBarShowing){
            isSearchBarShowing = false;
            searchView.hideWithAnimation();
            mainTitleBar.changeTitle();
        }else{
            isSearchBarShowing = true;
            searchView.showWithAnimation();
            mainTitleBar.changeSearch();
        }
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
        Intent intent = new Intent();
        intent.setClass(this, CategoryActivity.class);
        startActivity(intent);
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        presenter.loadArticlesByKeyWord(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onHideAnimationEnd() {
        if(searchView.getSearchString() != null && !searchView.getSearchString().equals("")){
            presenter.loadArticles(mainTitleBar.getTitleString());
        }
    }

    @Override
    public void onShowAnimationEnd() {
        if(searchView.getSearchString() != null && !searchView.getSearchString().equals("")) {
            presenter.loadArticlesByKeyWord(searchView.getSearchString());
        }
    }

    @Override
    public void onLoadWeather(WeatherModel weatherModel) {
        if(weatherModel != null){
            ApplicationContext.setWeatherModel(weatherModel);
        }
    }

    @Override
    public void onPopupWindowClick(View view) {
        String category = ((CategoryView)view).getCategoryString();
        presenter.loadArticles(category);
        mainTitleBar.setTitleString(category);
        customPopWindow.dissmiss();
    }
}
