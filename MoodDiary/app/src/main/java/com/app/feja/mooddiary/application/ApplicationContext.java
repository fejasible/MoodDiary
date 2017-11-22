package com.app.feja.mooddiary.application;


import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.ThemeAdapter;
import com.app.feja.mooddiary.adapter.WeatherAdapter;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.ui.activity.ThemeActivity;
import com.app.feja.mooddiary.util.PicassoImageLoader;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

public class ApplicationContext extends Application{

    public static final String THEME_KEY = "theme";
    public static final String CUSTOM_THEME_KEY = "custom_theme";

    private static Context context;
    private static int screenWidth;
    private static int screenHeight;
    private static ThemeAdapter.Data themeData;
    private static String sharedPreferencesName = "user";
    private static WeatherModel weatherModel = new WeatherModel();

    public static String getSharedPreferencesName() {
        return sharedPreferencesName;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = getApplicationContext();

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new PicassoImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(8);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素

        themeData = getMDTheme();
        if(themeData == null){
            themeData = new ThemeAdapter.Data(
                    ContextCompat.getColor(getApplicationContext(), R.color.theme_color_main),
                    getString(R.string.theme_color_name_main));
        }
    }

    /**
     * 尽可能不要使用此方法
     * @return context
     */
    public static Context getContext()
    {
        return context;
    }

    public static int getScreenWidth(){
        return screenWidth;
    }

    public static int getScreenHeight(){
        return screenHeight;
    }

    public static WeatherModel getWeatherModel() {
        return weatherModel;
    }

    public static void setWeatherModel(WeatherModel weatherModel) {
        ApplicationContext.weatherModel = weatherModel;
    }

    public static ThemeAdapter.Data getThemeData() {
        return themeData;
    }

    public static void setThemeData(ThemeAdapter.Data themeData) {
        ApplicationContext.themeData = themeData;

    }

    private ThemeAdapter.Data getMDTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(ApplicationContext.getSharedPreferencesName(), Context.MODE_PRIVATE);
        if(sharedPreferences == null){
            return null;
        }else{
            String themeString = sharedPreferences.getString(THEME_KEY, "");
            try {
                return new Gson().fromJson(themeString, ThemeAdapter.Data.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
