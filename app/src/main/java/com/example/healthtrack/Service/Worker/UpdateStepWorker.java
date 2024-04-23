package com.example.healthtrack.Service.Worker;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Controller.StepController;

import com.example.healthtrack.Utils.SharedPreferences.*;

import com.example.healthtrack.Utils.CommonUtils;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;

public class UpdateStepWorker extends Worker {

    private Context context;
    private StepController stepController;

    public UpdateStepWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        stepController = new StepController(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        String step = String.valueOf(CommonUtils.getStepNumber());
        int weight = SharedPreferencesUtil.getWeight(context);
        Log.d(TAG, "số bước chân hiện tại: " + step);
        JsonObject newData = new JsonObject();
        newData.addProperty("numberStep", step);
        newData.addProperty("weight", weight);
        JsonObject requestBody = new JsonObject();
        requestBody.add("newData", newData);
        updateStep(requestBody);

        return Result.success();
    }

    private void updateStep(JsonObject requestBody) {
        stepController.updateStep(context, requestBody, new StepController.UpdateCallback() {
            @Override
            public void onSuccess(ResponseBody setGoals) {

            }

            @Override
            public void onError() {

            }
        });

    }

    public static void updateStepWorker(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicWorkRequest =
                new PeriodicWorkRequest.Builder(UpdateStepWorker.class, 1, TimeUnit.HOURS)
                        .setConstraints(constraints)
                        .build();

        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork("UpdateStepsWork", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
    }
}
