package com.blackdog.dictation_teacher.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseActivity;
import com.blackdog.dictation_teacher.singleton.LoginSharedPref;
import com.blackdog.dictation_teacher.singleton.MyTeacherInfo;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Teacher;
import com.blackdog.dictation_teacher.net.ApiRequester;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private EditText mLoginIdView;
    private EditText mPasswordView;
    private Button mLoginButton;
    private CheckBox mAutoLoginCheckBox;
    private TextView mSignUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbarTitle.setText("로그인");

        mLoginIdView = (EditText) findViewById(R.id.etLoginId);
        mPasswordView = (EditText) findViewById(R.id.etLoginPassword);
        mAutoLoginCheckBox = (CheckBox) findViewById(R.id.cbAutoLogin);

        mSignUpView = (TextView) findViewById(R.id.tvSignUp);
        mSignUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        mLoginButton = (Button) findViewById(R.id.btLogin);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin(mLoginIdView.getText().toString(), mPasswordView.getText().toString());
            }
        });
    }

    private void saveLoginInfoToPref(String login_id, String password) {
        LoginSharedPref pref = new LoginSharedPref();
        pref.saveLoginInfo(getApplicationContext(), login_id, password);
    }

    private void attemptLogin(String id, String password) {
        if(id.isEmpty() || id == null) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.isEmpty() || password == null) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiRequester.getInstance().loginTeacher(id, password, new ApiRequester.UserCallback<Teacher>() {
            @Override
            public void onSuccess(Teacher result) {
                if(result == null) {
                    Toast.makeText(getApplicationContext(), "아이디와 비밀번호를 확인해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    if(mAutoLoginCheckBox.isChecked()) {
                        saveLoginInfoToPref(mLoginIdView.getText().toString(), mPasswordView.getText().toString());
                    }
                    MyTeacherInfo.getInstance().setTeacher(result);

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "서버와의 연결을 확인해주세요.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //회원가입 시, 회원가입한 아이디, 비밀번호 자동으로 로그인 폼에 입력되게
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                String loginId = data.getStringExtra("loginId");
                String password = data.getStringExtra("password");

                if(loginId != null) {
                    mLoginIdView.setText(loginId);
                }
                if(password != null) {
                    mPasswordView.setText(password);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //만약 반환값이 없을 경우의 코드를 여기에 작성하세요.
            }
        }
    }
}
