package com.itashiev.ogrnot.ogrnotapplication.model.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.text.MessageFormat;

public class Student {
    private String number;
    private String name;
    private String surname;
    private String birthplace;
    private String birthday;
    private String father;
    private String mother;
    private String nationality;
    private String photoUrl;

    @JsonCreator
    public Student(@JsonProperty("number") String number, @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname, @JsonProperty("birthplace") String birthplace,
                   @JsonProperty("birthday") String birthday, @JsonProperty("father") String father,
                   @JsonProperty("mother") String mother, @JsonProperty("nationality") String nationality,
                   @JsonProperty("photo") String photoUrl) {
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.birthplace = birthplace;
        this.birthday = birthday;
        this.father = father;
        this.mother = mother;
        this.nationality = nationality;
        this.photoUrl = photoUrl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFullName() {
        return MessageFormat.format("{0} {1}", name, surname);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
