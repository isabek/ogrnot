package com.itashiev.ogrnot.ogrnotapplication.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.RESTClient.OgrnotRestClient;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class MainMenuFragment extends Fragment {

    TextView studentNumberTextView;
    TextView facultyTextView;
    TextView departmentTextView;
    LinearLayout mainMenuLinearLayout;
    ProgressBar mainMenuProgressBar;

    public MainMenuFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_main_menu, container, false);

        studentNumberTextView = (TextView) inflate.findViewById(R.id.student_number);
        facultyTextView = (TextView) inflate.findViewById(R.id.faculty);
        departmentTextView = (TextView) inflate.findViewById(R.id.department);

        mainMenuLinearLayout = (LinearLayout)inflate.findViewById(R.id.main_menu_linear_layout);
        mainMenuProgressBar = (ProgressBar)inflate.findViewById(R.id.main_menu_progressbar);

        getDataFromApi();

        return inflate;
    }

    public void setDataToElements(String studentNumber, String faculty, String department) {

        studentNumberTextView.setText(studentNumber);
        facultyTextView.setText(faculty);
        departmentTextView.setText(department);
    }

    private void getDataFromApi() {

        RequestParams params = new RequestParams();
        params.put("authKey", AuthKeyStore.getAuthKey(getActivity().getApplicationContext()));

        OgrnotRestClient.get("main-info", params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String number = (String) response.get("number");
                    String faculty = (String) response.get("faculty");
                    String department = (String) response.get("department");
                    setDataToElements(number, faculty, department);

                    mainMenuProgressBar.setVisibility(View.INVISIBLE);
                    mainMenuLinearLayout.setVisibility(View.VISIBLE);

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
