package com.blackdog.dictation_teacher.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.RecordByPeriodAdapter;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;

import java.util.ArrayList;
import java.util.List;

public class RecordByPeriodActivity extends BaseDrawerActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_by_period);
        toolbarTitle.setText("회차 별 시험 리스트");

        mRecyclerView = (RecyclerView) findViewById(R.id.record_by_period_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<QuizHistory> list = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            QuizHistory item = new QuizHistory();
            item.setDate("2017년 10월 15일");
            list.add(item);
        }


        // specify an adapter (see also next example)
        mAdapter = new RecordByPeriodAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
    }
}
