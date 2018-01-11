package com.app.feja.mooddiary.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.PopupWindowAdapter;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.factory.DiaryFactory;
import com.app.feja.mooddiary.factory.TypeFactory;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleEditPresenter;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.fragment.ArticleList2Fragment;
import com.app.feja.mooddiary.ui.fragment.ArticleListFragment;
import com.app.feja.mooddiary.ui.fragment.ArticleNoListFragment;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.ui.view.WeatherView;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.CategoryView;
import com.app.feja.mooddiary.widget.MainTitleBar;
import com.app.feja.mooddiary.widget.SearchView;
import com.app.feja.mooddiary.widget.TabView;
import com.example.zhouwei.library.CustomPopWindow;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * created by fejasible@163.com
 */
public class MainActivity extends BaseActivity implements TabView.OnTabClickListener,
        MainTitleBar.OnTitleBarClickListener, ArticleListView, View.OnClickListener, TextWatcher,
        SearchView.OnAnimationListener, WeatherView, PopupWindowAdapter.OnPopupWindowItemClickListener {

    private ArticleListFragment articleListFragment;
    private ArticleList2Fragment articleList2Fragment;
    private int articleListStyle;
    private ArticleNoListFragment articleNoListFragment;
    private ArticleListPresenter presenter;
    private PopupWindowAdapter popupWindowAdapter;
    private CustomPopWindow customPopWindow;
//    private CustomPopWindow countWindow;
    private LinearLayout popupLayout;
    private CompactCalendarView compactCalendarView;
    private Date selectDate;
    private SearchView searchView;
    private List<DiaryEntity> diaryEntities;
//    private LinearLayout statisticsLayout;
//    private TextView statisticsDate;
//    private TextView statisticsDiary;

    @BindView(R.id.tab_view)
    TabView tabView;
    @BindView(R.id.main_title_bar)
    MainTitleBar mainTitleBar;
    @BindView(R.id.id_container_main)
    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (articleListFragment == null) {
            articleListFragment = new ArticleListFragment();
        }
        if (articleList2Fragment == null){
            articleList2Fragment = new ArticleList2Fragment();
        }

        articleListStyle = 1;
        setArticleListStyle();

        presenter = new ArticleListPresenter(this);

        tabView.setOnTabClickListener(this);
        mainTitleBar.setOnTitleBarClickListener(this);

        if (isFirstStart()) {
            TypeFactory typeFactory = new TypeFactory();
            DiaryFactory diaryFactory = new DiaryFactory();
            presenter.editType(typeFactory.nextStandard().getType());

            ArticleEditPresenter articleEditPresenter = new ArticleEditPresenter(null);
            articleEditPresenter.editArticle(diaryFactory.createDefaultDairy());
//            for (int i = 0; i < 50; i++) {
//                TypeEntity typeEntity = typeFactory.next();
//                presenter.editType(typeEntity.getType());
//                articleEditPresenter.editArticle(diaryFactory.next(typeEntity));
//            }
        }
    }


    /**
     * Tab被点击
     *
     * @param item item
     */
    @Override
    public void onClick(int item) {
        Intent intent;
        switch (item) {
            case 0:
                switchArticleListStyle();
//                if (countWindow == null) {
//                    statisticsLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_statistics, null, false);
//                    statisticsDate = (TextView) statisticsLayout.findViewById(R.id.id_day_count_text_view);
//                    statisticsDiary = (TextView) statisticsLayout.findViewById(R.id.id_mood_count_text_view);
//                    statisticsLayout.measure(0, 0);
//                    countWindow = new CustomPopWindow.PopupWindowBuilder(this)
//                            .setView(statisticsLayout)
//                            .size(TheApplication.getScreenWidth() / 2, statisticsLayout.getMeasuredHeight())
//                            .create();
//                }
//                statisticsLayout.setBackgroundColor(TheApplication.getThemeData().getColor());
//                statisticsDiary.setText((presenter.getAllArticleCount() + ""));
//                statisticsDate.setText((presenter.getAllArticleDateCount() + ""));
//                countWindow.showAsDropDown(tabView, -tabView.getHeight() - countWindow.getHeight(), 0);
                break;
            case 1:
                intent = new Intent(getApplicationContext(), ArticleEditActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }


    private boolean isCalendarShowing = false;

    /**
     * 日历被点击
     */
    @Override
    public void onCalendarClick() {
        if (compactCalendarView == null) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.activity_calendar, null, false);
            compactCalendarView = (CompactCalendarView) linearLayout.findViewById(R.id.compactcalendar_view);
            linearLayout.removeView(compactCalendarView);
            mainLayout.addView(compactCalendarView, 1);
            compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
            compactCalendarView.setLocale(TimeZone.getDefault(), Locale.CHINESE);
            compactCalendarView.setUseThreeLetterAbbreviation(true);
            compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
                @Override
                public void onDayClick(Date dateClicked) {
                    selectDate = dateClicked;
                    presenter.loadArticles(dateClicked);
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
        } else if (!compactCalendarView.isAnimating()) {
            if (!isCalendarShowing) {
                isCalendarShowing = true;
                this.refreshCalendarEvent();
                DateTime dateTime = new DateTime();
                mainTitleBar.changeDate(dateTime.getTime());
                compactCalendarView.setCurrentDate(dateTime.toDate());
                presenter.loadArticles(selectDate);
                mainTitleBar.changeDate();
                compactCalendarView.showCalendarWithAnimation();
            } else {
                isCalendarShowing = false;
                presenter.loadArticles(mainTitleBar.getTitleString());
                mainTitleBar.changeTitle();
                compactCalendarView.hideCalendarWithAnimation();
            }
        }
        setThemeColor(TheApplication.getThemeData().getColor());
    }

    private void refreshCalendarEvent() {
        compactCalendarView.removeAllEvents();
        List<DiaryEntity> diaryEntities = presenter.getAllArticles();
        List<Event> events = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntities) {
            events.add(new Event(Color.WHITE, diaryEntity.getCreateTime().getTime()));
        }
        compactCalendarView.addEvents(events);
    }

    private CategoryView totalView;
    private CategoryView editView;

    /**
     * 分类被点击
     */
    @Override
    public void onCategoryClick() {
        // 获取分类信息
        List<TypeEntity> typeEntities = presenter.getAllTypes();

        if (popupLayout == null) {
            popupLayout = (LinearLayout) LayoutInflater.from(this)
                    .inflate(R.layout.item_category_popup_window, null);

            //所有分类
            totalView = (CategoryView) popupLayout.findViewById(R.id.id_popup_all_category_view);
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
            editView = (CategoryView) popupLayout.findViewById(R.id.id_popup_edit_category_view);
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

        editView.setBackgroundColor(TheApplication.getThemeData().getColor());
        totalView.setBackgroundColor(TheApplication.getThemeData().getColor());

        popupWindowAdapter.setData(typeEntities);
        popupWindowAdapter.notifyDataSetChanged();

        if (customPopWindow == null) {
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(popupLayout)
                    .size(TheApplication.getScreenWidth() * 3 / 5, TheApplication.getScreenHeight() / 3)
                    .create();
        }
        customPopWindow.showAsDropDown(mainTitleBar, TheApplication.getScreenWidth() / 5, 5);
    }

    private boolean isSearchBarShowing = false;

    /**
     * 搜索被点击
     */
    @Override
    public void onSearchClick() {
        if (searchView == null) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater
                    .from(this)
                    .inflate(R.layout.item_search_bar, null, false);
            searchView = (SearchView) linearLayout.findViewById(R.id.search_view);
            linearLayout.removeView(searchView);
            mainLayout.addView(searchView, 1);
            isSearchBarShowing = true;
            mainTitleBar.changeSearch();
            searchView.addTextChangedListener(this);
            searchView.setOnAnimationListener(this);
            searchView.showWithAnimation();
        } else if (isSearchBarShowing) {
            isSearchBarShowing = false;
            searchView.hideWithAnimation();
            mainTitleBar.changeTitle();
        } else {
            isSearchBarShowing = true;
            searchView.showWithAnimation();
            mainTitleBar.changeSearch();
        }
    }

    /**
     * 加载日记
     * @param diaryEntities 加载的日记
     */
    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {
        this.diaryEntities = diaryEntities;
        articleListFragment.onLoadArticles(diaryEntities);
        articleList2Fragment.onLoadArticles(diaryEntities);

        if (diaryEntities == null || diaryEntities.size() == 0) {
            if(articleNoListFragment == null){
                articleNoListFragment = new ArticleNoListFragment();
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, articleNoListFragment).commit();
        } else {
            setArticleListStyle();
        }
    }


    /**
     * 分类被点击
     *
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
        if (isCalendarShowing) {
            presenter.loadArticles(selectDate);
        } else {
            presenter.loadArticles(mainTitleBar.getTitleString());
        }
        this.setThemeColor(TheApplication.getThemeData().getColor());
    }

    private void setThemeColor(int color) {
        if (mainTitleBar != null) mainTitleBar.setBackgroundColor(color);
        if (tabView != null) tabView.setThemeColor(color);
        if (compactCalendarView != null) {
            int themeColor = TheApplication.getThemeData().getColor();

            float[] hsv = new float[3];
            Color.colorToHSV(themeColor, hsv);
            float[] hsv1 = hsv.clone();
            float[] hsv2 = hsv.clone();

            if (hsv[1] * 3 < 1) {
                hsv1[1] = hsv[1] + 1.0f / 6.0f;
                hsv2[1] = hsv[1] + 1.0f / 3.0f;
            } else if (hsv[1] * 3 < 2) {
                hsv1[1] = hsv[1] - 1.0f / 6.0f;
                hsv2[1] = hsv[1] + 1.0f / 6.0f;
            } else {
                hsv1[1] = hsv[1] - 1.0f / 6.0f;
                hsv2[1] = hsv[1] - 1.0f / 3.0f;
            }

            compactCalendarView.setCalendarBackgroundColor(themeColor);
            compactCalendarView.setCurrentSelectedDayBackgroundColor(Color.HSVToColor(hsv1));
            compactCalendarView.setCurrentDayBackgroundColor(Color.HSVToColor(hsv2));
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
        if (searchView.getSearchString() != null && !searchView.getSearchString().equals("")) {
            presenter.loadArticles(mainTitleBar.getTitleString());
        }
    }

    @Override
    public void onShowAnimationEnd() {
        if (searchView.getSearchString() != null && !searchView.getSearchString().equals("")) {
            presenter.loadArticlesByKeyWord(searchView.getSearchString());
        }
    }

    @Override
    public void onLoadWeather(WeatherModel weatherModel) {
        if (weatherModel != null) {
            TheApplication.setWeatherModel(weatherModel);
        }
    }

    @Override
    public void onPopupWindowClick(View view) {
        String category = ((CategoryView) view).getCategoryString();
        presenter.loadArticles(category);
        mainTitleBar.setTitleString(category);
        customPopWindow.dissmiss();
    }

    private void switchArticleListStyle(){
        switch (articleListStyle) {
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, articleList2Fragment).commit();
                articleListStyle = 2;
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, articleListFragment).commit();
                articleListStyle = 1;
                break;
            default:
                break;
        }
    }

    private void setArticleListStyle(){
        switch (articleListStyle){
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, articleListFragment).commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, articleList2Fragment).commit();
                break;
            default:
                break;
        }
    }

}
