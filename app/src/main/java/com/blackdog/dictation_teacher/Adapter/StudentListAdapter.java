package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.RecordManagerActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.Student;

import java.util.List;

/**
 * Created by DH on 2017-10-12.
 */

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {
    private static final String TAG = "StudentListAdapter.java";
    private List<Student> mStudentList;
    private Context mContext;

    public StudentListAdapter(Context _context, List<Student> _studentList) {
        mContext = _context;
        mStudentList = _studentList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mSchool;
        public TextView mGrade;
        public TextView mClassNumber;
        public TextView mStudentNumber;
        public TextView mStudentName;
        public Button mStudentDetail;

        public ViewHolder(View v) {
            super(v);
            mSchool = (TextView) v.findViewById(R.id.tv_school);
            mGrade = (TextView) v.findViewById(R.id.tv_grade);
            mClassNumber = (TextView) v.findViewById(R.id.tv_class_number);
            mStudentNumber = (TextView) v.findViewById(R.id.tv_student_number);
            mStudentName = (TextView) v.findViewById(R.id.tv_student_name);
            mStudentDetail = (Button) v.findViewById(R.id.bt_student_detail);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mSchool.setText(mStudentList.get(position).getSchool());
        holder.mGrade.setText(mStudentList.get(position).getGrade());
        holder.mClassNumber.setText(mStudentList.get(position).getClass_());
        holder.mStudentNumber.setText(mStudentList.get(position).getStudentId().toString());
        holder.mStudentName.setText(mStudentList.get(position).getName());

        holder.mStudentDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int selectedPosition = holder.getAdapterPosition();

                Student selectedItem = mStudentList.get(selectedPosition);

                if(selectedItem == null) {
                    Log.d(TAG, "onClick: 클릭한 학생 객체가 null");
                    return;
                }

                Intent intent = new Intent(mContext, RecordManagerActivity.class);
                intent.putExtra("student", selectedItem);
                mContext.startActivity(intent);

//                Toast.makeText(mContext, "테스트용", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }



}
