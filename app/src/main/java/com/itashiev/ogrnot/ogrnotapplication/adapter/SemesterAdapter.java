package com.itashiev.ogrnot.ogrnotapplication.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
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

    private int counter = 0;

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

        if (counter % 2 == 0) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#fff5f5f5"));
        }
        counter++;

        if (semester.getGpa() == null && semester.getTotalAverage() == null) {
            holder.semesterGpaLinearLayout.setVisibility(View.INVISIBLE);
            holder.semesterTotalAverageLinearLayout.setVisibility(View.INVISIBLE);
            return;
        }

        if (semester.getLessons() == null) {
            holder.cardView.setClickable(false);

            holder.semesterNameTextView.setTypeface(
                    holder.semesterNameTextView.getTypeface(),
                    Typeface.BOLD);
            holder.semesterTotalCreditTextView.setTypeface(
                    holder.semesterTotalCreditTextView.getTypeface(),
                    Typeface.BOLD);
            holder.semesterTotalAverageTextView.setTypeface(
                    holder.semesterTotalAverageTextView.getTypeface(),
                    Typeface.BOLD);
            holder.semesterGpaTextView.setTypeface(
                    holder.semesterGpaTextView.getTypeface(),
                    Typeface.BOLD);
            holder.semesterTotalCreditLabelTextView.setTypeface(
                    holder.semesterTotalCreditLabelTextView.getTypeface(),
                    Typeface.BOLD);
            holder.semesterTotalAverageLabelTextView.setTypeface(
                    holder.semesterTotalAverageLabelTextView.getTypeface(),
                    Typeface.BOLD);
            holder.semesterGpaLabelTextView.setTypeface(
                    holder.semesterGpaLabelTextView.getTypeface(),
                    Typeface.BOLD);
            holder.cardView.setCardBackgroundColor(Color.parseColor("#fffafafa"));
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
        private final TextView semesterTotalCreditLabelTextView;
        private final TextView semesterTotalAverageLabelTextView;
        private final TextView semesterGpaLabelTextView;

        private final LinearLayout semesterTotalAverageLinearLayout;
        private final LinearLayout semesterGpaLinearLayout;

        private CardView cardView;

        SemesterViewHolder(View itemView) {
            super(itemView);
            semesterNameTextView = (TextView) itemView.findViewById(R.id.semester_name);
            semesterTotalCreditTextView = (TextView) itemView.findViewById(R.id.semester_total_credit);
            semesterTotalAverageTextView = (TextView) itemView.findViewById(R.id.semester_total_average);
            semesterGpaTextView = (TextView) itemView.findViewById(R.id.semester_gpa);

            semesterTotalCreditLabelTextView = (TextView) itemView.findViewById(R.id.semester_total_credit_label);
            semesterTotalAverageLabelTextView = (TextView) itemView.findViewById(R.id.semester_total_average_label);
            semesterGpaLabelTextView = (TextView) itemView.findViewById(R.id.semester_gpa_label);

            semesterTotalAverageLinearLayout = (LinearLayout) itemView.findViewById(R.id.semester_total_average_layout);
            semesterGpaLinearLayout = (LinearLayout) itemView.findViewById(R.id.semester_gpa_layout);

            cardView = (CardView) itemView;

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
