package com.app.feja.mooddiary.ui.fragment;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.curlpage.CurlPage;
import com.app.feja.mooddiary.widget.curlpage.CurlView;

import java.util.ArrayList;
import java.util.List;


/**
 * created by fejasible@163.com
 */
public class ArticleList2Fragment extends Fragment implements ArticleListView{

    private CurlView curlView;

    private List<DiaryEntity> diaryEntities;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list_2, container, false);

        curlView = (CurlView) view.findViewById(R.id.id_curl_view);
        curlView.setPageProvider(new PageProvider());
        curlView.setSizeChangedObserver(new SizeChangedObserver());
        curlView.setCurrentIndex(0);
        curlView.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.whiteSmoke));
        if(diaryEntities != null){
            onLoadArticles(diaryEntities);
        }
        return view;
    }

    public void setDiaryEntities(List<DiaryEntity> diaryEntities) {
        this.diaryEntities = diaryEntities;
    }

    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {
        if(diaryEntities == null){
            diaryEntities = new ArrayList<>();
        }
        this.diaryEntities = diaryEntities;
        Log.e("----", "on load articles");
    }


    @Override
    public void onPause() {
        super.onPause();
        curlView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        curlView.onResume();
    }

    private class PageProvider implements CurlView.PageProvider{
        @Override
        public int getPageCount() {
            return diaryEntities != null ? diaryEntities.size() : 0;
        }

        private Bitmap loadBitmap(int width, int height, int index) {
            Log.e("------", "width:" + width + " height:" + height + " index:" + index);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            return bitmap;
        }

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {
            page.setTexture(loadBitmap(width, height, index), CurlPage.SIDE_FRONT);
            page.setColor(Color.WHITE, CurlPage.SIDE_BACK);
        }
    }

    private class SizeChangedObserver implements CurlView.SizeChangedObserver{

        @Override
        public void onSizeChanged(int width, int height) {
            if (width > height) {
                curlView.setViewMode(CurlView.SHOW_TWO_PAGES);
                curlView.setMargins(.1f, .05f, .1f, .05f);
            } else {
                curlView.setViewMode(CurlView.SHOW_ONE_PAGE);
                curlView.setMargins(.1f, .1f, .1f, .1f);
            }
        }
    }

}
