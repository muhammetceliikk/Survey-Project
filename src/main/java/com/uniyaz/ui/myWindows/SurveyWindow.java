package com.uniyaz.ui.myWindows;

import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.Buttons.MyDeleteButton;
import com.uniyaz.ui.component.Buttons.MySaveButton;
import com.uniyaz.ui.component.MyTabSheet;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

public class SurveyWindow extends Window {

    @PropertyId("id")
    private TextField id;

    @PropertyId("name")
    private TextField name;

    private SurveyService surveyService;

    private VerticalLayout verticalLayout;
    private FormLayout mainFormLayout;
    private BeanItem<Survey> surveyBeanItem;
    private FieldGroup binder;
    private MySaveButton saveButton;
    private MyDeleteButton deleteButton;

    public SurveyWindow() {
        this(new Survey());
    }

    public SurveyWindow(Survey survey) {

        surveyBeanItem = new BeanItem<Survey>(survey);
        binder = new FieldGroup(surveyBeanItem);

        setWidth(45, Unit.PERCENTAGE);
        setHeight(250, Unit.PIXELS);
        center();

        buildMainLayout();

        verticalLayout = new VerticalLayout();
        verticalLayout.setSizeFull();
        verticalLayout.addComponent(mainFormLayout);

        verticalLayout.setComponentAlignment(mainFormLayout,Alignment.MIDDLE_CENTER);

        setContent(verticalLayout);

        binder.bindMemberFields(this);
        id.setEnabled(false);
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();
        mainFormLayout.setMargin(true);

        id = new TextField();
        id.setCaption("ID");
        id.setNullRepresentation("");
        mainFormLayout.addComponent(id);

        name = new TextField();
        name.setCaption("Survey Title");
        name.setNullRepresentation("");
        mainFormLayout.addComponent(name);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        mainFormLayout.addComponent(buttonLayout);

        saveButton = buildSaveButton();
        buttonLayout.addComponent(saveButton);

        if (surveyBeanItem.getBean().getId() != null) {
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

                    Survey survey = surveyBeanItem.getBean();
                    surveyService = new SurveyService();
                    surveyService.saveSurvey(survey);

                    close();

                    MyUI myUI = (MyUI) UI.getCurrent();
                    ContentComponent contentComponent = myUI.getContentComponent();

                    //contette tabsheet var.belki tabsheetin içindeki surveyin tableını reslersin.
                    MyTabSheet myTabSheet = new MyTabSheet();
                    contentComponent.addComponent(myTabSheet);

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
                    Survey survey = surveyBeanItem.getBean();
                    surveyService = new SurveyService();
                    surveyService.deleteSurvey(survey);

                    close();

                    MyUI myUI = (MyUI) UI.getCurrent();
                    ContentComponent contentComponent = myUI.getContentComponent();

                    //contette tabsheet var.belki tabsheetin içindeki surveyin tableını reslersin.
                    MyTabSheet myTabSheet = new MyTabSheet();
                    contentComponent.addComponent(myTabSheet);
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        return deleteButton;
    }
}
