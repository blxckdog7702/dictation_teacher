package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.R;

public class StudentListActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        toolbarTitle.setText("학생목록");
    }
}
