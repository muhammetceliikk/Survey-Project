package com.uniyaz.ui.myWindows;

import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.MySaveButton;
import com.uniyaz.ui.page.MyTabSheet;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;

public class PanelWindow extends Window {

    @PropertyId("id")
    private TextField id;

    @PropertyId("name")
    private TextField name;

    private TextField surveyName;

    private Survey survey;

    private PanelService panelService;

    private VerticalLayout verticalLayout;
    private FormLayout mainFormLayout;
    private BeanItem<MyPanel> panelBeanItem;
    private FieldGroup binder;
    private MySaveButton saveButton;

    public PanelWindow() {
    }

    public PanelWindow(Survey survey) {
        this(new MyPanel(),survey);
    }

    public PanelWindow(MyPanel myPanel, Survey survey) {

        this.survey = survey;

        panelBeanItem = new BeanItem<MyPanel>(myPanel);
        binder = new FieldGroup(panelBeanItem);

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
        surveyName.setEnabled(false);
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
}
