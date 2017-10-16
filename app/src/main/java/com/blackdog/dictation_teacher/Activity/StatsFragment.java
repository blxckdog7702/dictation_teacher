package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blackdog.dictation_teacher.Activity.base.BaseChartFragment;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.ChartItem.ChartItem;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.blackdog.dictation_teacher.models.RectifyCount;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.models.Teacher;
import com.blackdog.dictation_teacher.singleton.QuizHistoryListSingle;
import com.blackdog.dictation_teacher.singleton.Util;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.PieData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//학생 개인 통계
public class StatsFragment extends BaseChartFragment {

//    @BindView(R.id.lvStats) ListView lvStats;
//    @BindView(R.id.tvStats) TextView tvStats;

    private ArrayList<ChartItem> chartItems;
    private ArrayList<Teacher> teachers;
    private ArrayList<QuizHistory> quizHistories;
    private Student student;

    @BindView(R.id.tv_average_combined_chart)
    TextView tvAverage;
    @BindView(R.id.tv_rectify_pie_chart)
    TextView tvRectify;

    String[] marker = {"Property1", "Property2", "Property3", "Property4", "Property5",
            "Property6", "Property7", "Property8", "Property9", "Property10",};

    CombinedChart combinedChart;
    PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);


        //상태바 안보이게 하는 코드
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ButterKnife.bind(this, view);

//        getServerData();
//        initModels();
//        setupView();
        initStudent();

        tvAverage.setText(student.getName() + " 학생의 점수와 반 평균 점수");
        tvRectify.setText(student.getName() + " 학생이 자주 틀리는 문법");

        pieChart = (PieChart) view.findViewById(R.id.student_pie_chart);
        combinedChart = (CombinedChart) view.findViewById(R.id.student_combined_chart);
        drawCombinedChart();
        drawPieChart();

        return view;
    }

    private void initStudent() {
        student = ((RecordManagerActivity) getActivity()).getStudent();

        if (student == null) {
            return;
        }
    }

    private void drawPieChart() {
        pieChart.getLayoutParams().height = (int) ((Util.getInstance().getDisplayHeigth(getContext()) / 5) * 3);

        ArrayList<Integer> rectifyTotal = new ArrayList<>();

        List<QuizResult> quizResultList = student.getQuizResults();

        for (int i = 0; i < 10; i++) {
            rectifyTotal.add(0);
        }

        for (QuizResult quizResult : quizResultList) {
            RectifyCount item = quizResult.getRectifyCount();
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


        PieData pieData = generatePieData(rectifyTotal, marker);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void drawCombinedChart() {
        combinedChart.getLayoutParams().height = (int) ((Util.getInstance().getDisplayHeigth(getContext()) / 5) * 3);

        ArrayList<Integer> onesAverage = new ArrayList<>();
        ArrayList<Integer> totalAverage = new ArrayList<>();

        List<QuizHistory> list = QuizHistoryListSingle.getInstance().getQuizHistoryList();
        for (QuizHistory quizHistory : list) {
            if (quizHistory.getAverage() == null) {
                break;
            }

            totalAverage.add(quizHistory.getAverage().intValue());

            List<QuizResult> quizResultList = quizHistory.getQuizResults();
            for (QuizResult quizResult : quizResultList) {
                if (quizResult.getStudentName().equals(student.getName())) {
                    onesAverage.add(quizResult.getScore());
                    break;
                }
            }
        }

        if (onesAverage.size() == 0 || totalAverage.size() == 0) {
            return;
        }

        LineData lineData = generateLineData(onesAverage);
        BarData barData = generateBarData(totalAverage);

        //line : 학생
        //bar : 반

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
