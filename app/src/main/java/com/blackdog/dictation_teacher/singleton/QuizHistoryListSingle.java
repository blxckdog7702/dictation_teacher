package com.blackdog.dictation_teacher.singleton;

import com.blackdog.dictation_teacher.models.QuizHistory;

import java.util.List;

/**
 * Created by DH on 2017-10-16.
 */

public class QuizHistoryListSingle {
    private static QuizHistoryListSingle instance;

    private List<QuizHistory> quizHistoryList;

    public static QuizHistoryListSingle getInstance() {
        if (instance == null) {
            instance = new QuizHistoryListSingle();
        }
        return instance;
    }

    public List<QuizHistory> getQuizHistoryList() {
        return quizHistoryList;
    }

    public void setQuizHistoryList(List<QuizHistory> quizHistoryList) {
        this.quizHistoryList = quizHistoryList;
    }
}
