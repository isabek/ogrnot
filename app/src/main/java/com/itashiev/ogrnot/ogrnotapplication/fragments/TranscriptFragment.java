package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itashiev.ogrnot.ogrnotapplication.R;

public class TranscriptFragment extends Fragment {


    public TranscriptFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transcript, container, false);
    }


}
