package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseTabLayoutActivity;
import com.blackdog.dictation_teacher.R;

//학생 개인용 기록 화면()
public class RecordManagerActivity extends BaseTabLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_manager);

        // TODO: 2017-10-15 이거 클릭한 학생 이름에 따라 바뀌도록 설정
        toolbarTitle.setText("누구 누구의 성적");
    }

    @Override
    protected void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new RecordFragment(), "성 적 표");
        adapter.addFrag(new StatsFragment(), "취 약 점");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
