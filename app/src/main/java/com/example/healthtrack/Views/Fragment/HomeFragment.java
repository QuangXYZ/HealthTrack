package com.example.healthtrack.Views.Fragment;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Controller.ExerciseController;
import com.example.healthtrack.Controller.SetGoalsController;
import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Models.Exercise;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.Network.Request.StepRequest;
import com.example.healthtrack.R;

import com.example.healthtrack.Network.Respone.SetGoalsResponse;
import com.example.healthtrack.Network.Respone.StepResponse;
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.Service.UpdateUiCallBack;
import com.example.healthtrack.Service.Worker.UpdateStepWorker;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.CommonUtils;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;
import com.example.healthtrack.Views.Activity.HistoryStepActivity;
import com.example.healthtrack.Views.Activity.SetGoalsActivity;
import com.example.healthtrack.Views.Adapters.ExerciseAdapter;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.gson.JsonObject;
import com.shawnlin.numberpicker.NumberPicker;

import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;

public class HomeFragment extends Fragment {

    private RecyclerView exerciseRecyclerview;
    private ExerciseAdapter adapter;
    private LinearLayout layout;
    private ArrayList<Exercise> exerciseList;
    private boolean mIsBind;
    private TextView walkingStep, tvStep, tvStepGoals, tvTime, tvTimeGoals, tvCalo, tvCaloGoals, tvKm, tvKmGoals;
    private SwipeRefreshLayout swipeRefreshLayout;
    private StepController stepController;
    private SetGoalsController setGoalsController;
    private ArrayList<Step> mListStep;
    private ArrayList<SetGoals> mListSetGoals;
    private ExerciseController exerciseController;
    private CircularProgressIndicator progressStep;
    private LinearProgressIndicator progressCalo, progressTime, progressKm;
    private int step1, calo, stepGoals, caloGoals, kmGoals, km;
    private int time, timeGoals;
    private StepService mStepService;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("WeightPrefs", MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        innit(view);

        if (!sharedPreferences.getBoolean("ShowDialog", false)) {
            dialogInputWeight();

            // Cập nhật cờ
            sharedPreferences.edit().putBoolean("ShowDialog", true).apply();
        }

        settingUpListeners();
        return view;
    }

    private void innit(View view) {
        layout = view.findViewById(R.id.layout);
        exerciseRecyclerview = view.findViewById(R.id.exercise_recyclerview);
        exerciseList = new ArrayList<>();

        adapter = new ExerciseAdapter((Activity) getContext(), exerciseList);
        exerciseRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        exerciseRecyclerview.setAdapter(adapter);
        exerciseRecyclerview.setNestedScrollingEnabled(true);
        walkingStep = view.findViewById(R.id.step_home);
        showStepCount(CommonUtils.getStepNumber(), 0);
        setupService();
        stepController = new StepController(getContext());
        setGoalsController = new SetGoalsController(getContext());
        exerciseController = new ExerciseController(getContext());
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
        progressBar = view.findViewById(R.id.exercise_progress);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        mListStep = new ArrayList<>();
        mListSetGoals = new ArrayList<>();


        UpdateStepWorker.updateStepWorker(getContext());

//        CreateStepWorker.createStepWorker(getContext());

        progressBar.setVisibility(View.VISIBLE);
        exerciseController.getExercise(getContext(), new ExerciseController.GetExercise() {
            @Override
            public void onSuccess(ArrayList<Exercise> exercise) {
                exerciseList.addAll(exercise);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    void settingUpListeners() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryStepActivity.class);
                getContext().startActivity(intent);
            }
        });

        class MyAsyncTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... params) {
                // Thực hiện tác vụ cần đợi
                int weight = SharedPreferencesUtil.getWeight(getContext());
                Log.d(TAG, "Cân nặng SharePre: " + weight);
                JsonObject newData = new JsonObject();
                newData.addProperty("numberStep", CommonUtils.getStepNumber());
                newData.addProperty("weight", weight);
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
                        int weight = SharedPreferencesUtil.getWeight(getContext());
                        String step = String.valueOf(CommonUtils.getStepNumber());
                        JsonObject newData = new JsonObject();
                        newData.addProperty("numberStep", step);
                        newData.addProperty("weight", weight);
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
        ChallengeController challengeController = new ChallengeController();
        challengeController.updateChallengeStep(new ChallengeController.ChallengeControllerCallback() {
            @Override
            public void onSuccess(String message) {

            }

            @Override
            public void onError(String error) {

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

    private void dialogInputWeight() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_weight);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.number_picker_weight);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.d(TAG, "Cân nặng: " + newVal);
                SharedPreferencesUtil.saveWeight(getContext(), newVal);

            }
        });

        Button btnNext = dialog.findViewById(R.id.btn_next_dialog);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                //insert Step
                int weight = SharedPreferencesUtil.getWeight(getContext());
                String idUser = SharedPrefUser.getId(getContext());
                LocalDate today = LocalDate.now();
                CommonUtils.clearStepNumber();
                StepRequest stepRequest = new StepRequest();
                stepRequest.setIdUser(idUser);
                stepRequest.setNumberStep(CommonUtils.getStepNumber());
                stepRequest.setWeight(weight);
                stepRequest.setDate(String.valueOf(today));
                insertStep(stepRequest);

                //insert set goals
                SetGoals setGoals = new SetGoals();
                setGoals.setIdUser(idUser);
                setGoals.setDistanceGoals(10);
                setGoals.setCaloGoals(100);
                setGoals.setTimeGoals("50");
                setGoals.setNumberStepGoals(1000);
                setGoalsController.insertSetGoals(setGoals);
            }
        });
        dialog.show();
    }

    private void insertStep(StepRequest stepRequest) {
        stepController.insertStep(getContext(), stepRequest, new StepController.InsertCallback() {

            @Override
            public void onSuccess(StepRequest stepRequest) {

            }

            @Override
            public void onError() {

            }
        });
    }
}