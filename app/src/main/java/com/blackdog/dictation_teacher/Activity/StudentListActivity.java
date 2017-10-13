package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.StudentListAdapter;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends BaseDrawerActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        toolbarTitle.setText("학생목록");

        mRecyclerView = (RecyclerView) findViewById(R.id.student_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Student> dataset = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Student item = new Student();
            item.setSchool("호성초등학교");
            item.setGrade("1학년");
            item.setClass_("2반");
            item.setStudentId(1);
            item.setName("홍길동");

            dataset.add(item);
        }

        // specify an adapter (see also next example)
        mAdapter = new StudentListAdapter(this, dataset);
        mRecyclerView.setAdapter(mAdapter);
    }
}
