package com.example.healthtrack.Controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Network.Api.ApiUtils;
import com.example.healthtrack.Network.Request.StepRequest;
import com.example.healthtrack.Network.Respone.BaseListResponse;
import com.example.healthtrack.Network.Respone.BaseResponse;
import com.example.healthtrack.Network.Respone.StepResponse;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import java.time.LocalDate;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthController {
    private ApiService apiService;

    public HealthController() {
        String token = DataLocalManager.getToken();
        apiService = ApiUtils.getApiService(token);
    }

    public void insertHealthActivity(HealthActivity healthActivity, final InsertCallback insertCallback) {
        apiService.insertHealthActivity(healthActivity).enqueue(new Callback<BaseResponse<HealthActivity>>() {
            @Override
            public void onResponse(Call<BaseResponse<HealthActivity>> call, Response<BaseResponse<HealthActivity>> response) {
                if (response.isSuccessful()) {
                    insertCallback.onSuccess(response.body().getData());
                } else {
                    insertCallback.onError("Can't insert health data");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<HealthActivity>> call, Throwable t) {
                insertCallback.onError(t.getMessage());
            }
        });
    }

    public void updateHealthActivity(JsonObject requestBody, final UpdateCallback updateCallback) {
        String idUser = DataLocalManager.getUser().get_id();
        LocalDate today = LocalDate.now();
        apiService.updateHealthActivity(idUser, String.valueOf(today), RequestBody.create(MediaType.parse("application/json"), requestBody.toString()))
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            updateCallback.onSuccess(response.body());
                            Log.d(TAG, "call api success: " + response.body().toString());
                        } else {
                            updateCallback.onError();
                            Log.d(TAG, "call api error: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        updateCallback.onError();
                        Log.d(TAG, "call api error2: " + t.getMessage());
                    }
                });
    }

    public void getHealthActivity(final GetCallback callback) {
        String idUser = DataLocalManager.getUser().get_id();
        LocalDate today = LocalDate.now();
        apiService.getHealthActivity(idUser, String.valueOf(today))
                .enqueue(new Callback<BaseListResponse<HealthActivity>>() {
                    @Override
                    public void onResponse(Call<BaseListResponse<HealthActivity>> call, Response<BaseListResponse<HealthActivity>> response) {
                        if (response.isSuccessful()) {
                            callback.onSuccess(response.body().getData());
                        } else {
                            callback.onError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseListResponse<HealthActivity>> call, Throwable t) {
                        callback.onError(t.getMessage());
                    }
                });
    }

    public interface UpdateCallback {
        void onSuccess(ResponseBody setGoals);

        void onError();
    }

    public interface GetCallback {
        void onSuccess(List<HealthActivity> healthActivity);

        void onError(String error);
    }

    public interface InsertCallback {
        void onSuccess(HealthActivity healthActivity);

        void onError(String error);
    }
}
