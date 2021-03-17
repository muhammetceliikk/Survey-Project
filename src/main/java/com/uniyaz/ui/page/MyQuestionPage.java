package com.uniyaz.ui.page;

import com.uniyaz.core.domain.EnumQType;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.service.QuestionService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.*;
import com.uniyaz.ui.myWindows.QuestionWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.util.List;

public class MyQuestionPage extends VerticalLayout {

    private MyPanel myPanel;
    private MyAddButton addQuestion;

    private Table table;
    private IndexedContainer container;
    private FormLayout mainFormLayout;

    public MyQuestionPage(){

    }

    public MyQuestionPage(MyPanel myPanel) {
        this(new Question(),myPanel);
    }

    public MyQuestionPage(Question question, MyPanel myPanel) {

        this.myPanel = myPanel;

        setSizeFull();
        buildMainLayout();
        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

        fillTable();
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        Label panelTitle = new Label();
        panelTitle.setValue("Panel: "+myPanel.getName());
        mainFormLayout.addComponent(panelTitle);

        buildTable();
        mainFormLayout.addComponent(table);

        addQuestion = buildAddQuestionButton();
        mainFormLayout.addComponent(addQuestion);
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

    private void fillTable() {

        QuestionService questionService = new QuestionService();
        if(questionService.listQuestionById(myPanel) !=null){
            List<Question> questionList =questionService.listQuestionById(myPanel);
            for (Question question : questionList) {
                Item item = container.addItem(question);
                item.getItemProperty("id").setValue(question.getId());
                item.getItemProperty("name").setValue(question.getName());
                item.getItemProperty("question type").setValue(question.getQType());

                MyEditButton myEditButton = buildEditButton(question);
                item.getItemProperty("update").setValue(myEditButton);

            }
        }
    }

    private MyEditButton buildEditButton(Question question) {
        MyEditButton myEditButton = new MyEditButton();
        myEditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                QuestionWindow questionWindow = new QuestionWindow(question,myPanel);
                myUI.addWindow(questionWindow);
            }
        });
        return myEditButton;
    }

    private MyAddButton buildAddQuestionButton() {
        MyAddButton myAddButton = new MyAddButton();
        myAddButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                QuestionWindow questionWindow = new QuestionWindow(myPanel);
                myUI.addWindow(questionWindow);
            }
        });
        return myAddButton;
    }

    private MySaveButton buildAddChoiceButton(Question question) {
        MySaveButton mySaveButton = new MySaveButton();
        mySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();
/*
                MyChoicePage myChoicePage = new MyChoicePage(question);
                contentComponent.addComponent(myChoicePage);*/
            }
        });
        return mySaveButton;
    }

    public Table getTable() {
        return table;
    }
}
