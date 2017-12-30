package com.app.feja.mooddiary.http.model;

import com.app.feja.mooddiary.adapter.WeatherAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * created by fejasible@163.com
 {
     "results": [
         {
             "location": {
                 "id": "WX4FBXXFKE4F",
                 "name": "北京",
                 "country": "CN",
                 "path": "北京,北京,中国",
                 "timezone": "Asia/Shanghai",
                 "timezone_offset": "+08:00"
             },
             "now": {
                 "text": "晴",
                 "code": "0",
                 "temperature": "13"
             },
            "last_update": "2017-11-05T14:10:00+08:00"
        }
    ]
 }
 */
public class WeatherModel extends BaseModel{

    public WeatherModel(){
        this.results = new ArrayList<>();
        this.results.add(new Result());
    }

    private List<Result> results;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result{

        public Result(){
            this.location = new Location();
            this.now = new Now();
            this.last_update = "";
        }
        private Location location;

        private Now now;

        private String last_update;

        /**
         "location": {
             "id": "WX4FBXXFKE4F",
             "name": "北京",
             "country": "CN",
             "path": "北京,北京,中国",
             "timezone": "Asia/Shanghai",
             "timezone_offset": "+08:00"
         }
         */
        public static class Location{
            public Location() {
                this.id = "";
                this.name = "";
                this.country = "";
                this.path = "";
                this.timezone = "";
                this.timezone_offset = "";
            }

            private String id;
            private String name;
            private String country;
            private String path;
            private String timezone;
            private String timezone_offset;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }

            public String getTimezone_offset() {
                return timezone_offset;
            }

            public void setTimezone_offset(String timezone_offset) {
                this.timezone_offset = timezone_offset;
            }
        }

        /**
         "now": {
         "text": "晴",
         "code": "0",
         "temperature": "13"
         }
         */
        public static class Now{
            public Now() {
                this.text = "";
                this.code = WeatherAdapter.Unknown+"";
                this.temperature = "";
            }

            private String text;
            private String code;
            private String temperature;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }
        }


        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public Now getNow() {
            return now;
        }

        public void setNow(Now now) {
            this.now = now;
        }

        public String getLast_update() {
            return last_update;
        }

        public void setLast_update(String last_update) {
            this.last_update = last_update;
        }
    }


}
