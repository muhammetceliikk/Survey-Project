package com.uniyaz.core.dao;

import com.uniyaz.core.domain.Answer;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class AnswerDaoTest {

    @Test
    public void listAnswers(){

        AnswerDao answerDao = new AnswerDao();

        answerDao = new AnswerDao();
        List<Answer> answerList = answerDao.listAnswers();
        for (Answer answer : answerList) {
            System.out.println(answer.getMail());
            System.out.println(answer.getQuestion().getName());
            if(Objects.isNull(answer.getAnswer())){
                System.out.println(answer.getChoice().getName());

            }
            else {
                System.out.println(answer.getAnswer());
            }
            System.out.println("+++++++++++++");
        }
    }

}