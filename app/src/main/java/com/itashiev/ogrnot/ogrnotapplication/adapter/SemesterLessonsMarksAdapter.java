package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.grade.Lesson;

import java.util.List;

public class SemesterLessonsMarksAdapter extends RecyclerView.Adapter<SemesterLessonsMarksAdapter.SemesterLessonsMarksViewHolder> {

    private List<Lesson> lessons;
    private RecyclerView examsRecyclerView;
    private Context context;
    private LinearLayoutManager manager;

    public SemesterLessonsMarksAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public SemesterLessonsMarksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        manager = new LinearLayoutManager(context.getApplicationContext());
        View view = LayoutInflater.from(context).inflate(R.layout.semester_lessons_marks_layout, parent, false);
        examsRecyclerView = (RecyclerView) view.findViewById(R.id.lesson_exams_recycler_view);
        examsRecyclerView.setLayoutManager(manager);
        return new SemesterLessonsMarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SemesterLessonsMarksViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.lessonNameTextView.setText(lesson.getName());

        LessonExamsAdapter lessonExamsAdapter = new LessonExamsAdapter(lesson.getExams());
        examsRecyclerView.setAdapter(lessonExamsAdapter);
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class SemesterLessonsMarksViewHolder extends RecyclerView.ViewHolder {
        private final TextView lessonNameTextView;

        public SemesterLessonsMarksViewHolder(View itemView) {
            super(itemView);
            lessonNameTextView = (TextView) itemView.findViewById(R.id.lesson_name);
        }
    }
}

