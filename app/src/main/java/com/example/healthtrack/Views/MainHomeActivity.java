package com.example.healthtrack.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Fragment.ChallengeFragment;
import com.example.healthtrack.Views.Fragment.HealthFragment;
import com.example.healthtrack.Views.Fragment.HomeFragment;
import com.example.healthtrack.Views.Fragment.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_CHALLENGE = 2;
    private static final int FRAGMENT_HEALTH = 3;
    private static final int FRAGMENT_PROFILE = 4;
    private int currentFragment = FRAGMENT_HOME;
    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        init();
        settingUpListeners();
    }

    void init() {
        bottomNavigationView = findViewById(R.id.bottom_menu);
        toolbar = findViewById(R.id.class_main_toolbar);

        HomeFragment homeFragment = new HomeFragment();
        replaceFragment(homeFragment);
    }

    void settingUpListeners() {
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    if (currentFragment != FRAGMENT_HOME) {
                        HomeFragment homeFragment = new HomeFragment();
                        replaceFragment(homeFragment);
                        currentFragment = FRAGMENT_HOME;
                    }
                }
                else if (id == R.id.menu_challenge) {
                    if (currentFragment != FRAGMENT_CHALLENGE) {
                        ChallengeFragment challengeFragment = new ChallengeFragment();
                        replaceFragment(challengeFragment);
                        currentFragment = FRAGMENT_CHALLENGE;
                    }
                }
                else if (id == R.id.menu_health) {
                    if (currentFragment != FRAGMENT_HEALTH) {
                        HealthFragment healthFragment = new HealthFragment();
                        replaceFragment(healthFragment);
                        currentFragment = FRAGMENT_HEALTH;
                    }
                }
                else if (id == R.id.menu_profile) {
                    if (currentFragment!= FRAGMENT_PROFILE) {
                        ProfileFragment profileFragment = new ProfileFragment();
                        replaceFragment(profileFragment);
                        currentFragment = FRAGMENT_PROFILE;
                    }
                }

                return true;
        }
    });
}

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment);
        fragmentTransaction.commit();
    }
}
