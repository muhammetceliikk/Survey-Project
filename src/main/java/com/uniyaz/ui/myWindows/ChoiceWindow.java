package com.uniyaz.ui.myWindows;

import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.service.ChoiceService;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.core.service.QuestionService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.MyDeleteButton;
import com.uniyaz.ui.component.MyQTypeComboBox;
import com.uniyaz.ui.component.MySaveButton;
import com.uniyaz.ui.page.MyTabSheet;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;


public class ChoiceWindow extends Window {

    @PropertyId("id")
    private TextField id;

    @PropertyId("name")
    private TextField name;

    private TextField questionName;

    private Question question;

    private ChoiceService choiceService;

    private VerticalLayout verticalLayout;
    private FormLayout mainFormLayout;
    private BeanItem<Choice> choiceBeanItem;
    private FieldGroup binder;
    private MySaveButton saveButton;
    private MyDeleteButton deleteButton;

    public ChoiceWindow() {
    }

    public ChoiceWindow(Question question) {
        this(new Choice(),question);
    }

    public ChoiceWindow(Choice choice, Question question) {

        this.question = question;

        choiceBeanItem = new BeanItem<Choice>(choice);
        binder = new FieldGroup(choiceBeanItem);

        setWidth(45, Unit.PERCENTAGE);
        setHeight(300, Unit.PIXELS);
        center();

        buildMainLayout();
        verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(mainFormLayout);

        verticalLayout.setComponentAlignment(mainFormLayout,Alignment.MIDDLE_CENTER);

        setContent(verticalLayout);

        binder.bindMemberFields(this);
        questionName.setEnabled(false);
        id.setEnabled(false);
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();
        mainFormLayout.setMargin(true);

        questionName = new TextField();
        questionName.setValue(question.getName());
        questionName.setCaption("Question Title");
        questionName.setNullRepresentation("");
        mainFormLayout.addComponent(questionName);

        id = new TextField();
        id.setCaption("ID");
        id.setNullRepresentation("");
        mainFormLayout.addComponent(id);

        name = new TextField();
        name.setCaption("Choice value");
        name.setNullRepresentation("");
        mainFormLayout.addComponent(name);

        saveButton = buildSaveButton();
        mainFormLayout.addComponent(saveButton);

        deleteButton = buildDeleteButton();
        mainFormLayout.addComponent(deleteButton);
    }

    private MySaveButton buildSaveButton() {
        saveButton= new MySaveButton();
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                    Choice choice = choiceBeanItem.getBean();
                    choice.setQuestion(question);
                    choiceService = new ChoiceService();
                    choiceService.saveChoice(choice);
                    close();
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        return saveButton;
    }

    private MyDeleteButton buildDeleteButton() {

        deleteButton= new MyDeleteButton();
        deleteButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                    Choice choice = choiceBeanItem.getBean();
                    choiceService = new ChoiceService();
                    choiceService.deleteChoice(choice);
                    close();
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        return deleteButton;
    }

}
