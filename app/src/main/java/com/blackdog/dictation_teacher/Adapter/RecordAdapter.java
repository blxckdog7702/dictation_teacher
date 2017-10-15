package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackdog.dictation_teacher.Activity.RecordResultActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.blackdog.dictation_teacher.models.RecordModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private ArrayList<QuizResult> recordModels;
    private Context context;
    private RecordViewHolder holder;

    public RecordAdapter(Context context, ArrayList<QuizResult> recordModels) {
        this.context = context;
        this.recordModels = recordModels;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup v = (ViewGroup) mInflater.inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecordViewHolder holder, final int position) {
        final QuizResult recordModel = recordModels.get(position);
        this.holder = holder;

        holder.tvDate.setText(recordModel.getDate());
        holder.tvRank.setText(recordModel.getRank() + "등");
        holder.tvScore.setText(recordModel.getScore() + "점");
        if(recordModel.getScore() >= 80){
            holder.tvComment.setTextColor(Color.GREEN);
        }
        else if(recordModel.getScore() >= 50){
            holder.tvComment.setTextColor(Color.BLUE);
        }
        else{
            holder.tvComment.setTextColor(Color.RED);
        }
//        holder.tvComment.setText(recordModel.getComment());

    }

    @Override
    public int getItemCount() {
        return (null != recordModels ? recordModels.size() : 0);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvRank) TextView tvRank;
        @BindView(R.id.tvScore) TextView tvScore;
        @BindView(R.id.tvDate) TextView tvDate;
        @BindView(R.id.tvComment) TextView tvComment;

        public RecordViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.lrRecord)
        public void onCheck() {
            //// TODO: 2017-10-14 수정
//            Util.getInstance().moveActivity(context, RecordResultActivity.class);
            Log.v("RecordAdapter", "Listener OK");
        }
    }
}
