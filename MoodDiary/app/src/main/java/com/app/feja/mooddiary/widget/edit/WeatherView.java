package com.app.feja.mooddiary.widget.edit;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.widget.base.BaseView;

public class WeatherView extends BaseView{

    private WeatherModel weatherModel = new WeatherModel();
    private Paint themePaint;
    private Paint backgroundPaint;
    private WeatherModel.Result result;

    public WeatherView(Context context) {
        super(context);
        this.init();
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    public WeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.result = weatherModel.getResults().get(0);
        this.init();
    }

    private void init(){
        this.initPaint();
    }

    private void initPaint(){
        this.themePaint = new Paint();
        this.themePaint.setTextSize(this.getHeight() / 3);
        this.themePaint.setAntiAlias(true);
        this.themePaint.setColor(ContextCompat.getColor(getContext(), R.color.lightSkyBlue));

        this.backgroundPaint = new Paint();
        this.backgroundPaint.setTextSize(this.getHeight() / 6);
        this.backgroundPaint.setAntiAlias(true);
        this.backgroundPaint.setColor(ContextCompat.getColor(getContext(), R.color.lightGrey));
    }

    public WeatherModel getWeatherModel() {
        return weatherModel;
    }

    public void setWeatherModel(WeatherModel weatherModel) {
        this.weatherModel = weatherModel;
        this.result = weatherModel.getResults().get(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.initPaint();
        canvas.drawText(getResources().getString(R.string.location) + result.getLocation().getName(),
                themePaint.getTextSize(), themePaint.getTextSize()*4/3, themePaint);
        canvas.drawText(getResources().getString(R.string.weather) + result.getNow().getText(),
                this.getWidth()/3, themePaint.getTextSize()*4/3, themePaint);
        canvas.drawText(getResources().getString(R.string.temperature) + result.getNow().getTemperature() + "°C",
                this.getWidth()*2/3, themePaint.getTextSize()*4/3, themePaint);

        canvas.drawText(
                getResources().getString(R.string.last_update) + result.getLast_update(),
                themePaint.getTextSize(),
                this.getHeight() - backgroundPaint.getTextSize(),
                backgroundPaint
        );
    }
}
