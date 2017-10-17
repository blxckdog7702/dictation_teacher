package com.blackdog.dictation_teacher.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        public TextView tvIsReady;

        public ViewHolder(View v) {
            super(v);
            tvName = (TextView)v.findViewById(R.id.tv_ready_student_name);
            tvIsReady = (TextView)v.findViewById(R.id.tv_is_ready);
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

        if(mReadyList.get(position).isReady()) {
            holder.tvIsReady.setText("O");
        } else {
            holder.tvIsReady.setText("X");
        }
    }

    @Override
    public int getItemCount() {
        return mReadyList.size();
    }
}
