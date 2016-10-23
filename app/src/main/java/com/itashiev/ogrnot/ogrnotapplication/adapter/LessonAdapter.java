package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.lesson.Lesson;

import java.util.List;


public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> {

    private List<Lesson> lessons;

    public LessonAdapter(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public LessonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_layout, parent, false);
        return new LessonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonViewHolder holder, int position) {
        Lesson lesson = lessons.get(position);
        holder.lessonCodeTextView.setText(lesson.getCode());
        holder.lessonNameTextView.setText(lesson.getName());
        holder.lessonCreditTextView.setText(lesson.getCredit());
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {

        private TextView lessonCodeTextView;
        private TextView lessonNameTextView;
        private TextView lessonCreditTextView;

        public LessonViewHolder(View itemView) {
            super(itemView);
            lessonCodeTextView = (TextView) itemView.findViewById(R.id.lesson_code);
            lessonNameTextView = (TextView) itemView.findViewById(R.id.lesson_name);
            lessonCreditTextView = (TextView) itemView.findViewById(R.id.lesson_credit);
        }
    }
}
