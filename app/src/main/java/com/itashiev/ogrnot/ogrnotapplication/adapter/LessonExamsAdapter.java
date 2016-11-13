package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.model.grade.Exam;

import java.util.List;

public class LessonExamsAdapter extends RecyclerView.Adapter<LessonExamsAdapter.LessonExamViewHolder> {

    private final List<Exam> exams;

    public LessonExamsAdapter(List<Exam> exams) {
        this.exams = exams;
    }

    @Override
    public LessonExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_marks_layout, parent, false);
        return new LessonExamsAdapter.LessonExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonExamViewHolder holder, int position) {
        Exam exam = exams.get(position);
        holder.examNameTextView.setText(exam.getName());
        holder.examMarkTextView.setText(exam.getMark());
        holder.examAvgTextView.setText(exam.getAvg());
    }

    @Override
    public int getItemCount() {
        return exams.size();
    }

    public static class LessonExamViewHolder extends RecyclerView.ViewHolder {
        TextView examNameTextView;
        TextView examMarkTextView;
        TextView examAvgTextView;

        public LessonExamViewHolder(View itemView) {
            super(itemView);
            examNameTextView = (TextView) itemView.findViewById(R.id.exam_name);
            examMarkTextView = (TextView) itemView.findViewById(R.id.exam_mark);
            examAvgTextView = (TextView) itemView.findViewById(R.id.exam_avg);
        }
    }
}
