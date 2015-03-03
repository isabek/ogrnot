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

import com.itashiev.ogrnot.ogrnotapplication.R;


public class LoginActivity extends Activity implements LoaderCallbacks<Cursor> {

    private EditText studentNumberEditTextView;
    private EditText passwordEditTextView;
    private Button signInButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        studentNumberEditTextView  = (EditText)findViewById(R.id.student_number);
        passwordEditTextView = (EditText)findViewById(R.id.password);
        signInButtonView = (Button) findViewById(R.id.button_sign_in);

        signInButtonView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

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



