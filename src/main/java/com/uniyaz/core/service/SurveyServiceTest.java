package com.uniyaz.core.service;

import com.uniyaz.core.domain.Survey;
import org.junit.Test;

import java.util.List;


public class SurveyServiceTest {

    @Test
    public void listSurveys() {
        SurveyService surveyService = new SurveyService();
        List<Survey> surveyList = surveyService.listSurveys();
        if (surveyList != null) {
            for (Survey survey : surveyList) {
                System.out.println(survey);
            }
        }
    }
}