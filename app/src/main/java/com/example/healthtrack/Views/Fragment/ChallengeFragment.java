package com.example.healthtrack.Views.Fragment;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.healthtrack.R;


public class ChallengeFragment extends Fragment {

    Button createBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        init(view);
        settingUpListeners();
        return view;
    }
    void init(View view){
        createBtn = view.findViewById(R.id.fragment_challenge_create_button);


    }
    @SuppressLint("ClickableViewAccessibility")
    void settingUpListeners(){
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

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation myAnim = AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
                createBtn.setAnimation(myAnim);
            }
        });
    }

}