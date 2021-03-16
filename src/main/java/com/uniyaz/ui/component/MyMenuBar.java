package com.uniyaz.ui.component;

import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.page.SurveyPage;
import com.uniyaz.ui.page.SurveyListPage;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.UI;

public class MyMenuBar extends com.vaadin.ui.MenuBar {

    private ContentComponent contentComponent;

    public MyMenuBar() {
        setSizeFull();

        MyUI myUI = (MyUI) UI.getCurrent();
        contentComponent = myUI.getContentComponent();

        buildSurveyMenuItem();
    }

    private void buildSurveyMenuItem() {
        MenuItem productTransactionsMenuItem = addItem("Survey Transactions", null);
        productTransactionsMenuItem.addItem("Add Survey", FontAwesome.PLUS, new Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                SurveyPage surveyPage = new SurveyPage();
                contentComponent.addComponent(surveyPage);
            }
        });

        productTransactionsMenuItem.addItem("List Surveys", FontAwesome.LIST, new Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                SurveyListPage surveyListPage = new SurveyListPage();
                contentComponent.addComponent(surveyListPage);
            }
        });

    }
}


