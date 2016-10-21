package com.itashiev.ogrnot.ogrnotapplication.model.transcript.preparatory;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Lesson {
    private String code;
    private String name;
    private String mark;
    private String credit;

    @JsonCreator
    public Lesson(@JsonProperty("code") String code, @JsonProperty("name") String name,
                  @JsonProperty("mark") String mark, @JsonProperty("credit") String credit) {
        this.code = code;
        this.name = name;
        this.mark = mark;
        this.credit = credit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
