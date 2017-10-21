package com.blackdog.dictation_teacher.Activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.Adapter.StudentListAdapter;
import com.blackdog.dictation_teacher.service.RecyclerItemClickListener;
import com.blackdog.dictation_teacher.service.VerticalSpaceItemDecoration;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.net.ApiRequester;
import com.blackdog.dictation_teacher.singleton.QuizHistoryListSingle;

import java.io.IOException;
import java.util.List;

public class StudentListActivity extends BaseDrawerActivity {
    private static final String TAG = "StudentListActivity";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mTotalStatButton;
    private List<Student> mStudentList;
    private Student selectedstudent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        toolbarTitle.setText("학생 목록");

       // mTotalStatButton = (TextView) findViewById(R.id.bt_total_stat);
       // mTotalStatButton.setPaintFlags(mTotalStatButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        mRecyclerView = (RecyclerView) findViewById(R.id.student_recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        requestTeachersQuizHistoryList();
        requestStudentList();

//        mTotalStatButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //우리반전체통계
//                Intent intent = new Intent(getApplicationContext(), TotalStatisticsActivity.class);
//                startActivity(intent);
//            }
//        });

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                selectedstudent = mStudentList.get(position);

                if(selectedstudent == null) {
                    Log.d(TAG, "onClick: 클릭한 학생 객체가 null");
                    return;
                }
                Toast.makeText(getApplicationContext(), selectedstudent.getName() ,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), RecordManagerActivity.class);
                intent.putExtra("student", selectedstudent);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(getApplicationContext(),position+"번 째 아이템 롱 클릭",Toast.LENGTH_SHORT).show();
            }
        }));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.student_list_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.totalbutton)
        {
            Toast.makeText(getApplicationContext(), "전체성적보기", Toast.LENGTH_SHORT).show();


            //우리반전체통계
            Intent intent = new Intent(getApplicationContext(), TotalStatisticsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestTeachersQuizHistoryList() {
        try {
            ApiRequester.getInstance().getTeachersQuizHistories(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<QuizHistory>>() {
                @Override
                public void onSuccess(List<QuizHistory> result) {
                    if (result == null) {
                        return;
                    }
                    QuizHistoryListSingle.getInstance().setQuizHistoryList(result);
                }

                @Override
                public void onFail() {
                    Toast.makeText(StudentListActivity.this, "시험 이력 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestStudentList() {
        ApiRequester.getInstance().getTeachersStudents(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                if (result == null) {
                    return;
                }
                mStudentList = result;
                mAdapter = new StudentListAdapter(StudentListActivity.this, result);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(48));
            }

            @Override
            public void onFail() {
                Toast.makeText(StudentListActivity.this, "학생 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
