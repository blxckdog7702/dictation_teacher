package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Adapter.ApplicantListAdapter;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.List;

public class ApplicantActivity extends AppCompatActivity {

    private List<Student> applicantList;
    private RecyclerView rv_list;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final String teacherLoginID = "test@test.com";

        //로딩바
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //리스트 셋팅
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(new LinearLayoutManager(this));

        ApiRequester.getInstance().getTeachersApplicants(teacherLoginID, new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                applicantList = result;
                //TODO: 화면 갱신하도록
                rv_list.setAdapter(new ApplicantListAdapter(ApplicantActivity.this, teacherLoginID, applicantList));
                progressBar.setVisibility(View.GONE);
                Log.d("dev","어뎁터 갱신");
            }

            @Override
            public void onFail() {
                //TODO: 토스트를 띄울까..
                Toast.makeText(getBaseContext(),"지원자 정보 가져오기 실패",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
