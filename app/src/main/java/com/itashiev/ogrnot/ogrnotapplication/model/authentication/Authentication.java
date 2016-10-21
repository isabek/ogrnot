package com.itashiev.ogrnot.ogrnotapplication.model.authentication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Authentication {
    private String authKey;

    @JsonCreator
    public Authentication(@JsonProperty("authKey") String authKey) {
        this.authKey = authKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
