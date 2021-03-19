package com.uniyaz.ui.page;

import com.uniyaz.core.domain.CustomerSurvey;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.CustomerSurveyService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.Buttons.MyAddButton;
import com.uniyaz.ui.component.Buttons.MyEditButton;
import com.uniyaz.ui.myWindows.SurveyWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;

import java.util.List;

public class FilledSurveysPage extends BasePage {

    private Table table;

    private IndexedContainer container;
    private VerticalLayout mainLayout;
    TextField searchField;
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

        searchField = new TextField();
        searchLayout.addComponent(searchField);

        Button searchButton = buildSearchButton();
        searchLayout.addComponent(searchButton);

        buildTable();
        mainLayout.addComponent(table);
    }

    private void buildTable() {

        table = new Table();
        table.setPageLength(table.size());
        table.setSelectable(true);

        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "SURVEY", "MAIL", "EDIT");

    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("mail", String.class, null);
        container.addContainerProperty("update", MyEditButton.class, null);
    }

    private void fillTable() {

        CustomerSurveyService customerSurveyService = new CustomerSurveyService();

        List<CustomerSurvey> customerSurveyList = customerSurveyService.listCustomerSurveys();
        if(customerSurveyList !=null) {
            for (CustomerSurvey customerSurvey : customerSurveyList) {
                Survey survey = customerSurvey.getSurvey();
                String mail = customerSurvey.getMail();

                Item item = container.addItem(customerSurvey);
                item.getItemProperty("id").setValue(customerSurvey.getId());
                item.getItemProperty("name").setValue(survey.getName());
                item.getItemProperty("mail").setValue(mail);

                MyEditButton myEditButton = buildEditButton(survey,mail);
                item.getItemProperty("update").setValue(myEditButton);
            }
        }
    }

    private void fillTableByMail(String mail) {
        CustomerSurveyService customerSurveyService = new CustomerSurveyService();

        List<CustomerSurvey> customerSurveyList = customerSurveyService.listCustomerSurveysByMail(mail);
        if(customerSurveyList !=null) {
            container.removeAllItems();
            for (CustomerSurvey customerSurvey : customerSurveyList) {
                Survey survey = customerSurvey.getSurvey();

                Item item = container.addItem(customerSurvey);
                item.getItemProperty("id").setValue(customerSurvey.getId());
                item.getItemProperty("name").setValue(survey.getName());
                item.getItemProperty("mail").setValue(customerSurvey.getMail());

                MyEditButton myEditButton = buildEditButton(survey,mail);
                item.getItemProperty("update").setValue(myEditButton);
            }
        }
    }

    private MyEditButton buildEditButton(Survey survey,String mail) {
        MyEditButton myEditButton = new MyEditButton();
        myEditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();
                contentComponent.addComponent(new PreparedSurveyPage(survey,mail,true));
            }
        });
        return myEditButton;
    }

    private Button buildSearchButton() {
        Button searchButton = new Button();
        searchButton.setIcon(FontAwesome.SEARCH);
        searchButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String mail = searchField.getValue();
                fillTableByMail(mail);
            }
        });
        return searchButton;
    }

    public Table getTable() {
        return table;
    }
}
