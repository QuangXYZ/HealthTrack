package com.example.healthtrack.Utils;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
//        ComponentName componentName = new ComponentName(this, CreateStep.class);
//        JobInfo.Builder builder = new JobInfo.Builder(0, componentName);
//
//        // Thiết lập định kỳ thực hiện công việc
//        builder.setPeriodic(24 * 60 * 60 * 1000); // 24 giờ
//
//        // Thiết lập yêu cầu kết nối mạng (nếu cần thiết)
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//
//        // Thiết lập bắt đầu vào lúc 22 giờ
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, 23);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//
//        // Nếu thời gian hiện tại đã qua 22 giờ, thì cập nhật để bắt đầu vào ngày mai
//        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        long startTime = calendar.getTimeInMillis();
//
//        // Đặt thời gian bắt đầu công việc
//        builder.setPeriodic(startTime - System.currentTimeMillis());
//
//        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//        JobInfo jobInfo = builder.build();
//        jobScheduler.schedule(jobInfo);

    }
}
