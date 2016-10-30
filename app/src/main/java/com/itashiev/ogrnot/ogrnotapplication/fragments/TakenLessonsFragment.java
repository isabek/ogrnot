package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.adapter.LessonAdapter;
import com.itashiev.ogrnot.ogrnotapplication.model.lesson.Lesson;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.Storage;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakenLessonsFragment extends HelperFragment {
    private ProgressBar studentTakenLessonsProgressBar;
    RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private RecyclerView recyclerView;
    private View inflate;

    private static final String TAG = "TakenLessonsFragment";

    public TakenLessonsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_taken_lessons, container, false);
        studentTakenLessonsProgressBar = (ProgressBar) inflate.findViewById(R.id.student_taken_lessons_progressbar);
        recyclerView = (RecyclerView) inflate.findViewById(R.id.student_taken_lessons_recycler_view);
        manager = new LinearLayoutManager(getActivity().getApplicationContext());

        getLessonsFromApi();

        return inflate;
    }

    private void getLessonsFromApi() {


        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        String authKey = Storage.getAuthKey(getActivity().getApplicationContext());
        apiService.getLessons(authKey).enqueue(new Callback<List<Lesson>>() {
            @Override
            public void onResponse(Call<List<Lesson>> call, Response<List<Lesson>> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
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

                studentTakenLessonsProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<Lesson>> call, Throwable t) {
                studentTakenLessonsProgressBar.setVisibility(View.INVISIBLE);
                showNoInternetConnectionToast();
                Log.e(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillLessonsView(List<Lesson> lessons) {
        recyclerView.setLayoutManager(manager);
        adapter = new LessonAdapter(lessons);
        recyclerView.setAdapter(adapter);
    }
}
