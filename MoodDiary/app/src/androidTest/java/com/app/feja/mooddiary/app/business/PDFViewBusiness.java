package com.app.feja.mooddiary.app.business;

import com.app.feja.mooddiary.app.util.CommonParams;
import com.robotium.solo.Solo;

/**
 * created by fejasible@163.com
 */

public class PDFViewBusiness extends BaseBusiness{

    public PDFViewBusiness(Solo solo) {
        super(solo);
    }

    public void scrollPDF(){
        drag(DIRECTION.DOWN);
        sleep(CommonParams.sleep);
        drag(DIRECTION.DOWN);
        sleep(CommonParams.sleep);
    }

}
