package com.app.feja.mooddiary.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.model.entity.DiaryEntity;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.presenter.ArticleListPresenter;
import com.app.feja.mooddiary.widget.CategoryView;

import java.util.List;
import java.util.Random;

import cn.qqtheme.framework.picker.OptionPicker;

public class CategoryAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private OptionPicker categoryPicker;
    private ArticleListPresenter articleListPresenter;
    private Activity activity;

    private Random random = new Random();

    private List<TypeEntity> typeEntities;
    private List<DiaryEntity> diaryEntities;

    public CategoryAdapter(ArticleListPresenter articleListPresenter, Activity activity) {
        this.articleListPresenter = articleListPresenter;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RelativeLayout relativeLayout = (RelativeLayout)
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categroy_view, null, false);
        relativeLayout.setOnClickListener(this);
        return new ViewHolder(relativeLayout);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        int count = 0;
        for(DiaryEntity diaryEntity: diaryEntities){
            if(diaryEntity.getType().getId().equals(typeEntities.get(position).getId())){
                count++ ;
            }
        }
        viewHolder.textView.setText(typeEntities.get(position).getType());
        viewHolder.textViewCount.setText("(" + count + ")");
    }

    @Override
    public int getItemCount() {
        return typeEntities == null ? 0 : typeEntities.size();
    }

    @Override
    public void onClick(View v) {
        final RelativeLayout relativeLayout = (RelativeLayout) v;
        final TextView textView = (TextView) relativeLayout.findViewById(R.id.id_category_text_view);
        if(categoryPicker == null){
            categoryPicker = new OptionPicker(activity, new String[]{
                    activity.getString(R.string.delete_category_only),
                    activity.getString(R.string.delete_category_and_diary)
            });
        }
        categoryPicker.setTitleText(activity.getString(R.string.delete_category_)+textView.getText().toString());
        categoryPicker.setTitleTextColor(Color.GRAY);
        categoryPicker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                if(textView.getText().toString().equals(activity.getString(R.string.no_sort))){
                    Toast.makeText(activity.getApplicationContext(),
                            activity.getString(R.string.delete_category_can_not_be_done), Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (index){
                    case 0:
                        articleListPresenter.deleteTypeOnly(textView.getText().toString());
                        articleListPresenter.loadCategories();
                        break;
                    case 1:
                        articleListPresenter.deleteTypeAndDiary(textView.getText().toString());
                        articleListPresenter.loadCategories();
                        break;
                    default:
                        break;
                }
            }
        });
        categoryPicker.show();
    }


    private class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView textViewCount;

        ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.id_category_text_view);
            textViewCount = (TextView) itemView.findViewById(R.id.id_category_text_view_count);
        }
    }


    public List<TypeEntity> getTypeEntities() {
        return typeEntities;
    }

    public void setTypeEntities(List<TypeEntity> typeEntities) {
        this.typeEntities = typeEntities;
    }

    public List<DiaryEntity> getDiaryEntities() {
        return diaryEntities;
    }

    public void setDiaryEntities(List<DiaryEntity> diaryEntities) {
        this.diaryEntities = diaryEntities;
    }
}
