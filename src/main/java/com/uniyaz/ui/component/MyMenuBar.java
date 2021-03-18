package com.uniyaz.ui.component;

import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.page.MyTabSheet;
import com.uniyaz.ui.page.SurveyStartPage;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.UI;

public class MyMenuBar extends com.vaadin.ui.MenuBar {

    private MyTabSheet myTabSheet;
    private ContentComponent contentComponent;

    public MyMenuBar() {
        setSizeFull();

        MyUI myUI = (MyUI) UI.getCurrent();
        contentComponent = myUI.getContentComponent();

        buildSurveyMenuItem();
    }

    private void buildSurveyMenuItem() {
        MenuItem addListMenuItem = addItem("Add/List Survey", FontAwesome.PLUS,new Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                myTabSheet = new MyTabSheet();
                contentComponent.addComponent(myTabSheet);
            }
        });

        MenuItem fillSurveyMenuItem = addItem("Fill Survey", FontAwesome.PLUS,new Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
                contentComponent.addComponent(new SurveyStartPage());
            }
        });

        MenuItem showSolvedSurveys = addItem("Solved Surveys", FontAwesome.PLUS,new Command() {
            @Override
            public void menuSelected(MenuItem menuItem) {
            }
        });

    }
}


