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
import com.itashiev.ogrnot.ogrnotapplication.RESTClient.OgrnotRestClient;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TranscriptFragment extends Fragment {
    View inflate;

    ProgressBar transcriptProgressBar;

    public TranscriptFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_transcript, container, false);

        getLessonsFromApi();

        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        transcriptProgressBar = (ProgressBar) inflate.findViewById(R.id.student_transcript_progressbar);
    }

    private void getLessonsFromApi() {

        RequestParams params = new RequestParams();
        params.put("authKey", AuthKeyStore.getAuthKey(getActivity().getApplicationContext()));

        OgrnotRestClient.get("student-transcript", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONObject preparatory = response.getJSONObject("preparatory");
                    JSONArray preparatoryLessons = preparatory.getJSONArray("lessons");

                    if (preparatoryLessons.length() > 0) {
                        LinearLayout preparatoryLinearLayout = (LinearLayout) inflate.findViewById(R.id.preparatory_linear_layout);
                        LinearLayout preparatoryLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.preparatory_layout, null);
                        LinearLayout preparatoryLessonsLinearLayout = (LinearLayout) preparatoryLayout.findViewById(R.id.preparatory_lessons_linear_layout);

                        JSONObject preparatoryLesson;
                        for (int i = 0; i < preparatoryLessons.length(); i++) {
                            preparatoryLesson = preparatoryLessons.getJSONObject(i);

                            String lessonCode = (String) preparatoryLesson.get("code");
                            String lessonName = (String) preparatoryLesson.get("name");
                            String lessonMark = (String) preparatoryLesson.get("mark");
                            String lessonCredit = (String) preparatoryLesson.get("credit");

                            LinearLayout preparatoryLessonLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.preparatory_lesson_layout, null);

                            TextView lessonCodeTextView = (TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_code);
                            TextView lessonNameTextView = (TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_name);
                            TextView lessonMarkTextView = (TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_mark);
                            TextView lessonCreditTextView = (TextView) preparatoryLessonLayout.findViewById(R.id.preparatory_lesson_credit);

                            lessonCodeTextView.setText(lessonCode);
                            lessonNameTextView.setText(lessonName);
                            lessonMarkTextView.setText(lessonMark);
                            lessonCreditTextView.setText(lessonCredit);

                            preparatoryLessonsLinearLayout.addView(preparatoryLessonLayout);
                        }
                        preparatoryLinearLayout.addView(preparatoryLayout);
                    }

                    LinearLayout semestersLinearLayout = (LinearLayout) inflate.findViewById(R.id.semesters_linear_layout);
                    LinearLayout semesterLayout;
                    JSONObject undergraduate = response.getJSONObject("undergraduate");
                    JSONArray semesters = undergraduate.getJSONArray("semesters");
                    JSONObject semester;

                    for (int i = 0; i < semesters.length(); i++) {

                        semester = (JSONObject) semesters.get(i);

                        String semesterName = (String) semester.get("name");

                        JSONArray lessons = semester.getJSONArray("lessons");
                        JSONObject lesson;

                        semesterLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_layout, null);

                        LinearLayout semesterLinearLayout = (LinearLayout) semesterLayout.findViewById(R.id.semester_linear_layout);

                        TextView semesterNameTextView = (TextView) semesterLayout.findViewById(R.id.semester_name);
                        semesterNameTextView.setText(semesterName);

                        for (int j = 0; j < lessons.length(); j++) {
                            lesson = lessons.getJSONObject(j);

                            String lessonCode = (String) lesson.get("code");
                            String lessonName = (String) lesson.get("name");
                            String lessonMark = (String) lesson.get("mark");
                            String lessonCredit = (String) lesson.get("credit");
                            String lessonSupplement = (String) lesson.get("supplement");

                            LinearLayout lessonLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.semester_lesson_layout, null);

                            TextView lessonCodeTextView = (TextView) lessonLayout.findViewById(R.id.taken_lessons_lesson_code);
                            TextView lessonNameTextView = (TextView) lessonLayout.findViewById(R.id.taken_lessons_lesson_name);
                            TextView lessonMarkTextView = (TextView) lessonLayout.findViewById(R.id.taken_lessons_semester_mark);
                            TextView lessonSupplementTextView = (TextView) lessonLayout.findViewById(R.id.taken_lessons_supplement);
                            TextView lessonCreditTextView = (TextView) lessonLayout.findViewById(R.id.taken_lessons_lesson_credit);

                            lessonCodeTextView.setText(lessonCode);
                            lessonNameTextView.setText(lessonName);
                            lessonMarkTextView.setText(lessonMark);
                            lessonCreditTextView.setText(lessonCredit);
                            lessonSupplementTextView.setText(lessonSupplement);

                            semesterLinearLayout.addView(lessonLayout);
                        }
                        semestersLinearLayout.addView(semesterLayout);
                    }

                    transcriptProgressBar.setVisibility(View.INVISIBLE);
                    semestersLinearLayout.setVisibility(View.VISIBLE);

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
