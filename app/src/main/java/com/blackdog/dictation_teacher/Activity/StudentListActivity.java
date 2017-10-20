package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.StudentListAdapter;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;
import com.blackdog.dictation_teacher.singleton.QuizHistoryListSingle;

import java.io.IOException;
import java.util.List;

public class StudentListActivity extends BaseDrawerActivity {
    private static final String TAG = "StudentListActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mTotalStatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        toolbarTitle.setText("학생목록");

        mTotalStatButton = (Button) findViewById(R.id.bt_total_stat);
        mRecyclerView = (RecyclerView) findViewById(R.id.student_recycler_view);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        requestTeachersQuizHistoryList();
        requestStudentList();

        mTotalStatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //우리반전체통계
                Intent intent = new Intent(getApplicationContext(), TotalStatisticsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void requestTeachersQuizHistoryList() {
        try {
            ApiRequester.getInstance().getTeachersQuizHistories(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<QuizHistory>>() {
                @Override
                public void onSuccess(List<QuizHistory> result) {
                    if (result == null) {
                        return;
                    }
                    QuizHistoryListSingle.getInstance().setQuizHistoryList(result);
                }

                @Override
                public void onFail() {
                    Toast.makeText(StudentListActivity.this, "시험 이력 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestStudentList() {
        ApiRequester.getInstance().getTeachersStudents(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                if (result == null) {
                    return;
                }
                mAdapter = new StudentListAdapter(StudentListActivity.this, result);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFail() {
                Toast.makeText(StudentListActivity.this, "학생 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
