package com.uniyaz.core.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SURVEY")
public class Survey extends BaseEntity{

    public Survey() {

    }

    public Survey(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id) && Objects.equals(name, survey.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, name);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
