package com.example.healthtrack.Controller;

import android.util.Log;

import com.example.healthtrack.Api.ApiService;
import com.example.healthtrack.Api.ApiUtils;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Request.JoinChallengeRequest;
import com.example.healthtrack.Respone.BaseListResponse;
import com.example.healthtrack.Respone.BaseResponse;
import com.example.healthtrack.SharedPreferences.SharedPreferencesUtil;
import com.example.healthtrack.Utils.DataLocalManager;

import java.time.LocalDate;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeController {
    private ApiService apiService;

    public ChallengeController() {
        String token = DataLocalManager.getToken();
        apiService = ApiUtils.getApiService(token);
    }
    public void createChallenge(String name, String dateStart, int target, final ChallengeControllerCallback challengeControllerCallback) {
        Challenge newChallenge = new Challenge(
                name,"",
                dateStart,
                target
        );
        apiService.createChallenge(newChallenge).enqueue(new Callback<BaseResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseResponse<Challenge>> call, Response<BaseResponse<Challenge>> response) {
                if (response.isSuccessful()) {
                    Log.d("Challenge", response.body().getData().toString());
                    challengeControllerCallback.onSuccess(response.body().getMessage());
                } else {
                    Log.d("Challenge", response.body().getData().toString());
                    challengeControllerCallback.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Challenge>> call, Throwable t) {
                    challengeControllerCallback.onError(t.getMessage());
            }
        });
    }

    public void joinChallenge(String idChallenge, final ChallengeControllerCallback challengeControllerCallback){
        JoinChallengeRequest joinChallengeRequest = new JoinChallengeRequest(idChallenge);
        apiService.joinChallenge(joinChallengeRequest).enqueue(new Callback<BaseResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseResponse<Challenge>> call, Response<BaseResponse<Challenge>> response) {
                if (response.isSuccessful()) {
                    Log.d("Challenge", response.body().getData().toString());
                    challengeControllerCallback.onSuccess(response.body().getMessage());
                } else {
                    Log.d("Challenge", response.body().getData().toString());
                    challengeControllerCallback.onError(response.body().getMessage());

                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Challenge>> call, Throwable t) {
                challengeControllerCallback.onError(t.getMessage());
            }
        });


    }

    public void getChallengeUser(final GetChallengeCallback getChallengeCallback) {
        String idUser = DataLocalManager.getUser().get_id();
        apiService.getChallengeUser(idUser).enqueue(new Callback<BaseListResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Challenge>> call, Response<BaseListResponse<Challenge>> response) {
                if (response.isSuccessful()) {
                    Log.d("Challenge", response.body().getData().toString());
                    getChallengeCallback.onSuccess(response.body().getData());
                } else {
                    Log.d("Challenge", response.body().getData().toString());
                    getChallengeCallback.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<Challenge>> call, Throwable t) {
                getChallengeCallback.onError(t.getMessage());
            }
        });
    }

    public interface ChallengeControllerCallback {
        void onSuccess(String message);

        void onError(String error);
    }
    public interface GetChallengeCallback {
        void onSuccess(List<Challenge> challenges);

        void onError(String error);
    }
}
