package com.blackdog.dictation_teacher.net;

import com.blackdog.dictation_teacher.models.Quiz;
import com.blackdog.dictation_teacher.models.QuizHistory;
import com.blackdog.dictation_teacher.models.School;
import com.blackdog.dictation_teacher.models.Student;
import com.blackdog.dictation_teacher.models.Teacher;
import com.blackdog.dictation_teacher.models.RectifyCount;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    //선생님의 학생 목록 가져오기
    @GET("/teachers/{teacher_id}/students")
    Call<List<Student>> getTeachersStudents(@Path("teacher_id") String teacherID);
    //선생님이 본 모든 시험 결과에 대한 취약점 합산
  	@GET("/teachers/{teacher_id}/quiz_histories/rectify_count")
  	Call<RectifyCount> getRecifyCountToAllQuizHistories(@Path("teacher_id") String teacherID);
    //등록된 선생님 목록보기
    @GET("/students/{student_id}/teachers")
    Call<List<Teacher>> getStudentsTeachers(@Path("student_id") String studentID);
    //매칭 끊기 - 이거 사용
    @DELETE("/matching/teacher_id/{teacher_id}/student_id/{student_id}")
    Call<ResponseBody> unConnectedMatching(@Path("student_id") String studentID, @Path("teacher_id") String teacherID);
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
    //선생님 퀴즈 목록 가져오기
    @GET("/teachers/{teacher_id}/quizzes")
    Call<List<Quiz>> getTeachersQuizzes(@Path("teacher_id") String teacherID);
    //선생님 퀴즈 목록 추가하기
    @POST("/teachers/{teacher_id}/quizzes")
    Call<ResponseBody> addTeachersQuiz(@Path("teacher_id") String teacherID, @Body JsonObject quiz);

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
            //테스트용 서버3
//            .baseUrl("http://dictation.run.goorm.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
