package com.uniyaz.ui.page;

import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.ui.component.MySaveButton;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

public class MyPanelPage extends VerticalLayout {

    @PropertyId("id")
    private TextField id;

    @PropertyId("name")
    private TextField name;

    private TextField surveyName;

    private Survey survey;

    private PanelService panelService;
    private FormLayout mainFormLayout;
    private BeanItem<MyPanel> panelBeanItem;
    private FieldGroup binder;
    private MySaveButton saveButton;

    public MyPanelPage() {

    }

    public MyPanelPage(Survey survey) {
        this(new MyPanel(),survey);
    }

    public MyPanelPage(MyPanel panel, Survey survey) {

        this.survey = survey;

        panelBeanItem = new BeanItem<MyPanel>(panel);
        binder = new FieldGroup(panelBeanItem);

        setSizeFull();
        buildMainLayout();
        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

        binder.bindMemberFields(this);
        surveyName.setEnabled(false);
        id.setEnabled(false);
    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        id = new TextField();
        id.setCaption("ID");
        id.setNullRepresentation("");
        mainFormLayout.addComponent(id);

        surveyName = new TextField();
        surveyName.setValue(survey.getName());
        surveyName.setCaption("Survey Title");
        surveyName.setNullRepresentation("");
        mainFormLayout.addComponent(surveyName);

        name = new TextField();
        name.setCaption("Panel Title");
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
                    MyPanel panel = panelBeanItem.getBean();
                    panel.setSurvey(survey);
                    panelService = new PanelService();
                    panelService.savePanel(panel);
                } catch (FieldGroup.CommitException e) {
                    e.printStackTrace();
                }
            }
        });
        return saveButton;
    }
}
