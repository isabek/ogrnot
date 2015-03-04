package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.RESTClient.OgrnotRestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private EditText studentNumberEditTextView;
    private EditText passwordEditTextView;
    private Button signInButtonView;
    private ProgressBar loginProgressBar;
    private ScrollView loginFormScrollView;
    private ImageView logoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentNumberEditTextView = (EditText) findViewById(R.id.student_number);
        passwordEditTextView = (EditText) findViewById(R.id.password);
        signInButtonView = (Button) findViewById(R.id.button_sign_in);
        loginProgressBar = (ProgressBar)findViewById(R.id.login_progress);
        loginFormScrollView = (ScrollView) findViewById(R.id.login_form);
        logoImageView = (ImageView)findViewById(R.id.logo);

        signInButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentNumber = studentNumberEditTextView.getText().toString();
                String password = passwordEditTextView.getText().toString();

                System.out.println(studentNumber);
                System.out.println(password);

                RequestParams params = new RequestParams();
                params.put("user", studentNumber);
                params.put("pass", password);

                OgrnotRestClient.post("authenticate", params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String authKey = (String) response.get("authKey");
                        } catch (JSONException e) {
                            Log.e("LoginActivity", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                        System.out.println("2");
                        System.out.println(responseString);
                        System.out.println(statusCode + "");
                    }
                });

            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}



