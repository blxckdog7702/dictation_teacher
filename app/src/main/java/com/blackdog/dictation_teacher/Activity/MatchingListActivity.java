package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.MatchingListAdapter;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.List;

public class MatchingListActivity extends BaseDrawerActivity {
    private static final String TAG = "MatchingListActivity.java";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.matching_list_recycler_view);

        toolbarTitle.setText("학생 등록 요청");

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ApiRequester.getInstance().getTeachersApplicants(MyTeacherInfo.getInstance().getTeacher().getLoginId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                mAdapter = new MatchingListAdapter(getApplicationContext(), result);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFail() {

            }
        });
    }
}
