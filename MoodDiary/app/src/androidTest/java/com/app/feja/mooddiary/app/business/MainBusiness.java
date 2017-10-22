package com.app.feja.mooddiary.app.business;


import com.app.feja.mooddiary.R;
import com.app.feja.mooddiary.app.util.CommonParams;
import com.app.feja.mooddiary.application.ApplicationContext;
import com.robotium.solo.Solo;

public class MainBusiness extends BaseBusiness{

    public enum TAB{
        DIARY_LIST, DIARY_EDIT, DIARY_SETTINGS
    }

    public MainBusiness(Solo solo) {
        super(solo);
    }

    public void clickTab(TAB tab){
        int dx = this.screen_width/3;
        int tabHeight = (int)ApplicationContext.getContext().getResources().getDimension(R.dimen.x100);
        switch (tab){
            case DIARY_LIST:
                clickLongOnScreen(dx/2, this.screen_height - tabHeight/2, CommonParams.sleep);
                break;
            case DIARY_EDIT:
                clickLongOnScreen(dx/2+dx, this.screen_height - tabHeight/2, CommonParams.sleep);
                break;
            case DIARY_SETTINGS:
                clickLongOnScreen(dx/2+2*dx, this.screen_height - tabHeight/2, CommonParams.sleep);
                break;
        }
    }

    public void checkArticleListExist(){
        checkIdExist(""+R.id.layout_article_list);
    }

    public void checkTitleBarExist(){
        checkIdExist(""+R.id.main_title_bar);
    }

    public void checkTabExist(){
        checkIdExist(""+R.id.tab_view);
    }


}
