package com.example.healthtrack.Views.Fragment;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
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

import com.example.healthtrack.Controller.SetGoalsController;
import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.R;
import com.example.healthtrack.Network.Respone.SetGoalsResponse;
import com.example.healthtrack.Network.Respone.StepResponse;
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.Service.UpdateUiCallBack;
import com.example.healthtrack.Service.Worker.UpdateStepWorker;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.CommonUtils;
import com.example.healthtrack.Views.Activity.HistoryStepActivity;
import com.example.healthtrack.Views.Adapters.ExerciseAdapter;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class HomeFragment extends Fragment {

    private RecyclerView exerciseRecyclerview;
    private ExerciseAdapter adapter;
    private LinearLayout layout;
    private ArrayList<Integer> exercise;
    private boolean mIsBind;
    private TextView walkingStep, tvStep, tvStepGoals, tvTime, tvTimeGoals, tvCalo, tvCaloGoals, tvKm, tvKmGoals;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StepController stepController;
    private SetGoalsController setGoalsController;
    private ArrayList<Step> mListStep;
    private ArrayList<SetGoals> mListSetGoals;

    private CircularProgressIndicator progressStep;
    private LinearProgressIndicator progressCalo, progressTime, progressKm;
    private int step1, calo, stepGoals, caloGoals, kmGoals, km;
    private int time, timeGoals;
    private StepService mStepService;

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
    }

    // Phương thức để thiết lập đối tượng StepService từ MainActivity hoặc từ bất kỳ nơi nào cần truy cập

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
        walkingStep = view.findViewById(R.id.step_home);
        showStepCount(CommonUtils.getStepNumber(), 0);
        setupService();
        stepController = new StepController(getContext());
        setGoalsController = new SetGoalsController(getContext());
        swipeRefreshLayout = view.findViewById(R.id.course_stored_swipe);
        progressStep = (CircularProgressIndicator) view.findViewById(R.id.circularProgressIndicator_home_step);
        progressCalo = (LinearProgressIndicator) view.findViewById(R.id.linearProgressIndicator_calo_home);
        progressTime = (LinearProgressIndicator) view.findViewById(R.id.linearProgressIndicator_time_home);
        progressKm = (LinearProgressIndicator) view.findViewById(R.id.linearProgressIndicator_km_home);
//        tvStep = view.findViewById(R.id.step_home);
        tvStepGoals = view.findViewById(R.id.step_goals_home);
        tvTime = view.findViewById(R.id.time_home);
        tvTimeGoals = view.findViewById(R.id.time_goals_home);
        tvCalo = view.findViewById(R.id.calo_home);
        tvCaloGoals = view.findViewById(R.id.calo_golas_home);
        tvKm = view.findViewById(R.id.km_home);
        tvKmGoals = view.findViewById(R.id.km_goals_home);
        mListStep = new ArrayList<>();
        mListSetGoals = new ArrayList<>();



        UpdateStepWorker.updateStepWorker(getContext());
//        CreateStepWorker.createStepWorker(getContext());
    }

    void settingUpListeners(){
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryStepActivity.class);
                getContext().startActivity(intent);
            }
        });

//        walkingStep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CommonUtils.clearStepNumber();
//                mStepService.resetStepCount();
//                Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "NumberStep: " + CommonUtils.getStepNumber());
//            }
//        });

        class MyAsyncTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                // Thực hiện tác vụ cần đợi
//                String step = String.valueOf(CommonUtils.getStepNumber());
                JsonObject newData = new JsonObject();
                newData.addProperty("numberStep", CommonUtils.getStepNumber());
                newData.addProperty("weight", 50);
                JsonObject requestBody = new JsonObject();
                requestBody.add("newData", newData);
                updateStep(requestBody);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Thực hiện các hành động sau khi tác vụ hoàn thành
                getStepCurrent();
                getSetGoals();
            }
        }

        // Khởi tạo và thực thi AsyncTask
        MyAsyncTask task = new MyAsyncTask();
        task.execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                class MyAsyncTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Thực hiện tác vụ cần đợi
                        String step = String.valueOf(CommonUtils.getStepNumber());
                        JsonObject newData = new JsonObject();
                        newData.addProperty("numberStep", step);
                        newData.addProperty("weight", 50);
                        JsonObject requestBody = new JsonObject();
                        requestBody.add("newData", newData);
                        updateStep(requestBody);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Thực hiện các hành động sau khi tác vụ hoàn thành
                        getStepCurrent();
                        getSetGoals();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }

                // Khởi tạo và thực thi AsyncTask
                MyAsyncTask task = new MyAsyncTask();
                task.execute();
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

    private void getStepCurrent() {
        stepController.getStepCurrent(getContext(), new StepController.GetCurrentCallback() {
            @Override
            public void onSuccess(StepResponse<Step> step) {
                mListStep = (ArrayList<Step>) step.getData();
                for (int i = 0; i < mListStep.size(); i++) {
//                    tvStep.setText(String.valueOf(mListStep.get(0).getNumberStep()));
                    tvCalo.setText(String.valueOf(mListStep.get(0).getCalo()));
                    tvTime.setText(String.valueOf(mListStep.get(0).getTime()));
                    tvKm.setText(String.valueOf(mListStep.get(0).getDistance()));
                    step1 = mListStep.get(0).getNumberStep();
                    calo = mListStep.get(0).getCalo();
                    time = Integer.valueOf(mListStep.get(0).getTime());
                    km = Integer.valueOf((int) mListStep.get(0).getDistance());
                }
                calculateProgressStep(progressStep);
                calculateProgressCalo(progressCalo);
                calculateProgressTime(progressTime);
                calculateProgressDistance(progressKm);
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSetGoals() {
        String idUser = SharedPrefUser.getId(getContext());
        setGoalsController.getSetGoals(getContext(), idUser, new SetGoalsController.Callback() {
            @Override
            public void onSetGoals(SetGoalsResponse<SetGoals> setGoals) {
                mListSetGoals = (ArrayList<SetGoals>) setGoals.getData();
                for (int i = 0; i < mListSetGoals.size(); i++) {
                    tvStepGoals.setText(String.valueOf("Mục tiêu: " + mListSetGoals.get(0).getNumberStepGoals()));
                    tvKmGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getDistanceGoals()));
                    tvCaloGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getCaloGoals()));
                    tvTimeGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getTimeGoals()));
                    stepGoals = mListSetGoals.get(0).getNumberStepGoals();
                    kmGoals = mListSetGoals.get(0).getDistanceGoals();
                    caloGoals = mListSetGoals.get(0).getCaloGoals();
                    timeGoals = Integer.valueOf(mListSetGoals.get(0).getCaloGoals());
                }
                calculateProgressStep(progressStep);
                calculateProgressDistance(progressKm);
                calculateProgressCalo(progressCalo);
                calculateProgressTime(progressTime);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    private void updateStep(JsonObject requestBody) {
        stepController.updateStep(getContext(), requestBody, new StepController.UpdateCallback() {
            @Override
            public void onSuccess(ResponseBody setGoals) {

            }

            @Override
            public void onError() {

            }
        });
    }

    private void calculateProgressStep(CircularProgressIndicator progressStep) {
        if (step1 != 0 && stepGoals != 0) {
            progressStep.setProgress(Math.round(step1 * 100 / stepGoals));
        }
    }

    private void calculateProgressCalo(LinearProgressIndicator progressCalo) {
        if (calo != 0 && caloGoals != 0) {
            progressCalo.setProgress(Math.round(calo * 100 / caloGoals));
        }
    }

    private void calculateProgressTime(LinearProgressIndicator progressTime) {
        if (time != 0 && timeGoals != 0) {
            progressTime.setProgress(Integer.valueOf(Math.round(time * 100 / timeGoals)));
        }
    }

    private void calculateProgressDistance(LinearProgressIndicator progressDistance) {
        if (km != 0 && kmGoals != 0) {
            progressDistance.setProgress(Integer.valueOf((int) Math.round(km * 100 / kmGoals)));
        }
    }
}