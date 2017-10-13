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

public class StudentManageAdapter extends RecyclerView.Adapter<StudentManageAdapter.ViewHolder> {
    private List<Student> mStudentList;
    private Context mContext;

    public StudentManageAdapter(Context _context, List<Student> _studentList) {
        mContext = _context;
        mStudentList = _studentList;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mStudentName;
        public TextView mGrade;
        public TextView mClassNumber;
        public TextView mStudentNumber;
        public Button mDelete;

        public ViewHolder(View v) {
            super(v);
            mStudentName = (TextView) v.findViewById(R.id.tv_managing_student_name);
            mGrade = (TextView) v.findViewById(R.id.tv_managing_student_grade);
            mClassNumber = (TextView) v.findViewById(R.id.tv_managing_student_class_number);
            mStudentNumber = (TextView) v.findViewById(R.id.tv_managing_student_student_number);
            mDelete = (Button) v.findViewById(R.id.bt_managing_student_delete);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_manage_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mGrade.setText(mStudentList.get(position).getGrade());
        holder.mClassNumber.setText(mStudentList.get(position).getClass_());
        holder.mStudentNumber.setText(mStudentList.get(position).getStudentId().toString());
        holder.mStudentName.setText(mStudentList.get(position).getName());

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "삭제", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }
}
