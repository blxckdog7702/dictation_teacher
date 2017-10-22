package com.blackdog.dictation_teacher.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.StudentManageAdapter;
import com.blackdog.dictation_teacher.service.VerticalSpaceItemDecoration;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.List;

public class StudentManageActivity extends BaseDrawerActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_manage);

        toolbarTitle.setText("학생 관리");

        mRecyclerView = (RecyclerView) findViewById(R.id.student_manage_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//
//        alertDialogBuilder
//                .setTitle("매칭끊기").setMessage("선생님과의 매칭을 끊으시겠습니까?")
//                .setPositiveButton("끊기", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton)
//                    {
//                        Toast.makeText(StudentManageActivity.this, "섹스파티", Toast.LENGTH_SHORT).show();
//                    }
//                }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                Toast.makeText(StudentManageActivity.this, "파티파티섹스", Toast.LENGTH_SHORT).show();
//
//            }
//        }).show();

        requestStudentList();
    }

    private void requestStudentList() {
        ApiRequester.getInstance().getTeachersStudents(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                if(result == null) {
                    return;
                }
                mAdapter = new StudentManageAdapter(StudentManageActivity.this, result);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(15));
            }
            @Override
            public void onFail() {
            }
        });
    }
}
