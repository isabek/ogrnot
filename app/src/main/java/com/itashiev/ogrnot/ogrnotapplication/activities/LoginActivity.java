package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.authentication.Authentication;
import com.itashiev.ogrnot.ogrnotapplication.model.authentication.Credentials;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.Storage;

import java.net.HttpURLConnection;

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

        useCredentials();

        signInButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = studentNumberEditTextView.getText().toString().trim();
                String pass = passwordEditTextView.getText().toString().trim();
                callLoginApi(user, pass);
            }
        });
    }

    private void useCredentials() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean areCredentialsSaved = preferences.getBoolean("save_login_credentials_key", false);
        String number = Storage.getNumber(getApplicationContext());
        String password = Storage.getPassword(getApplicationContext());

        if (areCredentialsSaved && !number.isEmpty() && !password.isEmpty()) {
            studentNumberEditTextView.setText(number.trim());
            passwordEditTextView.setText(password.trim());
        }
    }

    private void callLoginApi(final String number, final String password) {
        final OgrnotApiInterface apiService = OgrnotApiClient
                .getClient(getApplicationContext())
                .create(OgrnotApiInterface.class);

        initProgressDialog();

        Credentials credentials = new Credentials()
                .setNumber(number)
                .setPassword(password);

        apiService.authenticate(credentials).enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(Call<Authentication> call, retrofit2.Response<Authentication> response) {
                if (call.isExecuted() && response.isSuccessful()) {
                    saveCredentials(number, password);
                    progressDialog.dismiss();
                    Storage.setAuthKey(getApplicationContext(), response.body().getAuthKey());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent);
                } else if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
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

    private void saveCredentials(String number, String password) {
        Storage.setNumber(getApplicationContext(), number);
        Storage.setPassword(getApplicationContext(), password);
    }
}



