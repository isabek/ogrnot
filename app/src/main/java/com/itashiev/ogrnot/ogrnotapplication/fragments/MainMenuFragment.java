package com.itashiev.ogrnot.ogrnotapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itashiev.ogrnot.ogrnotapplication.R;


public class MainMenuFragment extends Fragment {
    public MainMenuFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }
}
