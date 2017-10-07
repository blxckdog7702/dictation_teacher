package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.blackdog.dictation_teacher.LoginSharedPref;
import com.blackdog.dictation_teacher.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Teacher;
import com.blackdog.dictation_teacher.net.ApiRequester;

public class SplashActivity extends AppCompatActivity {
    static int SPLASH_TIME_OUT = 3000;
    private AsyncTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LoginSharedPref pref = new LoginSharedPref();
        final String login_id = pref.getLoginId(getApplicationContext());
        final String password = pref.getPassword(getApplicationContext());

        if (login_id.equals("")) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(loginIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_TIME_OUT);
        } else {
            loginTask = new AsyncTask<Void, Void, Boolean>() {

                @Override
                protected Boolean doInBackground(Void... params) {
                    attemptLogin(login_id, password);
                    return null;
                }

                @Override
                protected void onCancelled() {
                    super.onCancelled();
                    Toast.makeText(getApplicationContext(), "서버와의 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();

                    //자동 로그인은 실패처리, 로그인 액티비티 시작
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                            SplashActivity.this.startActivity(loginIntent);
                            SplashActivity.this.finish();
                        }
                    }, SPLASH_TIME_OUT);
                }

                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    super.onPostExecute(aBoolean);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                            SplashActivity.this.startActivity(mainIntent);
                            SplashActivity.this.finish();
                        }
                    }, SPLASH_TIME_OUT);
                }
            }.execute();
        }
    }

    //스플래시에서도 로그인을 해야 result로 넘어온 MyTeacherInfo를 갖고 있는다.
    private void attemptLogin(String id, String password) {
        ApiRequester.getInstance().loginTeacher(id, password, new ApiRequester.UserCallback<Teacher>() {
            @Override
            public void onSuccess(Teacher result) {
                if (result == null) {
                    loginTask.cancel(true);
                } else {
                    MyTeacherInfo.getInstance().setTeacher(result);
                }
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "서버와의 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
