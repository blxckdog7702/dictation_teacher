package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseDrawerActivity;
import com.blackdog.dictation_teacher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuizMakingActivity extends BaseDrawerActivity {
    private static final String TAG = "QuizMakingActivity.java";

    @BindView(R.id.et_quiz_name)
    TextView mQuizName;
    @BindView(R.id.et_question_1)
    TextView mQuestion1;
    @BindView(R.id.et_question_2)
    TextView mQuestion2;
    @BindView(R.id.et_question_3)
    TextView mQuestion3;
    @BindView(R.id.et_question_4)
    TextView mQuestion4;
    @BindView(R.id.et_question_5)
    TextView mQuestion5;
    @BindView(R.id.et_question_6)
    TextView mQuestion6;
    @BindView(R.id.et_question_7)
    TextView mQuestion7;
    @BindView(R.id.et_question_8)
    TextView mQuestion8;
    @BindView(R.id.et_question_9)
    TextView mQuestion9;
    @BindView(R.id.et_question_10)
    TextView mQuestion10;
    @BindView(R.id.bt_make_quiz)
    Button mButtonMakeQuiz;

    @OnClick(R.id.bt_make_quiz)
    void onClickBtMakeQuiz() {
        //빈칸 체크
        if (isEmptyForm()) {
            return;
        }

        //문제 보내기 요청
    }

    // TODO: 2017-10-14 키보드가 et 가림

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_making);

        toolbarTitle.setText("문제 작성");

        ButterKnife.bind(this);
    }

    private boolean isEmptyForm() {
        if (mQuizName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "시험 제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuizName.requestFocus();
            return true;
        } else if (mQuestion1.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion1.requestFocus();
            return true;
        } else if (mQuestion2.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion2.requestFocus();
            return true;
        } else if (mQuestion3.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion3.requestFocus();
            return true;
        } else if (mQuestion4.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion4.requestFocus();
            return true;
        } else if (mQuestion5.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion5.requestFocus();
            return true;
        } else if (mQuestion6.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion6.requestFocus();
            return true;
        } else if (mQuestion7.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion7.requestFocus();
            return true;
        } else if (mQuestion8.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion8.requestFocus();
            return true;
        } else if (mQuestion9.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion9.requestFocus();
            return true;
        } else if (mQuestion10.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "문제를 입력해주세요.", Toast.LENGTH_SHORT).show();
            mQuestion10.requestFocus();
            return true;
        } else {
            return false;
        }
    }
}
