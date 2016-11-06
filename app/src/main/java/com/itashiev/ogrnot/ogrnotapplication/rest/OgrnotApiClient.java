package com.itashiev.ogrnot.ogrnotapplication.rest;

import android.content.Context;
import android.util.Log;

import com.itashiev.ogrnot.ogrnotapplication.storage.Storage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OgrnotApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(final Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            final Request.Builder builder = chain
                                    .request()
                                    .newBuilder()
                                    .addHeader("appKey", Config.APP_KEY);

                            if (chain.request() != null
                                    && chain.request().url() != null
                                    && chain.request().url().encodedPath() != null
                                    && !chain.request().url().encodedPath().equals("/api/v1/authenticate")) {
                                builder.addHeader("Authorization", Storage.getAuthKey(context));
                            }

                            final Request request = builder.build();
                            return chain.proceed(request);
                        }
                    })
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
