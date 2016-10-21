package com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class General {
    private String gpa;
    private String totalCredit;
    private String totalAverage;

    @JsonCreator
    public General(@JsonProperty("gpa") String gpa, @JsonProperty("totalCredit") String totalCredit, @JsonProperty("totalAverage") String totalAverage) {
        this.gpa = gpa;
        this.totalCredit = totalCredit;
        this.totalAverage = totalAverage;
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
