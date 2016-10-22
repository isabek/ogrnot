package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.authentication.Authentication;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.AuthKeyStore;

import org.apache.http.HttpStatus;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends Activity {
    private ProgressDialog progressDialog;

    private EditText studentNumberEditTextView;
    private EditText passwordEditTextView;
    private Button signInButtonView;

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentNumberEditTextView = (EditText) findViewById(R.id.student_number);
        passwordEditTextView = (EditText) findViewById(R.id.password);
        signInButtonView = (Button) findViewById(R.id.button_sign_in);

        signInButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = studentNumberEditTextView.getText().toString().trim();
                String pass = passwordEditTextView.getText().toString().trim();
                callLoginApi(user, pass);
            }
        });
    }

    private void callLoginApi(String user, String pass) {
        final OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);

        initProgressDialog();

        apiService.authenticate(user, pass).enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, retrofit2.Response<Authentication> response) {
                if (call.isExecuted() && response.isSuccessful()) {
                    progressDialog.dismiss();
                    AuthKeyStore.setAuthKey(getApplicationContext(), response.body().getAuthKey());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (response.code() == HttpStatus.SC_UNAUTHORIZED) {
                    setMessageToProgressDialog(getString(R.string.wrong_credentials));
                } else {
                    setMessageToProgressDialog(getString(R.string.authentication_failed));
                }

                Log.d(TAG, "onResponse: " + response.raw());
            }

            @Override
            public void onFailure(Call<Authentication> call, Throwable t) {
                setMessageToProgressDialog(getString(R.string.no_internet_connection));
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
            }
        });
    }

    private void setMessageToProgressDialog(String message) {
        progressDialog.setMessage(message);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 2000);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.authenticate));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }
}



