package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseChartActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.blackdog.dictation_teacher.models.RectifyCount;
import com.blackdog.dictation_teacher.singleton.QuizHistoryListSingle;
import com.blackdog.dictation_teacher.singleton.Util;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;
import java.util.List;

public class TotalStatisticsActivity extends BaseChartActivity {
    LineChart lineChart;
    BubbleChart bubbleChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_statistics);

        toolbarTitle.setText("반 전체 통계");
        drawLineChart();
        drawBubbleChart();
    }

    private void drawBubbleChart() {
        bubbleChart = (BubbleChart) findViewById(R.id.total_stat_bubble_chart);
        bubbleChart.getLayoutParams().height = (int) ((Util.getInstance().getDisplayHeigth(this) / 5) * 3);

        ArrayList<Integer> rectifyTotal= new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            rectifyTotal.add(0);
        }

        List<QuizHistory> quizResultList = QuizHistoryListSingle.getInstance().getQuizHistoryList();

        for (QuizHistory QuizHistory : quizResultList) {
            RectifyCount item = QuizHistory.getRectifyCount();
            rectifyTotal.set(0, rectifyTotal.get(0) + item.getProperty1());
            rectifyTotal.set(1, rectifyTotal.get(1) + item.getProperty2());
            rectifyTotal.set(2, rectifyTotal.get(2) + item.getProperty3());
            rectifyTotal.set(3, rectifyTotal.get(3) + item.getProperty4());
            rectifyTotal.set(4, rectifyTotal.get(4) + item.getProperty5());
            rectifyTotal.set(5, rectifyTotal.get(5) + item.getProperty6());
            rectifyTotal.set(6, rectifyTotal.get(6) + item.getProperty7());
            rectifyTotal.set(7, rectifyTotal.get(7) + item.getProperty8());
            rectifyTotal.set(8, rectifyTotal.get(8) + item.getProperty9());
            rectifyTotal.set(9, rectifyTotal.get(9) + item.getProperty10());
        }

        BubbleData bubbleData = generateBubbleData(rectifyTotal);
        bubbleChart.setData(bubbleData);
        bubbleChart.invalidate();
    }

    private void drawLineChart() {
        lineChart = (LineChart) findViewById(R.id.total_stat_line_chart);
        lineChart.getLayoutParams().height = (int) ((Util.getInstance().getDisplayHeigth(this) / 5) * 3);

        ArrayList<Integer> averageTotal = new ArrayList<>();

        List<QuizHistory> list = QuizHistoryListSingle.getInstance().getQuizHistoryList();

        if(list == null) {
            return;
        }

        for (QuizHistory item : list) {
            averageTotal.add(item.getAverage().intValue());
        }

        LineData lineData = generateLineData(averageTotal);

        lineChart.setData(lineData);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.invalidate();
    }
}
