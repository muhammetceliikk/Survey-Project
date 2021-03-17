package com.uniyaz.core.service;

import com.uniyaz.core.dao.QuestionDao;
import com.uniyaz.core.domain.MyPanel;
import com.uniyaz.core.domain.Question;

import java.util.List;

public class QuestionService {
    
    private QuestionDao questionDao;

    public QuestionService() {
    }

    public void saveQuestion(Question question) {
        questionDao = new QuestionDao();
        questionDao.saveQuestion(question);
    }

    public List<Question> listQuestions() {
        questionDao = new QuestionDao();
        return questionDao.listQuestions();
    }

    public List<Question> listQuestionById(MyPanel myPanel) {
        questionDao = new QuestionDao();
        return questionDao.listQuestionsById(myPanel);
    }
    
}
