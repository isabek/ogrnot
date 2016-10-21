package com.itashiev.ogrnot.ogrnotapplication.model.transcript;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.preparatory.Preparatory;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Undergraduate;

import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transcript {

    private Preparatory preparatory;

    private Undergraduate undergraduate;

    @JsonCreator
    public Transcript(@JsonProperty("preparatory") Preparatory preparatory, @JsonProperty("undergraduate") Undergraduate undergraduate) {
        this.preparatory = preparatory;
        this.undergraduate = undergraduate;
    }

    public Preparatory getPreparatory() {
        return preparatory;
    }

    public void setPreparatory(Preparatory preparatory) {
        this.preparatory = preparatory;
    }

    public Undergraduate getUndergraduate() {
        return undergraduate;
    }

    public void setUndergraduate(Undergraduate undergraduate) {
        this.undergraduate = undergraduate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
