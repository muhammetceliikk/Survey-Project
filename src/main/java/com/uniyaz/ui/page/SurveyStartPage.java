package com.uniyaz.ui.page;

import com.uniyaz.ui.component.MyComboBox;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class SurveyStartPage extends VerticalLayout {

    private TextField mail;
    private Button startButton;
    private MyComboBox surveyComboBox;
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

        surveyComboBox = new MyComboBox();
        surveyComboBox.setCaption("Select survey");
        mainFormLayout.addComponent(surveyComboBox);

        startButton = new Button();
        startButton.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        startButton.setCaption("START");
        mainFormLayout.addComponent(startButton);

    }
}
