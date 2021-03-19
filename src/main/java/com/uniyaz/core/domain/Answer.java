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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_QUESTION", foreignKey = @ForeignKey(name = "FK_ANSWER_QUESTION"))
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CHOICE", foreignKey = @ForeignKey(name = "FK_ANSWER_CHOICE"))
    private Choice choice ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SURVEY", foreignKey = @ForeignKey(name = "FK_ANSWER_SURVEY"))
    private Survey survey ;

    @Column(name = "ANSWER")
    private String answer;

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
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

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", mail='" + mail + '\'' +
                ", question=" + question +
                ", choice=" + choice +
                ", answer='" + answer + '\'' +
                '}';
    }
}
