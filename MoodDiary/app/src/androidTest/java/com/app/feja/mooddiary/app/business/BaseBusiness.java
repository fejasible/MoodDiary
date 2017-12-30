package com.app.feja.mooddiary.app.business;


import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.app.feja.mooddiary.app.util.CommonParams;
import com.robotium.solo.Solo;

import org.junit.Assert;

/**
 * created by fejasible@163.com
 */
public class BaseBusiness {

    protected int screen_width = 0;
    protected int screen_height = 0;

    protected Solo solo;


    public enum DIRECTION{
        LEFT, RIGHT, UP, DOWN
    }


    public BaseBusiness(Solo solo){
        Activity activity = solo.getCurrentActivity();
        DisplayMetrics wdm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(wdm);
        screen_width = wdm.widthPixels;
        screen_height = wdm.heightPixels;
        this.solo = solo;
    }
    /**
     * 通过id点击组件
     * @param id
     */
    public void clickOnView(int id){
        solo.clickOnView(solo.getView(id));
    }


    public void inputTextAfterClear(int id, final String str){
        final EditText editText = (EditText) solo.getView(id);
        solo.clearEditText(editText);
        solo.enterText(editText, str);
    }

    //检测Toast文本存在
    public void checkToastTextExist(String text) {
        Assert.assertTrue(solo.waitForText(text));
    }

    //检测文本存在
    public void checkTextExist(String text) {
        sleep(CommonParams.sleep / 2);
        Assert.assertTrue(solo.waitForText(text));

    }
    public void checkTextExistByIndex(String text,int index) {
        sleep(CommonParams.sleep / 2);
        Assert.assertTrue(solo.waitForText(text,index,CommonParams.sleep / 2));

    }
    //检测文本不存在
    public void checkTextNotExist(String text) {
        sleep(CommonParams.sleep / 2);
        Assert.assertTrue(!solo.waitForText(text));
    }

    //检测界面存在
    public void checkActivityExist(String activity, int timeout) {
        sleep(CommonParams.sleep / 2);
        Assert.assertTrue(solo.getCurrentActivity().getClass().getSimpleName().equals(activity));
    }

    //检测界面不存在
    public void checkActivityNotExist(String activity, int timeout) {
        sleep(CommonParams.sleep / 2);
        Assert.assertFalse(solo.getCurrentActivity().getClass().getSimpleName().equals(activity));
    }

    //检测两个String是否相等
    public void checkStringEquals(String expected,String actual){
        Assert.assertEquals(expected, actual);
    }

    //检测组件存在
    public void checkIdExist(String id) {
        Assert.assertTrue(solo.getView(id) != null);
    }

    //检测组件存在
    public void checkIdExistByIndex(String id, int index) {
        sleep(CommonParams.sleep / 2);
        Assert.assertTrue(solo.getView(id, index) != null);
    }
    //检测组件不存在
    public void checkIdNotExistByIndex(String id, int index) {
        Assert.assertFalse(solo.getView(id, index) != null);
    }
    //检测组件存在
    public void checkSomeIdExistByIndex(String id,int i) {
        for(int index=0;index<i; index++ )
        {
            sleep(CommonParams.sleep / 2);
            Assert.assertTrue(solo.getView(id, index) != null);
        }
    }

    //点击文本并等待
    public void clickOnTextAndSleep(String text, int sleep) {
        solo.clickOnText(text);
        if(sleep > 0) {
            solo.sleep(sleep);
        }
    }
    //点击文本并等待
    public void clickOnTextAndSleep(String text, int index, int sleep) {
        solo.clickOnText(text, index);
        if(sleep > 0) {
            solo.sleep(sleep);
        }
    }
    //点击button并等待
    public void clickOnButtonAndSleep(String text, int sleep) {
        solo.clickOnButton(text);
        if(sleep > 0) {
            solo.sleep(sleep);
        }
    }

    //点击组件并等待
    public void clickOnIdAndSleep(String id, int sleep) {
        solo.clickOnView(solo.getView(id));
        if(sleep > 0) {
            solo.sleep(sleep);
        }
    }

    /**
     * 点击组件并等待
     * @param id
     * @param sleep
     */
    public void clickOnIdAndSleep(int id, int sleep) {
        solo.clickOnView(solo.getView(id));
        if(sleep > 0) {
            solo.sleep(sleep);
        }
    }

    //点击组件并等待
    public void clickOnIdAndSleep(String id, int index, int sleep) {
        solo.clickOnView(solo.getView(id, index));
        if(sleep > 0) {
            solo.sleep(sleep);
        }
    }

    public void drag(DIRECTION direction){
        drag(direction, 3);
    }

    //拖拽，可向四个方向
    public void drag(DIRECTION direction, int stepCount) {
        if (direction.equals(DIRECTION.DOWN)) {
            solo.drag(screen_width / 2, screen_width / 2, screen_height / 8, screen_height * 7 / 8, stepCount);
        }
        if (direction.equals(DIRECTION.UP)) {
            solo.drag(screen_width / 2, screen_width / 2, screen_height * 7 / 8, screen_height / 8, stepCount);
        }
        if (direction.equals(DIRECTION.LEFT)) {
            solo.drag(screen_width * 7 / 8, screen_width / 8, screen_height / 2, screen_height / 2, stepCount);
        }
        if (direction.equals(DIRECTION.RIGHT)) {
            solo.drag(screen_width / 8, screen_width * 7 / 8, screen_height / 2, screen_height / 2, stepCount);
        }
        solo.sleep(CommonParams.sleep / 2);
    }

    //拖拽，参数分别为起终点想x、y坐标
    public void drag(float start_x, float end_x, float start_y, float end_y) {
        solo.drag(start_x, end_x,
                start_y, end_y,
                3);
    }

    //等待
    public void sleep(int time) {
        solo.sleep(time);
    }

    //根据文本获取view
    public View getViewByText(String text) {
        return solo.getText(text);
    }

    public View getViewById(int id){
        return solo.getView(id);
    }

    //点击手机返回按键
    public void goBack() {
        solo.goBack();
        solo.sleep(CommonParams.sleep / 2);
    }

    //点击文本
    public void pressText(String Text) {
        clickOnTextAndSleep(Text, 0);
    }

    //点击文本
    public void pressText(String Text,int index) {
        clickOnTextAndSleep(Text, index,CommonParams.sleep / 2);
    }

    //长按文本
    public void clickLongOnText(String text) {
        solo.clickLongOnText(text);
    }

    //长按文本
    public void clickLongOnText(String text, int time) {
        solo.clickLongOnText(text, 0, time);
    }
    //点击屏幕坐标
    public void clickOnScreen(float x, float y) {
        solo.clickOnScreen(x, y);
    }
    //长按坐标
    public void clickLongOnScreen(float x, float y, int... time) {
        solo.clickLongOnScreen(x, y, time[0]);
    }
    //长按ID
    public void clickLongOnID(String ID) {
        solo.clickLongOnView(solo.getView(ID));
    }
    //长按ID
    public void clickLongOnIDByIndex(String ID,int index) {
        solo.clickLongOnView(solo.getView(ID,index));
    }

    //长按控件
    public void clickLongOnView(View view) {
        solo.clickLongOnView(view);
    }
    //点击控件
    public void clickOnView(View view){
        solo.clickOnView(view);
    }
    //当前Activity的名字
    public String getActivityName() {
        return solo.getCurrentActivity().getClass().getName();
    }

    //获取控件X坐标
    public int getX(View view) {
        int []location = new int[2];
        view.getLocationOnScreen(location);
        return location[0];
    }
    //获取控件Y坐标
    public int getY(View view) {
        int []location = new int[2];
        view.getLocationOnScreen(location);
        return location[1];
    }
    //判断弹出软键盘
    public void hasKeyboard(EditText view) {
        InputMethodManager imm2 = (InputMethodManager) solo.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Assert.assertTrue(imm2.hideSoftInputFromWindow(view.getWindowToken(), 0));
    }


    //判断未弹出软键盘
    public void hasNotKeyboard(EditText view) {
        InputMethodManager imm2 = (InputMethodManager) solo.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        Assert.assertFalse(imm2.hideSoftInputFromWindow(view.getWindowToken(), 0));
    }

    //检查TextView的文字颜色
    public void checkTextColor(String viewId, int i) {
        TextView tv = (TextView) solo.getView(viewId);
        Assert.assertTrue(tv.getCurrentTextColor() == i);
    }

    //隐藏软键盘
    public void hideKeyboard(EditText view) {
        InputMethodManager imm2 = (InputMethodManager) solo.getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm2.hideSoftInputFromWindow(view.getWindowToken(), 0)) {
            solo.goBack();
        }
    }


    //点击组件的某一位置，系数从0.0--1表示从左上角到右下角
    public void clickOnPartOfView(String view, int index, float ratio_x, float ratio_y) {
        int[] xy = new int[2];

        View publisher = solo.getView(view, index);
        publisher.getLocationOnScreen(xy);

        final int viewHeight = publisher.getHeight();
        final int viewWidth = publisher.getWidth();
        float x = xy[0] + viewWidth * ratio_x;
        float y = xy[1] + viewHeight *  ratio_y;
        clickOnScreen(x, y);
        sleep(CommonParams.sleep / 2);
    }
    //提取字符串中的数字
    public String getNumbers(String str) {
        String str2="";
        for(int i=0;i<str.length();i++)
        {
            if(str.charAt(i)>=48 && str.charAt(i)<=57)
                str2+=str.charAt(i);
        }
        return str2;
    }

}
