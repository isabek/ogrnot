package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.grade.Lesson;

import java.util.List;

public class SemesterLessonsMarksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int MARKS_ALERT_VIEW_TYPE = 0;
    private static final int MARKS_VIEW_TYPE = 1;
    private final Context context;
    private List<Lesson> lessons;

    public SemesterLessonsMarksAdapter(Context context, List<Lesson> lessons) {
        this.context = context;
        this.lessons = lessons;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        if (viewType == MARKS_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.semester_lessons_marks_layout, parent, false);
            return new SemesterLessonsMarksViewHolder(view);
        }

        View view = LayoutInflater.from(context).inflate(R.layout.alert_lessons_marks, parent, false);
        return new SemesterLessonsMarksAlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SemesterLessonsMarksViewHolder && position > 0) {
            Lesson lesson = lessons.get(position - 1);
            SemesterLessonsMarksViewHolder lessonsMarksViewHolder = (SemesterLessonsMarksViewHolder) holder;

            lessonsMarksViewHolder.lessonNameTextView.setText(lesson.getName());

            lessonsMarksViewHolder.examsRecyclerView.setHasFixedSize(true);
            lessonsMarksViewHolder.examsRecyclerView.setAdapter(new LessonExamsAdapter(lesson.getExams()));
            lessonsMarksViewHolder.examsRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        } else if (holder instanceof SemesterLessonsMarksAlertViewHolder) {
            ((SemesterLessonsMarksAlertViewHolder) holder).alertTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MARKS_ALERT_VIEW_TYPE;
        }
        return MARKS_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        return lessons.size() == 0 ? 0 : lessons.size() + 1;
    }


    private static class SemesterLessonsMarksViewHolder extends RecyclerView.ViewHolder {
        private final TextView lessonNameTextView;
        private RecyclerView examsRecyclerView;

        SemesterLessonsMarksViewHolder(View itemView) {
            super(itemView);
            lessonNameTextView = (TextView) itemView.findViewById(R.id.lesson_name);
            examsRecyclerView = (RecyclerView) itemView.findViewById(R.id.lesson_exams_recycler_view);
        }
    }

    private static class SemesterLessonsMarksAlertViewHolder extends RecyclerView.ViewHolder {
        private final TextView alertTextView;

        SemesterLessonsMarksAlertViewHolder(View itemView) {
            super(itemView);
            alertTextView = (TextView) itemView.findViewById(R.id.alert_title);
        }
    }
}

