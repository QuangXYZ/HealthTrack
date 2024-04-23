package com.example.healthtrack.Controller;

import android.content.Context;
import android.util.Log;

import com.example.healthtrack.Models.Exercise;

import java.util.ArrayList;

import com.example.healthtrack.Network.Api.*;
import com.example.healthtrack.Network.Respone.BaseListResponse;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;

import java.util.List;


import retrofit2.Call;
import retrofit2.Response;

public class ExerciseController {
    private ApiService apiService;
    private Context context;

    public ExerciseController(Context context) {
        this.context = context;
    }

    public void getExercise(Context context, final GetExercise callback) {
        String token = SharedPreferencesUtil.getToken(context);
        apiService = ApiUtils.getApiService(token);
        apiService.getExercise()
                .enqueue(new retrofit2.Callback<BaseListResponse<Exercise>>() {

                    @Override
                    public void onResponse(Call<BaseListResponse<Exercise>> call, Response<BaseListResponse<Exercise>> response) {
                        if (response.isSuccessful()) {
                            ArrayList<Exercise> exerciseList = new ArrayList<>();
                            for (Exercise exercise : response.body().getData()) {
                                exerciseList.add(exercise);
                            }
                            callback.onSuccess(exerciseList);
                        } else {
                            callback.onFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseListResponse<Exercise>> call, Throwable t) {
                        callback.onFailure();
                        Log.d("TAG", "loi: " + String.valueOf(t.getMessage()));
                    }
                });
    }

    public interface GetExercise {
        void onSuccess(ArrayList<Exercise> exercise);

        void onFailure();
    }
}
