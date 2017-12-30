package com.app.feja.mooddiary.factory;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.base.BaseView;

import java.util.Iterator;

/**
 * created by fejasible@163.com
 */
public class BubbleFactory implements Iterator<TextView> {

    private static char[] data = "fejasible@163.com".toCharArray();
    private int i = 0;
    private Context context;

    public BubbleFactory(Context context) {
        this.context = context;
    }

    @Override
    public boolean hasNext() {
        return i < data.length;
    }

    @Override
    public TextView next() {
        TextView textView = new TextView(context);
        if(i<data.length){
            textView.setText(String.valueOf(data[i]));
            textView.setTextColor(TheApplication.getThemeData().getColor());
            i++;
            return textView;
        }else{
            return null;
        }
    }



}
