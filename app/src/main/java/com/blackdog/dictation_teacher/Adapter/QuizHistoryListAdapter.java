package com.blackdog.dictation_teacher.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.QuizHistoryActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by JBStat on 2016-11-29.
 */
public class QuizHistoryListAdapter extends RecyclerView.Adapter<QuizHistoryListAdapter.ViewHolder>  {

    List<QuizHistory> quizHistories;
    Context context;


    public QuizHistoryListAdapter(Context _context,List<QuizHistory> _quizHistories)
    {
        context = _context;
        quizHistories = _quizHistories;
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        LinearLayout layout;

        TextView tv_number;
        TextView tv_period;

        public ViewHolder(View view) {
            super(view);

            layout = (LinearLayout) view.findViewById(R.id.layout_item_quiz_history);

            tv_number = (TextView) view.findViewById(R.id.tv_number);
            tv_period = (TextView) view.findViewById(R.id.tv_period);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz_history,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {

        final QuizHistory quizHistory = quizHistories.get(position);
//        Log.d("quizHistory",quizHistory.getQuizNumber()+"");
        holder.tv_number.setText( quizHistory.getQuizNumber().toString());
        holder.tv_period.setText( quizHistory.getDate());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(context,"테스트용",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuizHistoryActivity.class);
                intent.putExtra("quizHistoryId",quizHistory.getId());
                context.startActivity(intent);
                //화면 옮겨가기
//                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(context);
//                alert_confirm.setMessage(title).setCancelable(false)
//                        .setPositiveButton("내용보기", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                //enock page 6 : 새로운 화면 만들어 띄어주기
//                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse( post.getLink() ));
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(intent);
//                            }
//                        })
//                        .setNeutralButton("즐겨찾기 등록", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                ArrayList<Post> posts = postListDAO.loadPostList("like");
//                                posts.add(post);
//                                postListDAO.savePostList("like",posts);
//
//
//                                Toast.makeText(context,"즐겨찾기에 추가되었습니다",Toast.LENGTH_SHORT).show();
//
//                            }
//                        })
//                        .setCancelable(true);
//                AlertDialog alert = alert_confirm.create();
//                alert.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return quizHistories.size();
    }
}
