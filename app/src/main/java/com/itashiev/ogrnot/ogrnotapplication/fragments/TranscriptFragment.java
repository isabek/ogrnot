package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.preparatory.Lesson;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.Transcript;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.General;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Semester;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranscriptFragment extends Fragment {

    ProgressBar transcriptProgressBar;
    private LinearLayout preparatoryLinearLayout;
    private LinearLayout preparatoryLayout;
    private LinearLayout preparatoryLessonsLinearLayout;
    private LinearLayout preparatoryLessonLayout;
    private LinearLayout semestersLinearLayout;
    private LinearLayout generalGPALayout;

    private static final String TAG = "TranscriptFragment";

    public TranscriptFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_transcript, container, false);

        preparatoryLinearLayout = (LinearLayout) inflate.findViewById(R.id.preparatory_linear_layout);
        preparatoryLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.preparatory_layout, null);
        preparatoryLessonsLinearLayout = (LinearLayout) preparatoryLayout.findViewById(R.id.preparatory_lessons_linear_layout);
        preparatoryLessonLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.preparatory_lesson_layout, null);
        semestersLinearLayout = (LinearLayout) inflate.findViewById(R.id.semesters_linear_layout);
        generalGPALayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.general_gpa_layout, null);
        transcriptProgressBar = (ProgressBar) inflate.findViewById(R.id.student_transcript_progressbar);

        getLessonsFromApi();

        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void getLessonsFromApi() {
        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        String authKey = AuthKeyStore.getAuthKey(getActivity().getApplicationContext());

        apiService.getTranscript(authKey).enqueue(new Callback<Transcript>() {
            @Override
            public void onResponse(Call<Transcript> call, Response<Transcript> response) {
                if (call.isExecuted() && response.isSuccessful()) {
                    Transcript transcript = response.body();

                    fillPreparatoryView(transcript);
                    fillUndergraduateView(transcript);
                    fillGpaView(transcript);

                    transcriptProgressBar.setVisibility(View.INVISIBLE);
                    semestersLinearLayout.setVisibility(View.VISIBLE);

                    Log.d(TAG, "onResponse: " + transcript);
                } else {
                    Log.d(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Transcript> call, Throwable t) {
                Log.d(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillGpaView(Transcript transcript) {
        if (transcript.getUndergraduate() != null && transcript.getUndergraduate().getGeneral() != null) {
            General general = transcript.getUndergraduate().getGeneral();
            ((TextView) generalGPALayout.findViewById(R.id.general_total_credit)).setText(general.getTotalCredit());
            ((TextView) generalGPALayout.findViewById(R.id.general_total_credit_default)).setText(general.getTotalAverage());
            ((TextView) generalGPALayout.findViewById(R.id.general_gpa)).setText(general.getGpa());
            semestersLinearLayout.addView(generalGPALayout);
        }
    }

    private void fillUndergraduateView(Transcript transcript) {
        if (transcript.getUndergraduate() != null && transcript.getUndergraduate().getSemesters() != null) {
            for (Semester semester : transcript.getUndergraduate().getSemesters()) {
                LinearLayout semesterLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_layout, null);
                LinearLayout semesterLinearLayout = (LinearLayout) semesterLayout.findViewById(R.id.semester_linear_layout);
                ((TextView) semesterLayout.findViewById(R.id.semester_name)).setText(semester.getName());

                for (com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Lesson lesson : semester.getLessons()) {
                    LinearLayout lessonLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lesson_layout, null);
                    ((TextView) lessonLayout.findViewById(R.id.taken_lessons_lesson_code)).setText(lesson.getCode());
                    ((TextView) lessonLayout.findViewById(R.id.taken_lessons_lesson_name)).setText(lesson.getName());
                    ((TextView) lessonLayout.findViewById(R.id.taken_lessons_semester_mark)).setText(lesson.getMark());
                    ((TextView) lessonLayout.findViewById(R.id.taken_lessons_supplement)).setText(lesson.getSupplement());
                    ((TextView) lessonLayout.findViewById(R.id.taken_lessons_lesson_credit)).setText(lesson.getCredit());
                    semesterLinearLayout.addView(lessonLayout);
                }

                ((TextView) semesterLayout.findViewById(R.id.semester_gpa)).setText(semester.getGpa());
                ((TextView) semesterLayout.findViewById(R.id.semester_total_credit_default)).setText(semester.getTotalAverage());
                ((TextView) semesterLayout.findViewById(R.id.semester_total_credit)).setText(semester.getTotalCredit());

                semestersLinearLayout.addView(semesterLayout);
            }
        }
    }

    private void fillPreparatoryView(Transcript transcript) {
        if (transcript.getPreparatory() != null && transcript.getPreparatory().getLessons() != null) {
            for (Lesson lesson : transcript.getPreparatory().getLessons()) {
                ((TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_code)).setText(lesson.getCode());
                ((TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_name)).setText(lesson.getName());
                ((TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_mark)).setText(lesson.getMark());
                ((TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_credit)).setText(lesson.getCredit());
                preparatoryLessonsLinearLayout.addView(preparatoryLessonLayout);
            }
            preparatoryLinearLayout.addView(preparatoryLayout);
        }
    }

}
