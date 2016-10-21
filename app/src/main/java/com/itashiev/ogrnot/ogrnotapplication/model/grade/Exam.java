package com.itashiev.ogrnot.ogrnotapplication.model.grade;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Exam {
    private String name;
    private String mark;
    private String avg;

    @JsonCreator
    public Exam(@JsonProperty("name") String name, @JsonProperty("mark") String mark, @JsonProperty("avg") String avg) {
        this.name = name;
        this.mark = mark;
        this.avg = avg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
