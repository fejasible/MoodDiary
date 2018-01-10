package com.app.feja.mooddiary.application;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.adapter.ThemeAdapter;
import com.app.feja.mooddiary.http.model.WeatherModel;
import com.app.feja.mooddiary.ui.activity.BaseActivity;
import com.app.feja.mooddiary.ui.activity.PasswordActivity;
import com.app.feja.mooddiary.ui.activity.WelcomeActivity;
import com.app.feja.mooddiary.util.pdf.PDFManager;
import com.app.feja.mooddiary.util.PicassoImageLoader;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

/**
 * created by fejasible@163.com
 */
public class TheApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String THEME_KEY = "theme";
    public static final String CUSTOM_THEME_KEY = "custom_theme";

    private int finalCount;
    private boolean appStart = false;

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
        registerActivityLifecycleCallbacks(this);
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
        TheApplication.weatherModel = weatherModel;
    }

    public static ThemeAdapter.Data getThemeData() {
        return themeData;
    }

    public static void setThemeData(ThemeAdapter.Data themeData) {
        TheApplication.themeData = themeData;

    }

    private ThemeAdapter.Data getMDTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(TheApplication.getSharedPreferencesName(), Context.MODE_PRIVATE);
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

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        finalCount++;
        if(finalCount == 1 && appStart){
            // 从后台切换到了前台
            if(activity != null && activity instanceof BaseActivity){
                if(((BaseActivity) activity).getTag().equals(WelcomeActivity.TAG)){
                    appStart = true;
                    return;
                }
                if(getPassword().equals("")){
                    return ;
                }
                Intent intent = new Intent(activity, PasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(PasswordActivity.ACTION_BUNDLE_NAME, PasswordActivity.ACTION.AGAIN_ENTER);
                intent.putExtras(bundle);
                activity.startActivity(intent);
            }
        }
        appStart = true;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        finalCount--;
        if(finalCount == 0){
            // 从前台移至了后台
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


    private String getPassword(){
        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sharedPreferences == null){
            return "";
        }else{
            return sharedPreferences.getString(PasswordActivity.PASSWORD_KEY, "");
        }
    }
}
