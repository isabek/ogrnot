package com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class Undergraduate {
    private List<Semester> semesters;
    private General general;

    @JsonCreator
    public Undergraduate(@JsonProperty("semesters") List<Semester> semesters, @JsonProperty("general") General general) {
        this.semesters = semesters;
        this.general = general;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
