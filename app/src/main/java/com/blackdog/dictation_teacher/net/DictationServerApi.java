package com.blackdog.dictation_teacher.net;

import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.School;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.models.Teacher;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by user on 2017-08-14.
 */

public interface DictationServerApi {

    //학교 목록보기
    @GET("/schools")
    Call<List<School>> getSchools();
    //학교 검색하기
    @GET("/schools/search")
    Call<List<School>> searchSchool(@Query("region1") String region1, @Query("region2") String region2, @Query("name") String name);
		//매칭 신청하기
		@FormUrlEncoded
		@POST("/matching/apply")
		Call<ResponseBody> applyMatching(@Field("teacher_login_id") String teacherLoginID, @Field("student_id") String studentID);
		//매칭 수락하기
		@FormUrlEncoded
		@POST("/matching/accept")
		Call<ResponseBody> acceptMatching(@Field("teacher_login_id") String teacherLoginID, @Field("student_id") String studentID);
		//매칭 삭제하기
		@FormUrlEncoded
		@POST("/matching/cancel")
		Call<ResponseBody> cancelMatching(@Field("teacher_login_id") String teacherLoginID, @Field("student_id") String studentID);
		//매칭 목록보기
		@GET("/matching/list/{teacher_login_id}")
		Call<List<Student>> getTeachersApplicants(@Path("teacher_login_id") String teacherLoginID);
    //선생님 중복 검사
	  @GET("/teachers/check_duplicate")
	  Call<ResponseBody> checkDuplicateTeacher( @Query("login_id") String loginId);
	  //선생님 가입
	  @POST("/teachers")
	  Call<Teacher> signUpTeacher(@Body JsonObject teacher);
    //퀴즈 목록 가져오기

    @GET("/quizzes")
    Call<List<Quiz>> getTeachersQuizzes();

    //선생님 로그인
    @FormUrlEncoded
    @POST("/auth/login")
    Call<Teacher> login(@Field("login_id") String loginID, @Field("password") String password, @Field("type") String type);

    @GET("/teachers/{id}/quiz_histories")
    Call<List<QuizHistory>> getTeachersQuizHistories(@Path("id") String id);

    @GET("/quiz_histories/{id}")
    Call<QuizHistory> getQuizHistory(@Path("id") String id);

    @FormUrlEncoded
    @POST("/quiz/start")
    Call<ResponseBody> startQuiz(@Field("teacher_id") String teacherId, @Field("quiz_number") int quizNumber);

    @Headers("Content-Type: application/json")
    @POST("/quiz/end")
    Call<QuizHistory> endQuiz(@Body JsonObject endedQuiz);

    public static final Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("http://dictation.run.goorm.io")
            //테스트용 서버1
            .baseUrl("https://dev-dictation-server.herokuapp.com")
            //테스트용 서버2
//            .baseUrl("https://dictation-server-minung.c9users.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
