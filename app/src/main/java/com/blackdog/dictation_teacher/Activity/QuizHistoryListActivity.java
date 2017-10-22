package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.QuizHistoryListAdapter;
import com.blackdog.dictation_teacher.service.VerticalSpaceItemDecoration;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.io.IOException;
import java.util.List;


public class QuizHistoryListActivity extends BaseDrawerActivity {

    RecyclerView rv_list;
    EditText et_search;
    int postID;
    String postCategory;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quiz_history_list);
        super.onCreate(savedInstanceState);
        toolbarTitle.setText("회차 별 시험 리스트");

        //로딩바
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //리스트 셋팅
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        rv_list.addItemDecoration(dividerItemDecoration);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        try {
            ApiRequester.getInstance().getTeachersQuizHistories(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<QuizHistory>>() {
                @Override
                public void onSuccess(List<QuizHistory> quizHistoryList) {

                    if(quizHistoryList == null) {
                        Toast.makeText(QuizHistoryListActivity.this, "시험 이력이 없습니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    rv_list.setAdapter(new QuizHistoryListAdapter(QuizHistoryListActivity.this, quizHistoryList));
                    rv_list.addItemDecoration(new VerticalSpaceItemDecoration(10));

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
