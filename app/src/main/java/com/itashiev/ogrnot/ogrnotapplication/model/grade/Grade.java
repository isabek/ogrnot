package com.itashiev.ogrnot.ogrnotapplication.model.grade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Grade {
    private List<Lesson> lessons;

    @JsonCreator
    public Grade(@JsonProperty("lessons") List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
