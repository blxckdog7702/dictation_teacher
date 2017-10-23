package com.blackdog.dictation_teacher.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseActivity;
import com.blackdog.dictation_teacher.Adapter.ReadyListAdapter;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.models.StudentReady;
import com.blackdog.dictation_teacher.net.ApiRequester;
import com.blackdog.dictation_teacher.service.MyFirebaseMessagingService;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.google.firebase.messaging.FirebaseMessaging;
import com.skyfishjy.library.RippleBackground;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class QuizReadyActivity extends BaseActivity {
    private static final String TAG = "QuizReadyActivity";
    private Button button;
    private Quiz selectedQuiz;
    private TextView tvNumberOfReady;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Student> mStudentList;
    private List<StudentReady> mReadyList;
    private int numberOfReady;
    private RippleBackground rbRippleBackground;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_ready);
        rbRippleBackground = (RippleBackground) findViewById(R.id.rbRippleBackground);
        rbRippleBackground.bringToFront();
        img = (ImageView) findViewById(R.id.ivStudentPhone);
        rbRippleBackground.startRippleAnimation();

        toolbarTitle.setText("대기 학생 확인");

        Intent intent = getIntent();
        selectedQuiz = (Quiz) intent.getSerializableExtra("selectedQuiz");

        if (selectedQuiz == null) {
            return;
        }

        registerReceiver(myReciver, new IntentFilter(MyFirebaseMessagingService.READY_TO_QUIZ));

        button = (Button) findViewById(R.id.bt_quiz_start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuizClick();
            }
        });
        tvNumberOfReady = (TextView) findViewById(R.id.tv_number_of_ready);
        mRecyclerView = (RecyclerView) findViewById(R.id.ready_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mReadyList = new ArrayList<>();
        requestStudentList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciver);
    }

    private void requestStudentList() {
        ApiRequester.getInstance().getTeachersStudents(MyTeacherInfo.getInstance().getTeacher().getId(), new ApiRequester.UserCallback<List<Student>>() {
            @Override
            public void onSuccess(List<Student> result) {
                if (result == null) {
                    return;
                }

                mStudentList = result;

                for (Student item : mStudentList) {
                    StudentReady convert = new StudentReady();
                    convert.setName(item.getName());
                    convert.setId(item.getId());
                    convert.setSchool(item.getSchool());
                    convert.set_class(item.getClass_());
                    convert.setGrade(item.getGrade());
                    convert.setNumber(String.valueOf(item.getStudentId()));

                    convert.setReady(false);
                    mReadyList.add(convert);
                }


                mAdapter = new ReadyListAdapter(mReadyList);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFail() {
                Toast.makeText(QuizReadyActivity.this, "학생 목록을 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startQuizClick() {
        if (numberOfReady < 1) {
            Toast.makeText(QuizReadyActivity.this, "준비 학생이 1명 이상이여야 시험을 시작할 수 있습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            ApiRequester.getInstance().startQuiz(MyTeacherInfo.getInstance().getTeacher().getId(), selectedQuiz.getNumber(), numberOfReady);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), QuizControlActivity.class);
        intent.putExtra("selectedQuiz", selectedQuiz);
        startActivity(intent);
    }

    private BroadcastReceiver myReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("message");
            String name = intent.getStringExtra("name");
            String id = intent.getStringExtra("id");

            if (name == null) {
                return;
            }

            if (message == null) {
                return;
            }

            if (id == null) {
                return;
            }

            if (message.equals("ready")) {
                for (StudentReady item : mReadyList) {
                    //테스트는 이름으로
//                    if(item.getName().equals(name)) {
                    if (item.getId().equals(id)) {
                        numberOfReady++;
                        item.setReady(true);
                        Toast.makeText(QuizReadyActivity.this, name + "학생이 대기합니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                mAdapter.notifyDataSetChanged();
            } else if (message.equals("cancel")) {
                for (StudentReady item : mReadyList) {
                    //테스트는 이름으로
//                    if(item.getName().equals(name)) {
                    if (item.getId().equals(id)) {
                        if (numberOfReady > 0) {
                            numberOfReady--;
                            item.setReady(false);
                            Toast.makeText(QuizReadyActivity.this, name + "학생이 대기를 취소합니다.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            tvNumberOfReady.setText("현재 대기 학생 수 : " + numberOfReady);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseMessaging.getInstance().subscribeToTopic(MyTeacherInfo.getInstance().getTeacher().getLoginId() + "TEACHER");
    }

    @Override
    protected void onPause() {
        super.onPause();
        FirebaseMessaging.getInstance().unsubscribeFromTopic(MyTeacherInfo.getInstance().getTeacher().getLoginId() + "TEACHER");
    }
}
