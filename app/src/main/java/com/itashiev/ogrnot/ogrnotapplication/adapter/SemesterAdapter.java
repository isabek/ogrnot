package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itashiev.ogrnot.ogrnotapplication.R;
import com.itashiev.ogrnot.ogrnotapplication.activities.SemesterActivity;
import com.itashiev.ogrnot.ogrnotapplication.fragments.TranscriptFragment;
import com.itashiev.ogrnot.ogrnotapplication.model.transcript.undergraduate.Semester;

import java.io.Serializable;
import java.util.List;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.SemesterViewHolder> {

    private final TranscriptFragment transcriptFragment;
    private List<Semester> semesters;

    public SemesterAdapter(List<Semester> semesters, TranscriptFragment transcriptFragment) {
        this.semesters = semesters;
        this.transcriptFragment = transcriptFragment;
    }

    @Override
    public SemesterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_layout, parent, false);
        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SemesterViewHolder holder, int position) {
        Semester semester = semesters.get(position);
        holder.semesterNameTextView.setText(semester.getName());
        holder.semesterTotalCreditTextView.setText(semester.getTotalCredit());

        if (semester.getGpa() == null && semester.getTotalAverage() == null) {
            holder.semesterGpaLinearLayout.setVisibility(View.INVISIBLE);
            holder.semesterTotalAverageLinearLayout.setVisibility(View.INVISIBLE);
            return;
        }

        holder.semesterTotalAverageTextView.setText(semester.getTotalAverage());
        holder.semesterGpaTextView.setText(semester.getGpa());
    }

    @Override
    public int getItemCount() {
        return semesters.size();
    }

    class SemesterViewHolder extends RecyclerView.ViewHolder {
        private final TextView semesterTotalCreditTextView;
        private final TextView semesterNameTextView;
        private final TextView semesterTotalAverageTextView;
        private final TextView semesterGpaTextView;

        private final LinearLayout semesterTotalAverageLinearLayout;
        private final LinearLayout semesterGpaLinearLayout;

        SemesterViewHolder(View itemView) {
            super(itemView);
            semesterNameTextView = (TextView) itemView.findViewById(R.id.semester_name);
            semesterTotalCreditTextView = (TextView) itemView.findViewById(R.id.semester_total_credit);
            semesterTotalAverageTextView = (TextView) itemView.findViewById(R.id.semester_total_average);
            semesterGpaTextView = (TextView) itemView.findViewById(R.id.semester_gpa);
            semesterTotalAverageLinearLayout = (LinearLayout) itemView.findViewById(R.id.semester_total_average_layout);
            semesterGpaLinearLayout = (LinearLayout) itemView.findViewById(R.id.semester_gpa_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Semester semester = semesters.get(index);
                    if (semester.getLessons() == null) {
                        return;
                    }
                    Activity activity = transcriptFragment.getActivity();
                    Intent intent = new Intent(activity, SemesterActivity.class);
                    intent.putExtra("lessons", (Serializable) semester.getLessons());
                    intent.putExtra("semesterName", semester.getName());
                    transcriptFragment.startActivity(intent);
                }
            });
        }
    }
}
