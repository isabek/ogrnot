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

public class TakenLessonsFragment extends Fragment {

    View inflate;

    public TakenLessonsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflate = inflater.inflate(R.layout.fragment_taken_lessons, container, false);
        getLessonsFromApi();

        return inflate;
    }

    private void getLessonsFromApi() {

        RequestParams params = new RequestParams();
        params.put("authKey", AuthKeyStore.getAuthKey(getActivity().getApplicationContext()));

        OgrnotRestClient.get("student-taken-lessons", params, new JsonHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                try {

                    LinearLayout studentTakenLessonsLinearLayout = (LinearLayout) inflate.findViewById(R.id.student_taken_lessons_layout);
                    LinearLayout studentLessonsLinearLayout = (LinearLayout) inflate.findViewById(R.id.student_taken_lessons);
                    ProgressBar studentTakenLessonsProgressBar = (ProgressBar) inflate.findViewById(R.id.student_taken_lessons_progressbar);
                    LinearLayout lessonLayout;

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject lesson = response.getJSONObject(i);

                        String lessonCode = (String) lesson.get("code");
                        String lessonName = (String) lesson.get("name");
                        String lessonCredit = (String) lesson.get("credit");

                        lessonLayout = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.lesson_layout, null);

                        TextView lessonCodeTextView = (TextView) lessonLayout.getChildAt(0);
                        TextView lessonNameTextView = (TextView) lessonLayout.getChildAt(1);
                        TextView lessonCreditTextView = (TextView) lessonLayout.getChildAt(2);

                        lessonCodeTextView.setText(lessonCode);
                        lessonNameTextView.setText(lessonName);
                        lessonCreditTextView.setText(lessonCredit);

                        studentLessonsLinearLayout.addView(lessonLayout);
                    }

                    studentTakenLessonsLinearLayout.setVisibility(View.VISIBLE);
                    studentTakenLessonsProgressBar.setVisibility(View.INVISIBLE);


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
