package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.SurveyService;
import com.uniyaz.ui.component.MyQTypeComboBox;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

public class SurveyStartPage extends VerticalLayout {

    private TextField mail;
    private Button startButton;
    private ComboBox surveyComboBox;
    private FormLayout mainFormLayout;

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

        startButton = new Button();
        startButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        startButton.setCaption("START");
        mainFormLayout.addComponent(startButton);

    }

    private ComboBox buildSurveyComboBox() {
        ComboBox comboBox = new ComboBox();
        comboBox.setCaption("Select survey");
        SurveyService surveyService = new SurveyService();
        if(surveyService.listSurveys()!=null){
            List<Survey> surveyList =surveyService.listSurveys();
            for (Survey survey : surveyList) {
                comboBox.addItem(survey);
                comboBox.setItemCaption(survey,survey.getName());
            }
        }
        return comboBox;
    }
}
