package com.app.feja.mooddiary.app.testcase;


import android.support.test.rule.ActivityTestRule;

import com.app.feja.mooddiary.app.business.MainBusiness;
import com.app.feja.mooddiary.ui.activity.MainActivity;

import org.junit.Test;

/**
 * created by fejasible@163.com
 */
public class MainTestCase extends BaseTestCase{

    public MainTestCase(){
        this.activityTestRule = new ActivityTestRule<>(MainActivity.class);
    }

    @Test
    public void test_main_001(){
        mainBusiness.checkTitleBarExist();
//        mainBusiness.checkArticleListExist();
        mainBusiness.checkTabExist();
        mainBusiness.clickTab(MainBusiness.TAB.DIARY_SETTINGS);
        mainBusiness.clickTab(MainBusiness.TAB.DIARY_LIST);
        mainBusiness.clickTab(MainBusiness.TAB.DIARY_EDIT);
    }

}
