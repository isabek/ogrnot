package com.itashiev.ogrnot.ogrnotapplication.fragments;

import android.app.Fragment;
import android.content.Intent;

import com.itashiev.ogrnot.ogrnotapplication.activities.LoginActivity;

public class HelperFragment extends Fragment {
    public void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);
    }
}