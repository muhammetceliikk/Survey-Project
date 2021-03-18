package com.uniyaz.core.service;

import com.uniyaz.core.dao.SurveyDao;
import com.uniyaz.core.domain.Survey;

import java.util.List;

public class SurveyService {

    SurveyDao surveyDao;

    public SurveyService() {

    }

    public void saveSurvey(Survey survey){
        surveyDao = new SurveyDao();
        surveyDao.saveSurvey(survey);
    }

    public void deleteSurvey(Survey survey) {
        surveyDao = new SurveyDao();
        surveyDao.deleteSurvey(survey);
    }

    public List<Survey> listSurveys() {
        surveyDao = new SurveyDao();
        return surveyDao.listSurveys();
    }

}
