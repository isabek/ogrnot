package com.itashiev.ogrnot.ogrnotapplication.fragments;


import android.app.Fragment;
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
import com.itashiev.ogrnot.ogrnotapplication.RESTClient.OgrnotRestClient;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class PersonalInfoFragment extends Fragment {

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
    ProgressBar personalinfoProgressBar;

    public PersonalInfoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_personal_info, container, false);

        personaInfoRelativeLayout = (RelativeLayout) inflate.findViewById(R.id.personal_info_relative_layout);
        personalinfoProgressBar = (ProgressBar) inflate.findViewById(R.id.personal_info_progressbar);


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

    private void setDataToElements(String number, String name, String surname, String birthplace, String birthday, String father, String mother, String nationality, String url){

        studentNumberTextView.setText(number);
        nameTextView.setText(name);
        surnameTextView.setText(surname);
        birthplaceTextView.setText(birthplace);
        birthdayTextView.setText(birthday);
        fatherTextView.setText(father);
        motherTextView.setText(mother);
        nationalityTextView.setText(nationality);

        loadImage(url);
    }

    private void loadImage(String url){
        Picasso.with(getActivity().getApplicationContext()).load(url).into(photoImageView);
    }

    private void getDataFromApi() {

        RequestParams params = new RequestParams();
        params.put("authKey", AuthKeyStore.getAuthKey(getActivity().getApplicationContext()));

        OgrnotRestClient.get("student-info", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    String number = (String) response.get("number");
                    String name = (String) response.get("name");
                    String surname = (String) response.get("surname");
                    String birthplace = (String) response.get("birthplace");
                    String birthday = (String) response.get("birthday");
                    String father = (String) response.get("father");
                    String mother = (String) response.get("mother");
                    String nationality = (String) response.get("nationality");
                    String url = (String) response.get("photo");

                    setDataToElements(number, name, surname, birthplace, birthday, father, mother, nationality, url);

                    personaInfoRelativeLayout.setVisibility(View.VISIBLE);
                    personalinfoProgressBar.setVisibility(View.INVISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Log.d("FAILURE", response.toString());
            }
        });
    }

}
