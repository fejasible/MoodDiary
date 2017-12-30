package com.app.feja.mooddiary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.widget.base.BaseView;

/**
 * created by fejasible@163.com
 */
public class PasswordView extends BaseView {

    /** 画笔 */
    private Paint paint;
    /** 颜色 */
    private int backgroundColor = Color.WHITE, themeColor;
    /** 是否点击 */
    private boolean pressDown = false;
    /** 点击位置 */
    private float touchX, touchY;
    /** 数字键盘尺寸 */
    private int dx, dy, keyboardY;
    /** 数字键盘矩形范围 */
    private Rect[] rects = new Rect[12];
    /** 输入的密码 */
    private String password = "";
    /** 输入密码长度限制 */
    private int circleNum = 4;
    /** 输入监听器 */
    private OnEnterFinishListener enterFinishListener;
    private OnHelpClickListener helpClickListener;
    /** 基础字体大小 */
    private int baseTextSize;
    /** “输入密码”文字 */
    private String enterString;

    public PasswordView(Context context) {
        super(context);
        this.init();
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public PasswordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    private void init(){
        enterString = this.getResources().getString(R.string.enter_password);
        themeColor = ContextCompat.getColor(getContext(), R.color.lightSkyBlue);
        this.paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initSize();
    }

    /**
     * 初始化数字键盘高，单位间隔，高度及矩形范围
     */
    private void initSize(){
        keyboardY = this.height * 5/9;
        dx = this.width / 3;
        dy = (this.height - keyboardY) / 4;
        baseTextSize = this.width / 18;
        int y = keyboardY - dy;
        int x = 0;
        for(int i=0; i<rects.length; i++){
            if(i%3 == 0){
                x = 0;
                y += dy;
            }else{
                x += dx;
            }
            rects[i] = new Rect(x, y, x+dx, y+dy);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制背景
        paint.reset();
        paint.setColor(backgroundColor);
        canvas.drawRect(0, 0, this.width, this.height, paint);

        // 绘制“输入密码”文字
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setTextSize(baseTextSize);
        canvas.drawText(enterString, this.width/2 - baseTextSize*enterString.length()/2,
                (this.height - this.keyboardY)/2 + baseTextSize/2, paint);

        // 绘制密码圆圈
        paint.reset();
        paint.setAntiAlias(true);
        int rad = this.width / 50;
        int circleY = (this.height - this.keyboardY)/2 + baseTextSize + rad*2;
        for(int i=0; i<circleNum; i++){
            int circleX = this.width / 2 - (circleNum - 1) * rad * 4 / 2 + i * rad * 4;
            paint.setColor(this.themeColor);
            canvas.drawCircle(circleX, circleY, rad, paint);
            if(i >= password.length()){
                paint.setColor(this.backgroundColor);
                canvas.drawCircle(circleX, circleY, rad * 5/6, paint);
            }
        }

        // 绘制键盘线
        paint.reset();
        paint.setColor(Color.LTGRAY);
        for(int i=0; i<2; i++){
            canvas.drawLine(dx + dx*i, keyboardY, dx + dx*i, this.height, paint);
        }
        for(int i=0; i<4; i++){
            canvas.drawLine(0, keyboardY + i*dy, this.width, keyboardY + i*dy, paint);
        }

        // 点击时背景变化
        paint.reset();
        if(pressDown){
            paint.setColor(Color.LTGRAY);
            for(Rect rect: rects){
                if(rect.contains((int)this.touchX, (int)this.touchY)){
                    // 绘制点击效果
                    canvas.drawRect(rect, paint);
                    break;
                }
            }
        }

        // 绘制数字
        paint.reset();
        paint.setColor(this.themeColor);
        paint.setAntiAlias(true);
        paint.setTextSize(baseTextSize);
        int x0 = dx/2 - baseTextSize/3;
        int y = keyboardY - dy/2 + baseTextSize/3;
        int x = 0;
        for(int i=1; i<10; i++){
            if((i-1)%3 == 0){ x = x0; y += dy; }
            else{ x += dx; }
            canvas.drawText(i+"", x, y, paint);
        }
        canvas.drawText(0+"", x - dx, y + dy, paint);

        // 绘制问号
        paint.reset();
        paint.setColor(this.themeColor);
        paint.setTextSize(baseTextSize * 4/3);
        canvas.drawText("?", dx/2 - baseTextSize/3, this.height - dy/2 + baseTextSize/2, paint);

        // 绘制箭头
        paint.reset();
        paint.setColor(this.themeColor);
        paint.setTextSize(baseTextSize * 4/3);
        canvas.drawText("←", this.width - dx/2 - baseTextSize*2/3, this.height - dy/2 + baseTextSize/3, paint);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.touchX = event.getX();
        this.touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.pressDown = true;
                break;

            case MotionEvent.ACTION_UP:
                this.pressDown = false;
                for(int i=0; i<rects.length; i++){
                    if(rects[i].contains((int)this.touchX, (int)this.touchY)){
                        // 判断点击操作
                        if(i == 9){//点击了最左下
                            this.helpClickListener.onHelpClick();
                        }else if(i == 11){// 点击了箭头
                            if(password.length() > 0) {
                                password = password.substring(0, password.length() - 1);
                            }
                        }else{// 点击了数字
                            if(i == 10){
                                password += 0;
                            }else{
                                password += i+1;
                            }
                            if(password.length() == circleNum){
                                this.enterFinishListener.enterFinish(password);// 密码录入结束
                            }
                        }
                        break;
                    }
                }
                break;
        }
        invalidate();
        return true;
    }

    public void setOnEnterFinishListener(OnEnterFinishListener listener){
        this.enterFinishListener = listener;
    }

    public void setOnHelpClickListener(OnHelpClickListener listener){
        this.helpClickListener = listener;
    }

    public void setViewBackgroundColor(int color){
        this.backgroundColor = color;
    }

    public void setThemeColor(int color){
        this.themeColor = color;
    }

    /**
     * 设置密码显示的位数
     * @param count 密码位数
     */
    public void setNumberCount(int count){
        this.circleNum = count;
    }

    public int getNumberCount(){
        return this.circleNum;
    }

    /**
     * 当密码输入完毕时的监听操作
     */
    public interface OnEnterFinishListener{
        void enterFinish(String password);
    }

    public String getEnterString() {
        return enterString;
    }

    public void setEnterString(String enterString) {
        this.enterString = enterString;
    }

    /**
     * 当帮助按钮被点击时的监听操作
     */
    public interface OnHelpClickListener{
        void onHelpClick();
    }

    public void clearPassword(){
        this.password = "";
    }


}
