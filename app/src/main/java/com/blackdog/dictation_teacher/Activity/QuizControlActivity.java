package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.net.FcmRequester;
import com.blackdog.dictation_teacher.net.TTSRequester;

public class QuizControlActivity extends AppCompatActivity {
    private static final String TAG = "QuizControlActivity";
    private TextView tvQuestion;
    private TextView tvCurrentQuestionNumber;
    private Button btnDictationEnd;

    private int currentQuestionNumber = 1;
    private Quiz selectedQuiz;
    
    private TTSRequester mTTSRequester = null;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_control);

        Intent intent = getIntent();
        selectedQuiz = (Quiz) intent.getSerializableExtra("selectedQuiz");

        tvCurrentQuestionNumber = (TextView) findViewById(R.id.text_currentQuestionNumber);
        tvQuestion = (TextView) findViewById(R.id.text_question);
        btnDictationEnd = (Button) findViewById(R.id.button_dictationEnd);

        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        //현재 문제 번호 보여주기
        tvCurrentQuestionNumber.setText(String.valueOf(currentQuestionNumber));
        //현재 문제 내용 보여주기
        tvQuestion.setText(selectedQuiz.getQuestions().get(currentQuestionNumber - 1).getSentence());
        //강제 글자 단위 줄 바꿈
        tvQuestion.setText(tvQuestion.getText().toString().replace(" ", "\u00A0"));
    }


    public void previousClick(View view) {
        //끝내기 버튼 숨김
        if (btnDictationEnd.getVisibility() == View.VISIBLE) {
            btnDictationEnd.setVisibility(View.INVISIBLE);
        }

        if (currentQuestionNumber != 1) {
            FcmRequester.getInstance().requestMoveToPrevious();
            currentQuestionNumber--;
            showCurrentQuestion();
        }
    }

    public void nextClick(View view) {
        //끝내기 버튼 표시
        if (currentQuestionNumber == 9) {
            btnDictationEnd.setVisibility(View.VISIBLE);
        }

        if (currentQuestionNumber != 10) {
            FcmRequester.getInstance().requestMoveToNext();
            currentQuestionNumber++;
            showCurrentQuestion();
        }
    }

    public void readSentenceClick(View view) {
        //disable double click
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();


        //null check
        if (tvQuestion.getText() == null) {
            return;
        }

        //초기 1회
        if (mTTSRequester == null) {
            mTTSRequester = new TTSRequester();
            mTTSRequester.execute(tvQuestion.getText().toString());
        }
        //그 외 버튼 눌렀을 때
        else {
            if (mTTSRequester.getMp() == null) {
                return;
            }
            if (!mTTSRequester.getMp().isPlaying()) {
                mTTSRequester = new TTSRequester();
                mTTSRequester.execute(tvQuestion.getText().toString());
            }
        }
    }

    public void dictationEndClick(View view) {
        FcmRequester.getInstance().requestDictationEnd();
    }
}
