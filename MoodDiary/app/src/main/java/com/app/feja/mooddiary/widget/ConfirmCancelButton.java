package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.application.TheApplication;
import com.app.feja.mooddiary.widget.base.TouchListenView;


/**
 * created by fejasible@163.com
 */
public class ConfirmCancelButton extends TouchListenView{

    private Paint paint;
    private String cancelString;
    private String confirmString;
    private boolean confirmEnable = true;

    private OnButtonClickListener onButtonClickListener = new OnButtonClickListener() {
        @Override
        public void onCancelClick() {
            Toast.makeText(TheApplication.getContext(), "cancel", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onConfirmClick() {
            Toast.makeText(TheApplication.getContext(), "confirm", Toast.LENGTH_SHORT).show();
        }
    };

    public ConfirmCancelButton(Context context, int touchZoneNum, @Nullable int[] rectPercents) {
        super(context, touchZoneNum, rectPercents);
        this.init();
    }

    public ConfirmCancelButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public ConfirmCancelButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    public boolean isConfirmEnable() {
        return confirmEnable;
    }

    public void setConfirmEnable(boolean confirmEnable) {
        this.confirmEnable = confirmEnable;
    }

    public OnButtonClickListener getOnButtonClickListener() {
        return onButtonClickListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    private void init(){
        this.paint = new Paint();
        this.cancelString = getResources().getString(R.string.cancel);
        this.confirmString = getResources().getString(R.string.confirm);
        this.setOnItemTouchListener(new OnItemTouchListener() {
            @Override
            public void onItemTouch(int item, Rect touchRect, MotionEvent event) {
                switch (item){
                    case 0:
                        onButtonClickListener.onCancelClick();
                        break;
                    case 2:
                        if(confirmEnable) {
                            onButtonClickListener.onConfirmClick();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void defaultSettings() {
        this.touchZoneNum = 3;
        this.rectPercents = new int[]{335, 50, 335};
        this.orientation = ORIENTATION.HORIZONTAL;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        paint.reset();
        if(getPressRects()[0]){
            paint.setColor(Color.DKGRAY);
            canvas.drawRect(getRects()[0], paint);
        }else{
            paint.setColor(Color.GRAY);
            canvas.drawRect(getRects()[0], paint);
        }
        if(confirmEnable){
            if(getPressRects()[2]){
                paint.setColor(Color.DKGRAY);
                canvas.drawRect(getRects()[2], paint);
            }else{
                paint.setColor(Color.GRAY);
                canvas.drawRect(getRects()[2], paint);
            }
        }
        this.drawCancel(getRects()[0].centerX(), getRects()[0].centerY(), paint, canvas);
        this.drawConfirm(getRects()[2].centerX(), getRects()[2].centerY(), paint, canvas);
    }


    public interface OnButtonClickListener{
        void onCancelClick();
        void onConfirmClick();
    }

    private void drawCancel(int x, int y, Paint paint, Canvas canvas){
        paint.reset();
        paint.setTextSize(TheApplication.getScreenHeight() > TheApplication.getScreenWidth()
                ? this.height/2 : this.width/2);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        drawCenterText(cancelString, x, y, paint, canvas);
    }

    private void drawConfirm(int x, int y, Paint paint, Canvas canvas){
        paint.reset();
        paint.setTextSize(TheApplication.getScreenHeight() > TheApplication.getScreenWidth()
                ? this.height/2 : this.width/2);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        drawCenterText(confirmString, x, y, paint, canvas);
    }

}
