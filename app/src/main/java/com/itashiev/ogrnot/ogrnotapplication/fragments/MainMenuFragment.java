package com.itashiev.ogrnot.ogrnotapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.info.MainInfo;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;

import org.apache.http.HttpStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainMenuFragment extends HelperFragment {


    TextView studentNumberTextView;
    TextView facultyTextView;
    TextView departmentTextView;
    LinearLayout mainMenuLinearLayout;
    ProgressBar mainMenuProgressBar;

    private static final String TAG = "MainMenuFragment";

    public MainMenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_main_menu, container, false);

        studentNumberTextView = (TextView) inflate.findViewById(R.id.student_number);
        facultyTextView = (TextView) inflate.findViewById(R.id.faculty);
        departmentTextView = (TextView) inflate.findViewById(R.id.department);
        mainMenuLinearLayout = (LinearLayout) inflate.findViewById(R.id.main_menu_linear_layout);
        mainMenuProgressBar = (ProgressBar) inflate.findViewById(R.id.main_menu_progressbar);

        getDataFromApi();

        return inflate;
    }

    private void getDataFromApi() {
        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        String authKey = AuthKeyStore.getAuthKey(getActivity().getApplicationContext());
        apiService.getMainInfo(authKey).enqueue(new Callback<MainInfo>() {
            @Override
            public void onResponse(Call<MainInfo> call, Response<MainInfo> response) {
                if (response.code() == HttpStatus.SC_UNAUTHORIZED) {
                    startLoginActivity();
                    return;
                }

                if (call.isExecuted() && response.isSuccessful()) {
                    MainInfo mainInfo = response.body();
                    fillView(mainInfo);
                    Log.d(TAG, "onResponse: " + mainInfo);

                } else {
                    Log.d(TAG, "onResponse: " + response.raw());
                }
                mainMenuProgressBar.setVisibility(View.INVISIBLE);
                mainMenuLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MainInfo> call, Throwable t) {
                Log.e(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillView(MainInfo mainInfo) {
        studentNumberTextView.setText(mainInfo.getNumber());
        facultyTextView.setText(mainInfo.getFaculty());
        departmentTextView.setText(mainInfo.getDepartment());
    }
}
