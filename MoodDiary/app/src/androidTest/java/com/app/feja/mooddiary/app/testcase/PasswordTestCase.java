package com.app.feja.mooddiary.app.testcase;


import android.support.test.rule.ActivityTestRule;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.app.business.PasswordBusiness;
import com.app.feja.mooddiary.ui.activity.PasswordActivity;

import org.junit.Test;

public class PasswordTestCase extends BaseTestCase{


    public PasswordTestCase() {
        this.activityTestRule = new ActivityTestRule<>(PasswordActivity.class);
    }

    @Test
    public void test_password_001() throws Exception{
        passwordBusiness.checkPasswordExist();
        passwordBusiness.clickOnNumbers(
                PasswordBusiness.KEYBOARD.NUMBER_1,
                PasswordBusiness.KEYBOARD.NUMBER_2,
                PasswordBusiness.KEYBOARD.NUMBER_3,
                PasswordBusiness.KEYBOARD.NUMBER_4
        );
        passwordBusiness.checkIdExist(""+R.id.layout_article_list);
    }

}
