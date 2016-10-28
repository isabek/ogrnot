package com.itashiev.ogrnot.ogrnotapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.adapter.SemesterLessonAdapter;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Lesson;

import java.util.ArrayList;
import java.util.List;

public class SemesterActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;
    private SemesterLessonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.semester_lessons_recycle_view);
        manager = new LinearLayoutManager(getApplicationContext());

        ArrayList<Lesson> lessons = (ArrayList<Lesson>) getIntent().getSerializableExtra("lessons");
        String semesterName = getIntent().getStringExtra("semesterName");
        setTitle(semesterName);
        fillLessons(lessons);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void fillLessons(List<Lesson> lessons) {
        recyclerView.setLayoutManager(manager);
        adapter = new SemesterLessonAdapter(lessons);
        recyclerView.setAdapter(adapter);
    }

}
