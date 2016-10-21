package com.itashiev.ogrnot.ogrnotapplication.model.grade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Lesson {
    private String name;
    private List<Exam> exams;

    @JsonCreator
    public Lesson(@JsonProperty("name") String name, @JsonProperty("exams") List<Exam> exams) {
        this.name = name;
        this.exams = exams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
