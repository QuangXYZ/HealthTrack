package com.example.healthtrack.Service.Worker;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleJob(context);
    }

    private void scheduleJob(Context context) {
        ComponentName componentName = new ComponentName(context, CreateStep.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, componentName);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // Ví dụ: Yêu cầu kết nối mạng
        // Thiết lập thêm các yêu cầu khác cho công việc nếu cần

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}
