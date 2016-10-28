package com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class Semester{
    private String name;
    private List<Lesson> lessons;
    private String gpa;
    private String totalCredit;
    private String totalAverage;

    @JsonCreator
    public Semester(@JsonProperty("name") String name, @JsonProperty("lessons") List<Lesson> lessons,
                    @JsonProperty("gpa") String gpa, @JsonProperty("totalCredit") String totalCredit,
                    @JsonProperty("totalAverage") String totalAverage) {
        this.name = name;
        this.lessons = lessons;
        this.gpa = gpa;
        this.totalCredit = totalCredit;
        this.totalAverage = totalAverage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getTotalAverage() {
        return totalAverage;
    }

    public void setTotalAverage(String totalAverage) {
        this.totalAverage = totalAverage;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
