package com.app.feja.mooddiary.app.testcase;


import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import com.app.feja.mooddiary.app.business.BaseBusiness;
import com.app.feja.mooddiary.app.business.MainBusiness;
import com.app.feja.mooddiary.app.business.PasswordBusiness;
import com.app.feja.mooddiary.ui.activity.BaseActivity;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BaseTestCase {

    @Rule
    public ActivityTestRule activityTestRule;

    protected int screen_width = 0;
    protected int screen_height = 0;
    protected Solo solo;

    protected BaseBusiness baseBusiness;
    protected PasswordBusiness passwordBusiness;
    protected MainBusiness mainBusiness;

    public BaseTestCase(){
        this.activityTestRule = new ActivityTestRule<>(BaseActivity.class);
    }


    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), activityTestRule.getActivity());
        baseBusiness = new BaseBusiness(solo);
        passwordBusiness = new PasswordBusiness(solo);
        mainBusiness = new MainBusiness(solo);


        Activity activity = solo.getCurrentActivity();
        DisplayMetrics wdm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(wdm);
        screen_width = wdm.widthPixels;
        screen_height = wdm.heightPixels;
    }

    @After
    public void tearDown() throws Exception{

    }
}
