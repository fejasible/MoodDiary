package com.app.feja.mooddiary.adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.setting.ThemeSelectBar;
import com.google.gson.Gson;

import java.util.List;

/**
 * created by fejasible@163.com
 */
public class ThemeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Data> data;
    private OnItemClickListener onItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_theme_select_bar, null, false);
        ThemeSelectBar themeSelectBar = (ThemeSelectBar) view.findViewById(R.id.id_theme_select_view);
        themeSelectBar.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.themeSelectBar.setColor(data.get(position).color);
        viewHolder.themeSelectBar.setText(data.get(position).text);
        viewHolder.themeSelectBar.setTag(position);
        viewHolder.themeSelectBar.setSelect(data.get(position).select);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ThemeSelectBar themeSelectBar;
        ViewHolder(View itemView) {
            super(itemView);
            themeSelectBar = (ThemeSelectBar) itemView.findViewById(R.id.id_theme_select_view);
        }
    }

    public static class Data{
        public Data() {
        }

        public Data(int color, String text) {
            this.color = color;
            this.text = text;
        }

        private int color = Color.BLACK;
        private String text = "————";
        private boolean select = false;

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Data) {
                Data data = (Data) obj;
                return this.getColor() == data.getColor();
            }
            return false;
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        ThemeSelectBar themeSelectBar = (ThemeSelectBar) v;
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(themeSelectBar, (int)themeSelectBar.getTag());
        }
    }
}
