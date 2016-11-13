package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.adapter.SemesterAdapter;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.Transcript;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.preparatory.Preparatory;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.General;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Lesson;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Semester;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.view.EmptyRecyclerView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TranscriptFragment extends HelperFragment {

    public static final String GENERAL_GPA_TR_RESOURCE_KEY = "general_gpa_tr";
    public static final String PREPARATORY_TR_RESOURCE_KEY = "preparatory_tr";
    private Call<Transcript> call;

    private ProgressBar transcriptProgressBar;
    private View inflate;
    private EmptyRecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private RecyclerView.Adapter adapter;
    private Map<String, String> stringResources = new HashMap<>();

    private static final String TAG = "TranscriptFragment";

    public TranscriptFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_transcript, container, false);
        recyclerView = (EmptyRecyclerView) inflate.findViewById(R.id.student_transcript_recycler_view);
        transcriptProgressBar = (ProgressBar) inflate.findViewById(R.id.student_transcript_progressbar);
        manager = new LinearLayoutManager(getActivity().getApplicationContext());

        stringResources.put(GENERAL_GPA_TR_RESOURCE_KEY, getString(R.string.general_gpa_tr));
        stringResources.put(PREPARATORY_TR_RESOURCE_KEY, getString(R.string.preparatory_tr));

        getLessonsFromApi();

        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        if (call != null) {
            call.cancel();
        }
        super.onStop();
    }

    private void getLessonsFromApi() {
        OgrnotApiInterface apiService = OgrnotApiClient
                .getClient(getActivity().getApplicationContext())
                .create(OgrnotApiInterface.class);

        call = apiService.getTranscript();
        call.enqueue(new Callback<Transcript>() {
            @Override
            public void onResponse(Call<Transcript> call, Response<Transcript> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    startLoginActivity();
                    return;
                }

                if (call.isExecuted() && response.isSuccessful()) {
                    Transcript transcript = response.body();
                    fillTranscript(transcript);
                    transcriptProgressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);

                    Log.d(TAG, "onResponse: " + transcript);
                } else {
                    Log.d(TAG, "onResponse: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<Transcript> call, Throwable t) {
                if (!call.isCanceled()) {
                    transcriptProgressBar.setVisibility(View.INVISIBLE);
                    showNoInternetConnectionToast();
                }
                Log.d(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillTranscript(Transcript transcript) {
        List<Semester> semesters = new ArrayList<>();
        addPreparatorySemester(transcript, semesters);
        addUndergraduateSemesters(transcript, semesters);
        addGeneralGpa(transcript, semesters);

        recyclerView.setLayoutManager(manager);
        adapter = new SemesterAdapter(semesters, this);
        recyclerView.setAdapter(adapter);

        View emptyView = inflate.findViewById(R.id.student_transcript_empty_view);
        recyclerView.setEmptyView(emptyView);
    }

    private void addGeneralGpa(Transcript transcript, List<Semester> semesters) {
        if (transcript == null
                || transcript.getUndergraduate() == null
                || transcript.getUndergraduate().getGeneral() == null) {
            return;
        }

        General general = transcript.getUndergraduate().getGeneral();
        Semester semester = new Semester(stringResources.get(GENERAL_GPA_TR_RESOURCE_KEY), null, general.getGpa(), general.getTotalCredit(), general.getTotalAverage());
        semesters.add(semester);
    }

    private void addUndergraduateSemesters(Transcript transcript, List<Semester> semesters) {
        if (transcript == null
                || transcript.getUndergraduate() == null
                || transcript.getUndergraduate().getSemesters() == null) {
            return;
        }
        semesters.addAll(transcript.getUndergraduate().getSemesters());
    }

    private void addPreparatorySemester(Transcript transcript, List<Semester> semesters) {
        if (transcript == null
                || transcript.getPreparatory() == null
                || transcript.getPreparatory().getLessons() == null) {
            return;
        }

        Preparatory preparatory = transcript.getPreparatory();
        List<Lesson> lessons = preparatory.getLessons();
        String credit = String.valueOf(getAllCredit(lessons));
        Semester semester = new Semester(stringResources.get(PREPARATORY_TR_RESOURCE_KEY), lessons, null, credit, null);
        semesters.add(semester);
    }

    private int getAllCredit(List<Lesson> lessons) {
        int credit = 0;
        for (Lesson lesson : lessons) {
            credit += Integer.parseInt(lesson.getCredit());
        }
        return credit;
    }
}
