package com.example.healthtrack.Service.Worker;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Network.Request.StepRequest;
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.CommonUtils;

public class CreateStep extends JobService {

    private StepController stepController;
    private Context context;
    private StepService stepService;


    public CreateStep() {
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Context context = getApplicationContext();
        // Khởi tạo StepService
        Intent intent = new Intent(getApplicationContext(), StepService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        performScheduledTask(context);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // Hủy kết nối với StepService khi công việc bị hủy
        if (stepService != null) {
            unbindService(serviceConnection);
            stepService = null;
        }
        return false;
    }

    private void performScheduledTask(Context context) {
        // Thực hiện nhiệm vụ cần thiết ở đây
//        stepService.resetStepCount();
        CommonUtils.clearStepNumber();
        String idUser = SharedPrefUser.getId(context);
        StepRequest stepRequest = new StepRequest();
        stepRequest.setIdUser(idUser);
        stepRequest.setNumberStep(CommonUtils.getStepNumber());
        stepRequest.setWeight(50);
        insertStep(stepRequest);
    }

    private void insertStep(StepRequest stepRequest) {
        Context context = getApplicationContext();
        stepController = new StepController(context);
        stepController.insertStep(context, stepRequest, new StepController.InsertCallback() {
            @Override
            public void onSuccess(StepRequest stepRequest) {

            }

            @Override
            public void onError() {

            }
        });
    }

    // ServiceConnection để quản lý việc kết nối và ngắt kết nối với StepService
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            // Được gọi khi kết nối với StepService thành công
            StepService.StepBinder binder = (StepService.StepBinder) iBinder;
            stepService = binder.getService();

            // Gọi resetStepCount() từ StepService
            if (stepService != null) {
                stepService.resetStepCount();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            // Được gọi khi kết nối với StepService bị ngắt
            stepService = null;
        }
    };
}
