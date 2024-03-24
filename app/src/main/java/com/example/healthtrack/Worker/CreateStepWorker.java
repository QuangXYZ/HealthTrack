package com.example.healthtrack.Worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Request.StepRequest;
import com.example.healthtrack.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.CommonUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CreateStepWorker extends Worker {

    private StepController stepController;
    private Context context;

    public CreateStepWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        stepController = new StepController(context);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        // Lấy thời gian hiện tại
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        // Kiểm tra xem hiện tại có phải là 0 giờ.
        if (hourOfDay == 0) {
            CommonUtils.clearStepNumber();
            String idUser = SharedPrefUser.getId(context);
            StepRequest stepRequest = new StepRequest();
            stepRequest.setIdUser(idUser);
            stepRequest.setNumberStep(CommonUtils.getStepNumber());
            stepRequest.setWeight(50);
            insertStep(stepRequest);
            return Result.success();
        }
        // Nếu không phải lúc 0 giờ, công việc này sẽ kết thúc và chờ lần lặp tiếp theo.
        return Result.success();

    }

    private void insertStep(StepRequest stepRequest) {
        stepController.insertStep(context, stepRequest, new StepController.InsertCallback() {
            @Override
            public void onSuccess(StepRequest stepRequest) {

            }

            @Override
            public void onError() {

            }
        });
    }

    public static void createStepWorker(Context context) {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest.Builder dailyWorkBuilder =
                new PeriodicWorkRequest.Builder(CreateStepWorker.class, 24, TimeUnit.HOURS)
                        .setConstraints(constraints);

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "CreateStepWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                dailyWorkBuilder.build());

    }
}
