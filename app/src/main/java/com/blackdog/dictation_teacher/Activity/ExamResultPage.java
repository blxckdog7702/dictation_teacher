package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.singleton.Util;
import com.blackdog.dictation_teacher.models.Question;
import com.blackdog.dictation_teacher.models.QuestionResult;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.blackdog.dictation_teacher.models.retify.PnuNlpSpeller;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class ExamResultPage extends BaseActivity {

    QuizResult quizResult;
    List<Question> questions;

    @BindView(R.id.ivGradeOne)
    ImageView ivGradeOne;
    @BindView(R.id.ivGradeTwo)
    ImageView ivGradeTwo;
    @BindView(R.id.ivGradeThree)
    ImageView ivGradeThree;
    @BindView(R.id.ivGradeFour)
    ImageView ivGradeFour;
    @BindView(R.id.ivGradeFive)
    ImageView ivGradeFive;
    @BindView(R.id.ivGradeSix)
    ImageView ivGradeSix;
    @BindView(R.id.ivGradeSeven)
    ImageView ivGradeSeven;
    @BindView(R.id.ivGradeEight)
    ImageView ivGradeEight;
    @BindView(R.id.ivGradeNine)
    ImageView ivGradeNine;
    @BindView(R.id.ivGradeTen)
    ImageView ivGradeTen;
    @BindView(R.id.ivScore)
    ImageView ivScore;
    @BindView(R.id.konfettiView)
    KonfettiView konfettiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_result_page);

        toolbarTitle.setText("시험 상세 보기");

        ButterKnife.bind(this);

        Intent intent = getIntent();
        quizResult = (QuizResult) intent.getSerializableExtra("quizResult");

        if (quizResult == null) {
            return;
        }
        questions = quizResult.getQuiz().getQuestions();

        setScoreImage();
        setCorrectImage();
    }

    private void setCorrectImage() {
        for (QuestionResult questionResult : quizResult.getQuestionResult()) {
            if (questionResult.getQuestionNumber() == 1) {
                if (questionResult.getCorrect()) {
                    ivGradeOne.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeOne.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 2) {
                if (questionResult.getCorrect()) {
                    ivGradeTwo.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeTwo.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 3) {
                if (questionResult.getCorrect()) {
                    ivGradeThree.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeThree.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 4) {
                if (questionResult.getCorrect()) {
                    ivGradeFour.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeFour.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 5) {
                if (questionResult.getCorrect()) {
                    ivGradeFive.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeFive.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 6) {
                if (questionResult.getCorrect()) {
                    ivGradeSix.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeSix.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 7) {
                if (questionResult.getCorrect()) {
                    ivGradeSeven.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeSeven.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 8) {
                if (questionResult.getCorrect()) {
                    ivGradeEight.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeEight.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 9) {
                if (questionResult.getCorrect()) {
                    ivGradeNine.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeNine.setImageResource(R.drawable.ic_check_no);
                }
            } else if (questionResult.getQuestionNumber() == 10) {
                if (questionResult.getCorrect()) {
                    ivGradeTen.setImageResource(R.drawable.ic_check_ok);
                } else {
                    ivGradeTen.setImageResource(R.drawable.ic_check_no);
                }
            }
        }
    }

    private void setScoreImage() {
        if (quizResult.getScore() == 0) {
            ivScore.setImageResource(R.drawable.score0);
        } else if (quizResult.getScore() == 10) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 20) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 30) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 40) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 50) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 60) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 70) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 80) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 90) {
            ivScore.setImageResource(R.drawable.score10);
        } else if (quizResult.getScore() == 100) {
            ivScore.setImageResource(R.drawable.score100);
            konfettiView.build()
                    .addColors(Color.rgb(241, 95, 95), Color.rgb(165, 102, 255), Color.rgb(250, 237, 125))
                    .setDirection(0.0, 359.0)
                    .setSpeed(3f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(1500L)
                    .addShapes(Shape.RECT)
                    .addSizes(new Size(12, 5f))
                    .setPosition(0f, Util.getInstance().getDisplayWidth(this), 0f, 50f)
                    .stream(300, 3000L);
        }
    }

    private void showDetailPage(int num) {
        Intent intent = new Intent(this, ExamResultDetailedPage.class);

        if(quizResult.getQuestionResult().size() - 1 < num) {
            Toast.makeText(this, "시험 내역이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra("questionResult", quizResult.getQuestionResult().get(num));
        intent.putExtra("question", questions.get(num).getSentence());
        startActivity(intent);
    }

    @OnClick(R.id.btResultOne)
    void onClickBtResultOne() {
        showDetailPage(0);
    }

    @OnClick(R.id.btResultTwo)
    void onClickBtResultTwo() {
        showDetailPage(1);
    }

    @OnClick(R.id.btResultThree)
    void onClickBtResultThree() {
        showDetailPage(2);
    }

    @OnClick(R.id.btResultFour)
    void onClickBtResultFour() {
        showDetailPage(3);
    }

    @OnClick(R.id.btResultFive)
    void onClickBtResultFive() {
        showDetailPage(4);
    }

    @OnClick(R.id.btResultSix)
    void onClickBtResultSix() {
        showDetailPage(5);
    }

    @OnClick(R.id.btResultSeven)
    void onClickBtResultSeven() {
        showDetailPage(6);
    }

    @OnClick(R.id.btResultEight)
    void onClickBtResultEight() {
        showDetailPage(7);
    }

    @OnClick(R.id.btResultNine)
    void onClickBtResultNine() {
        showDetailPage(8);
    }

    @OnClick(R.id.btResultTen)
    void onClickBtResultTen() {
        showDetailPage(9);
    }
}
