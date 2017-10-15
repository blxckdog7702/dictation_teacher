package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blackdog.dictation_teacher.Activity.base.BaseChartFragment;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.Util;
import com.blackdog.dictation_teacher.models.ChartItem.ChartItem;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.Teacher;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;

//학생 개인 통계
public class StatsFragment extends BaseChartFragment {

//    @BindView(R.id.lvStats) ListView lvStats;
//    @BindView(R.id.tvStats) TextView tvStats;

    private ArrayList<ChartItem> chartItems;
    private ArrayList<Teacher> teachers;
    private ArrayList<QuizHistory> quizHistories;
    String[] marker = {"Property1","Property2","Property3","Property4","Property5",
            "Property6","Property7","Property8","Property9","Property10",};

    CombinedChart combinedChart;
    PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        //상태바 안보이게 하는 코드
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        ButterKnife.bind(this, view);

//        getServerData();
//        initModels();
//        setupView();

        pieChart = (PieChart) view.findViewById(R.id.student_pie_chart);
        combinedChart = (CombinedChart) view.findViewById(R.id.student_combined_chart);
        drawCombinedChart();
        drawPieChart();

        return view;
    }

    private void drawPieChart() {
        pieChart.getLayoutParams().height =  (int) ((Util.getInstance().getDisplayHeigth(getContext()) / 5) * 3);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(100);
        data.add(150);
        data.add(50);
        data.add(1);
        data.add(100);
        data.add(150);
        data.add(50);
        data.add(150);
        data.add(50);

        PieData pieData = generatePieData(data, marker);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void drawCombinedChart() {
        combinedChart.getLayoutParams().height =  (int) ((Util.getInstance().getDisplayHeigth(getContext()) / 5) * 3);

        ArrayList<Integer> data = new ArrayList<>();
        data.add(1);
        data.add(100);
        data.add(150);
        data.add(50);

        LineData lineData = generateLineData(data);
        BarData barData = generateBarData(data);

        CombinedData combinedData = generateCombinedData(lineData, barData);

        combinedChart.setData(combinedData);
        combinedChart.invalidate();
    }
//
//    private void initModels() {
//        ArrayList<Integer> myProperty = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
//        ArrayList<Integer> groupProperty = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
//        ArrayList<Integer> myScore = new ArrayList<>();
//        ArrayList<Double> groupAverage = new ArrayList<>();
//        String[] marker = {"Property1","Property2","Property3","Property4","Property5",
//                "Property6","Property7","Property8","Property9","Property10",};
//        for(QuizHistory quizHistory : quizHistories){
//            groupAverage.add(quizHistory.getAverage());
//            groupProperty.set(0, groupProperty.get(0) + quizHistory.getRectifyCount().getProperty1());
//            groupProperty.set(1, groupProperty.get(1) + quizHistory.getRectifyCount().getProperty2());
//            groupProperty.set(2, groupProperty.get(2) + quizHistory.getRectifyCount().getProperty3());
//            groupProperty.set(3, groupProperty.get(3) + quizHistory.getRectifyCount().getProperty4());
//            groupProperty.set(4, groupProperty.get(4) + quizHistory.getRectifyCount().getProperty5());
//            groupProperty.set(5, groupProperty.get(5) + quizHistory.getRectifyCount().getProperty6());
//            groupProperty.set(6, groupProperty.get(6) + quizHistory.getRectifyCount().getProperty7());
//            groupProperty.set(7, groupProperty.get(7) + quizHistory.getRectifyCount().getProperty8());
//            groupProperty.set(8, groupProperty.get(8) + quizHistory.getRectifyCount().getProperty9());
//            groupProperty.set(9, groupProperty.get(9) + quizHistory.getRectifyCount().getProperty10());
//            for(QuizResult quizResult : quizHistory.getQuizResults()){
//                if(quizResult.getStudentName().equals(Student.getInstance().getName())){
//                    myScore.add(quizResult.getScore());
//                    myProperty.set(0, myProperty.get(0) + quizResult.getRectifyCount().getProperty1());
//                    myProperty.set(1, myProperty.get(1) + quizResult.getRectifyCount().getProperty2());
//                    myProperty.set(2, myProperty.get(2) + quizResult.getRectifyCount().getProperty3());
//                    myProperty.set(3, myProperty.get(3) + quizResult.getRectifyCount().getProperty4());
//                    myProperty.set(4, myProperty.get(4) + quizResult.getRectifyCount().getProperty5());
//                    myProperty.set(5, myProperty.get(5) + quizResult.getRectifyCount().getProperty6());
//                    myProperty.set(6, myProperty.get(6) + quizResult.getRectifyCount().getProperty7());
//                    myProperty.set(7, myProperty.get(7) + quizResult.getRectifyCount().getProperty8());
//                    myProperty.set(8, myProperty.get(8) + quizResult.getRectifyCount().getProperty9());
//                    myProperty.set(9, myProperty.get(9) + quizResult.getRectifyCount().getProperty10());
//                }
//            }
//        }
//        chartItems = new ArrayList<>();
//        chartItems.add(new PieChartItem(generatePieData(myProperty, marker), getActivity()));
//        chartItems.add(new CombinedChartItem(generateCombinedData(generateLineData(groupProperty), generateBarData(myScore)), getActivity()));
//        chartItems.add(new BubbleChartItem(generateBubbleata(groupProperty), getActivity()));
//    }
//
//    private void setupView() {
//        ChartDataAdapter cda = new ChartDataAdapter(getActivity(), chartItems);
//        lvStats.setAdapter(cda);
//    }

//    private void getServerData(){
//        ApiRequester apiRequester = new ApiRequester();
//        try {
//            apiRequester.getStudentsTeachers(Student.getInstance().getId(), new ApiRequester.UserCallback<List<Teacher>>() {
//                @Override
//                public void onSuccess(List<Teacher> result) {
//                    teachers = (ArrayList<Teacher>) result;
//                }
//
//                @Override
//                public void onFail() {
//                    Log.e("RecordFragment", "Server Error");
//                }
//            });
//            if(teachers != null){
//                for(Teacher teacher : teachers){
//                    apiRequester.getTeachersQuizHistories(teacher.getLoginId(), new ApiRequester.UserCallback<List<QuizHistory>>() {
//
//                        @Override
//                        public void onSuccess(List<QuizHistory> result) {
//                            quizHistories = (ArrayList<QuizHistory>) result;
//                        }
//
//                        @Override
//                        public void onFail() {
//                            Log.e("RecordFragment", "Server Error");
//                        }
//                    });
//                }
//            }
//            else{
//                tvStats.setText("등록된 선생님이 없습니다.");
//            }
//
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            Log.e("RecordFragment0", e.getMessage().toString());
//        }
//    }

}
