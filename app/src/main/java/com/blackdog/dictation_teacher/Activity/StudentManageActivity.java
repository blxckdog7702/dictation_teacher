package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.StudentManageAdapter;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.List;

public class StudentManageActivity extends BaseDrawerActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);

        toolbarTitle.setText("학생 관리");

        mRecyclerView = (RecyclerView) findViewById(R.id.student_manage_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        requestStudentList();
    }

    private void requestStudentList() {
        ApiRequester.getInstance().getTeachersStudents(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                if(result == null) {
                    return;
                }
                mAdapter = new StudentManageAdapter(getApplicationContext(), result);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
