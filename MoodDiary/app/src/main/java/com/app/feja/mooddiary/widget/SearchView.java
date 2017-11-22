package com.app.feja.mooddiary.widget;


import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.ApplicationContext;

public class SearchView extends RelativeLayout{

    private ValueAnimator valueAnimator;
    private Integer targetHeight;
    private Integer saveChangeHeight = 0;
    private EditText editText;
    private OnAnimationListener onAnimationListener = new OnAnimationListener() {
        @Override
        public void onHideAnimationEnd() {

        }

        @Override
        public void onShowAnimationEnd() {

        }
    };
    private boolean currentStateShow = false;

    public SearchView(Context context) {
        super(context);
        this.init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public String getSearchString(){
        return editText.getText().toString();
    }

    public void init(){
        this.setPadding(10, 10, 10, 0);
        this.targetHeight = (int) getResources().getDimension(R.dimen.x70);
        editText = new EditText(getContext());
        editText.setTextSize(this.targetHeight/4);
        editText.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        editText.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_search_view));
        editText.setHint(getResources().getString(R.string.enter_keyword));
        editText.setSingleLine();
        this.addView(editText);
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        editText.addTextChangedListener(textWatcher);
    }

    public OnAnimationListener getOnAnimationListener() {
        return onAnimationListener;
    }

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }

    public Integer getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public void hideWithAnimation(){
        currentStateShow = false;
        valueAnimator = ValueAnimator.ofInt(targetHeight.equals(saveChangeHeight) ? targetHeight : saveChangeHeight, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(!currentStateShow){
                    SearchView.this.getLayoutParams().height = (int) animation.getAnimatedValue();
                    saveChangeHeight = (int) animation.getAnimatedValue();
                    SearchView.this.requestLayout();
                    if(0 == SearchView.this.getLayoutParams().height){
                        onAnimationListener.onHideAnimationEnd();
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                }
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();

    }
    public void showWithAnimation(){
        currentStateShow = true;
        valueAnimator = ValueAnimator.ofInt(saveChangeHeight.equals(0) ? 0 : saveChangeHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if(currentStateShow){
                    SearchView.this.getLayoutParams().height = (int) animation.getAnimatedValue();
                    saveChangeHeight = (int) animation.getAnimatedValue();
                    SearchView.this.requestLayout();
                    if(targetHeight.equals(SearchView.this.getLayoutParams().height)){
                        onAnimationListener.onShowAnimationEnd();
                        editText.requestFocus();
                        editText.findFocus();
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
                    }
                }
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    public interface OnAnimationListener{
        void onHideAnimationEnd();
        void onShowAnimationEnd();
    }

}
