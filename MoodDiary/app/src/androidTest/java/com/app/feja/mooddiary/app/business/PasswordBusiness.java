package com.app.feja.mooddiary.app.business;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.app.util.CommonParams;
import com.robotium.solo.Solo;

import org.junit.Assert;

public class PasswordBusiness extends BaseBusiness{

    public enum KEYBOARD{NUMBER_2, NUMBER_3,
        NUMBER_4 ,NUMBER_5 ,NUMBER_6,
        NUMBER_7, NUMBER_8, NUMBER_9,
        HELP,     NUMBER_0, DELETE,
        NUMBER_1
    }


    public PasswordBusiness(Solo solo) {
        super(solo);
    }

    public void clickOnNumbers(KEYBOARD... numbers){
        for(KEYBOARD number: numbers){
             clickOnNumber(number);
        }
    }

    public void clickOnNumber(KEYBOARD keyboard){
        int keyboardY = screen_height * 5/9;
        int dx = screen_width/3, dy = (screen_height - keyboardY) / 4;
        switch (keyboard){
            case NUMBER_1:
                solo.clickLongOnScreen(dx/2, keyboardY + dy/2, CommonParams.sleep);
                break;
            case NUMBER_2:
                solo.clickLongOnScreen(dx/2+dx, keyboardY + dy/2, CommonParams.sleep);
                break;
            case NUMBER_3:
                solo.clickLongOnScreen(dx/2+2*dx, keyboardY + dy/2, CommonParams.sleep);
                break;
            case NUMBER_4:
                solo.clickLongOnScreen(dx/2, keyboardY + dy/2+dy, CommonParams.sleep);
                break;
            case NUMBER_5:
                solo.clickLongOnScreen(dx/2+dx, keyboardY + dy/2+dy, CommonParams.sleep);
                break;
            case NUMBER_6:
                solo.clickLongOnScreen(dx/2+2*dx, keyboardY + dy/2+dy, CommonParams.sleep);
                break;
            case NUMBER_7:
                solo.clickLongOnScreen(dx/2, keyboardY + dy/2+2*dy, CommonParams.sleep);
                break;
            case NUMBER_8:
                solo.clickLongOnScreen(dx/2+dx, keyboardY + dy/2+2*dy, CommonParams.sleep);
                break;
            case NUMBER_9:
                solo.clickLongOnScreen(dx/2+2*dx, keyboardY + dy/2+2*dy, CommonParams.sleep);
                break;
            case HELP:
                solo.clickLongOnScreen(dx/2, keyboardY + dy/2+3*dy, CommonParams.sleep);
                break;
            case NUMBER_0:
                solo.clickLongOnScreen(dx/2+dx, keyboardY + dy/2+3*dy, CommonParams.sleep);
                break;
            case DELETE:
                solo.clickLongOnScreen(dx/2+2*dx, keyboardY + dy/2+3*dy, CommonParams.sleep);
                break;
        }
    }

    public void checkPasswordExist(){
        checkIdExist(""+R.id.password_border);
    }
}
