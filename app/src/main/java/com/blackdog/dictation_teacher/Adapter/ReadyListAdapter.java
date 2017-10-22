package com.blackdog.dictation_teacher.Adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.models.StudentReady;

import java.util.List;

/**
 * Created by DH on 2017-10-17.
 */

public class ReadyListAdapter extends RecyclerView.Adapter<ReadyListAdapter.ViewHolder> {
    List<StudentReady> mReadyList;

    public ReadyListAdapter(List<StudentReady> list) {
        mReadyList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public ImageView ivIsReady;
        public TextView tvSchool;
        public TextView tvGrade;
        public TextView tvClass;
        public TextView tvClassNumber;

        public ViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.tv_ready_student_name);
            ivIsReady = (ImageView) v.findViewById(R.id.iv_is_ready);
            tvSchool = (TextView)v.findViewById(R.id.tv_ready_school);
            tvGrade = (TextView)v.findViewById(R.id.tv_ready_grade);
            tvClass = (TextView)v.findViewById(R.id.tv_ready_class);
            tvClassNumber = (TextView)v.findViewById(R.id.tv_ready_class_number);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ready_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(mReadyList.get(position).getName());
        holder.tvSchool.setText(mReadyList.get(position).getSchool());
        holder.tvGrade.setText(mReadyList.get(position).getGrade());
        holder.tvClass.setText(mReadyList.get(position).get_class());
        holder.tvClassNumber.setText(mReadyList.get(position).getNumber());


        if (mReadyList.get(position).isReady()) {
            holder.ivIsReady.setImageResource(R.drawable.ic_check_ok);
        } else {
            holder.ivIsReady.setImageResource(R.drawable.ic_check_no);
        }


    }

    @Override
    public int getItemCount() {
        return mReadyList.size();
    }
}
