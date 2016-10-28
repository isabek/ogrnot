package com.itashiev.ogrnot.ogrnotapplication.model.transcript.preparatory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Lesson;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Preparatory {
    private List<Lesson> lessons;

    @JsonCreator
    public Preparatory(@JsonProperty("lessons") List<Lesson> lessons) {
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
