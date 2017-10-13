package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;

import java.util.List;

/**
 * Created by DH on 2017-10-13.
 */

public class MatchingListAdapter extends RecyclerView.Adapter<MatchingListAdapter.ViewHolder> {
    private List<Student> mMatchingList;
    private Context mContext;

    public MatchingListAdapter(Context _context, List<Student> _matchingList) {
        mMatchingList = _matchingList;
        mContext = _context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mStudentName;
        public TextView mGrade;
        public TextView mClassNumber;
        public TextView mStudentNumber;
        public Button mAccept;
        public Button mRefuse;

        public ViewHolder(View v) {
            super(v);
            mStudentName = (TextView) v.findViewById(R.id.tv_matching_student_name);
            mGrade = (TextView) v.findViewById(R.id.tv_matching_student_grade);
            mClassNumber = (TextView) v.findViewById(R.id.tv_matching_student_class_number);
            mStudentNumber = (TextView) v.findViewById(R.id.tv_matching_student_student_number);
            mAccept = (Button) v.findViewById(R.id.bt_matching_student_accept);
            mRefuse = (Button) v.findViewById(R.id.bt_matching_student_refuse);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_matching_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mGrade.setText(mMatchingList.get(position).getGrade());
        holder.mClassNumber.setText(mMatchingList.get(position).getClass_());
        holder.mStudentNumber.setText(mMatchingList.get(position).getStudentId().toString());
        holder.mStudentName.setText(mMatchingList.get(position).getName());

        holder.mAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "수락", Toast.LENGTH_SHORT).show();
            }
        });

        holder.mRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "거절", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMatchingList.size();
    }
}
