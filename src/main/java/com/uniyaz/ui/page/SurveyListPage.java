package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.MyAddButton;
import com.uniyaz.ui.component.MyEditButton;
import com.uniyaz.ui.component.MySaveButton;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.util.List;

public class SurveyListPage extends VerticalLayout {

    private Table table;
    private IndexedContainer container;
    private VerticalLayout mainLayout;

    public SurveyListPage() {

        setSizeFull();
        buildMainLayout();
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        fillTable();
    }

    private void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();

        buildTable();
        mainLayout.addComponent(table);
    }

    private void buildTable() {

        table = new Table();
        table.setPageLength(table.size());

        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "NAME", "Edit", "Add Panel");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("update", MyEditButton.class, null);
        container.addContainerProperty("add panel", MyAddButton.class, null);
    }

    private void fillTable() {

        SurveyService surveyService = new SurveyService();
        List<Survey> surveyList = surveyService.listSurveys();
        for (Survey survey : surveyList) {
            Item item = container.addItem(survey);
            item.getItemProperty("id").setValue(survey.getId());
            item.getItemProperty("name").setValue(survey.getName());

            MyEditButton myEditButton = buildEditButton(survey);
            item.getItemProperty("update").setValue(myEditButton);

            MyAddButton myAddButton = buildAddPanelButton(survey);
            item.getItemProperty("add panel").setValue(myAddButton);
        }

    }

    private MyEditButton buildEditButton(Survey survey) {
        MyEditButton myEditButton = new MyEditButton();
        myEditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();

                SurveyPage surveyPage = new SurveyPage(survey);
                contentComponent.addComponent(surveyPage);
            }
        });
        return myEditButton;
    }

    private MyAddButton buildAddPanelButton(Survey survey) {
        MyAddButton myAddButton = new MyAddButton();
        myAddButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();

                MyPanelPage myPanelPage = new MyPanelPage(survey);
                contentComponent.addComponent(myPanelPage);
            }
        });
        return myAddButton;
    }
}
