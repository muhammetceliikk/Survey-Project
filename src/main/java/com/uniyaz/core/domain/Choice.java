package com.uniyaz.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "CHOICE")
public class Choice extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_QUESTION", foreignKey = @ForeignKey(name = "FK_CHOICE_QUESTION"))
    private Question question;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public Long getId() {
        return id;
    }
}
