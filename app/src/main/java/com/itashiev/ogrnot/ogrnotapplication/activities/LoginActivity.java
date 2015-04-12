package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.RESTClient.OgrnotRestClient;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private EditText studentNumberEditTextView;
    private EditText passwordEditTextView;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentNumberEditTextView = (EditText) findViewById(R.id.student_number);
        passwordEditTextView = (EditText) findViewById(R.id.password);
        Button signInButtonView = (Button) findViewById(R.id.button_sign_in);

        signInButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String studentNumber = studentNumberEditTextView.getText().toString();
                String password = passwordEditTextView.getText().toString();

                RequestParams params = new RequestParams();
                params.put("user", studentNumber);
                params.put("pass", password);

                initProgressDialog();
                progressDialog.show();

                OgrnotRestClient.post("authenticate", params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            String authKey = (String) response.get("authKey");
                            AuthKeyStore.setAuthKey(getApplicationContext(), authKey);

                            progressDialog.dismiss();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);

                        } catch (JSONException e) {
                            setMessageToProgressDialog(getString(R.string.authentication_auth_key_not_found));
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                        setMessageToProgressDialog(getString(R.string.authentication_failed));
                    }
                });

            }
        });

    }

    private void setMessageToProgressDialog(String message){
        progressDialog.setMessage(message);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
    }

    private void initProgressDialog(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.authenticate));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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



