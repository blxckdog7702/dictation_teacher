package com.blackdog.dictation_teacher.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.base.BaseActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Teacher;
import com.blackdog.dictation_teacher.net.ApiRequester;

import java.util.regex.Pattern;

/**
 * A login screen that offers login via teacherId/password.
 */
public class SignUpActivity extends BaseActivity {

    /**
     * id to identity read_contacts permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo:hello", "bar:world"
    };
    private static final String TAG = "SignUpActivity";
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserSignUpTask mAuthTask = null;

    private boolean duplicateCheck;

    // UI references.
    private EditText mTeacherIdView;
    private EditText mPasswordView;
    private EditText mSchoolView;
    private EditText mGradeView;
    private EditText mClassNumberView;
    private EditText mNameView;
    private View mProgressView;
    private View mSignUpFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbarTitle.setText("회원가입");

        // Set up the 회원가입 form.
        mTeacherIdView = (EditText) findViewById(R.id.etLoginId);
        mTeacherIdView.setFilters(new InputFilter[]{filter});
        mPasswordView = (EditText) findViewById(R.id.etSignUpPassword);
        mSchoolView = (EditText) findViewById(R.id.school);
        mGradeView = (EditText) findViewById(R.id.grade);
        mClassNumberView = (EditText) findViewById(R.id.classNumber);
        mNameView = (EditText) findViewById(R.id.name);
        mSignUpFormView = findViewById(R.id.sign_up_form);
        mProgressView = findViewById(R.id.sign_up_progress);

        //회원가입 버튼 리스너
        Button mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

        //중복검사 변수
        duplicateCheck = false;

        //중복검사 버튼 리스너
        Button mDuplicationCheckButton = (Button) findViewById(R.id.duplication_check_button);
        mDuplicationCheckButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                idDuplicationCheck();
            }
        });

        //중복검사 이후에도 입력한 id가 바뀌면 다시 중복검사 실시하도록 처리
        mTeacherIdView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                duplicateCheck = false;
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid teacherId, missing fields, etc.), the
     * errors are presented and no actual login attempt is `de.
     */
    //회원 가입 시도
    private void attemptSignUp() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mTeacherIdView.setError(null);
        mPasswordView.setError(null);
        mSchoolView.setError(null);
        mGradeView.setError(null);
        mClassNumberView.setError(null);
        mNameView.setError(null);

        // Store values at the time of the SignUp attempt.
        String teacherId = mTeacherIdView.getText().toString();
        String password = mPasswordView.getText().toString();
        String school = mSchoolView.getText().toString();
        String grade = mGradeView.getText().toString();
        String classNumber = mClassNumberView.getText().toString();
        String name = mNameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 이름 유효성 검증
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

        // 반 유효성 검증
        if (TextUtils.isEmpty(classNumber)) {
            mClassNumberView.setError(getString(R.string.error_field_required));
            focusView = mClassNumberView;
            cancel = true;
        }

        // 학년 유효성 검증
        if (TextUtils.isEmpty(grade)) {
            mGradeView.setError(getString(R.string.error_field_required));
            focusView = mGradeView;
            cancel = true;
        }

        // 학교 유효성 검증
        if (TextUtils.isEmpty(school)) {
            mSchoolView.setError(getString(R.string.error_field_required));
            focusView = mSchoolView;
            cancel = true;
        }

        // 비밀번호 유효성 검증
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // 아이디 유효성 검증
        if (TextUtils.isEmpty(teacherId)) {
            mTeacherIdView.setError(getString(R.string.error_field_required));
            focusView = mTeacherIdView;
            cancel = true;
        } else if (!isTeacherIdValid(teacherId)) {
            mTeacherIdView.setError(getString(R.string.error_invalid_teacher_id));
            focusView = mTeacherIdView;
            cancel = true;
        } else if (!duplicateCheck) {
            mTeacherIdView.setError(getString(R.string.error_duplicate_teacher_id));
            focusView = mTeacherIdView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserSignUpTask(teacherId, password, school, grade, classNumber, name);
            mAuthTask.execute((Void) null);
        }
    }

    //아이디 유효성 검증 메서드 (추가 필요?)
    private boolean isTeacherIdValid(String id) {
        //TODO: Replace this with your own logic
        return id.length() >= 4;
    }

    //비밀번호 유효성 검증 메서드
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 4;
    }

    private void idDuplicationCheck() {
        String teacherId = mTeacherIdView.getText().toString();
        Log.d(TAG, "idDuplicationCheck: 시작");
        //이미 가입된 아이디가 있다면 서버로부터 true가 리턴
        ApiRequester.getInstance().checkDuplicateTeacher(teacherId, new ApiRequester.UserCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                duplicateCheck = !result;
                Log.d(TAG, "duplicationCheck: " + duplicateCheck);

                if (duplicateCheck) {
                    Toast.makeText(getApplicationContext(), "중복 검사가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    mTeacherIdView.setError(null);
                } else {
                    Toast.makeText(getApplicationContext(), "중복된 아이디가 존재합니다. 다른 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFail() {
                Toast.makeText(getApplicationContext(), "서버와의 연결을 확인해주세요.", Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * Shows the progress UI and hides the sign up form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserSignUpTask extends AsyncTask<Void, Void, Boolean> {
        private Teacher teacher;

        UserSignUpTask(String teacherId, String password, String school, String grade, String classNumber, String name) {
            teacher = new Teacher();

            teacher.setLoginId(teacherId);
            teacher.setPassword(password);
            teacher.setSchool(school);
            teacher.setGrade(grade);
            teacher.setClass_(classNumber);
            teacher.setName(name);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            //회원가입 요청
            ApiRequester.getInstance().signUpTeacher(teacher, new ApiRequester.UserCallback<Teacher>() {
                @Override
                public void onSuccess(Teacher result) {
                    Log.d(TAG, "onSuccess: 1111");
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                    teacher = result;
                }

                @Override
                public void onFail() {
                    Toast.makeText(getApplicationContext(), "서버와의 연결을 확인해주세요.", Toast.LENGTH_SHORT);
                }
            });
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            //성공하면 로그인 액티비티로 넘어감
            if (success) {
                Intent intent = new Intent();
                intent.putExtra("loginId", teacher.getLoginId());
                intent.putExtra("password", teacher.getPassword());
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                //실패했을 때?
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    //EditText 영어와 숫자만 입력가능하게 하는 필터
    protected InputFilter filter = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };
}

