package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.vaadin.data.Property;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class SurveyStartPage extends VerticalLayout {

    private TextField mail;

    private ComboBox surveyComboBox;

    private Button startButton;
    private FormLayout mainFormLayout;
    private Survey survey;

    public SurveyStartPage() {

        setSizeFull();
        buildMainLayout();
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();
        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

        mail = new TextField();
        mail.setCaption("Enter mail");
        mainFormLayout.addComponent(mail);

        surveyComboBox = buildSurveyComboBox();
        mainFormLayout.addComponent(surveyComboBox);

        startButton = buildStartButton();
        startButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        startButton.setCaption("START");
        mainFormLayout.addComponent(startButton);


    }

    private ComboBox buildSurveyComboBox() {

        ComboBox comboBox = new ComboBox();
        comboBox.setCaption("Select survey");
        comboBox.setNullSelectionAllowed(false);

        SurveyService surveyService = new SurveyService();
        List<Survey> surveyList=surveyService.listSurveys();
        if(surveyList!=null){
            for (Survey survey : surveyList) {
                comboBox.addItem(survey);
                comboBox.setItemCaption(survey,survey.getName());
            }
        }

        comboBox.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                survey =(Survey) valueChangeEvent.getProperty().getValue();
            }
        });

        return comboBox;
    }

    private Button buildStartButton() {

        Button startButton= new Button();
        startButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String mailString = mail.getValue();

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();
                contentComponent.addComponent(new PreparedSurveyPage(survey,mailString));
            }
        });

        return startButton;
    }
}
