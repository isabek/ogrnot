package com.itashiev.ogrnot.ogrnotapplication.fragments;

import android.os.Bundle;

import com.itashiev.ogrnot.ogrnotapplication.R;

public class PreferenceFragment extends android.preference.PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}