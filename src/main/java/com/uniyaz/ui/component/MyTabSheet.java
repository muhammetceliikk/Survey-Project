package com.uniyaz.ui.component;

import com.uniyaz.core.domain.*;
import com.uniyaz.ui.page.MyChoicePage;
import com.uniyaz.ui.page.MyPanelPage;
import com.uniyaz.ui.page.MyQuestionPage;
import com.uniyaz.ui.page.SurveyListPage;
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

        buildTabListener();
    }

    private void addTabs() {
        // Survey
        surveyListPage = new SurveyListPage();
        surveyListPage.setSizeUndefined();

        surveyTab = new VerticalLayout();
        surveyTab.setSizeFull();
        surveyTab.addComponent(surveyListPage);
        surveyTab.setComponentAlignment(surveyListPage,Alignment.MIDDLE_CENTER);
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

    private void buildTabListener() {

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
                        if(question!=null && !(EnumQType.TextField.equals(question.getQType())) && !(EnumQType.DateField.equals(question.getQType()))){
                            myChoicePage.getTable().addItemClickListener(new ItemClickEvent.ItemClickListener() {
                                @Override
                                public void itemClick(ItemClickEvent itemClickEvent) {
                                    choice = (Choice) itemClickEvent.getItemId();
                                }
                            });
                        }
                        else{
                            Notification.show("You must choose a question to access choice.\nOr Your Question type should not be Textfield or Datafield");
                            tabSheet.setSelectedTab(questionTab);
                        }
                        break;
                }
            }
        });
    }

}
