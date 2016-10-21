package com.itashiev.ogrnot.ogrnotapplication.rest;

import com.itashiev.ogrnot.ogrnotapplication.model.grade.Grade;
import com.itashiev.ogrnot.ogrnotapplication.model.lesson.Lesson;
import com.itashiev.ogrnot.ogrnotapplication.model.student.Student;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.Transcript;
import com.itashiev.ogrnot.ogrnotapplication.model.authentication.Authentication;
import com.itashiev.ogrnot.ogrnotapplication.model.info.MainInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OgrnotApiInterface {

    @FormUrlEncoded
    @POST("/api/v1/authenticate")
    Call<Authentication> authenticate(@Field("user") String user, @Field("pass") String pass);

    @GET("/api/v1/main-info")
    Call<MainInfo> getMainInfo(@Query("authKey") String authKey);

    @GET("/api/v1/student-info")
    Call<Student> getStudentInfo(@Query("authKey") String authKey);

    @GET("/api/v1/student-taken-lessons")
    Call<List<Lesson>> getLessons(@Query("authKey") String authKey);

    @GET("/api/v1/student-transcript")
    Call<Transcript> getTranscript(@Query("authKey") String authKey);

    @GET("/api/v1/student-semester-notes")
    Call<Grade> getGrade(@Query("authKey") String authKey);
}
