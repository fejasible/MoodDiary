package com.app.feja.mooddiary.adapter;


public class WeatherAdapter {
    public static final String WEATHER_RESOURCE_PREFIX_NAME = "weather_";
    public static final int Unknown = 99;

    public static String code2Name(int code){
        switch (code){
            case 0:
                return "Sunny";
            case 1:
                return "Clear";
            case 2:
                return "Fair";
            case 3:
                return "Fair";
            case 4:
                return "Cloudy";
            case 5:
                return "Partly Cloudy";
            case 6:
                return "Partly Cloudy";
            case 7:
                return "Mostly Cloudy";
            case 8:
                return "Mostly Cloudy";
            case 9:
                return "Overcast";
            case 10:
                return "Shower";
            case 11:
                return "Thundershower";
            case 12:
                return "Thundershower with Hail";
            case 13:
                return "Light Rain";
            case 14:
                return "Moderate Rain";
            case 15:
                return "Heavy Rain";
            case 16:
                return "Storm";
            case 17:
                return "Heavy Storm";
            case 18:
                return "Severe Storm";
            case 19:
                return "Ice Rain";
            case 20:
                return "Sleet";
            case 21:
                return "Snow Flurry";
            case 22:
                return "Light Snow";
            case 23:
                return "Moderate Snow";
            case 24:
                return "Heavy Snow";
            case 25:
                return "Snowstorm";
            case 26:
                return "Dust";
            case 27:
                return "Sand";
            case 28:
                return "Duststorm";
            case 29:
                return "Sandstorm";
            case 30:
                return "Foggy";
            case 31:
                return "Haze";
            case 32:
                return "Windy";
            case 33:
                return "Blustery";
            case 34:
                return "Hurricane";
            case 35:
                return "Tropical Storm";
            case 36:
                return "Tornado";
            case 37:
                return "Cold";
            case 38:
                return "Hot";
            case 99:
                return "Unknown";
            default:
                return null;
        }
    }

}
