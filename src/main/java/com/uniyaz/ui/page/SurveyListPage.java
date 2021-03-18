package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.*;
import com.uniyaz.ui.component.Buttons.MyAddButton;
import com.uniyaz.ui.component.Buttons.MyEditButton;
import com.uniyaz.ui.myWindows.SurveyWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;

public class SurveyListPage extends BasePage {

    private MyAddButton addSurveyButton;
    private Table table;

    private IndexedContainer container;
    private VerticalLayout mainLayout;

    public SurveyListPage() {

        setSizeFull();

        fillTable();
    }

    @Override
    public void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        buildTable();
        mainLayout.addComponent(table);

        addSurveyButton = buildAddSurveyButton();
        mainLayout.addComponent(addSurveyButton);
    }

    private void buildTable() {

        table = new Table();
        table.setPageLength(table.size());
        table.setSelectable(true);

        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "NAME", "Edit", "View");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("update", MyEditButton.class, null);
        container.addContainerProperty("view", MyEditButton.class, null);
    }

    private void fillTable() {

        SurveyService surveyService = new SurveyService();

        List<Survey> surveyList = surveyService.listSurveys();
        if(surveyList !=null) {
            for (Survey survey : surveyList) {
                Item item = container.addItem(survey);
                item.getItemProperty("id").setValue(survey.getId());
                item.getItemProperty("name").setValue(survey.getName());

                MyEditButton myEditButton = buildEditButton(survey);
                item.getItemProperty("update").setValue(myEditButton);

                MyEditButton myViewButton = buildViewButton(survey);
                item.getItemProperty("view").setValue(myViewButton);
            }
        }
    }

    private MyEditButton buildEditButton(Survey survey) {
        MyEditButton myEditButton = new MyEditButton();
        myEditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                SurveyWindow surveyWindow  = new SurveyWindow(survey);
                myUI.addWindow(surveyWindow);
            }
        });
        return myEditButton;
    }

    private MyEditButton buildViewButton(Survey survey) {
        MyEditButton myViewButton = new MyEditButton();
        myViewButton.setIcon(FontAwesome.EYE);
        myViewButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();
                contentComponent.addComponent(new PreparedSurveyPage(survey));
            }
        });
        return myViewButton;
    }

    private MyAddButton buildAddSurveyButton() {
        MyAddButton myAddButton = new MyAddButton();
        myAddButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                SurveyWindow surveyWindow  = new SurveyWindow();
                myUI.addWindow(surveyWindow);
            }
        });
        return myAddButton;
    }

    public Table getTable() {
        return table;
    }
}
