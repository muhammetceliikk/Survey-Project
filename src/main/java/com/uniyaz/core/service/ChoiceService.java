package com.uniyaz.core.service;

import com.uniyaz.core.dao.ChoiceDao;
import com.uniyaz.core.domain.Choice;
import com.uniyaz.core.domain.Question;
import com.uniyaz.core.domain.Survey;

import java.util.List;

public class ChoiceService {

    private ChoiceDao choiceDao;

    public ChoiceService() {
    }

    public void saveChoice(Choice choice) {
        choiceDao = new ChoiceDao();
        choiceDao.saveChoice(choice);
    }


    public void deleteChoice(Choice choice) {
        choiceDao = new ChoiceDao();
        choiceDao.deleteChoice(choice);
    }

    public List<Choice> listChoices() {
        choiceDao = new ChoiceDao();
        return choiceDao.listChoices();
    }

    public List<Choice> listChoicesById(Question question) {
        choiceDao = new ChoiceDao();
        return choiceDao.listChoicesById(question);
    }

}
