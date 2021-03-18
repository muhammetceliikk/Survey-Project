package com.uniyaz.ui.page;

import com.uniyaz.core.domain.*;
import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.service.*;
import com.uniyaz.ui.MyUI;
import com.uniyaz.ui.component.ContentComponent;
import com.uniyaz.ui.component.Buttons.MySaveButton;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

import java.text.SimpleDateFormat;
import java.util.*;

public class PreparedSurveyPage extends VerticalLayout {

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
    private Label surveyTitle;

    private List<Object> fields;
    private List<OptionGroup> singleChoices;
    private List<OptionGroup> multipleChoices;
    private List<Answer> answerList;

    MySaveButton mySaveButton;
    private String mail;


    public PreparedSurveyPage(Survey survey) {

        this(survey,"");

        mySaveButton.setVisible(false);
    }

    public PreparedSurveyPage(Survey survey, String mail) {

        this.mail = mail;
        this.survey = survey;
        buildMainLayout();
    }

    private void buildMainLayout() {

        mainLayout = new FormLayout();

        createSurvey(survey);

        mySaveButton = buildSaveButton();
        mainLayout.addComponent(mySaveButton);
        addComponent(mainLayout);
    }

    private MySaveButton buildSaveButton() {

        MySaveButton mySaveButton = new MySaveButton();
        mySaveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                CustomerSurveyService customerSurveyService = new CustomerSurveyService();
                CustomerSurvey customerSurvey = new CustomerSurvey();
                customerSurvey.setSurvey(survey);
                customerSurvey.setMail(mail);
                customerSurveyService.saveCustomerSurvey(customerSurvey);

                answerList = new ArrayList<>();
                addAnswerstoList();
                AnswerService answerService = new AnswerService();
                answerService.saveAnswer(answerList);

                MyUI myUI = (MyUI) UI.getCurrent();
                ContentComponent contentComponent = myUI.getContentComponent();
                contentComponent.addComponent(new FilledSurveysPage());
            }
        });

        return mySaveButton;
    }

    public void createSurvey(Survey survey) {

        fields = new ArrayList<>();
        singleChoices = new ArrayList<>();
        multipleChoices = new ArrayList<>();

        surveyTitle = new Label();
        surveyTitle.setValue(survey.getName());
        surveyTitle.addStyleName(ValoTheme.LABEL_BOLD);
        surveyTitle.addStyleName(ValoTheme.LABEL_H1);

        mainLayout.addComponent(surveyTitle);
        mainLayout.setComponentAlignment(surveyTitle,Alignment.MIDDLE_CENTER);

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
                        createTextField(question);
                        questionLayout.addComponent(choiceTextField);
                        break;

                    case DateField:
                        createDateField(question);
                        questionLayout.addComponent(choiceDateField);
                        break;

                    case Single_Choice:
                        createSingleChoice(question);
                        questionLayout.addComponent(singleChoice);
                        break;

                    case Multiple_Choice:
                        createMultipleChoice(question);
                        questionLayout.addComponent(multipleChoice);
                        break;
                }
            }
            myMainPanel.setContent(panelLayout);
        }
    }

    private void createTextField(Question question) {
        choiceTextField = new TextField();
        choiceTextField.setId(String.valueOf(question.getId()));
        fields.add(choiceTextField);
    }

    private void createDateField(Question question) {
        choiceDateField = new DateField();
        choiceDateField.setId(String.valueOf(question.getId()));
        fields.add(choiceDateField);
    }

    private void createMultipleChoice(Question question) {
        choiceService = new ChoiceService();
        List<Choice> choiceList2 = choiceService.listChoicesById(question);
        multipleChoice = new OptionGroup();
        for (Choice choice : choiceList2) {
            multipleChoice.addItem(choice);
            multipleChoice.setItemCaption(choice,choice.getName());
            multipleChoice.setMultiSelect(true);
            multipleChoice.setId(String.valueOf(question.getId()));
        }
        multipleChoices.add(multipleChoice);
    }

    private void createSingleChoice(Question question) {
        choiceService = new ChoiceService();
        List<Choice> choiceList = choiceService.listChoicesById(question);
        singleChoice = new OptionGroup();
        for (Choice choice : choiceList) {
            singleChoice.addItem(choice);
            singleChoice.setItemCaption(choice,choice.getName());
            singleChoice.setMultiSelect(false);
            singleChoice.setId(String.valueOf(question.getId()));
        }
        singleChoices.add(singleChoice);
    }

    private void addAnswerstoList(){
        for (Object field : fields) {
            Answer answer = new Answer();
            answer.setMail(mail);
            if(field instanceof TextField){
                TextField textField = (TextField) field;
                answer.setQuestionID(Long.parseLong(textField.getId()));
                answer.setAnswer(textField.getValue());

            }
            else if(field instanceof DateField){
                DateField dateField = (DateField) field;
                answer.setQuestionID(Long.parseLong(dateField.getId()));
                Date date = dateField.getValue();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String strDate = formatter.format(date);
                answer.setAnswer(strDate);
            }
            answerList.add(answer);
        }

        for (OptionGroup singleChoice : singleChoices) {
            Answer answer = new Answer();
            answer.setMail(mail);
            answer.setQuestionID(Long.parseLong(singleChoice.getId()));
            Choice choice = (Choice) singleChoice.getValue();
            answer.setChoiceID(choice.getId());
            answerList.add(answer);
        }

        for (OptionGroup multipleChoices : multipleChoices) {
            Collection selectedItems = (Collection) multipleChoices.getValue();
            Iterator iterator = selectedItems.iterator();
            while (iterator.hasNext()) {
                Answer answer = new Answer();
                answer.setMail(mail);
                answer.setQuestionID(Long.parseLong(multipleChoices.getId()));
                Choice choice = (Choice) iterator.next();
                answer.setChoiceID(choice.getId());
                answerList.add(answer);
            }
        }

    }
}
