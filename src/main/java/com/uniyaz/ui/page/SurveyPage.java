package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.component.MySaveButton;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

public class SurveyPage extends VerticalLayout {

    @PropertyId("id")
    private TextField id;

    @PropertyId("name")
    private TextField name;

    private SurveyService surveyService;
    private FormLayout mainFormLayout;
    private BeanItem<Survey> surveyBeanItem;
    private FieldGroup binder;
    private MySaveButton saveButton;

    public SurveyPage() {
        this(new Survey());
    }

    public SurveyPage(Survey survey) {

        surveyBeanItem = new BeanItem<Survey>(survey);
        binder = new FieldGroup(surveyBeanItem);

        setSizeFull();
        buildMainLayout();
        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

        binder.bindMemberFields(this);
        id.setEnabled(false);
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        id = new TextField();
        id.setCaption("ID");
        id.setNullRepresentation("");
        mainFormLayout.addComponent(id);

        name = new TextField();
        name.setCaption("Survey Title");
        name.setNullRepresentation("");
        mainFormLayout.addComponent(name);

        saveButton = buildSaveButton();
        mainFormLayout.addComponent(saveButton);
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
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        return saveButton;
    }
}
