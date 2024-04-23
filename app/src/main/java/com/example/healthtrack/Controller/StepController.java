package com.example.healthtrack.Controller;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Network.Api.ApiUtils;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.Network.Request.StepRequest;
import com.example.healthtrack.Network.Respone.StepResponse;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import java.time.LocalDate;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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

    public void updateStep(Context context, JsonObject requestBody, final UpdateCallback updateCallback) {
        String token = SharedPreferencesUtil.getToken(context);
        String idUser = SharedPrefUser.getId(context);
        LocalDate today = LocalDate.now();
        apiService = ApiUtils.getApiService(token);
        apiService.updateStep(idUser, String.valueOf(today), RequestBody.create(MediaType.parse("application/json"), requestBody.toString()))
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

    public void insertStep(Context context, StepRequest stepRequest, final InsertCallback insertCallback) {
        String token = SharedPreferencesUtil.getToken(context);
        apiService = ApiUtils.getApiService(token);
        apiService.insertStep(stepRequest).enqueue(new retrofit2.Callback<StepRequest>() {
            @Override
            public void onResponse(Call<StepRequest> call, Response<StepRequest> response) {
                if (response.isSuccessful()) {
                    insertCallback.onSuccess(response.body());
                } else {
                    insertCallback.onError();
                }
            }

            @Override
            public void onFailure(Call<StepRequest> call, Throwable t) {
                insertCallback.onError();
            }
        });
    }

    public void getStepCurrent(Context context, final GetCurrentCallback callback) {
        String token = SharedPreferencesUtil.getToken(context);
        String idUser = SharedPrefUser.getId(context);
        LocalDate today = LocalDate.now();
        Log.d(TAG, "Ngày hiện tại2: " + String.valueOf(today));
        apiService = ApiUtils.getApiService(token);
        apiService.getStepHistory(idUser, String.valueOf(today))
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


    public interface GetHistoryCallback {
        void onSuccess(StepResponse<Step> step);

        void onFailure();
    }

    public interface UpdateCallback {
        void onSuccess(ResponseBody setGoals);

        void onError();
    }

    public interface InsertCallback {
        void onSuccess(StepRequest stepRequest);

        void onError();
    }

    public interface GetCurrentCallback {
        void onSuccess(StepResponse<Step> step);

        void onFailure();
    }
}
