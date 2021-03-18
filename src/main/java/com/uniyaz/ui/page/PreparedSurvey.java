package com.uniyaz.ui.page;

import com.uniyaz.core.domain.*;
import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.service.ChoiceService;
import com.uniyaz.core.service.PanelService;
import com.uniyaz.core.service.QuestionService;
import com.uniyaz.ui.component.MySaveButton;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;


public class PreparedSurvey extends Window {

    private PanelService panelService;
    private QuestionService questionService;
    private ChoiceService choiceService;

    private FormLayout mainLayout;
    private FormLayout panelLayout;
    private Panel myMainPanel;
    private HorizontalLayout questionLayout;
    private HorizontalLayout choiceLayout;
    private Survey survey;

    private OptionGroup singleChoice;
    private OptionGroup multipleChoice;
    private TextField choiceTextField;
    private DateField choiceDateField;

    private Label panelName;
    private Label questionName;

    public PreparedSurvey(Survey survey) {
        this.survey = survey;
        buildMainLayout();
    }

    private void buildMainLayout() {
        mainLayout = new FormLayout();
        createSurvey(survey);

        MySaveButton mySaveButton = new MySaveButton();
        mySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
            }
        });
        mainLayout.addComponent(mySaveButton);
        setContent(mainLayout);
    }

    public void createSurvey(Survey survey) {
        panelService = new PanelService();
        List<MyPanel> myPanelList = panelService.listPanelsById(survey);
        for (MyPanel myPanel : myPanelList) {
            myMainPanel = new Panel();

            panelLayout = new FormLayout();
            panelName = new Label();
            panelName.setValue(myPanel.getName());
            panelName.addStyleName(ValoTheme.LABEL_BOLD);
            panelName.addStyleName(ValoTheme.LABEL_H2);
            panelLayout.addComponent(panelName);

            mainLayout.addComponent(myMainPanel);

            questionService = new QuestionService();
            List<Question> questionList = questionService.listQuestionById(myPanel);
            for (Question question : questionList) {
                questionLayout = new HorizontalLayout();
                questionLayout.setSpacing(true);
                questionName = new Label();
                questionName.setValue(question.getName());
                questionLayout.addComponent(questionName);
                panelLayout.addComponent(questionLayout);

                switch (question.getQType()) {
                    case TextField:
                        choiceLayout = new HorizontalLayout();
                        choiceLayout.setSpacing(true);
                        choiceTextField= new TextField();
                        choiceLayout.addComponent(choiceTextField);
                        questionLayout.addComponent(choiceLayout);
                        break;
                    case DateField:
                        choiceLayout = new HorizontalLayout();
                        choiceLayout.setSpacing(true);
                        choiceDateField = new DateField();
                        choiceLayout.addComponent(choiceDateField);
                        questionLayout.addComponent(choiceLayout);
                        break;
                    case Single_Choice:
                        choiceService = new ChoiceService();
                        List<Choice> choiceList = choiceService.listChoicesById(question);
                        singleChoice = new OptionGroup();
                        for (Choice choice : choiceList) {
                            choiceLayout = new HorizontalLayout();
                            choiceLayout.setSpacing(true);
                            singleChoice.addItem(choice.getName());
                            singleChoice.setMultiSelect(false);
                            choiceLayout.addComponent(singleChoice);
                            questionLayout.addComponent(choiceLayout);
                        }
                        break;
                    case Multiple_Choice:
                        choiceService = new ChoiceService();
                        List<Choice> choiceList2 = choiceService.listChoicesById(question);
                        multipleChoice = new OptionGroup();
                        for (Choice choice : choiceList2) {
                            choiceLayout = new HorizontalLayout();
                            choiceLayout.setSpacing(true);
                            multipleChoice.addItem(choice.getName());
                            multipleChoice.setMultiSelect(true);
                            choiceLayout.addComponent(multipleChoice);
                            questionLayout.addComponent(choiceLayout);
                        }
                        break;
                }
            }
            myMainPanel.setContent(panelLayout);
        }
    }
}
