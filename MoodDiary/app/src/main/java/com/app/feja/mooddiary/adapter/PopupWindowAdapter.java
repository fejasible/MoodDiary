package com.app.feja.mooddiary.adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.app.feja.mooddiary.model.entity.TypeEntity;
import com.app.feja.mooddiary.widget.CategoryView;

import java.util.List;

public class PopupWindowAdapter extends RecyclerView.Adapter{

    private List<TypeEntity> data;
    private OnPopupWindowItemClickListener listener;

    public OnPopupWindowItemClickListener getOnPopupWindowItemClickListener() {
        return listener;
    }

    public void setOnPopupWindowItemClickListener(OnPopupWindowItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_category, null, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPopupWindowClick(v.findViewById(R.id.id_category_view));
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.categoryView.setCategoryString(data.get(position).getType());
        viewHolder.categoryView.setBackgroundColor(ApplicationContext.getThemeData().getColor());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setData(List<TypeEntity> data){
        this.data = data;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder{

        CategoryView categoryView;

        ViewHolder(View itemView) {
            super(itemView);
            categoryView = (CategoryView) itemView.findViewById(R.id.id_category_view);
        }
    }

    public interface OnPopupWindowItemClickListener{
        void onPopupWindowClick(View view);
    }

}
