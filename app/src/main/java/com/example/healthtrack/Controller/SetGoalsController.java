package com.example.healthtrack.Controller;

import android.content.Context;

import com.example.healthtrack.Api.ApiService;
import com.example.healthtrack.Api.ApiUtils;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Respone.SetGoalsResponse;
import com.example.healthtrack.SharedPreferences.SharedPreferencesUtil;

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

    public interface Callback {
        void onSetGoals(SetGoalsResponse<SetGoals> setGoals);
        void onFailure();
    }
}
