package com.app.feja.mooddiary.app.testcase;

import android.support.test.rule.ActivityTestRule;

import com.app.feja.mooddiary.app.util.CommonParams;
import com.app.feja.mooddiary.ui.activity.SettingsActivity;
import com.app.feja.mooddiary.util.DateTime;

import org.junit.Test;

/**
 * created by fejasible@163.com
 */

public class ExportTestCase extends BaseTestCase {

    public ExportTestCase() {
        this.activityTestRule = new ActivityTestRule<>(SettingsActivity.class);
    }

    @Test
    public void testExport() throws Exception {
        settingBusiness.clickOnExportDiary();
        exportDiaryBusiness.clickOnExportExecute();
        exportDiaryBusiness.clickOnHistory();
        DateTime dateTime = new DateTime();
        String fileName = dateTime.toString(DateTime.Format.DATE) + "_" + dateTime.toString(DateTime.Format.DATE) + ".pdf";
        exportDiaryBusiness.chooseFile(fileName);
        pdfViewBusiness.scrollPDF();
    }
}
