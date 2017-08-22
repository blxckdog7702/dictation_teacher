package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.ExamResultPage;
import com.blackdog.dictation_teacher.Activity.QuizHistoryActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuestionResult;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.QuizResult;

import java.util.List;


/**
 * Created by JBStat on 2016-11-29.
 */
public class QuizResultListAdapter extends RecyclerView.Adapter<QuizResultListAdapter.ViewHolder> {

    List<QuizResult> quizResults;
    Context context;


    public QuizResultListAdapter(Context _context, List<QuizResult> _quizResults) {
        context = _context;
        quizResults = _quizResults;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layout;

        TextView tv_name;
        TextView tv_score;
        TextView tv_quiz_number;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layout_item_quiz_result);

            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_score = (TextView) view.findViewById(R.id.tv_score);
            tv_quiz_number = (TextView) view.findViewById(R.id.tv_quiz_number);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_result, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final QuizResult quizResult = quizResults.get(position);
        String correctString = "";

        for (QuestionResult qr : quizResult.getQuestionResult())
            if (qr.getCorrect()) correctString += "O|";
            else correctString += "X|";

//        Log.d("quizHistory",quizHistory.getQuizNumber()+"");
        holder.tv_name.setText(quizResult.getStudentName());
        holder.tv_score.setText(quizResult.getScore().toString());
        holder.tv_quiz_number.setText(correctString);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "테스트용", Toast.LENGTH_SHORT).show();
                //화면 옮겨가기

                Intent intent = new Intent(context, ExamResultPage.class);
                intent.putExtra("OBJECT",quizResult);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return quizResults.size();
    }
}
