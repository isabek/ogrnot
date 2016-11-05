package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.fragments.MainMenuFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.PersonalInfoFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.PreferenceFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.SemesterMarksFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.TakenLessonsFragment;
import com.itashiev.ogrnot.ogrnotapplication.fragments.TranscriptFragment;
import com.itashiev.ogrnot.ogrnotapplication.model.student.Student;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiClient;
import com.itashiev.ogrnot.ogrnotapplication.rest.OgrnotApiInterface;
import com.itashiev.ogrnot.ogrnotapplication.storage.Storage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    public static final String DEFAULT_MENU_ITEM_ID_NAME = "nav_main_menu_item";
    public static final String DEFAULT_SELECTED_MENU_ITEM_KEY = "default_selected_menu_item_key";

    private AlertDialog.Builder alertDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        int itemId = getDefaultSelectedMenuItemId();
        selectFragmentByMenuItem(itemId);

        setNavigationHeaderData();
    }

    private int getDefaultSelectedMenuItemId() {
        String menuItemId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(DEFAULT_SELECTED_MENU_ITEM_KEY, DEFAULT_MENU_ITEM_ID_NAME);
        return getResources().getIdentifier(menuItemId, "id", getPackageName());
    }

    private void setNavigationHeaderData() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View inflate = LayoutInflater
                .from(getApplicationContext())
                .inflate(R.layout.nav_header_main, navigationView);

        final TextView studentNumberTextView = (TextView) inflate.findViewById(R.id.student_number);
        final TextView studentFullNameTextView = (TextView) inflate.findViewById(R.id.student_full_name);

        OgrnotApiInterface apiService = OgrnotApiClient.getClient().create(OgrnotApiInterface.class);
        apiService.getStudentInfo(Storage.getAuthKey(getApplicationContext())).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (call.isExecuted() && response.isSuccessful()) {
                    Student student = response.body();
                    studentFullNameTextView.setText(student.getFullName());
                    studentNumberTextView.setText(student.getNumber());
                }
                Log.d(TAG, "onResponse: " + response.raw());
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        selectFragmentByMenuItem(itemId);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void selectFragmentByMenuItem(int menuItemId) {
        Fragment fragment;
        switch (menuItemId) {
            case R.id.nav_main_menu_item:
                fragment = new MainMenuFragment();
                break;

            case R.id.nav_personal_info_item:
                fragment = new PersonalInfoFragment();
                break;

            case R.id.nav_taken_lessons_item:
                fragment = new TakenLessonsFragment();
                break;

            case R.id.nav_semester_marks_item:
                fragment = new SemesterMarksFragment();
                break;

            case R.id.nav_transcript_item:
                fragment = new TranscriptFragment();
                break;

            case R.id.nav_settings:
                fragment = new PreferenceFragment();
                break;

            default:
                fragment = new MainMenuFragment();
                break;
        }

        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragmentManager
                .beginTransaction()
                .replace(R.id.content_main, fragment)
                .commit();

        MenuItem menuItem = (((NavigationView) findViewById(R.id.nav_view)).getMenu()).findItem(menuItemId);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());

    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_logout) {
            alertDialog = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
                    .setMessage(getString(R.string.logout_action_confirm))
                    .setPositiveButton(R.string.logout_action_positive_button_text, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {
                            dialog.dismiss();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 100);
                        }
                    })
                    .setNegativeButton(R.string.logout_action_negative_button_text, null);
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}