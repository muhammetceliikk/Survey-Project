package com.uniyaz.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "ANSWER")
public class Answer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAIL", length = 70)
    private String mail;

    @Column(name = "ID_QUESTION")
    private Long questionID;

    @Column(name = "ID_CHOICE")
    private Long choiceID;

    @Column(name = "ANSWER")
    private String answer;

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    public Long getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(Long choiceID) {
        this.choiceID = choiceID;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
