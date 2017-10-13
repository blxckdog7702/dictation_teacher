package com.blackdog.dictation_teacher.Activity.base;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.blackdog.dictation_teacher.Activity.LoginActivity;
import com.blackdog.dictation_teacher.Activity.MainActivity;
import com.blackdog.dictation_teacher.Activity.MatchingListActivity;
import com.blackdog.dictation_teacher.Activity.StudentListActivity;
import com.blackdog.dictation_teacher.Activity.StudentManageActivity;
import com.blackdog.dictation_teacher.Adapter.StudentManageAdapter;
import com.blackdog.dictation_teacher.LoginSharedPref;
import com.blackdog.dictation_teacher.R;
import com.blackdog.dictation_teacher.Util;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseDrawerActivity extends BaseActivity {

    @BindView(R.id.drawerlayout) FlowingDrawer drawerlayout;
    @Nullable @BindView(R.id.vNavigation) NavigationView vNavigation;

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;

    //Cannot be bound via Butterknife, hosting view is initialized later (see setupHeader() method)
    private ImageView ivMenuUserProfilePhoto;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_base_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.content);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
    }

    protected void bindViews() {
        super.bindViews();
        ButterKnife.bind(this);
        setupToolbar();
        setupMenu();
        setupHeader();
    }

    private void setupMenu() {
        drawerlayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                String menuTitle = menuItem.getTitle().toString();
                switch (menuTitle) {
                    case "시험 준비" : {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    }
                    case "학생 등록 요청" : {
                        Intent intent = new Intent(getApplicationContext(), MatchingListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    }
                    case "학생 목록" : {
                        Intent intent = new Intent(getApplicationContext(), StudentListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    }
                    case "학생 관리" : {
                        Intent intent = new Intent(getApplicationContext(), StudentManageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    }
                    case "로그아웃" : {
                        logoutClick();
                        break;
                    }
                }

                Toast.makeText(getApplicationContext(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                drawerlayout.closeMenu();
                return false;
            }
        }) ;
    }

    private void setupHeader() {
        View headerView = vNavigation.getHeaderView(0);
        ivMenuUserProfilePhoto = (ImageView) headerView.findViewById(R.id.ivMenuUserProfilePhoto);
        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });
//        Picasso.with(this)
//                .load(R.drawable.profile)
//                .placeholder(R.drawable.img_circle_placeholder)
//                .resize(avatarSize, avatarSize)
//                .centerCrop()
//                .transform(new CircleTransformation())
//                .into(ivMenuUserProfilePhoto);
    }

    public void onGlobalMenuHeaderClick(final View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                int[] startingLocation = new int[2];
//                v.getLocationOnScreen(startingLocation);
//                startingLocation[0] += v.getWidth() / 2;
//                UserProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
//                overridePendingTransition(0, 0);
            }
        }, 200);
    }

    @Override
    public void setupToolbar(){
        super.setupToolbar();
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlayout.toggleMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerlayout.isMenuVisible()) {
            drawerlayout.closeMenu();
        } else {
            Util.getInstance().onBackPressed(this);
        }
    }

    //로그아웃 시, SharedPref에 있는 로그인 정보 날림.
    public void logoutClick() {
        LoginSharedPref pref = new LoginSharedPref();
        pref.deleteLoginInfo(getApplicationContext());

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
