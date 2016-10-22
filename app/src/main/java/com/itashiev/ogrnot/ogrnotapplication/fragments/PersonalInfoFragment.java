package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.student.Student;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonalInfoFragment extends HelperFragment {

    TextView studentNumberTextView;
    TextView nameTextView;
    TextView surnameTextView;
    TextView birthplaceTextView;
    TextView birthdayTextView;
    TextView fatherTextView;
    TextView motherTextView;
    TextView nationalityTextView;
    ImageView photoImageView;

    RelativeLayout personaInfoRelativeLayout;
    ProgressBar personalInfoProgressBar;

    private static final String TAG = "PersonalInfoFragment";

    public PersonalInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_personal_info, container, false);

        personaInfoRelativeLayout = (RelativeLayout) inflate.findViewById(R.id.personal_info_relative_layout);
        personalInfoProgressBar = (ProgressBar) inflate.findViewById(R.id.personal_info_progressbar);

        studentNumberTextView = (TextView) inflate.findViewById(R.id.student_number);
        nameTextView = (TextView) inflate.findViewById(R.id.student_name);
        surnameTextView = (TextView) inflate.findViewById(R.id.student_surname);
        birthplaceTextView = (TextView) inflate.findViewById(R.id.student_birthplace);
        birthdayTextView = (TextView) inflate.findViewById(R.id.student_birthday);
        fatherTextView = (TextView) inflate.findViewById(R.id.student_fathers_name);
        motherTextView = (TextView) inflate.findViewById(R.id.student_mothers_name);
        nationalityTextView = (TextView) inflate.findViewById(R.id.student_nationality);
        photoImageView = (ImageView) inflate.findViewById(R.id.student_image);

        getDataFromApi();

        return inflate;
    }

    private void getDataFromApi() {
        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        String authKey = AuthKeyStore.getAuthKey(getActivity().getApplicationContext());
        apiService.getStudentInfo(authKey).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.code() == HttpStatus.SC_UNAUTHORIZED) {
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
        loadImage(studentInfo.getPhotoUrl());
    }

    private void loadImage(String url) {
        try {
            Picasso.with(getActivity().getApplicationContext()).load(url).into(photoImageView);
        } catch (Exception ex) {
            Log.e(TAG, "Exception: " + ex.getMessage());
        }
    }


}
