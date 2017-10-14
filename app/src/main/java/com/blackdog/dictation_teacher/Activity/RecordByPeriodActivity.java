package com.blackdog.dictation_teacher.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.R;

public class RecordByPeriodActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_by_period);

        toolbarTitle.setText("회차 별 시험 리스트");
    }
}
