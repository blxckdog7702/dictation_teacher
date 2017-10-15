package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Adapter.QuizHistoryListAdapter;
import com.blackdog.dictation_teacher.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.io.IOException;
import java.util.List;


public class QuizHistoryListActivity extends AppCompatActivity {

    RecyclerView rv_list;
    EditText et_search;
    int postID;
    String postCategory;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);

        //로딩바
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //리스트 셋팅
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        try {
            ApiRequester.getInstance().getTeachersQuizHistories(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<QuizHistory>>() {
                @Override
                public void onSuccess(List<QuizHistory> quizHistoryList) {
                    Log.d("quizHistory",quizHistoryList.size()+"");
                    for(QuizHistory q : quizHistoryList)
                        Log.d("quizHistory",q.getId());
                    rv_list.setAdapter(new QuizHistoryListAdapter(QuizHistoryListActivity.this, quizHistoryList));
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFail() {
                    Toast.makeText(getBaseContext(),"실패",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
