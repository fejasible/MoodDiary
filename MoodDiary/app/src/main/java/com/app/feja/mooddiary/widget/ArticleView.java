package com.app.feja.mooddiary.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.constant.FACE;
import com.app.feja.mooddiary.util.DateTime;
import com.app.feja.mooddiary.widget.base.BaseView;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;


public class ArticleView extends BaseView {

    private Paint paint;
    private TextPaint textPaint;

    private int themeColor, backgroundColor;

    private float touchX, touchY;

    private Rect abstractRect = new Rect();
    private Rect dateRect = new Rect();
    private Rect faceRect = new Rect();

    private boolean pressAbstract = false;
    private boolean pressDate = false;
    private boolean pressFace = false;

    private OnArticleViewClickListener listener;

    private FACE face = FACE.CALM;

    private DateTime today = new DateTime();
    private String articleAbstract = "欢乐，悲伤，难过，喜悦，都留在过去\n明天将会是新的冒险\n心情日记，记录心情";

    private final String ARTICLE_VIEW_ID = UUID.randomUUID().toString();

    public ArticleView(Context context) {
    super(context);
    this.init();
    }

    public ArticleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public ArticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public void setToday(DateTime today){
        this.today = today;
    }

    public DateTime getToday(){
        return this.today;
    }

    public void setArticleAbstract(String articleAbstract){
        this.articleAbstract = articleAbstract;
    }

    public String getArticleAbstract(){
        return this.articleAbstract;
    }

    public FACE getFace() {
        return face;
    }

    public void setFace(FACE face) {
        this.face = face;
    }

    public OnArticleViewClickListener getListener() {
        return listener;
    }

    public void setListener(OnArticleViewClickListener listener) {
        this.listener = listener;
    }

    public String getARTICLE_VIEW_ID() {
        return ARTICLE_VIEW_ID;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.abstractRect.set(this.width/4+this.width/20, 0, this.width, this.height);
        this.dateRect.set(0, 0, this.width/4, this.height);
        int rad = this.width < this.height ? this.width/10 : this.height/10;
        this.faceRect.set(this.width/4-rad, this.height/3-rad, this.width/4+rad, this.height/3+rad);
    }

    @Override
    public void onDraw(Canvas canvas){
        paint.reset();
        paint.setColor(themeColor);
        canvas.drawLine(this.width/4, 0, this.width/4, this.height, paint);
        int rad = this.width < this.height ? this.width/10 : this.height/10;

        this.drawFace(this.width/4, this.height/3, rad, face, paint, canvas);
        this.drawDate(0, 0, this.width/4, this.height, paint, canvas);
        this.drawTextBoard(this.width/4+this.width/20, 0, this.width, this.height, paint, canvas);
    }

    private void init(){
        this.paint = new Paint();
        this.textPaint = new TextPaint();
        this.themeColor =  ContextCompat.getColor(getContext(), R.color.lightSkyBlue);
        this.backgroundColor = ContextCompat.getColor(getContext(), R.color.whiteMostly);

        listener = new OnArticleViewClickListener() {
            @Override
            public void onFaceClick(String s) {
                Toast.makeText(getContext(), "face", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateClick(String s) {
                Toast.makeText(getContext(), "date", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAbstractClick(String s) {
                Toast.makeText(getContext(), "abstract", Toast.LENGTH_SHORT).show();
            }
        };
    }


    private void drawFace(int x, int y, int rad, FACE face, Paint paint, Canvas canvas){
        paint.reset();
        paint.setAntiAlias(true);

        RectF rectLeft = new RectF(x-rad*2/3, y-rad/2, x-rad/3, y);
        RectF rectRight = new RectF(x+rad/3, y-rad/2, x+rad*2/3, y);
        RectF rectBottom = new RectF(x-rad/3, y, x+rad/3, y+rad/2);

        if(pressFace){
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(backgroundColor);
        }
        canvas.drawCircle(x, y, rad, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(themeColor);
        paint.setStrokeWidth(2.0f);
        canvas.drawCircle(x, y, rad, paint);
        paint.setColor(themeColor);
        switch(face){
            case MIRTHFUL:
//                canvas.drawBitmap(mirthfulFace, x-mirthfulFace.getWidth()/2,
//                        y-mirthfulFace.getHeight()/2, paint);
                canvas.drawArc(rectLeft, 180, 180, false, paint);
                canvas.drawArc(rectRight, 180, 180, false, paint);
                canvas.drawArc(rectBottom, 0, 180, false, paint);
                break;
            case SMILING:
                canvas.drawLine(x-rad*2/3, y-rad/4, x-rad/3, y-rad/4, paint);
                canvas.drawLine(x+rad/3, y-rad/4, x+rad*2/3, y-rad/4, paint);
                canvas.drawArc(rectBottom, 0, 180, false, paint);
                break;
            case CALM:
                canvas.drawLine(x-rad*2/3, y-rad/4, x-rad/3, y-rad/4, paint);
                canvas.drawLine(x+rad/3, y-rad/4, x+rad*2/3, y-rad/4, paint);
                canvas.drawLine(x-rad/4, y+rad/3, x+rad/4, y+rad/3, paint);
                break;
            case DISAPPOINTED:
                canvas.drawArc(rectLeft, 0, 180, false, paint);
                canvas.drawArc(rectRight, 0, 180, false, paint);
                canvas.drawLine(x-rad/4, y+rad/2, x+rad/4, y+rad/2, paint);
                break;
            case SAD:
                rectLeft.offset(0, -rad/8);
                rectRight.offset(0, -rad/8);
                rectBottom.offset(0, rad/4);
                canvas.drawArc(rectLeft, 0, 180, false, paint);
                canvas.drawArc(rectRight, 0, 180, false, paint);
                canvas.drawArc(rectBottom, 180, 180, false, paint);
                break;
        }

    }

    private void drawDate(int left, int top, int right, int bottom, Paint paint, Canvas canvas){
        int width = right - left, height = bottom - top;
        int centerX = (left+right)/2;
        int centerY = (top+bottom)/2;
        int baseTextSize = width < height ? width/4 : height/4;
        paint.reset();
        if(pressDate){
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(themeColor);
        }
        paint.setTextSize(baseTextSize);
        canvas.drawText(""+today.getDay(), centerX-baseTextSize*3/2, top + height/3, paint);
        paint.setTextSize(baseTextSize/2);
        canvas.drawText(""+today.getWeek(), centerX, top + height/3, paint);
        canvas.drawText(today.toString(DateTime.Format.READABLE_MONTH), centerX-baseTextSize, centerY, paint);
    }

    private void drawTextBoard(int left, int top, int right, int bottom, Paint paint,
                               Canvas canvas){
        int width = right - left, height = bottom - top; // 计算宽高
        int baseTextSize = ((bottom-height/9) - (top+height/10))/6; //设定字体大小

        paint.reset();
        paint.setTextSize(baseTextSize);
        if(pressAbstract){
            paint.setColor(Color.LTGRAY);
        }else{
            paint.setColor(Color.WHITE);
        }
        paint.setAntiAlias(true);

        float[] points = { // 边框路线
                left, top+height/3,
                left+width/20, top+height*2/9,
                left+width/20, top+height/10,
                right-width/20, top+height/10,
                right-width/20, bottom-height/9,
                left+width/20, bottom-height/9,
                left+width/20, top+height*4/9
        };
        Path path = new Path();
        path.moveTo(points[0], points[1]);
        for(int i=2; i<points.length; i+=2){
            path.lineTo(points[i], points[i+1]);
        }
        path.close();
        canvas.drawPath(path, paint);

//        float[] points2 = { // 边框路线
//                left, top+height/3, left+width/20, top+height*2/9,
//                left+width/20, top+height*2/9, left+width/20, top+height/10,
//                left+width/20, top+height/10, right-width/20, top+height/10,
//                right-width/20, top+height/10, right-width/20, bottom-height/9,
//                right-width/20, bottom-height/9, left+width/20, bottom-height/9,
//                left+width/20, bottom-height/9, left+width/20, top+height*4/9,
//                left+width/20, top+height*4/9, left, top+height/3};
//        paint.setColor(themeColor);
//        canvas.drawLines(points2, 0, points2.length, paint); // 绘制边框路线


        textPaint.setColor(Color.DKGRAY);
        textPaint.setTextSize(baseTextSize);
        textPaint.setAntiAlias(true);
        int outerWidth = (right-width/20)-(left+width/15); // 计算文本宽度范围

        // 文本载体
        StaticLayout layout = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) { // 比较sdk版本
            Class<?> clazz = null;
            try {
                clazz = Class.forName("android.text.StaticLayout");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz != null) {
                try {
                    Constructor constructor = clazz.getConstructor(CharSequence.class, int.class,
                            int.class, TextPaint.class, int.class, Layout.Alignment.class,
                            TextDirectionHeuristic.class, float.class, float.class, boolean.class,
                            TextUtils.TruncateAt.class, int.class, int.class);
                    layout = (StaticLayout) constructor.newInstance(articleAbstract, 0,
                            articleAbstract.length(), textPaint, outerWidth,
                            Layout.Alignment.ALIGN_NORMAL, TextDirectionHeuristics.FIRSTSTRONG_LTR,
                            1.0F, 1.0F, true, TextUtils.TruncateAt.MARQUEE, 10, 3);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if(layout == null){
            // 计算需要省略字符的长度
            int bufferEnd;
            bufferEnd = articleAbstract.length() < outerWidth * 3/baseTextSize
                    ? articleAbstract.length() : outerWidth * 3/baseTextSize;
            layout = new StaticLayout(articleAbstract, 0, bufferEnd, textPaint,
                    outerWidth, Layout.Alignment.ALIGN_NORMAL, 1.0F, 1.0F, true, TextUtils.TruncateAt.END, 10);
        }

        canvas.save();
        canvas.translate(left+width/15, top+height/8);
        layout.draw(canvas);
        canvas.restore();

        // 绘制时间
        paint.setColor(themeColor);
        canvas.drawText(today.toString(DateTime.Format.READABLE_TIME), left+width/15, bottom-height/6, paint);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        this.touchX = event.getX();
        this.touchY = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(abstractRect.contains((int)this.touchX, (int)this.touchY)){
                    pressAbstract = true;
                }else if(dateRect.contains((int)this.touchX, (int)this.touchY)){
                    pressDate = true;
                }else if(faceRect.contains((int)this.touchX, (int)this.touchY)){
                    pressFace = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                pressDate = false;
                pressFace = false;
                pressAbstract = false;
                if(abstractRect.contains((int)this.touchX, (int)this.touchY)){
                    listener.onAbstractClick(ARTICLE_VIEW_ID);
                }else if(dateRect.contains((int)this.touchX, (int)this.touchY)){
                    listener.onDateClick(ARTICLE_VIEW_ID);
                }else if(faceRect.contains((int)this.touchX, (int)this.touchY)){
                    listener.onFaceClick(ARTICLE_VIEW_ID);
                }
                break;
            default:
                pressDate = false;
                pressFace = false;
                pressAbstract = false;
                break;

        }
        invalidate();
        return true;
    }

    public interface OnArticleViewClickListener{
        void onFaceClick(String articleViewId);
        void onDateClick(String articleViewId);
        void onAbstractClick(String articleViewId);
    }


}
