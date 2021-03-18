package com.uniyaz.ui.page;

import com.uniyaz.core.domain.CustomerSurvey;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.CustomerSurveyService;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.MyAddButton;
import com.uniyaz.ui.component.MyEditButton;
import com.uniyaz.ui.myWindows.SurveyWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;

public class FilledSurveysPage extends BasePage {

    private MyAddButton addSurveyButton;
    private Table table;

    private IndexedContainer container;
    private VerticalLayout mainLayout;
    private String mail;

    public FilledSurveysPage() {

        fillTable();
    }

    public FilledSurveysPage(String mail) {
        this.mail=mail;

        fillTableByMail(mail);
    }

    @Override
    public void buildMainLayout() {

        mainLayout = new VerticalLayout();
        mainLayout.setSizeUndefined();
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);

        HorizontalLayout searchLayout= new HorizontalLayout();
        mainLayout.addComponent(searchLayout);

        TextField searchField = new TextField();
        searchLayout.addComponent(searchField);

        Button searchButton = new Button();
        searchButton.setIcon(FontAwesome.SEARCH);
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String mail = searchField.getValue();
                fillTableByMail(mail);
            }
        });
        searchLayout.addComponent(searchButton);

        buildTable();
        mainLayout.addComponent(table);
/*
        addSurveyButton = buildAddSurveyButton();
        mainLayout.addComponent(addSurveyButton);*/
    }

    private void buildTable() {

        table = new Table();
        table.setPageLength(table.size());
        table.setSelectable(true);

        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "SURVEY", "MAIL");
//        table.setColumnHeaders("ID", "SURVEY", "MAIL", "Edit", "View");

    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("mail", String.class, null);/*
        container.addContainerProperty("update", MyEditButton.class, null);
        container.addContainerProperty("view", MyEditButton.class, null);*/
    }

    private void fillTable() {

        CustomerSurveyService customerSurveyService = new CustomerSurveyService();

        List<CustomerSurvey> customerSurveyList = customerSurveyService.listCustomerSurveys();
        if(customerSurveyList !=null) {
            for (CustomerSurvey customerSurvey : customerSurveyList) {
                Item item = container.addItem(customerSurvey);
                item.getItemProperty("id").setValue(customerSurvey.getId());
                item.getItemProperty("name").setValue(customerSurvey.getSurvey().getName());
                item.getItemProperty("mail").setValue(customerSurvey.getMail());
/*
                MyEditButton myEditButton = buildEditButton(survey);
                item.getItemProperty("update").setValue(myEditButton);

                MyEditButton myViewButton = buildViewButton(survey);
                item.getItemProperty("view").setValue(myViewButton);*/
            }
        }
    }

    private void fillTableByMail(String mail) {
        CustomerSurveyService customerSurveyService = new CustomerSurveyService();

        List<CustomerSurvey> customerSurveyList = customerSurveyService.listCustomerSurveysByMail(mail);
        if(customerSurveyList !=null) {
            container.removeAllItems();
            for (CustomerSurvey customerSurvey : customerSurveyList) {
                Item item = container.addItem(customerSurvey);
                item.getItemProperty("id").setValue(customerSurvey.getId());
                item.getItemProperty("name").setValue(customerSurvey.getSurvey().getName());
                item.getItemProperty("mail").setValue(customerSurvey.getMail());
/*
                MyEditButton myEditButton = buildEditButton(survey);
                item.getItemProperty("update").setValue(myEditButton);

                MyEditButton myViewButton = buildViewButton(survey);
                item.getItemProperty("view").setValue(myViewButton);*/
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
