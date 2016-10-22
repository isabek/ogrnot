package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.grade.Exam;
import com.itashiev.ogrnot.ogrnotapplication.model.grade.Grade;
import com.itashiev.ogrnot.ogrnotapplication.model.grade.Lesson;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;

import org.apache.http.HttpStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SemesterMarksFragment extends HelperFragment {

    private LinearLayout semestersLessonsMarksLinearLayout;
    private ProgressBar semesterLessonsMarksProgressBar;

    private static final String TAG = "SemesterMarksFragment";

    public SemesterMarksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_semester_marks, container, false);
        semestersLessonsMarksLinearLayout = (LinearLayout) inflate.findViewById(R.id.semester_lessons_marks);
        semesterLessonsMarksProgressBar = (ProgressBar) inflate.findViewById(R.id.semester_lessons_marks_progressbar);

        getLessonsMarksFromApi();

        return inflate;
    }

    private void getLessonsMarksFromApi() {
        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        String authKey = AuthKeyStore.getAuthKey(getActivity().getApplicationContext());

        apiService.getGrade(authKey).enqueue(new Callback<Grade>() {
            @Override
            public void onResponse(Call<Grade> call, Response<Grade> response) {
                if (response.code() == HttpStatus.SC_UNAUTHORIZED) {
                    startLoginActivity();
                    return;
                }

                if (call.isExecuted() && response.isSuccessful()) {
                    Grade grade = response.body();
                    fillExamsView(grade);
                    Log.d(TAG, "onResponse: " + grade);
                } else {
                    Log.d(TAG, "onResponse: " + response.raw());
                }

                semestersLessonsMarksLinearLayout.setVisibility(View.VISIBLE);
                semesterLessonsMarksProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Grade> call, Throwable t) {
                semesterLessonsMarksProgressBar.setVisibility(View.INVISIBLE);
                showNoInternetConnectionToast();
                Log.e(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillExamsView(Grade grade) {
        if (grade.getLessons() != null && grade.getLessons().size() > 0) {
            for (Lesson lesson : grade.getLessons()) {
                LinearLayout semesterLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lessons_marks_layout, null);
                LinearLayout semesterLinearLayout = (LinearLayout) semesterLayout.findViewById(R.id.lesson_marks_linear_layout);

                ((TextView) semesterLayout.findViewById(R.id.lesson_name)).setText(lesson.getName());

                for (Exam exam : lesson.getExams()) {
                    LinearLayout examLinearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lesson_marks_layout, null);

                    ((TextView) examLinearLayout.findViewById(R.id.exam_name)).setText(exam.getName());
                    ((TextView) examLinearLayout.findViewById(R.id.exam_mark)).setText(exam.getMark());
                    ((TextView) examLinearLayout.findViewById(R.id.exam_avg)).setText(exam.getAvg());

                    semesterLinearLayout.addView(examLinearLayout);
                }
                semestersLessonsMarksLinearLayout.addView(semesterLayout);
            }

        } else {
            LinearLayout semesterMarksEmptyLinearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lessons_marks_empty_layout, null);
            semestersLessonsMarksLinearLayout.addView(semesterMarksEmptyLinearLayout);
        }
    }
}
