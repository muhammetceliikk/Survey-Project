package com.uniyaz.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER_SURVEY")
public class CustomerSurvey extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MAIL")
    private String mail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_SURVEY", foreignKey = @ForeignKey(name = "FK_CUSTOMER_SURVEY_SURVEY"))
    private Survey survey;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
