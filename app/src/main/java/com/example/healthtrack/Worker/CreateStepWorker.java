package com.example.healthtrack.Worker;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
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
        executeIfNotExecutedYet();
        return Result.success();

    }

    private void executeIfNotExecutedYet() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isExecuted = sharedPreferences.getBoolean("isExecuted", false);

        if (!isExecuted) {
            // Lấy thời gian hiện tại
            long currentTimeMillis = System.currentTimeMillis();
            // Đặt thời gian thành 10 giờ sáng của ngày hôm nay
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long tenAMTimeMillis = calendar.getTimeInMillis();

            // Nếu hiện tại đã qua 10 giờ, tính thời gian đến 10 giờ sáng của ngày tiếp theo
            if (currentTimeMillis >= tenAMTimeMillis) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                tenAMTimeMillis = calendar.getTimeInMillis();
            }

            // Tính khoảng cách thời gian còn lại đến 10 giờ sáng
            long delay = tenAMTimeMillis - currentTimeMillis;

            // Lên lịch cho công việc thực thi vào thời điểm tính được
            scheduleNextExecution(delay);

            // Đánh dấu rằng công việc đã được thực hiện
            sharedPreferences.edit().putBoolean("isExecuted", true).apply();

            // Thực hiện các hoạt động cụ thể (ví dụ: CommonUtils.clearStepNumber(), insertStep(stepRequest),...)
            if (currentTimeMillis >= tenAMTimeMillis && currentTimeMillis <= tenAMTimeMillis + TimeUnit.HOURS.toMillis(1) ) {
                // Trong khoảng thời gian từ 10 giờ đến 11 giờ sáng, thực hiện các hoạt động cụ thể ở đây
                CommonUtils.clearStepNumber();
                String idUser = SharedPrefUser.getId(context);
                StepRequest stepRequest = new StepRequest();
                stepRequest.setIdUser(idUser);
                stepRequest.setNumberStep(CommonUtils.getStepNumber());
                stepRequest.setWeight(50);
                insertStep(stepRequest);
            }
        }
    }


    private void scheduleNextExecution(long delay) {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(CreateStepWorkerCopy.class)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .build();
        WorkManager.getInstance(context).enqueue(workRequest);
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
