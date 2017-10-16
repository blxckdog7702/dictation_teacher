package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseActivity;
import com.blackdog.dictation_teacher.Adapter.QuizHistoryAdapter;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.io.IOException;


public class QuizHistoryActivity extends BaseActivity {

    RecyclerView rv_list;
    EditText et_search;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quiz_history);
        super.onCreate(savedInstanceState);
        toolbarTitle.setText("몇월 몇일 무슨 시험");

        String quizHistoryId = getIntent().getStringExtra("quizHistoryId");


        //로딩바
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //리스트 셋팅
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        try {
            ApiRequester.getInstance().getQuizHistory(quizHistoryId, new ApiRequester.UserCallback<QuizHistory>() {
                @Override
                public void onSuccess(QuizHistory result) {
                    //화면 갱신하기
                    Toast.makeText(getBaseContext(), result.getQuizNumber()+"", Toast.LENGTH_SHORT).show();
                    rv_list.setAdapter(new QuizHistoryAdapter(QuizHistoryActivity.this, result.getQuizResults()));
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onFail() {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
