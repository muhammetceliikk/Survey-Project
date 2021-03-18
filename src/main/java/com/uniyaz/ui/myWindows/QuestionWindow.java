package com.uniyaz.ui.myWindows;

import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.service.QuestionService;
import com.uniyaz.ui.component.Buttons.MyDeleteButton;
import com.uniyaz.ui.component.MyQTypeComboBox;
import com.uniyaz.ui.component.Buttons.MySaveButton;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;


public class QuestionWindow extends Window {

    @PropertyId("id")
    private TextField id;

    @PropertyId("name")
    private TextField name;

    @PropertyId("QType")
    private MyQTypeComboBox QType;

    private TextField panelName;

    private MyPanel myPanel;

    private QuestionService questionService;

    private VerticalLayout verticalLayout;
    private FormLayout mainFormLayout;
    private BeanItem<Question> questionBeanItem;
    private FieldGroup binder;
    private MySaveButton saveButton;
    private MyDeleteButton deleteButton;

    public QuestionWindow() {
    }

    public QuestionWindow(MyPanel myPanel) {
        this(new Question(),myPanel);
    }

    public QuestionWindow(Question question, MyPanel myPanel) {

        this.myPanel = myPanel;

        questionBeanItem = new BeanItem<Question>(question);
        binder = new FieldGroup(questionBeanItem);

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
        panelName.setEnabled(false);
        id.setEnabled(false);
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();
        mainFormLayout.setMargin(true);

        panelName = new TextField();
        panelName.setValue(myPanel.getName());
        panelName.setCaption("Panel Title");
        panelName.setNullRepresentation("");
        mainFormLayout.addComponent(panelName);

        id = new TextField();
        id.setCaption("ID");
        id.setNullRepresentation("");
        mainFormLayout.addComponent(id);

        name = new TextField();
        name.setCaption("Question Title");
        name.setNullRepresentation("");
        mainFormLayout.addComponent(name);

        QType = new MyQTypeComboBox();
        QType.setCaption("Question Type");
        mainFormLayout.addComponent(QType);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        mainFormLayout.addComponent(buttonLayout);

        saveButton = buildSaveButton();
        buttonLayout.addComponent(saveButton);

        if (questionBeanItem.getBean().getId() != null) {
            deleteButton = buildDeleteButton();
            buttonLayout.addComponent(deleteButton);
        }

    }

    private MySaveButton buildSaveButton() {
        saveButton= new MySaveButton();
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                    Question question = questionBeanItem.getBean();
                    question.setPanel(myPanel);
                    questionService = new QuestionService();
                    questionService.saveQuestion(question);
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
                    Question question = questionBeanItem.getBean();
                    question.setPanel(myPanel);
                    questionService = new QuestionService();
                    questionService.deleteQuestion(question);
                    close();
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        return deleteButton;
    }
}
