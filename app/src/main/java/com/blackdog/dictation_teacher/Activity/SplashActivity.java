package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.blackdog.dictation_teacher.singleton.LoginSharedPref;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Teacher;
import com.blackdog.dictation_teacher.net.ApiRequester;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity.java";
    static int SPLASH_TIME_OUT = 3000;
    private AsyncTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LoginSharedPref pref = new LoginSharedPref();
        final String login_id = pref.getLoginId(getApplicationContext());
        final String password = pref.getPassword(getApplicationContext());

        new Handler().postDelayed((new Runnable() {
            @Override
            public void run() {
                //자동 로그인 정보가 없으면
                if (login_id.equals("")) {
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(loginIntent);
                    SplashActivity.this.finish();
                } else {
                    loginTask = new AsyncTask<Void, Void, Boolean>() {
                        // TODO: 2017-10-14 가끔 못받아올 때 있음. 다시 체크
                        @Override
                        protected Boolean doInBackground(Void... params) {
                            attemptLogin(login_id, password);
                            return null;
                        }
                    }.execute();
                }
            }
        }), SPLASH_TIME_OUT);
    }

    //스플래시에서도 로그인을 해야 result로 넘어온 MyTeacherInfo를 갖고 있는다.
    private void attemptLogin(String id, String password) {
        ApiRequester.getInstance().loginTeacher(id, password, new ApiRequester.UserCallback<Teacher>() {
            @Override
            public void onSuccess(Teacher result) {
                if (result == null) {
                    //// TODO: 2017-10-16 이거 작동 안함
                    loginTask.cancel(true);
                } else {
                    MyTeacherInfo.getInstance().setTeacher(result);

                    //로그인
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }

            @Override
            public void onFail() {
                loginTask.cancel(true);
                //자동 로그인은 실패처리, 로그인 액티비티 시작
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(loginIntent);
                SplashActivity.this.finish();
            }
        });
    }
}
