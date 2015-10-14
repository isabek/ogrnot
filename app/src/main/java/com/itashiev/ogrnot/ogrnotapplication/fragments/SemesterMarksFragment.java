package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.RESTClient.OgrnotRestClient;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SemesterMarksFragment extends Fragment {

    View inflate;

    public SemesterMarksFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_semester_marks, container, false);
        getLessonsMarksFromAPI();

        return inflate;
    }

    private void getLessonsMarksFromAPI() {

        RequestParams params = new RequestParams();
        params.put("authKey", AuthKeyStore.getAuthKey(getActivity().getApplicationContext()));

        OgrnotRestClient.get("student-semester-notes", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray lessons = response.getJSONArray("lessons");
                    LinearLayout semestersLessonsMarksLinearLayout = (LinearLayout) inflate.findViewById(R.id.semester_lessons_marks);
                    ProgressBar semesterLessonsMarksProgressBar = (ProgressBar) inflate.findViewById(R.id.semester_lessons_marks_progressbar);

                    if (lessons.length() > 0) {
                        for (int i = 0; i < lessons.length(); i++) {
                            JSONObject lesson = lessons.getJSONObject(i);
                            JSONArray lessonExams = lesson.getJSONArray("exams");

                            LinearLayout semesterLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lessons_marks_layout, null);
                            LinearLayout semesterLinearLayout = (LinearLayout) semesterLayout.findViewById(R.id.lesson_marks_linear_layout);

                            ((TextView) semesterLayout.findViewById(R.id.lesson_name)).setText(lesson.getString("name"));

                            for (int j = 0; j < lessonExams.length(); j++) {
                                JSONObject lessonExam = (JSONObject) lessonExams.get(j);

                                LinearLayout examLinearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lesson_marks_layout, null);

                                ((TextView) examLinearLayout.findViewById(R.id.exam_name)).setText(lessonExam.getString("name"));
                                ((TextView) examLinearLayout.findViewById(R.id.exam_mark)).setText(lessonExam.getString("mark"));
                                ((TextView) examLinearLayout.findViewById(R.id.exam_avg)).setText(lessonExam.getString("avg"));

                                semesterLinearLayout.addView(examLinearLayout);
                            }

                            semestersLessonsMarksLinearLayout.addView(semesterLayout);
                        }


                    } else {

                        LinearLayout semesterMarksEmptyLinearLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lessons_marks_empty_layout, null);
                        semestersLessonsMarksLinearLayout.addView(semesterMarksEmptyLinearLayout);
                    }

                    semestersLessonsMarksLinearLayout.setVisibility(View.VISIBLE);
                    semesterLessonsMarksProgressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("FAILURE", response.toString());
            }
        });
    }
}
