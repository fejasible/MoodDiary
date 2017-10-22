package com.app.feja.mooddiary.application;


import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ApplicationContext extends Application{
    private static Context context;
    private static int screenWidth;
    private static int screenHeight;

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
}
