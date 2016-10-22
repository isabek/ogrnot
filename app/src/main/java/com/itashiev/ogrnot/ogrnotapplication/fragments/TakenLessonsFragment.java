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
import com.itashiev.ogrnot.ogrnotapplication.model.lesson.Lesson;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;

import org.apache.http.HttpStatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakenLessonsFragment extends HelperFragment {
    private LinearLayout studentTakenLessonsLinearLayout;
    private LinearLayout studentLessonsLinearLayout;
    private ProgressBar studentTakenLessonsProgressBar;

    private static final String TAG = "TakenLessonsFragment";

    public TakenLessonsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_taken_lessons, container, false);

        studentTakenLessonsLinearLayout = (LinearLayout) inflate.findViewById(R.id.student_taken_lessons_layout);
        studentLessonsLinearLayout = (LinearLayout) inflate.findViewById(R.id.student_taken_lessons);
        studentTakenLessonsProgressBar = (ProgressBar) inflate.findViewById(R.id.student_taken_lessons_progressbar);

        getLessonsFromApi();

        return inflate;
    }

    private void getLessonsFromApi() {


        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        String authKey = AuthKeyStore.getAuthKey(getActivity().getApplicationContext());
        apiService.getLessons(authKey).enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.code() == HttpStatus.SC_UNAUTHORIZED) {
                    startLoginActivity();
                    return;
                }

                if (call.isExecuted() && response.isSuccessful()) {
                    List<Lesson> lessons = response.body();
                    fillLessonsView(lessons);

                    Log.d(TAG, "onResponse: " + lessons);
                } else {
                    Log.d(TAG, "onResponse: " + response.raw());
                }

                studentTakenLessonsLinearLayout.setVisibility(View.VISIBLE);
                studentTakenLessonsProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillLessonsView(List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            LinearLayout layout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.lesson_layout, null);
            ((TextView) layout.findViewById(R.id.lesson_name)).setText(lesson.getName());
            ((TextView) layout.findViewById(R.id.lesson_code)).setText(lesson.getCode());
            ((TextView) layout.findViewById(R.id.lesson_credit)).setText(lesson.getCredit());
            studentLessonsLinearLayout.addView(layout);
        }
    }
}
