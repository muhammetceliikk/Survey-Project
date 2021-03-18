package com.uniyaz.ui.page;

import com.uniyaz.core.domain.EnumQType;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.service.QuestionService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.Buttons.MyAddButton;
import com.uniyaz.ui.component.Buttons.MyEditButton;
import com.uniyaz.ui.myWindows.QuestionWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.util.List;

public class MyQuestionPage extends BasePage {

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

        fillPageByPanel(myPanel);
    }

    @Override
    public void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

//
//        Label panelTitle = new Label();
//        panelTitle.setValue("Panel: "+myPanel.getName());
//        mainFormLayout.addComponent(panelTitle);

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


    public void fillPageByPanel(MyPanel myPanel) {
        this.myPanel= myPanel;

        QuestionService questionService = new QuestionService();

        List<Question> questionList =questionService.listQuestionById(myPanel);
        fillTable(questionList);
    }

    private void fillTable(List<Question> questionList) {

        if(questionList!=null){
            container.removeAllItems();
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
                questionWindow.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent closeEvent) {
                        fillPageByPanel(myPanel);
                    }
                });
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
                questionWindow.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent closeEvent) {
                        fillPageByPanel(myPanel);
                    }
                });
            }
        });
        return myAddButton;
    }

    public Table getTable() {
        return table;
    }
}
