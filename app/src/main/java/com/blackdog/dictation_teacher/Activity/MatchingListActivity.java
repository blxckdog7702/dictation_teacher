package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.MatchingListAdapter;
import com.blackdog.dictation_teacher.service.RecyclerItemClickListener;
import com.blackdog.dictation_teacher.service.VerticalSpaceItemDecoration;
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

        toolbarTitle.setText("학생 등록 요청");

        mRecyclerView = (RecyclerView) findViewById(R.id.matching_list_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ApiRequester.getInstance().getTeachersApplicants(MyTeacherInfo.getInstance().getTeacher().getLoginId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                mAdapter = new MatchingListAdapter(MatchingListActivity.this, result);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
            }

            @Override
            public void onFail() {
            }
        });

    }
}
