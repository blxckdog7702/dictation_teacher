package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseChartActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.singleton.Util;
import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.LineData;

import java.util.ArrayList;

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
        bubbleChart.getLayoutParams().height =  (int) ((Util.getInstance().getDisplayHeigth(this) / 5) * 3);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(100);
        data.add(150);
        data.add(50);

        BubbleData bubbleData = generateBubbleData(data);
        bubbleChart.setData(bubbleData);
        bubbleChart.invalidate();
    }

    private void drawLineChart() {
        lineChart = (LineChart) findViewById(R.id.total_stat_line_chart);
        lineChart.getLayoutParams().height =  (int) ((Util.getInstance().getDisplayHeigth(this) / 5) * 3);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(100);
        data.add(150);
        data.add(50);

        LineData lineData = generateLineData(data);

        lineChart.setData(lineData);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.invalidate();
    }
}
