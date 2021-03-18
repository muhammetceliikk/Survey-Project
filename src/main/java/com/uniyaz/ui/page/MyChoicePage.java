package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.service.ChoiceService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.MyAddButton;
import com.uniyaz.ui.component.MyEditButton;
import com.uniyaz.ui.myWindows.ChoiceWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.util.List;

public class MyChoicePage extends BasePage {

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

        fillPageByQuestion(question);
    }

    @Override
    public void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

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
        table.setColumnHeaders("ID", "VALUE", "Edit");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("update", MyEditButton.class, null);
    }

    public void fillPageByQuestion(Question question) {
        this.question = question;

        ChoiceService choiceService = new ChoiceService();
        List<Choice> choiceList =choiceService.listChoicesById(question);
        fillTable(choiceList);
    }

    private void fillTable(List<Choice> choiceList) {

        if(choiceList !=null){
            container.removeAllItems();
            for (Choice choice : choiceList) {
                Item item = container.addItem(choice);
                item.getItemProperty("id").setValue(choice.getId());
                item.getItemProperty("name").setValue(choice.getName());

                MyEditButton myEditButton = buildEditButton(choice);
                item.getItemProperty("update").setValue(myEditButton);
            }
        }
    }

    private MyEditButton buildEditButton(Choice choice) {
        MyEditButton myEditButton = new MyEditButton();
        myEditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ChoiceWindow choiceWindow = new ChoiceWindow(choice,question);
                myUI.addWindow(choiceWindow);
                choiceWindow.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent closeEvent) {
                        fillPageByQuestion(question);
                    }
                });
            }
        });
        return myEditButton;
    }

    private MyAddButton buildAddChoiceButton() {
        MyAddButton myAddButton = new MyAddButton();
        myAddButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ChoiceWindow choiceWindow = new ChoiceWindow(question);
                myUI.addWindow(choiceWindow);
                choiceWindow.addCloseListener(new Window.CloseListener() {
                    @Override
                    public void windowClose(Window.CloseEvent closeEvent) {
                        fillPageByQuestion(question);
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
