package com.example.healthtrack.Worker;

import android.content.Context;

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
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.CommonUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CreateStepWorker extends Worker {

    private StepController stepController;
    private Context context;
    private final StepService stepService;

    public CreateStepWorker(@NonNull Context context, @NonNull WorkerParameters workerParams, StepService stepService) {
        super(context, workerParams);
        stepController = new StepController(context);
        this.context = context;
        this.stepService = stepService;
    }

    @NonNull
    @Override
    public Result doWork() {
        performScheduledTask();
        return Result.success();
    }

//    private void resetExecutedFlagIfNeeded() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        long lastResetTime = sharedPreferences.getLong("lastResetTime", 0);
//        Calendar calendar = Calendar.getInstance();
//        long currentTimeMillis = calendar.getTimeInMillis();
//
//        // Đặt thời gian thành 0 giờ của ngày hôm nay
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long midnightTimeMillis = calendar.getTimeInMillis();
//
//        // Kiểm tra xem đã qua nửa đêm chưa và cờ đã được đặt lại hay chưa
//        if (currentTimeMillis >= midnightTimeMillis && lastResetTime < midnightTimeMillis) {
//            // Đặt lại cờ và thời gian đặt lại
//            sharedPreferences.edit()
//                    .putBoolean("isExecutedToday", false)
//                    .putLong("lastResetTime", currentTimeMillis)
//                    .apply();
//        }
//    }
//
//    private void executeIfNotExecutedYet() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        boolean isExecutedToday = sharedPreferences.getBoolean("isExecutedToday", false);
//
//        // Lấy thời gian hiện tại
//        long currentTimeMillis = System.currentTimeMillis();
//        // Đặt thời gian thành 0 giờ của ngày hôm nay
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long midnightTimeMillis = calendar.getTimeInMillis();
//
//        // Nếu hiện tại đã qua 0 giờ, tính thời gian đến 0 giờ của ngày tiếp theo
//        if (currentTimeMillis >= midnightTimeMillis) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//            midnightTimeMillis = calendar.getTimeInMillis();
//        }
//
//        long delay = midnightTimeMillis - currentTimeMillis;
//
//        // Kiểm tra xem công việc đã được thực hiện hôm nay chưa
//        if (!isExecutedToday && currentTimeMillis >= midnightTimeMillis && currentTimeMillis <= midnightTimeMillis + TimeUnit.HOURS.toMillis(1)) {
//            // Trong khoảng thời gian từ 0 giờ đến 1 giờ sáng, thực hiện các hoạt động cụ thể ở đây
//            performScheduledTask();
//            // Đánh dấu công việc đã được thực hiện
//            sharedPreferences.edit().putBoolean("isExecutedToday", true).apply();
//        } else {
//            // Lên lịch cho công việc thực thi vào thời điểm tính được
//            scheduleNextExecution(delay);
//        }
//    }

// Phương thức performScheduledTask và scheduleNextExecution giữ nguyên như trước


//    @NonNull
//    @Override
//    public Result doWork() {
//        executeIfNotExecutedYet();
//        return Result.success();
//    }
//
//    private void executeIfNotExecutedYet() {
//        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        boolean isExecutedToday = sharedPreferences.getBoolean("isExecutedToday", false);
//
//        // Lấy thời gian hiện tại
//        long currentTimeMillis = System.currentTimeMillis();
//        // Đặt thời gian thành 10 giờ sáng của ngày hôm nay
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        long tenAMTimeMillis = calendar.getTimeInMillis();
//
//        // Nếu hiện tại đã qua 10 giờ, tính thời gian đến 10 giờ sáng của ngày tiếp theo
//        if (currentTimeMillis >= tenAMTimeMillis) {
//            calendar.add(Calendar.DAY_OF_YEAR, 1);
//            tenAMTimeMillis = calendar.getTimeInMillis();
//        }
//
//        long delay = tenAMTimeMillis - currentTimeMillis;
//
//        // Kiểm tra xem công việc đã được thực hiện hôm nay chưa
//        if (!isExecutedToday && currentTimeMillis >= tenAMTimeMillis && currentTimeMillis <= tenAMTimeMillis + TimeUnit.HOURS.toMillis(1)) {
//            // Trong khoảng thời gian từ 10 giờ đến 11 giờ sáng, thực hiện các hoạt động cụ thể ở đây
//            performScheduledTask();
//            // Đánh dấu công việc đã được thực hiện
//            sharedPreferences.edit().putBoolean("isExecutedToday", true).apply();
//        } else {
//            // Lên lịch cho công việc thực thi vào thời điểm tính được
//            scheduleNextExecution(delay);
//        }
//    }

    private void performScheduledTask() {
        // Thực hiện nhiệm vụ cần thiết ở đây
        stepService.resetStepCount();
        CommonUtils.clearStepNumber();
        String idUser = SharedPrefUser.getId(context);
        StepRequest stepRequest = new StepRequest();
        stepRequest.setIdUser(idUser);
        stepRequest.setNumberStep(CommonUtils.getStepNumber());
        stepRequest.setWeight(50);
        insertStep(stepRequest);
    }

    private void scheduleNextExecution(long delay) {
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(CreateStepWorker.class)
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
        //ràng buộc có kết nối wifi mới chạy
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        // Tạo một PeriodicWorkRequest cho công việc hàng ngày
        PeriodicWorkRequest builder = new PeriodicWorkRequest.Builder(CreateStepWorker.class, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)// Thiết lập thời gian chờ đợi ban đầu để công việc bắt đầu vào 0 giờ ngày hôm sau
                                .build();

        // Lên lịch công việc với WorkManager
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "CreateStepWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                builder
        );
    }

    // Hàm tính toán thời gian chờ đợi ban đầu
    public static long calculateInitialDelay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long nextDayMidnight = calendar.getTimeInMillis();
        return nextDayMidnight - System.currentTimeMillis();
    }

}
