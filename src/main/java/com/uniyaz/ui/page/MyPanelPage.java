package com.uniyaz.ui.page;

import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.MyAddButton;
import com.uniyaz.ui.component.MyEditButton;
import com.uniyaz.ui.component.MySaveButton;
import com.uniyaz.ui.myWindows.PanelWindow;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.*;

import java.util.List;

public class MyPanelPage extends VerticalLayout {

    private Survey survey;
    private Button addPanel;

    private Table table;
    private IndexedContainer container;
    private FormLayout mainFormLayout;

    public MyPanelPage() {

    }

    public MyPanelPage(Survey survey) {
        this(new MyPanel(),survey);
    }

    public MyPanelPage(MyPanel panel, Survey survey) {

        this.survey = survey;

        setSizeFull();
        buildMainLayout();
        addComponent(mainFormLayout);
        setComponentAlignment(mainFormLayout, Alignment.MIDDLE_CENTER);

        fillTable();

    }

    private void buildMainLayout() {

        mainFormLayout = new FormLayout();
        mainFormLayout.setSizeUndefined();

        Label surveyTitle = new Label();
        surveyTitle.setValue("Survey: "+survey.getName());
        mainFormLayout.addComponent(surveyTitle);

        buildTable();
        mainFormLayout.addComponent(table);

        addPanel = buildAddPanelButton();
        mainFormLayout.addComponent(addPanel);
    }

    private void buildTable() {

        table = new Table();
        table.setPageLength(table.size());
        table.setSelectable(true);

        buildContainer();
        table.setContainerDataSource(container);
        table.setColumnHeaders("ID", "NAME", "Edit");
    }

    private void buildContainer() {

        container = new IndexedContainer();
        container.addContainerProperty("id", Long.class, null);
        container.addContainerProperty("name", String.class, null);
        container.addContainerProperty("update", MyEditButton.class, null);
    }

    private void fillTable() {

        PanelService panelService = new PanelService();
        if(panelService.listPanelsById(survey) !=null){
            List<MyPanel> panelList =panelService.listPanelsById(survey);
            for (MyPanel myPanel : panelList) {
                Item item = container.addItem(myPanel);
                item.getItemProperty("id").setValue(myPanel.getId());
                item.getItemProperty("name").setValue(myPanel.getName());

                MyEditButton myEditButton = buildEditButton(myPanel);
                item.getItemProperty("update").setValue(myEditButton);
            }
        }
    }

    private MyEditButton buildEditButton(MyPanel myPanel) {
        MyEditButton myEditButton = new MyEditButton();
        myEditButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                PanelWindow panelWindow = new PanelWindow(myPanel,survey);
                myUI.addWindow(panelWindow);
            }
        });
        return myEditButton;
    }

    private Button buildAddPanelButton() {
        MyAddButton myAddButton = new MyAddButton();
        myAddButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                PanelWindow panelWindow = new PanelWindow(survey);
                myUI.addWindow(panelWindow);
            }
        });
        return myAddButton;
    }

    private MySaveButton buildAddQuestionButton(MyPanel myPanel) {
        MySaveButton mySaveButton = new MySaveButton();
        mySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();

                MyQuestionPage myQuestionPage = new MyQuestionPage(myPanel);
                contentComponent.addComponent(myQuestionPage);
            }
        });
        return mySaveButton;
    }


    public Table getTable() {
        return table;
    }
}
