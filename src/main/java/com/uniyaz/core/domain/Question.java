package com.uniyaz.core.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "QUESTION")
public class Question extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(length = 25)
    @Enumerated(EnumType.STRING)
    private EnumQType QType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PANEL", foreignKey = @ForeignKey(name = "FK_QUESTION_PANEL"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private MyPanel myPanel;

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnumQType getQType() {
        return QType;
    }

    public void setQType(EnumQType QType) {
        this.QType = QType;
    }

    public MyPanel getPanel() {
        return myPanel;
    }

    public void setPanel(MyPanel myPanel) {
        this.myPanel = myPanel;
    }
}
