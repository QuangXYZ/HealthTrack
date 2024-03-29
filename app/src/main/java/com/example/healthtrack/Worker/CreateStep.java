package com.example.healthtrack.Worker;

import static android.os.Build.VERSION_CODES.S;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import androidx.work.Configuration;

import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Request.StepRequest;
import com.example.healthtrack.Service.StepService;
import com.example.healthtrack.SharedPreferences.SharedPrefUser;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Truy cập StepService thông qua getSystemService
                StepService stepService = (StepService) getSystemService(String.valueOf("step"));

                if (stepService != null) {
                    // Gọi resetStepCount() từ StepService
                    stepService.resetStepCount();
                }
            }
        }).start();
        performScheduledTask(context);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
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
}
