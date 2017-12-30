package com.app.feja.mooddiary.http.request;


import com.app.feja.mooddiary.http.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * created by fejasible@163.com
 */
public interface WeatherRequest {
    @GET("v3/weather/now.json")
    Call<WeatherModel> getApiWeather(@Query("key") String key, @Query("location") String location,
                                     @Query("language") String language, @Query("unit") String unit);
}
