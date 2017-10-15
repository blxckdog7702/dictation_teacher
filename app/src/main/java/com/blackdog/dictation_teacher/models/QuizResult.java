
package com.blackdog.dictation_teacher.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizResult implements Serializable {

    @SerializedName("lank")
    @Expose
    private Integer rank;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("question_results")
    @Expose
    private List<QuestionResult> questionResult = null;
    @SerializedName("rectify_count")
    @Expose
    private RectifyCount rectifyCount;
    @SerializedName("quiz_number")
    @Expose
    private Integer quizNumber;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Integer getQuizNumber() {
        return quizNumber;
    }

    public void setQuizNumber(Integer quizNumber) {
        this.quizNumber = quizNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<QuestionResult> getQuestionResult() {
        return questionResult;
    }

    public void setQuestionResult(List<QuestionResult> questionResult) {
        this.questionResult = questionResult;
    }

    public RectifyCount getRectifyCount() {
        return rectifyCount;
    }

    public void setRectifyCount(RectifyCount rectifyCount) {
        this.rectifyCount = rectifyCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
