package com.itashiev.ogrnot.ogrnotapplication.rest;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OgrnotApiClient {
    private static final String BASE_URL = "https://ogrnot-api.herokuapp.com/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
