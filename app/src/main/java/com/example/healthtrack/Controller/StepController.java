package com.example.healthtrack.Controller;

import android.content.Context;

import com.example.healthtrack.Api.ApiService;
import com.example.healthtrack.Api.ApiUtils;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.Respone.SetGoalsResponse;
import com.example.healthtrack.Respone.StepResponse;
import com.example.healthtrack.SharedPreferences.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StepController {
    private ApiService apiService;
    private Context context;

    public StepController(Context context) {
        this.context = context;
    }

    public void getStepHistory(Context context, String idUser, String date, final GetHistoryCallback callback) {
        String token = SharedPreferencesUtil.getToken(context);
        apiService = ApiUtils.getApiService(token);
        apiService.getStepHistory(idUser, date)
                .enqueue(new retrofit2.Callback<StepResponse<Step>>() {
                    @Override
                    public void onResponse(Call<StepResponse<Step>> call, Response<StepResponse<Step>> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.body());
                        } else {
                            callback.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<StepResponse<Step>> call, Throwable t) {
                        callback.onFailure();
                    }
                });
    }

    public interface GetHistoryCallback{
        void onSuccess(StepResponse<Step> step);
        void onFailure();
    }
}
