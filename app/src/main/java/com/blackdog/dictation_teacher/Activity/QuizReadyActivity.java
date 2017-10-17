package com.blackdog.dictation_teacher.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.net.ApiRequester;
import com.blackdog.dictation_teacher.service.MyFirebaseMessagingService;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class QuizReadyActivity extends BaseActivity {
    private Button button;
    private Quiz selectedQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_ready);
        toolbarTitle.setText("대기 학생 확인");

        Intent intent = getIntent();
        selectedQuiz = (Quiz) intent.getSerializableExtra("selectedQuiz");

        if(selectedQuiz == null) {
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
    }

    public void startQuizClick() {
        try {
            ApiRequester.getInstance().startQuiz(MyTeacherInfo.getInstance().getTeacher().getId(), selectedQuiz.getNumber());
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
//            if(intent.getExtras().getString("message").equals("ready")) {
            Toast.makeText(QuizReadyActivity.this, name + "이가 + " + message, Toast.LENGTH_SHORT).show();
//            }
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
