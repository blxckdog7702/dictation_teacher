package com.blackdog.dictation_teacher.net;

import android.util.Log;

import com.blackdog.dictation_teacher.models.EndedQuiz;
import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.QuizResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRequester {
    private static String TAG = "FcmRequester.java";
    public static final String TEACHER_ID = "599b03151c6e6f0159a72815";
    private static ApiRequester instance;

    DictationServerApi dictationServerApi;
    Gson gson;
    JsonParser parser;

    Callback callback;

    private ApiRequester() {
        parser = new JsonParser();
        gson = new Gson();
        dictationServerApi = DictationServerApi.retrofit.create(DictationServerApi.class);
    }

    public static ApiRequester getInstance() {
        if (instance == null) {
            instance = new ApiRequester();
        }
        return instance;
    }

    public interface UserCallback<T> {
        void onSuccess(T result);

        void onFail();
    }

    private class CustomCallback<T> implements Callback<T> {
        UserCallback callback;

        public CustomCallback(UserCallback<T> _callback) {
            callback = _callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            // TODO Auto-generated method stub
            if (response.isSuccessful()) {
                // tasks available
                callback.onSuccess(response.body());
            } else {
                // error response, no access to resource?
                System.out.println("서버 실패");
                callback.onFail();
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            // TODO Auto-generated method stub

            System.out.println(t.getMessage());
            callback.onFail();
        }
    }

    //quiz list를 리턴한다
    public void getTeachersQuizzes(UserCallback<List<Quiz>> userCallback) throws IOException {
        Call<List<Quiz>> call = dictationServerApi.getTeachersQuizzes();
        call.enqueue(new CustomCallback<List<Quiz>>(userCallback));
    }

    //quiz history를 리턴한다
    public void getQuizHistory(String id, UserCallback<QuizHistory> userCallback) throws IOException {
        Call<QuizHistory> call = dictationServerApi.getQuizHistory(id);
        call.enqueue(new CustomCallback<QuizHistory>(userCallback));
    }

    //선생님의 quiz history를 리턴한다
    public void getTeachersQuizHistories(String id, UserCallback<List<QuizHistory>> userCallback) throws IOException {
        Call<List<QuizHistory>> call = dictationServerApi.getTeachersQuizHistories(id);
        call.enqueue(new CustomCallback<List<QuizHistory>>(userCallback));
    }

    //시험을 시작하고 quiz history id를 리턴한다
    public void startQuiz(String teacherId, final int quizNumber) throws JsonSyntaxException, IOException {
        Call<ResponseBody> call = dictationServerApi.startQuiz(teacherId, quizNumber);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                    JsonObject object = null;
                    try {
                        object = new JsonParser().parse(response.body().string()).getAsJsonObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String quizHistoryId = null;
                    if (object == null) {
                        return;
                    }
                    quizHistoryId = object.get("quiz_history_id").getAsString();

                    try {
                        FcmRequester.getInstance().requestDictationStart(quizNumber, quizHistoryId);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d(TAG, "error onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "error onResponse: " + t.getMessage());
            }
        });
    }

    //시험을 끝내고 quiz result를 전송한다
    public void endQuiz(String quizHistoryId, String studentId, QuizResult quizResult, UserCallback<QuizHistory> userCallback) throws JsonSyntaxException, IOException {
        EndedQuiz endedQuiz = new EndedQuiz();
        endedQuiz.setQuizHistoryId(quizHistoryId);
        endedQuiz.setQuizResult(quizResult);
        endedQuiz.setStudentId(studentId);

        Call<QuizHistory> call = dictationServerApi.endQuiz(parser.parse(gson.toJson(endedQuiz)).getAsJsonObject());
        call.enqueue(new CustomCallback<QuizHistory>(userCallback));
    }
}
