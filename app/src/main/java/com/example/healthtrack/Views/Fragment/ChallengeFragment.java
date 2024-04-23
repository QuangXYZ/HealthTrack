package com.example.healthtrack.Views.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChallengeFragment extends Fragment {

    TextView tab1, tab2;
    private static final int FRAGMENT_PRIVATE = 1;
    private static final int FRAGMENT_PUBLIC = 2;
    private static int currentFragment;

    CircleImageView img;
    TextView name, lv;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        init(view);
        settingUpListeners();

        return view;
    }

    void init(View view) {
        tab1 = view.findViewById(R.id.fragment_challenge_tab1);
        tab2 = view.findViewById(R.id.fragment_challenge_tab2);
        swipeRefreshLayout = view.findViewById(R.id.fragment_challenge_refresh);
        img = view.findViewById(R.id.fragment_challenge_img);
        name = view.findViewById(R.id.fragment_challenge_name);
        lv = view.findViewById(R.id.fragment_challenge_lv);

        User user = DataLocalManager.getUser();
        name.setText(user.getName());
        lv.setText("Level: " + user.getLevel());
        if (user.getProfilePicture() != null)
            Glide.with(this).load(user.getProfilePicture()).into(img);


        currentFragment = FRAGMENT_PRIVATE;


        PrivateChallengeFragment challengeFragment = new PrivateChallengeFragment();
        replaceFragment(challengeFragment);


    }

    @SuppressLint("ClickableViewAccessibility")
    void settingUpListeners() {
//            createBtn.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    switch (event.getAction()) {
//                        case MotionEvent.ACTION_DOWN: {
//                            v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
//                            v.invalidate();
//                            break;
//                        }
//                        case MotionEvent.ACTION_UP: {
//                            v.getBackground().clearColorFilter();
//                            v.invalidate();
//                            break;
//                        }
//                    }
//                    return false;
//                }
//            });


        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentFragment != FRAGMENT_PRIVATE) {

                    selectTab(FRAGMENT_PRIVATE);


                }

            }
        });
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment != FRAGMENT_PUBLIC) {

                    selectTab(FRAGMENT_PUBLIC);

                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (currentFragment == FRAGMENT_PRIVATE) {
                    PrivateChallengeFragment challengeFragment = new PrivateChallengeFragment();
                    replaceFragment(challengeFragment);
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (currentFragment == FRAGMENT_PUBLIC) {
                    PublicChallengeFragment challengeFragment = new PublicChallengeFragment();
                    replaceFragment(challengeFragment);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void selectTab(int tabNumber) {
        TextView selectedTab, noneSelectedTab;
        if (tabNumber == FRAGMENT_PRIVATE) {
            selectedTab = tab1;
            noneSelectedTab = tab2;

            PrivateChallengeFragment challengeFragment = new PrivateChallengeFragment();
            replaceFragment(challengeFragment);


        } else {
            selectedTab = tab2;
            noneSelectedTab = tab1;

            PublicChallengeFragment challengeFragment = new PublicChallengeFragment();
            replaceFragment(challengeFragment);


        }

        float slideTo = (tabNumber - currentFragment) * selectedTab.getWidth();
        TranslateAnimation translateAnimation = new TranslateAnimation(-slideTo, 0, 0, 0);

        translateAnimation.setDuration(400);

        if (currentFragment == FRAGMENT_PRIVATE) {
            tab2.startAnimation(translateAnimation);
        }
        if (currentFragment == FRAGMENT_PUBLIC) {
            tab1.startAnimation(translateAnimation);
        }

        translateAnimation.setAnimationListener(
                new Animation.AnimationListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onAnimationStart(Animation animation) {
                        selectedTab.setBackgroundResource(R.drawable.tab_background_select);

                        selectedTab.setTextColor(Color.WHITE);

                        noneSelectedTab.setBackgroundResource(getResources().getColor(android.R.color.transparent));

                        noneSelectedTab.setTextColor(getResources().getColor(R.color.blue));

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }
        );

        currentFragment = tabNumber;

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction().setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.fragment_challenge_frame, fragment);
        fragmentTransaction.commit();
    }

}