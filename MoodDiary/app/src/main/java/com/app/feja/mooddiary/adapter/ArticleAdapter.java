package com.app.feja.mooddiary.adapter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.ui.activity.ArticleBrowseActivity;
import com.app.feja.mooddiary.widget.ArticleView;

import java.util.List;

/**
 * created by fejasible@163.com
 */
public class ArticleAdapter extends RecyclerView.Adapter implements ArticleView.OnArticleViewClickListener {

    private List<DiaryEntity> data;

    private Activity activity;

    public ArticleAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_article_view, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.articleView.setDiaryEntity(data.get(position));
        viewHolder.articleView.setThemeColor(TheApplication.getThemeData().getColor());
        viewHolder.articleView.setListener(this);
    }

    @Override
    public void onFaceClick(ArticleView articleView) {

    }

    @Override
    public void onDateClick(ArticleView articleView) {

    }

    @Override
    public void onAbstractClick(ArticleView articleView) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DiaryEntity.BUNDLE_NAME, articleView.getDiaryEntity());
        intent.putExtras(bundle);
        intent.setClass(activity.getApplicationContext(), ArticleBrowseActivity.class);
        activity.startActivity(intent);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        ArticleView articleView;

        ViewHolder(View itemView) {
            super(itemView);
            articleView = (ArticleView) itemView.findViewById(R.id.article_view);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public List<DiaryEntity> getData() {
        return data;
    }

    public void setData(List<DiaryEntity> data) {
        this.data = data;
    }
}
