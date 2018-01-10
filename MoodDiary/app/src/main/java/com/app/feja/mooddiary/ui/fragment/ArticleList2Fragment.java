package com.app.feja.mooddiary.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.constant.CONSTANT;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.ui.activity.ArticleBrowseActivity;
import com.app.feja.mooddiary.ui.view.ArticleListView;
import com.app.feja.mooddiary.widget.browse.ArticleInformationView;
import com.app.feja.mooddiary.widget.curlpage.CurlPage;
import com.app.feja.mooddiary.widget.curlpage.CurlView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * created by fejasible@163.com
 */
public class ArticleList2Fragment extends Fragment implements ArticleListView {

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
        curlView.setOnClickListener(new OnCurViewClickListener());
        curlView.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.whiteSmoke));
        if (diaryEntities != null) {
            onLoadArticles(diaryEntities);
        }
        return view;
    }

    public void setDiaryEntities(List<DiaryEntity> diaryEntities) {
        this.diaryEntities = diaryEntities;
    }

    @Override
    public void onLoadArticles(List<DiaryEntity> diaryEntities) {
        if (diaryEntities == null) {
            diaryEntities = new ArrayList<>();
        }
        this.diaryEntities = diaryEntities;
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

    /**
     * page内容管理
     */
    private class PageProvider implements CurlView.PageProvider {
        @Override
        public int getPageCount() {
            return diaryEntities != null ? diaryEntities.size() : 0;
        }


        private Bitmap loadBitmap(int width, int height, int index) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            View view = parseContentToView(diaryEntities.get(index), width, height);
            if (view == null) {
                return bitmap;
            } else {
                int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
                view.measure(measuredWidth, measuredHeight);
                view.layout(0, 0, width, height);
                view.draw(canvas);
                return bitmap;
            }
        }
        private Bitmap loadBackBitmap(int width, int height, int index) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);

            View view = parseContentToView(diaryEntities.get(index), width, height);
            if (view != null) {
                int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST);
                view.measure(measuredWidth, measuredHeight);
                view.layout(0, 0, width, height);
                view.draw(canvas);
            }

            Paint paint = new Paint();
            paint.setColor(TheApplication.getThemeData().getColor());
            paint.setAlpha(200);
            Rect rect = new Rect(0, 0, width, height);
            canvas.drawRect(rect, paint);

            return bitmap;
        }

        @Override
        public void updatePage(CurlPage page, int width, int height, int index) {
            page.setTexture(loadBitmap(width, height, index), CurlPage.SIDE_FRONT);
            page.setTexture(loadBackBitmap(width, height, index), CurlPage.SIDE_BACK);
        }

        private View parseContentToView(DiaryEntity diaryEntity, int width, int height) {
            // view
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_book_article, null, false);

            // 分类
            TextView categoryView = (TextView) view.findViewById(R.id.id_article_browse_category);
            categoryView.setText(diaryEntity.getType().getType());
            categoryView.setTextSize(diaryEntity.getTextSize() * 2 / 3);
            categoryView.setTextColor(TheApplication.getThemeData().getColor());

            // 日期等信息
            ArticleInformationView infoView = (ArticleInformationView) view.findViewById(R.id.article_browse_info_bar);
            infoView.setDiaryEntity(diaryEntity);

            // 内容
            EditText editText = (EditText) view.findViewById(R.id.id_browse_edit_text);
            editText.setTextSize(diaryEntity.getTextSize() * 2 / 3);
            editText.setText("");
            String content = diaryEntity.getContent();
            String[] split = content.split(CONSTANT.EDITABLE_IMAGE_TAG_START + ".*?"
                    + CONSTANT.EDITABLE_IMAGE_TAG_END);
            if (split.length == 0) {
                return null;
            }
            Pattern pattern = Pattern.compile(CONSTANT.EDITABLE_IMAGE_TAG_START + "(.*?)" +
                    CONSTANT.EDITABLE_IMAGE_TAG_END);
            Matcher matcher = pattern.matcher(content);
            int i = 0;
            editText.getEditableText().append(split[0]);
            while (matcher.find()) {
                String path = matcher.group(1);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                if (bitmap != null) {
                    path = CONSTANT.EDITABLE_IMAGE_TAG_START + path + CONSTANT.EDITABLE_IMAGE_TAG_END;
                    SpannableString spannableString = new SpannableString(path);
                    if (bitmap.getWidth() > width) {
                        try {
                            bitmap = Bitmap.createScaledBitmap(
                                    bitmap,
                                    width - 20,
                                    bitmap.getHeight() * (width - 20) / bitmap.getWidth(),
                                    false
                            );
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                    ImageSpan imageSpan = new ImageSpan(getActivity(), bitmap);
                    spannableString.setSpan(imageSpan, 0, path.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    editText.getEditableText().append(spannableString);
                } else {
                    editText.getEditableText().append(getString(R.string.image));
                }
                editText.getEditableText().append(split[i + 1]);
                i++;
            }
            return view;
        }
    }

    private class SizeChangedObserver implements CurlView.SizeChangedObserver {

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

    private class OnCurViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (diaryEntities == null || diaryEntities.size() == 0) {
                return;
            }
            CurlView curlView = (CurlView) v;
            int currentIndex = curlView.getCurrentIndex();
            if(currentIndex < 0 || currentIndex >= diaryEntities.size()){
                return;
            }
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(DiaryEntity.BUNDLE_NAME, diaryEntities.get(currentIndex));
            intent.putExtras(bundle);
            intent.setClass(getActivity(), ArticleBrowseActivity.class);
            getActivity().startActivity(intent);

        }
    }

}
