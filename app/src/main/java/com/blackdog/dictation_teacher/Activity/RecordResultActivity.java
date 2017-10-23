package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseChartActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Question;
import com.blackdog.dictation_teacher.models.QuestionResult;
import com.blackdog.dictation_teacher.singleton.Util;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.blackdog.dictation_teacher.models.RectifyCount;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

//몇점이에요~ 액티비티
public class RecordResultActivity extends BaseChartActivity implements OnChartValueSelectedListener {
    private static final String TAG = "RecordResultActivity";

    QuizResult quizResult;
    List<Question> questions;

    @BindView(R.id.pieChart)
    PieChart pieChart;
    private Typeface tf;

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

    String[] marker = {"띄어쓰기", "맞춤법", "붙여쓰기"};

    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record_result);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        quizResult = (QuizResult) intent.getSerializableExtra("quizResult");

        if (quizResult == null) {
            return;
        }

        questions = quizResult.getQuiz().getQuestions();

        if (toolbarTitle != null) {
            toolbarTitle.setText(quizResult.getStudentName() + "의 " + quizResult.getDate() + " 시험");
        }

        setScoreImage();
        setCorrectImage();

        drawPieChart();
        pieChart.getLayoutParams().height = (int) ((Util.getInstance().getDisplayHeigth(this) / 5) * 3);
        setPieChartStyle();
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
            ivScore.setImageResource(R.drawable.score_00);
        } else if (quizResult.getScore() == 10) {
            ivScore.setImageResource(R.drawable.score_10);
        } else if (quizResult.getScore() == 20) {
            ivScore.setImageResource(R.drawable.score_20);
        } else if (quizResult.getScore() == 30) {
            ivScore.setImageResource(R.drawable.score_30);
        } else if (quizResult.getScore() == 40) {
            ivScore.setImageResource(R.drawable.score_40);
        } else if (quizResult.getScore() == 50) {
            ivScore.setImageResource(R.drawable.score_50);
        } else if (quizResult.getScore() == 60) {
            ivScore.setImageResource(R.drawable.score_60);
        } else if (quizResult.getScore() == 70) {
            ivScore.setImageResource(R.drawable.score_70);
        } else if (quizResult.getScore() == 80) {
            ivScore.setImageResource(R.drawable.score_80);
        } else if (quizResult.getScore() == 90) {
            ivScore.setImageResource(R.drawable.score_90);
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
        intent.putExtra("quizResult", quizResult);
        intent.putExtra("questionNumber", num);
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

    private void drawPieChart() {
        RectifyCount rectifyCount = quizResult.getRectifyCount();
        ArrayList<Integer> data = new ArrayList<>();
        data.add(rectifyCount.getProperty1());
        data.add(rectifyCount.getProperty2());
        data.add(rectifyCount.getProperty3());
//        data.add(rectifyCount.getProperty4());
//        data.add(rectifyCount.getProperty5());
//        data.add(rectifyCount.getProperty6());
//        data.add(rectifyCount.getProperty7());
//        data.add(rectifyCount.getProperty8());
//        data.add(rectifyCount.getProperty9());
//        data.add(rectifyCount.getProperty10());

        PieData pieData = generatePieData(data, marker);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void setPieChartStyle() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        pieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        pieChart.setOnChartValueSelectedListener(this);

//        setData(4, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(false);

        // entry label styling
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.setEntryLabelTextSize(12f);

        pieChart.animateX(750);
        pieChart.animateY(700);
    }

//    private void setData(int count, float range) {
//
//        float mult = range;
//        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
//
//        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
//        // the chart.
//        for (int i = 0; i < count; i++) {
//            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
//        }
//
//        PieDataSet dataSet = new PieDataSet(entries, "");
//
//        dataSet.setDrawIcons(false);
//
//        dataSet.setSliceSpace(10f);
//        dataSet.setIconsOffset(new MPPointF(0, 40));
//        dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//        //dataSet.setSelectionShift(0f);
//
//
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
//        data.setValueTypeface(tf);
//        pieChart.setData(data);
//
//        // undo all highlights
//        pieChart.highlightValues(null);
//
//        pieChart.invalidate();
//    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("어떤 맞춤법이 취약한지 참고하세요!");
//        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}
