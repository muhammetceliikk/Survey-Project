package com.uniyaz.core.service;

import com.uniyaz.core.dao.AnswerDao;
import com.uniyaz.core.domain.Answer;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.domain.Survey;

import java.util.List;

public class AnswerService {

    private AnswerDao answerDao;

    public AnswerService() {
    }

    public void saveAnswer(List<Answer> answerList) {
        answerDao = new AnswerDao();
        answerDao.saveAnswer(answerList);
    }


    public void deleteAnswer(Answer answer) {
        answerDao = new AnswerDao();
        answerDao.deleteAnswer(answer);
    }

    public List<Answer> listAnswers() {
        answerDao = new AnswerDao();
        return answerDao.listAnswers();
    }

    public List<Answer> listAnswersByMail(String mail, Survey survey){
        answerDao = new AnswerDao();
        return answerDao.listAnswersByMail(mail,survey);
    }

    public List<Answer> listAnswersById(Question question) {
        answerDao = new AnswerDao();
        return answerDao.listAnswersById(question);
    }

}
