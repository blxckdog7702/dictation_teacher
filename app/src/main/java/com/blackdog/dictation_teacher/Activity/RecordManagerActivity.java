package com.blackdog.dictation_teacher.Activity;

import android.os.Bundle;

import com.blackdog.dictation_teacher.Activity.base.BaseTabLayoutActivity;
import com.blackdog.dictation_teacher.R;


public class RecordManagerActivity extends BaseTabLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_manager);

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
