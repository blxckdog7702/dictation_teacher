package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackdog.dictation_teacher.Activity.RecordResultActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private static final String TAG = "RecordAdapter.java";
    private List<QuizResult> mQuizResultList;
    private Context context;

    public RecordAdapter(Context context, List<QuizResult> mQuizResultList) {
        this.context = context;
        this.mQuizResultList = mQuizResultList;
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvRank)
        TextView tvRank;
        @BindView(R.id.tvScore)
        TextView tvScore;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvComment)
        TextView tvComment;
        @BindView(R.id.lrRecord)
        LinearLayout layoutRecord;

        public RecordViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup v = (ViewGroup) mInflater.inflate(R.layout.item_record, parent, false);
        return new RecordViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecordViewHolder holder, int position) {
        holder.tvDate.setText(mQuizResultList.get(position).getDate());
        //// TODO: 2017-10-16 서버에서 넘어오나 확인해봐야함
        holder.tvRank.setText(mQuizResultList.get(position).getRank() + "등");
        holder.tvScore.setText(mQuizResultList.get(position).getScore() + "점");

        holder.layoutRecord.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final int selectedPosition = holder.getAdapterPosition();
                QuizResult selectedItem = mQuizResultList.get(selectedPosition);

                if(selectedItem == null) {
                    return;
                }

                Intent intent = new Intent(context, RecordResultActivity.class);
                intent.putExtra("quizResult", selectedItem);
                context.startActivity(intent);
            }
        });
//        if(selectedItem.getScore() >= 80){
//            holder.tvComment.setTextColor(Color.GREEN);
//        }
//        else if(selectedItem.getScore() >= 50){
//            holder.tvComment.setTextColor(Color.BLUE);
//        }
//        else{
//            holder.tvComment.setTextColor(Color.RED);
//        }
//        holder.tvComment.setText(selectedItem.getComment());
    }

    @Override
    public int getItemCount() {
        return (null != mQuizResultList ? mQuizResultList.size() : 0);
    }

}

