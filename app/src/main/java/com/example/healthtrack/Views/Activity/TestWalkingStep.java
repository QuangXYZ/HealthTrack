package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;

import com.example.healthtrack.R;
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.Service.UpdateUiCallBack;
import com.example.healthtrack.Utils.CommonUtils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class TestWalkingStep extends AppCompatActivity {

    private boolean mIsBind;
    private TextView walkingStep;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            showStepCount(CommonUtils.getStepNumber(), stepService.getStepCount());
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    showStepCount(CommonUtils.getStepNumber(), stepCount);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    public void showStepCount(int totalStepNum, int currentCounts) {
        if (currentCounts < totalStepNum) {
            currentCounts = totalStepNum;
        }
        animateTextView(Integer.valueOf(walkingStep.getText().toString()), currentCounts, walkingStep);
//        walkingStep.setText(String.valueOf(currentCounts));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_walking_step);
        initData();
    }

    private void initData() {
        walkingStep = findViewById(R.id.text_step);

        showStepCount(CommonUtils.getStepNumber(), 0);
        setupService();
    }

    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        mIsBind = bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBind) {
            unbindService(mServiceConnection);
        }
    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(initialValue, finalValue);
        valueAnimator.setDuration(1500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                textview.setText(valueAnimator.getAnimatedValue().toString());
            }
        });
        valueAnimator.start();

    }
}