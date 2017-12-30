package com.app.feja.mooddiary.app.business;

import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.app.util.CommonParams;
import com.robotium.solo.Solo;

import junit.framework.Assert;

/**
 * created by fejasible@163.com
 */
public class ExportDiaryBusiness extends BaseBusiness {
    public ExportDiaryBusiness(Solo solo) {
        super(solo);
    }

    public void clickOnExportExecute() {
        clickOnIdAndSleep(R.id.id_export_execute, CommonParams.sleep * 20);
    }

    public void clickOnHistory() {
        clickOnPartOfView("" + R.id.id_export_title_bar, 0, (720.0f - 50.0f) / 720.0f, 0.5f);
    }

    public void chooseFile(String fileName){
        checkTextExist(fileName);
        clickOnTextAndSleep(fileName, CommonParams.sleep);
    }
}
