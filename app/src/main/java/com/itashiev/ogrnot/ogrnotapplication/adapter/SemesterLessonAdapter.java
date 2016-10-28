package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Lesson;

import java.util.List;

public class SemesterLessonAdapter extends RecyclerView.Adapter<SemesterLessonAdapter.SemesterLessonViewHolder> {

    private List<Lesson> lessons;

    public SemesterLessonAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }


    @Override
    public SemesterLessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_mark_layout, parent, false);
        return new SemesterLessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SemesterLessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.lessonNameTextView.setText(lesson.getName());
        holder.lessonCodeTextView.setText(lesson.getCode());
        holder.lessonMarkTextView.setText(lesson.getMark());
        holder.lessonCreditTextView.setText(lesson.getCredit());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    class SemesterLessonViewHolder extends RecyclerView.ViewHolder {
        private final TextView lessonNameTextView;
        private final TextView lessonCodeTextView;
        private final TextView lessonMarkTextView;
        private final TextView lessonCreditTextView;

        SemesterLessonViewHolder(View itemView) {
            super(itemView);
            lessonNameTextView = (TextView) itemView.findViewById(R.id.lesson_name);
            lessonCodeTextView = (TextView) itemView.findViewById(R.id.lesson_code);
            lessonMarkTextView = (TextView) itemView.findViewById(R.id.lesson_mark);
            lessonCreditTextView = (TextView) itemView.findViewById(R.id.lesson_credit);
        }
    }
}
