package com.app.feja.mooddiary;


import com.app.feja.mooddiary.constant.CONSTANT;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.http.request.WeatherRequest;
import com.app.feja.mooddiary.presenter.WeatherPresenter;
import com.app.feja.mooddiary.ui.view.WeatherView;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherTest {

    @Test
    public void testGetWeather() throws Exception{

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CONSTANT.WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherRequest weatherRequest = retrofit.create(WeatherRequest.class);
        Call<WeatherModel> call = weatherRequest.getApiWeather(CONSTANT.WEATHER_KEY, "beijing", "zh-Hans", "c");
        Response<WeatherModel> response = call.execute();
        System.out.println(response.body().getResults().get(0).getNow().getText());

    }

    @Test
    public void test_weather_connect() throws Exception{

        URL url = new URL("https://api.seniverse.com/v3/weather/now.json?key=wwvgmxal01ybl2o8&location=beijing&language=zh-Hans&unit=c");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        Map<String, List<String>> map = urlConnection.getHeaderFields();

        for(String key: map.keySet()){
            System.out.println(key + "-->" + map.get(key));
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

        System.out.println(result);


    }

}
