package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.icu.util.Calendar;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Fragment.ChallengeFragment;
import com.example.healthtrack.Views.Fragment.HealthFragment;
import com.example.healthtrack.Views.Fragment.HomeFragment;
import com.example.healthtrack.Views.Fragment.ProfileFragment;
import com.example.healthtrack.Service.Worker.AlarmReceiver;
import com.google.android.material.appbar.MaterialToolbar;

public class MainHomeActivity extends AppCompatActivity {
    LinearLayout homeLayout, challengeLayout, healthLayout, profileLayout;
    ImageView homeImg, challengeImg, profileImg, healthImg;
    TextView homeTxt, challengeTxt, healthTxt, profileTxt;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_CHALLENGE = 2;
    private static final int FRAGMENT_HEALTH = 3;
    private static final int FRAGMENT_PROFILE = 4;
    private int currentFragment = FRAGMENT_HOME;
    MaterialToolbar toolbar;
    TextView title;
    ImageView editprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        init();
        settingUpListeners();
        scheduleAlarm(this);
    }

    void init() {
        homeLayout = findViewById(R.id.main_home_layout);
        challengeLayout = findViewById(R.id.main_challenge_layout);
        healthLayout = findViewById(R.id.main_health_layout);
        profileLayout = findViewById(R.id.main_profile_layout);

        homeImg = findViewById(R.id.main_home_img);
        challengeImg = findViewById(R.id.main_challenge_img);
        healthImg = findViewById(R.id.main_health_img);
        profileImg = findViewById(R.id.main_profile_img);

        homeTxt = findViewById(R.id.main_home_text);
        challengeTxt = findViewById(R.id.main_challenge_text);
        healthTxt = findViewById(R.id.main_health_text);
        profileTxt = findViewById(R.id.main_profile_text);
        editprofile = findViewById(R.id.edit_profile);

//        toolbar = findViewById(R.id.class_main_toolbar);

        title = findViewById(R.id.main_title);


        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment, 1);
    }

    private void scheduleAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class); // AlarmReceiver là BroadcastReceiver của bạn
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // Nếu thời gian hiện tại đã qua 22 giờ, thiết lập báo thức cho ngày tiếp theo
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Đặt báo thức lặp lại hàng ngày
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    void settingUpListeners() {


        homeLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (currentFragment != FRAGMENT_HOME) {
                    challengeTxt.setVisibility(View.GONE);
                    healthTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);
                    editprofile.setVisibility(View.GONE);


                    challengeLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                    healthLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));


                    profileImg.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                    challengeImg.setImageDrawable(getResources().getDrawable(R.drawable.challenge));
                    healthImg.setImageDrawable(getResources().getDrawable(R.drawable.health));

                    homeTxt.setVisibility(View.VISIBLE);
                    homeImg.setImageDrawable(getResources().getDrawable(R.drawable.home_select));
                    homeLayout.setBackgroundResource(R.drawable.round_background);
                    title.setText("HealthTrack");

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(500);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.setAnimation(scaleAnimation);

                    HomeFragment homeFragment = new HomeFragment();
                    replaceFragment(homeFragment, 1);
                    currentFragment = FRAGMENT_HOME;
                }
            }
        });

        challengeLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (currentFragment != FRAGMENT_CHALLENGE) {
                    homeTxt.setVisibility(View.GONE);
                    healthTxt.setVisibility(View.GONE);
                    profileTxt.setVisibility(View.GONE);
                    editprofile.setVisibility(View.GONE);


                    homeLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                    healthLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));

                    profileImg.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                    homeImg.setImageDrawable(getResources().getDrawable(R.drawable.home));
                    healthImg.setImageDrawable(getResources().getDrawable(R.drawable.health));

                    challengeTxt.setVisibility(View.VISIBLE);
                    challengeLayout.setBackgroundResource(R.drawable.round_background);
                    challengeImg.setImageDrawable(getResources().getDrawable(R.drawable.challenge_select));
                    title.setText("Thử thách");

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(500);
                    scaleAnimation.setFillAfter(true);
                    challengeLayout.setAnimation(scaleAnimation);
                    ChallengeFragment challengeFragment = new ChallengeFragment();
                    replaceFragment(challengeFragment, 2);
                    currentFragment = FRAGMENT_CHALLENGE;
                }
            }
        });

        healthLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                challengeTxt.setVisibility(View.GONE);
                homeTxt.setVisibility(View.GONE);
                profileTxt.setVisibility(View.GONE);
                editprofile.setVisibility(View.GONE);


                challengeLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                homeLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                profileLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));


                profileImg.setImageDrawable(getResources().getDrawable(R.drawable.profile));
                homeImg.setImageDrawable(getResources().getDrawable(R.drawable.home));
                challengeImg.setImageDrawable(getResources().getDrawable(R.drawable.challenge));

                healthTxt.setVisibility(View.VISIBLE);
                healthLayout.setBackgroundResource(R.drawable.round_background);
                healthImg.setImageDrawable(getResources().getDrawable(R.drawable.health_select));
                title.setText("Sức khỏe");

                ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                healthLayout.setAnimation(scaleAnimation);
                if (currentFragment != FRAGMENT_HEALTH) {
                    HealthFragment healthFragment = new HealthFragment();
                    replaceFragment(healthFragment, 3);
                    currentFragment = FRAGMENT_HEALTH;
                }
            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                challengeTxt.setVisibility(View.GONE);
                healthTxt.setVisibility(View.GONE);
                homeTxt.setVisibility(View.GONE);
                editprofile.setVisibility(View.VISIBLE);

                challengeLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                healthLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));
                homeLayout.setBackgroundResource(getResources().getColor(android.R.color.transparent));


                healthImg.setImageDrawable(getResources().getDrawable(R.drawable.health));
                homeImg.setImageDrawable(getResources().getDrawable(R.drawable.home));
                challengeImg.setImageDrawable(getResources().getDrawable(R.drawable.challenge));

                profileTxt.setVisibility(View.VISIBLE);
                profileLayout.setBackgroundResource(R.drawable.round_background);
                profileImg.setImageDrawable(getResources().getDrawable(R.drawable.profile_select));
                title.setText("Trang của bạn");


                ScaleAnimation scaleAnimation = new ScaleAnimation(0.6f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                scaleAnimation.setDuration(500);
                scaleAnimation.setFillAfter(true);
                profileLayout.setAnimation(scaleAnimation);
                if (currentFragment != FRAGMENT_PROFILE) {
                    ProfileFragment profileFragment = new ProfileFragment();
                    replaceFragment(profileFragment, 4);
                    currentFragment = FRAGMENT_PROFILE;
                }
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainHomeActivity.this, EditProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });

    }

    private void replaceFragment(Fragment fragment, int index) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (index > currentFragment)
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
        else
            fragmentTransaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_righ);
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }

}
