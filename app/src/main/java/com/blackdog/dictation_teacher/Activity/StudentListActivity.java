package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.StudentListAdapter;
import com.blackdog.dictation_teacher.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.List;

public class StudentListActivity extends BaseDrawerActivity {
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

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

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

    private void requestStudentList() {
        ApiRequester.getInstance().getTeachersStudents(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                if(result == null) {
                    return;
                }
                mAdapter = new StudentListAdapter(getApplicationContext(), result);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
