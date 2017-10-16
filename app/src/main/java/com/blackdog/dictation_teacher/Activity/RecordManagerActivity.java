package com.blackdog.dictation_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseTabLayoutActivity;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.models.Student;

//학생 개인용 기록 화면()
public class RecordManagerActivity extends BaseTabLayoutActivity {
    private Student student;

    public Student getStudent() {
        return student;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_manager);

        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("student");
        if(student == null) {
            return;
        }
        // TODO: 2017-10-15 이거 클릭한 학생 이름에 따라 바뀌도록 설정
        toolbarTitle.setText(student.getName() + "의 성적");
    }

    @Override
    protected void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RecordFragment(), "성 적 표");
        adapter.addFrag(new StatsFragment(), "개 인 통 계");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
