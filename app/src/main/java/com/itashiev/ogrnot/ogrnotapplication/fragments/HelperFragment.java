package com.itashiev.ogrnot.ogrnotapplication.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.widget.Toast;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.activities.LoginActivity;

public class HelperFragment extends Fragment {
    public void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().finish();
        startActivity(intent);
    }

    public void showNoInternetConnectionToast() {
        showToast(getString(R.string.no_internet_connection));
    }

    public void showToast(String message) {
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}