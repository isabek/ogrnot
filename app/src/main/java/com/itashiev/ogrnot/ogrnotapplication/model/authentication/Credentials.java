package com.itashiev.ogrnot.ogrnotapplication.model.authentication;

public class Credentials {
    private String number;
    private String password;

    public String getNumber() {
        return number;
    }

    public Credentials setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Credentials setPassword(String password) {
        this.password = password;
        return this;
    }
}
