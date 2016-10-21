package com.itashiev.ogrnot.ogrnotapplication.model.info;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MainInfo {
    private String number;
    private String faculty;
    private String department;

    @JsonCreator
    public MainInfo(@JsonProperty("number") String number, @JsonProperty("faculty") String faculty, @JsonProperty("department") String department) {
        this.number = number;
        this.faculty = faculty;
        this.department = department;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
