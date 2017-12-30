package com.app.feja.mooddiary.app.business;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.app.util.CommonParams;
import com.robotium.solo.Solo;

/**
 * created by fejasible@163.com
 */
public class SettingBusiness extends BaseBusiness{


    public SettingBusiness(Solo solo) {
        super(solo);
    }

    public void clickOnExportDiary(){
        clickOnIdAndSleep(R.id.id_settings_export, CommonParams.sleep);
    }

    public void checkExportDiaryExist(){
        checkIdExist(""+ R.id.id_settings_export);
    }

}
