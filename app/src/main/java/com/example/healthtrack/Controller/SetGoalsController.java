package com.example.healthtrack.Controller;

import android.content.Context;

import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Network.Api.ApiUtils;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Network.Respone.SetGoalsResponse;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class SetGoalsController {

    private ApiService apiService;
    private Context context;
    private SetGoalsResponse setGoalsResponse;

    public SetGoalsController(Context context) {
        this.context = context;
    }

    public void getSetGoals(Context context, String idUser, final Callback callback) {
        String token = SharedPreferencesUtil.getToken(context);
        apiService = ApiUtils.getApiService(token);
        apiService.getSetGoals(idUser)
                .enqueue(new retrofit2.Callback<SetGoalsResponse<SetGoals>>() {
                    @Override
                    public void onResponse(Call<SetGoalsResponse<SetGoals>> call, Response<SetGoalsResponse<SetGoals>> response) {
                        if (response.isSuccessful()) {
                            setGoalsResponse = response.body();
                            callback.onSetGoals(response.body());
                        } else {
                            callback.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<SetGoalsResponse<SetGoals>> call, Throwable t) {
                        callback.onFailure();
                    }
                });
    }

    public void updateGoals(Context context, String idUser, JsonObject requestBody, final UpdateCallback updateCallback) {
        String token = SharedPreferencesUtil.getToken(context);
        apiService = ApiUtils.getApiService(token);
        apiService.updateGoals(idUser, RequestBody.create(MediaType.parse("application/json"), requestBody.toString()))
                .enqueue(new retrofit2.Callback<ResponseBody>() {
                             @Override
                             public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                 if (response.isSuccessful()) {
                                     if (response.isSuccessful()) {
                                         updateCallback.onSuccess(response.body());
                                     } else {
                                         updateCallback.onError();
//                                         Log.d("ERRORUpdate", response.errorBody().toString());
                                         response.body();
                                     }
                                 }
                             }

                             @Override
                             public void onFailure(Call<ResponseBody> call, Throwable t) {

                             }
                         }
                );
    }

    public void insertSetGoals(SetGoals setGoals) {
        String token = SharedPreferencesUtil.getToken(context);
        apiService = ApiUtils.getApiService(token);
        apiService.insertSetGoals(setGoals).enqueue(new retrofit2.Callback<SetGoals>() {
            @Override
            public void onResponse(Call<SetGoals> call, Response<SetGoals> response) {

            }

            @Override
            public void onFailure(Call<SetGoals> call, Throwable t) {

            }
        });
    }

    public interface Callback {
        void onSetGoals(SetGoalsResponse<SetGoals> setGoals);

        void onFailure();
    }

    public interface UpdateCallback {
        void onSuccess(ResponseBody setGoals);

        void onError();
    }

    public interface insertCallback {
        void onSuccess(SetGoals setGoals);

        void onError();
    }
}
