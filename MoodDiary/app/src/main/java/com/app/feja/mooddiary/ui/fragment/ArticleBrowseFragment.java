package com.app.feja.mooddiary.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.constant.CONSTANT;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.presenter.ArticleBrowsePresenter;
import com.app.feja.mooddiary.ui.activity.ArticleEditActivity;
import com.app.feja.mooddiary.ui.view.ArticleView;
import com.app.feja.mooddiary.widget.browse.ArticleBrowseTitleBar;
import com.app.feja.mooddiary.widget.browse.ArticleInformationView;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArticleBrowseFragment extends Fragment implements ArticleView{

    private View view;
    private ArticleBrowseTitleBar titleBar;
    private DiaryEntity diaryEntity;
    private EditText editText;
    private ArticleInformationView articleInformationView;
    private ArticleBrowsePresenter articleBrowsePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_article_browse, container, false);
        titleBar = (ArticleBrowseTitleBar) view.findViewById(R.id.article_browse_title_bar);
        editText = (EditText) view.findViewById(R.id.id_browse_edit_text);
        articleInformationView = (ArticleInformationView) view.findViewById(R.id.article_browse_info_bar);
        this.articleBrowsePresenter = new ArticleBrowsePresenter(this);

        Serializable serializable = getActivity().getIntent().getSerializableExtra(DiaryEntity.BUNDLE_NAME);
        if(serializable == null){
            diaryEntity = new DiaryEntity();
        }else{
            diaryEntity = (DiaryEntity) serializable;
        }

        titleBar.setOnTitleBarClickListener(new ArticleBrowseTitleBar.OnTitleBarClickListener() {
            @Override
            public void onBackClick() {
                getActivity().onBackPressed();
            }

            @Override
            public void onCategoryClick() {

            }

            @Override
            public void onGarbageClick() {
                articleBrowsePresenter.deleteArticle(diaryEntity);
                getActivity().onBackPressed();
            }

            @Override
            public void onPenClick() {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(DiaryEntity.BUNDLE_NAME, diaryEntity);
                intent.putExtras(bundle);
                intent.setClass(getActivity().getApplicationContext(), ArticleEditActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onLoadArticle(DiaryEntity diaryEntity) {
        titleBar.setDiaryEntity(diaryEntity);
        articleInformationView.setDiaryEntity(diaryEntity);
        editText.setTextSize(diaryEntity.getTextSize());

        editText.setText("");
        String content = diaryEntity.getContent();
        String[] split = content.split(CONSTANT.EDITABLE_IMAGE_TAG_START + ".*?"
                + CONSTANT.EDITABLE_IMAGE_TAG_END);
        if(split.length == 0){
            return;
        }
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
                if(bitmap.getWidth() > ApplicationContext.getScreenWidth()){
                    try {
                        bitmap = Bitmap.createScaledBitmap(
                                bitmap,
                                ApplicationContext.getScreenWidth()-20,
                                bitmap.getHeight()*(ApplicationContext.getScreenWidth()-20)/bitmap.getWidth(),
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
    }

    @Override
    public void onResume() {
        articleBrowsePresenter.loadArticle(diaryEntity);
        super.onResume();
    }
}
