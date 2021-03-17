package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.domain.Survey;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

public class MyTabSheet extends VerticalLayout {

    private SurveyListPage surveyListPage;
    private MyPanelPage myPanelPage;
    private MyQuestionPage myQuestionPage;

    private Survey survey;
    private MyPanel myPanel;
    private Question question;
    private Choice choice;

    private TabSheet tabsheet;
    private VerticalLayout surveyTab;
    private VerticalLayout panelTab;
    private VerticalLayout questionTab;
    private VerticalLayout choiceTab;


    public MyTabSheet() {

        buildTabSheet();

    }

    private void buildTabSheet() {

        tabsheet = new TabSheet();
        addComponent(tabsheet);

        addTabs();

        tabsheet.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent selectedTabChangeEvent) {

                TabSheet tabSheet = selectedTabChangeEvent.getTabSheet();
                Layout tab = (Layout) tabSheet.getSelectedTab();

                String caption = tabSheet.getTab(tab).getCaption();

                switch (caption){
                    case "Survey":
                        break;

                    case "Panel":
                        if(survey!=null){
                            tab.removeAllComponents();
                            myPanelPage= new MyPanelPage(survey);
                            myPanelPage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                                @Override
                                public void itemClick(ItemClickEvent itemClickEvent) {
                                    myPanel = (MyPanel) itemClickEvent.getItemId();
                                    question=null;
                                }
                            });
                            tab.addComponent(myPanelPage);
                        }
                        else{
                            Notification.show("You must choose a survey to access panel.");
                            tabSheet.setSelectedTab(surveyTab);
                        }
                        break;
                    case "Question":
                        if(myPanel!=null){
                            tab.removeAllComponents();
                            myQuestionPage= new MyQuestionPage(myPanel);
                            myQuestionPage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                                @Override
                                public void itemClick(ItemClickEvent itemClickEvent) {
                                    question = (Question) itemClickEvent.getItemId();
                                    choice=null;
                                }
                            });

                            tab.addComponent(myQuestionPage);
                        }
                        else{
                            Notification.show("You must choose a panel to access question.");
                            tabSheet.setSelectedTab(panelTab);
                        }
                        break;
                }
            }
        });
    }

    private void addTabs() {
        // Survey
        surveyListPage = new SurveyListPage();

        surveyTab = new VerticalLayout();
        surveyTab.setSizeFull();
        surveyTab.addComponent(surveyListPage);
        tabsheet.addTab(surveyTab,"Survey");

        surveyListPage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                survey = (Survey) itemClickEvent.getItemId();
                myPanel = null;
                question = null;
            }
        });

        //Panel
        myPanelPage = new MyPanelPage();
        panelTab = new VerticalLayout();
        panelTab.addComponent(myPanelPage);

        tabsheet.addTab(panelTab,"Panel");

        //Question
        MyQuestionPage myQuestionPage = new MyQuestionPage();

        questionTab = new VerticalLayout();
        questionTab.addComponent(myQuestionPage);
        questionTab.setCaption("Question");

        tabsheet.addComponent(questionTab);

        //Choice
        MyChoicePage myChoicePage = new MyChoicePage();

        choiceTab = new VerticalLayout();
        choiceTab.addComponent(myChoicePage);
        choiceTab.setCaption("Choice");

        tabsheet.addComponent(choiceTab);

    }
}
