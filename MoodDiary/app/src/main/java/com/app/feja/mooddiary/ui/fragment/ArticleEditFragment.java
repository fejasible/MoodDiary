package com.app.feja.mooddiary.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.PopupWindowAdapter;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.constant.CONSTANT;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleEditPresenter;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.ui.activity.CategoryActivity;
import com.app.feja.mooddiary.ui.view.ArticleEditView;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.ArticleEditTitleBar;
import com.app.feja.mooddiary.widget.CategoryView;
import com.app.feja.mooddiary.widget.EditToolBar;
import com.app.feja.mooddiary.widget.base.TouchListenView;
import com.app.feja.mooddiary.widget.edit.FaceChooseView;
import com.app.feja.mooddiary.widget.edit.MyRangeSeekBar;
import com.app.feja.mooddiary.widget.edit.WeatherView;
import com.example.zhouwei.library.CustomPopWindow;
import com.jaygoo.widget.RangeSeekBar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.qqtheme.framework.picker.DateTimePicker;

/**
 * created by fejasible@163.com
 */
public class ArticleEditFragment extends Fragment implements ArticleEditView,
        ArticleEditTitleBar.OnTitleBarClickListener, ArticleListView, View.OnClickListener,
        TouchListenView.OnItemTouchListener, View.OnTouchListener, RangeSeekBar.OnRangeChangedListener, DialogInterface.OnDismissListener, PopupWindowAdapter.OnPopupWindowItemClickListener {

    public static final int IMAGE_PICKER = 0;

    private ArticleEditTitleBar titleBar;
    private LinearLayout view;
    private EditText editText;
    private ArticleEditPresenter articleEditPresenter;
    private ArticleListPresenter articleListPresenter;
    private LinearLayout popupLayout;
    private CustomPopWindow customPopWindow;
    private DiaryEntity diaryEntity;
    private EditToolBar editToolBar;
    private ScrollView scrollView;
    private View currentView;
    private MyRangeSeekBar rangeSeekBar;
    private DateTimePicker dateTimePicker;
    private FaceChooseView faceChooseView;
    private WeatherView weatherView;
    private PopupWindowAdapter popupWindowAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.initView(inflater, container, savedInstanceState);
        this.initPresenter();
        this.initData();
        this.initTools();
        this.initListener();
        return view;
    }

    /**
     * 初始化Layout中的View
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    private void initView(LayoutInflater inflater, ViewGroup container,
                          Bundle savedInstanceState){
        view = (LinearLayout) inflater.inflate(R.layout.fragment_article_edit, container, false);
        titleBar = (ArticleEditTitleBar) view.findViewById(R.id.article_edit_title_bar);
        editText = (EditText) view.findViewById(R.id.editText);
        scrollView = (ScrollView) view.findViewById(R.id.id_edit_scroll_view);
        editToolBar = (EditToolBar) view.findViewById(R.id.id_edit_tool_bar);
    }

    /**
     * 初始化presenter
     */
    private void initPresenter(){
        articleEditPresenter = new ArticleEditPresenter(this);
        articleListPresenter = new ArticleListPresenter(this);
    }

    /**
     * 初始化数据
     */
    private void initData(){
        Serializable s = getActivity().getIntent().getSerializableExtra(DiaryEntity.BUNDLE_NAME);
        if(s != null){
            this.diaryEntity = (DiaryEntity) s;
        }else{
            this.diaryEntity = new DiaryEntity();
        }
        articleEditPresenter.loadArticle(diaryEntity);
        editToolBar.setFace(this.diaryEntity.getMood());
        editToolBar.setWeatherModel(TheApplication.getWeatherModel());
    }

    /**
     * 初始化edit tools
     */
    private void initTools(){
        //文字字号调整
        this.rangeSeekBar = new MyRangeSeekBar(getActivity());
        this.rangeSeekBar.setSeekBarMode(1);
        this.rangeSeekBar.setRange(8, 60);
        this.rangeSeekBar.setLineColor(ContextCompat.getColor(getActivity(), R.color.lightGrey),
                TheApplication.getThemeData().getColor());
        this.rangeSeekBar.setValue(diaryEntity.getTextSize());


        //插入图片选择
        dateTimePicker = new DateTimePicker(getActivity(), DateTimePicker.HOUR_24);
        DateTime now = new DateTime();
        DateTime diaryDate = new DateTime(diaryEntity.getCreateTime());
        dateTimePicker.setDateRangeEnd(now.getYear(), now.getMonth(), now.getDay());
        dateTimePicker.setDateRangeStart(2000, 1, 1);
        dateTimePicker.setSelectedItem(diaryDate.getYear(), diaryDate.getMonth(),
                diaryDate.getDay(), diaryDate.getHour(), diaryDate.getMinute());

        //表情选择
        this.faceChooseView = new FaceChooseView(getActivity(), 5, null);
        this.faceChooseView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                TheApplication.getScreenHeight() / 6
        ));
        this.faceChooseView.setOnItemTouchListener(new TouchListenView.OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                editToolBar.setFace(item);
                faceChooseView.setCurrentFace(item);
            }
        });

        //天气
        this.weatherView = new WeatherView(getActivity());
        this.weatherView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                TheApplication.getScreenHeight() / 12
        ));
        this.weatherView.setWeatherModel(editToolBar.getWeatherModel());
    }

    /**
     * 初始化各种监听器
     */
    private void initListener(){
        titleBar.setOnTitleBarClickListener(this);
        editToolBar.setOnItemTouchListener(this);
        scrollView.setOnTouchListener(this);
        rangeSeekBar.setOnRangeChangedListener(this);
        dateTimePicker.setOnDismissListener(this);
    }

    public DiaryEntity getDiary(){
        return diaryEntity;
    }

    /**
     * 加载日记
     * @param diaryEntity
     */
    @Override
    public void onLoadArticle(DiaryEntity diaryEntity) {
        editText.setTextSize(diaryEntity.getTextSize());

        editText.setText("");
        String content = diaryEntity.getContent();
        String[] split = content.split(CONSTANT.EDITABLE_IMAGE_TAG_START + ".*?"
                + CONSTANT.EDITABLE_IMAGE_TAG_END);
        Pattern pattern = Pattern.compile(CONSTANT.EDITABLE_IMAGE_TAG_START + "(.*?)" +
                CONSTANT.EDITABLE_IMAGE_TAG_END);
        Matcher matcher = pattern.matcher(content);
        int i = 0;
        editText.getEditableText().append(split[0]);
        while(matcher.find()){
            String path = matcher.group(1);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            if(bitmap != null){
                path = CONSTANT.EDITABLE_IMAGE_TAG_START + path + CONSTANT.EDITABLE_IMAGE_TAG_END;
                SpannableString spannableString = new SpannableString(path);
                if(bitmap.getWidth() > TheApplication.getScreenWidth()){
                    try {
                        bitmap = Bitmap.createScaledBitmap(
                                bitmap,
                                TheApplication.getScreenWidth()-20,
                                bitmap.getHeight()*(TheApplication.getScreenWidth()-20)/bitmap.getWidth(),
                                false
                        );
                    }catch (IllegalArgumentException e){
                        e.printStackTrace();
                    }
                }
                ImageSpan imageSpan = new ImageSpan(getActivity(), bitmap);
                spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                editText.getEditableText().append(spannableString);
            }else{
                editText.getEditableText().append(getString(R.string.image));
            }
            editText.getEditableText().append(split[i+1]);
            i++;
        }

        editText.setSelection(editText.getEditableText().length());
        titleBar.setDiaryEntity(diaryEntity);
    }

    /**
     * 取消
     */
    @Override
    public void onCancelClick() {
        getActivity().onBackPressed();
    }

    /**
     * 显示分类
     * @param diaryEntity
     */
    @Override
    public void onCategoryClick(DiaryEntity diaryEntity) {
        // 获取分类信息
        List<TypeEntity> typeEntities = articleListPresenter.getAllTypes();

        if(popupLayout == null){
            popupLayout = (LinearLayout) LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_category_popup_window, null);
            View view = popupLayout.findViewById(R.id.id_popup_all_category_view);
            popupLayout.removeView(view);

            //编辑我的分类
            CategoryView editView = (CategoryView) popupLayout.findViewById(R.id.id_popup_edit_category_view);
            editView.setCategoryString(getResources().getString(R.string.edit_my_category));
            editView.setOnClickListener(this);

            RecyclerView recyclerView = (RecyclerView) popupLayout.findViewById(R.id.id_popup_recycler_view);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            popupWindowAdapter = new PopupWindowAdapter();
            popupWindowAdapter.setOnPopupWindowItemClickListener(this);
            recyclerView.setAdapter(popupWindowAdapter);
        }

        popupWindowAdapter.setData(typeEntities);
        popupWindowAdapter.notifyDataSetChanged();

        if(customPopWindow == null){
            customPopWindow = new CustomPopWindow.PopupWindowBuilder(getActivity())
                    .setView(popupLayout)
                    .size(TheApplication.getScreenWidth() * 3 / 5, TheApplication.getScreenHeight() / 3)
                    .create();
        }
        customPopWindow.showAsDropDown(titleBar, TheApplication.getScreenWidth() / 5, 5);
    }

    /**
     * 保存
     * @param diaryEntity
     */
    @Override
    public void onSaveClick(DiaryEntity diaryEntity) {
        diaryEntity.setType(this.diaryEntity.getType());
        diaryEntity.setContent(editText.getText().toString());
        diaryEntity.setTextSize((int)rangeSeekBar.getCurrentRange()[0]);
        DateTime selectDateTime = new DateTime(
                Integer.valueOf(dateTimePicker.getSelectedYear()),
                Integer.valueOf(dateTimePicker.getSelectedMonth()),
                Integer.valueOf(dateTimePicker.getSelectedDay()),
                Integer.valueOf(dateTimePicker.getSelectedHour()),
                Integer.valueOf(dateTimePicker.getSelectedMinute()),
                0
        );
        diaryEntity.setCreateTime(selectDateTime.toDate());
        diaryEntity.setMood(editToolBar.getFace());

        articleEditPresenter.editArticle(diaryEntity);
        getActivity().onBackPressed();
    }

    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {

    }

    /**
     * Pop Window
     * @param v
     */
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

    /**
     * Edit Tools
     * @param item 第item个点击可区域，从0开始
     * @param touchRect 第i个点击区域rect
     * @param event 点击事件
     */
    @Override
    public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
        if(item == 4){
            return ;
        }
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        if(currentView != null){
            view.removeView(currentView);
        }
        switch (item){
            case 0:
                view.addView(this.rangeSeekBar);
                currentView = this.rangeSeekBar;
                editToolBar.setStatus(EditToolBar.UP);
                break;
            case 1:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
            case 2:
                dateTimePicker.show();
                break;
            case 3:
                view.addView(this.faceChooseView);
                currentView = this.faceChooseView;
                editToolBar.setStatus(EditToolBar.UP);
                break;
            case 5:
                if(editToolBar.getStatus() == EditToolBar.UP){
                    if(currentView != null){
                        view.removeView(currentView);
                    }
                    editToolBar.setStatus(EditToolBar.DOWN);
                }else if(editToolBar.getStatus() == EditToolBar.DOWN){
                    if(currentView != null){
                        view.addView(currentView);
                    }else{
                        editText.requestFocus();
                        editText.findFocus();
                        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                    }
                    editToolBar.setStatus(EditToolBar.UP);
                }
                break;
            default:
                currentView = null;
                break;
        }
    }

    /**
     * 点击ScrollView的响应
     */
    private long timePassed;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                timePassed = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if(System.currentTimeMillis() - timePassed < 100){
                    editText.requestFocus();
                    editText.findFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                    editToolBar.setStatus(EditToolBar.UP);
                    if(currentView != null){
                        view.removeView(currentView);
                        currentView = null;
                    }
                }
                timePassed = 0;
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 加载图片后返回响应
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for(ImageItem imageItem: images){
                    this.appendImage(imageItem, editText);
                }
            }
        }
        editToolBar.setStatus(EditToolBar.DOWN);
    }

    private void appendImage(ImageItem imageItem, EditText editText){
        int index = editText.getSelectionStart();
        SpannableString newLine = new SpannableString("\n");
        editText.getEditableText().insert(index, newLine);
        String path = CONSTANT.EDITABLE_IMAGE_TAG_START + imageItem.path + CONSTANT.EDITABLE_IMAGE_TAG_END;
        SpannableString spannableString = new SpannableString(path);
        Bitmap bitmap = BitmapFactory.decodeFile(imageItem.path);
        if(bitmap.getWidth() > TheApplication.getScreenWidth()){
            bitmap = Bitmap.createScaledBitmap(bitmap, editText.getWidth(),
                    bitmap.getHeight()*(editText.getWidth())/bitmap.getWidth(), false);
        }
        ImageSpan imageSpan = new ImageSpan(getActivity(), bitmap);
        spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (index < 0 || index >= editText.getEditableText().length()) {
            editText.getEditableText().append(spannableString);
        } else {
            editText.getEditableText().insert(index, spannableString);
        }

    }

    @Override
    public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
        editText.setTextSize(view.getCurrentRange()[0]);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        editToolBar.setStatus(EditToolBar.DOWN);
    }

    @Override
    public void onPopupWindowClick(View view) {
        String category = ((CategoryView)view).getCategoryString();
        diaryEntity.setType(articleListPresenter.getType(category));
        titleBar.setDiaryEntity(diaryEntity);
        customPopWindow.dissmiss();
    }
}
