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
import com.blackdog.dictation_teacher.models.QuizHistory;

import java.util.List;

/**
 * Created by DH on 2017-10-15.
 */

public class RecordByPeriodAdapter extends RecyclerView.Adapter<RecordByPeriodAdapter.ViewHolder> {
    private List<QuizHistory> mQuizHistoryList;
    private Context context;

    public RecordByPeriodAdapter(Context _context, List<QuizHistory> _dataset) {
        context = _context;
        mQuizHistoryList = _dataset;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        Button button;

        // each data item is just a string in this case
        public ViewHolder(View v) {
            super(v);
            textView = (TextView)v.findViewById(R.id.tv_record_by_period);
            button = (Button)v.findViewById(R.id.bt_view_record);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_record_by_period, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(mQuizHistoryList.get(position).getDate());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "테스트입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuizHistoryList.size();
    }
}
