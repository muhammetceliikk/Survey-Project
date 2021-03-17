package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.EnumQType;
import com.uniyaz.core.domain.Question;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.MyAddButton;
import com.uniyaz.ui.component.MyEditButton;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

public class MyChoicePage extends VerticalLayout {

    private Question question;
    private MyAddButton addChoice;

    private Table table;
    private IndexedContainer container;
    private FormLayout mainFormLayout;

    public MyChoicePage(){

    }

    public MyChoicePage(Question question) {
        this(new Choice(),question);
    }

    public MyChoicePage(Choice choice, Question question) {

        this.question = question;

        setSizeFull();
        buildMainLayout();
        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

        //fillTable();
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        Label questionTitle = new Label();
        questionTitle.setValue("Question: "+question.getName());
        mainFormLayout.addComponent(questionTitle);

        buildTable();
        mainFormLayout.addComponent(table);

        addChoice = buildAddChoiceButton();
        mainFormLayout.addComponent(addChoice);
    }

    private void buildTable() {

        table = new Table();
        table.setPageLength(table.size());
        table.setSelectable(true);

        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "NAME", "Question Type", "Edit");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("question type", EnumQType.class, null);
        container.addContainerProperty("update", MyEditButton.class, null);
    }

    private MyAddButton buildAddChoiceButton() {
        MyAddButton myAddButton = new MyAddButton();
        myAddButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();/*
                QuestionWindow questionWindow = new QuestionWindow(myPanel);
                myUI.addWindow(questionWindow);*/
            }
        });
        return myAddButton;
    }
}
