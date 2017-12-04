package com.app.feja.mooddiary.presenter;


import com.app.feja.mooddiary.constant.CONSTANT;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.http.request.WeatherRequest;
import com.app.feja.mooddiary.ui.view.WeatherView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherPresenter {

    private WeatherView weatherView;

    public WeatherPresenter(WeatherView weatherView) {
        this.weatherView = weatherView;
    }

    public void getWeather(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CONSTANT.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRequest weatherRequest = retrofit.create(WeatherRequest.class);
        Call<WeatherModel> call;
        try{
            call = weatherRequest.getApiWeather(CONSTANT.WEATHER_KEY, "beijing", "zh-Hans", "c");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return ;
        }
        call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                weatherView.onLoadWeather(response.body());
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {

            }
        });

    }

}
