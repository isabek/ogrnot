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
import com.itashiev.ogrnot.ogrnotapplication.model.student.Student;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.Storage;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalInfoFragment extends HelperFragment {

    private Call<Student> call;

    private TextView studentNumberTextView;
    private TextView nameTextView;
    private TextView surnameTextView;
    private TextView birthplaceTextView;
    private TextView birthdayTextView;
    private TextView fatherTextView;
    private TextView motherTextView;
    private TextView nationalityTextView;

    private LinearLayout personaInfoRelativeLayout;
    private ProgressBar personalInfoProgressBar;

    private static final String TAG = "PersonalInfoFragment";

    public PersonalInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_personal_info, container, false);

        personaInfoRelativeLayout = (LinearLayout) inflate.findViewById(R.id.personal_info_linear_layout);
        personalInfoProgressBar = (ProgressBar) inflate.findViewById(R.id.personal_info_progressbar);

        studentNumberTextView = (TextView) inflate.findViewById(R.id.student_number);
        nameTextView = (TextView) inflate.findViewById(R.id.student_name);
        surnameTextView = (TextView) inflate.findViewById(R.id.student_surname);
        birthplaceTextView = (TextView) inflate.findViewById(R.id.student_birthplace);
        birthdayTextView = (TextView) inflate.findViewById(R.id.student_birthday);
        fatherTextView = (TextView) inflate.findViewById(R.id.student_fathers_name);
        motherTextView = (TextView) inflate.findViewById(R.id.student_mothers_name);
        nationalityTextView = (TextView) inflate.findViewById(R.id.student_nationality);

        getDataFromApi();

        return inflate;
    }

    @Override
    public void onStop() {
        if (call != null) {
            call.cancel();
        }
        super.onStop();
    }

    private void getDataFromApi() {
        OgrnotApiInterface apiService = OgrnotApiClient
                .getClient(getActivity().getApplicationContext())
                .create(OgrnotApiInterface.class);

        call = apiService.getStudentInfo();
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    startLoginActivity();
                    return;
                }

                if (call.isExecuted() && response.isSuccessful()) {
                    Student studentInfo = response.body();
                    fillView(studentInfo);
                    Log.d(TAG, "onResponse: " + studentInfo);

                } else {
                    Log.d(TAG, "onResponse: " + response.raw());
                }
                personaInfoRelativeLayout.setVisibility(View.VISIBLE);
                personalInfoProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                if (!call.isCanceled()) {
                    personalInfoProgressBar.setVisibility(View.INVISIBLE);
                    showNoInternetConnectionToast();
                }
                Log.e(TAG, "onFailure: " + call.request(), t);
            }
        });
    }

    private void fillView(Student studentInfo) {
        studentNumberTextView.setText(studentInfo.getNumber());
        nameTextView.setText(studentInfo.getName());
        surnameTextView.setText(studentInfo.getSurname());
        birthplaceTextView.setText(studentInfo.getBirthplace());
        birthdayTextView.setText(studentInfo.getBirthday());
        fatherTextView.setText(studentInfo.getFather());
        motherTextView.setText(studentInfo.getMother());
        nationalityTextView.setText(studentInfo.getNationality());
    }

}
