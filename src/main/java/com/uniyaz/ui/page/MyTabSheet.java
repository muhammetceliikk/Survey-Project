package com.uniyaz.ui.page;

import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.domain.Survey;
import com.uniyaz.ui.MyUI;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.*;

public class MyTabSheet extends VerticalLayout {

    private SurveyListPage surveyListPage;
    private MyPanelPage myPanelPage;
    private MyQuestionPage myQuestionPage;
    private MyChoicePage myChoicePage;

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
                            myPanelPage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                                @Override
                                public void itemClick(ItemClickEvent itemClickEvent) {
                                    myPanel = (MyPanel) itemClickEvent.getItemId();

                                    myQuestionPage.fillPageByPanel(myPanel);
                                    question=null;
                                    choice=null;
                                }
                            });
                        }
                        else{
                            Notification.show("You must choose a survey to access panel.");
                            tabSheet.setSelectedTab(surveyTab);
                        }
                        break;
                    case "Question":
                        if(myPanel!=null){
                            myQuestionPage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                                @Override
                                public void itemClick(ItemClickEvent itemClickEvent) {
                                    question = (Question) itemClickEvent.getItemId();

                                    myChoicePage.fillPageByQuestion(question);
                                    choice=null;
                                }
                            });
                        }
                        else{
                            Notification.show("You must choose a panel to access question.");
                            tabSheet.setSelectedTab(panelTab);
                        }
                        break;
                    case "Choice":
                        if(question!=null){
                            myChoicePage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                                @Override
                                public void itemClick(ItemClickEvent itemClickEvent) {
                                    choice = (Choice) itemClickEvent.getItemId();
                                }
                            });
                        }
                        else{
                            Notification.show("You must choose a panel to access question.");
                            tabSheet.setSelectedTab(questionTab);
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

                myPanelPage.fillPageBySurvey(survey);
                myPanel=null;
                question=null;
                choice=null;
            }
        });

        //Panel
        myPanelPage = new MyPanelPage();
        panelTab = new VerticalLayout();
        panelTab.addComponent(myPanelPage);

        tabsheet.addTab(panelTab,"Panel");

        //Question
        myQuestionPage = new MyQuestionPage();

        questionTab = new VerticalLayout();
        questionTab.addComponent(myQuestionPage);
        questionTab.setCaption("Question");

        tabsheet.addComponent(questionTab);

        //Choice
        myChoicePage = new MyChoicePage();

        choiceTab = new VerticalLayout();
        choiceTab.addComponent(myChoicePage);
        choiceTab.setCaption("Choice");

        tabsheet.addComponent(choiceTab);
    }

    public VerticalLayout getSurveyTab() {
        return surveyTab;
    }

    public VerticalLayout getPanelTab() {
        return panelTab;
    }

    public VerticalLayout getQuestionTab() {
        return questionTab;
    }

    public VerticalLayout getChoiceTab() {
        return choiceTab;
    }

    public TabSheet getTabsheet() {
        return tabsheet;
    }
}
