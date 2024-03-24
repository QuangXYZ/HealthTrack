package com.example.healthtrack.Views.Fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.R;
import com.example.healthtrack.Request.StepRequest;
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.Service.UpdateUiCallBack;
import com.example.healthtrack.Utils.CommonUtils;
import com.example.healthtrack.Views.Activity.HistoryStepActivity;
import com.example.healthtrack.Views.Adapters.ExerciseAdapter;
import com.example.healthtrack.Views.Adapters.PrivateChallengeAdapter;
import com.example.healthtrack.Worker.CreateStepWorker;
import com.example.healthtrack.Worker.UpdateStepWorker;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class HomeFragment extends Fragment {

    private RecyclerView exerciseRecyclerview;
    private ExerciseAdapter adapter;
    private LinearLayout layout;
    private ArrayList<Integer> exercise;
    private boolean mIsBind;
    private TextView walkingStep;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StepController stepController;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            showStepCount(CommonUtils.getStepNumber(),
                    stepService.getStepCount());
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
        walkingStep.setText(String.valueOf(currentCounts));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        innit(view);
        settingUpListeners();
        return view;
    }

    private void innit(View view) {
        layout = view.findViewById(R.id.layout);
        exerciseRecyclerview = view.findViewById(R.id.exercise_recyclerview);
        exercise = new ArrayList<>();
        exercise.add(1);
        exercise.add(2);
        exercise.add(3);
        exercise.add(4);
        adapter = new ExerciseAdapter((Activity) getContext(), exercise);
        exerciseRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        exerciseRecyclerview.setAdapter(adapter);
        exerciseRecyclerview.setNestedScrollingEnabled(true);
        walkingStep = view.findViewById(R.id.text_step);
        showStepCount(CommonUtils.getStepNumber(), 0);
        setupService();
        stepController = new StepController(getContext());
        swipeRefreshLayout = view.findViewById(R.id.course_stored_swipe);

        UpdateStepWorker.updateStepWorker(getContext());
        CreateStepWorker.createStepWorker(getContext());
    }

    void settingUpListeners(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryStepActivity.class);
                getContext().startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setupService() {
        Intent intent = new Intent(getContext(), StepService.class);
        mIsBind = getActivity().bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        getContext().startService(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mIsBind) {
            getActivity().unbindService(mServiceConnection);
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