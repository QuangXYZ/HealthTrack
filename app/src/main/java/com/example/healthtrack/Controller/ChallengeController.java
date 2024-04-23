package com.example.healthtrack.Controller;

import android.util.Log;

import com.example.healthtrack.Models.User;
import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Network.Api.ApiUtils;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.Models.ChallengeMember;
import com.example.healthtrack.Network.Request.JoinChallengeRequest;
import com.example.healthtrack.Network.Request.LeaveChallengeRequest;
import com.example.healthtrack.Network.Respone.BaseListResponse;
import com.example.healthtrack.Network.Respone.BaseResponse;
import com.example.healthtrack.Utils.CommonUtils;
import com.example.healthtrack.Utils.DataLocalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
                name, "",
                dateStart,
                target
        );
        apiService.createChallenge(newChallenge).enqueue(new Callback<BaseResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseResponse<Challenge>> call, Response<BaseResponse<Challenge>> response) {
                if (response.isSuccessful()) {
                    Log.d("Challenge", response.body().getData().toString());
                    challengeControllerCallback.onSuccess(response.body().getMessage());
                    User user = DataLocalManager.getUser();
                    user.addChallengeId(response.body().getData().get_id());
                    apiService.updateUser(user)
                            .enqueue(new Callback<BaseResponse<User>>() {
                                @Override
                                public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {

                                }

                                @Override
                                public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

                                }
                            });
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

    public void joinChallenge(String idChallenge, final ChallengeControllerCallback challengeControllerCallback) {
        JoinChallengeRequest joinChallengeRequest = new JoinChallengeRequest(idChallenge);
        apiService.joinChallenge(joinChallengeRequest).enqueue(new Callback<BaseResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseResponse<Challenge>> call, Response<BaseResponse<Challenge>> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.d("Challenge", response.body().getData().toString());
                        challengeControllerCallback.onSuccess(response.body().getMessage());
                    } else {
                        Log.d("Challenge", response.body().getData().toString());
                        challengeControllerCallback.onError(response.body().getMessage());

                    }
                } catch (Exception e) {
                    challengeControllerCallback.onError(e.getMessage());

                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Challenge>> call, Throwable t) {
                challengeControllerCallback.onError(t.getMessage());
            }
        });


    }

    public void getPrivateChallengeUser(final GetChallengeCallback getChallengeCallback) {
        String idUser = DataLocalManager.getUser().get_id();
        apiService.getChallengeUser(idUser).enqueue(new Callback<BaseListResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Challenge>> call, Response<BaseListResponse<Challenge>> response) {
                if (response.isSuccessful()) {
                    List<Challenge> challenges = new ArrayList<>();

                    for (Challenge challenge : response.body().getData()) {
                        if (Objects.equals(challenge.getAccess(), "Private")) {
                            challenges.add(challenge);
                        }
                    }
                    getChallengeCallback.onSuccess(challenges);
                } else {
                    assert response.body() != null;
                    getChallengeCallback.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<Challenge>> call, Throwable t) {
                getChallengeCallback.onError(t.getMessage());
            }
        });
    }

    public void getPublicChallenge(final PublicChallengeCallback getChallengeCallback) {
        apiService.getPublicChallenge().enqueue(new Callback<BaseListResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseListResponse<Challenge>> call, Response<BaseListResponse<Challenge>> response) {
                String userId = DataLocalManager.getUser().get_id();
                if (response.isSuccessful()) {
                    List<Challenge> joinChallenges = new ArrayList<>();
                    List<Challenge> notJoinChallenges = new ArrayList<>();


                    for (Challenge challenge : response.body().getData()) {
                        boolean join = false;
                        for (ChallengeMember member : challenge.getListMember()) {
                            if (member.getUserId().equals(userId)) {
                                join = true;
                                joinChallenges.add(challenge);
                                break;
                            }

                        }
                        if (!join) {
                            notJoinChallenges.add(challenge);
                        }

                    }

                    getChallengeCallback.onJoinChallenge(joinChallenges);
                    getChallengeCallback.onNotJoinChallenge(notJoinChallenges);
                } else {
                    assert response.body() != null;
                    getChallengeCallback.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<Challenge>> call, Throwable t) {
                getChallengeCallback.onError(t.getMessage());
            }
        });
    }


    public void leaveChallenge(String idChallenge, final ChallengeControllerCallback challengeControllerCallback) {
        String idUser = DataLocalManager.getUser().get_id();
        LeaveChallengeRequest leaveChallengeRequest = new LeaveChallengeRequest(idUser, idChallenge);
        apiService.leaveChallenge(leaveChallengeRequest).enqueue(new Callback<BaseResponse<Challenge>>() {
            @Override
            public void onResponse(Call<BaseResponse<Challenge>> call, Response<BaseResponse<Challenge>> response) {
                if (response.isSuccessful()) {
                    challengeControllerCallback.onSuccess(response.body().getMessage());
                } else {
                    challengeControllerCallback.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Challenge>> call, Throwable t) {
                challengeControllerCallback.onError(t.getMessage());
            }
        });
    }

    public void updateChallengeStep(final ChallengeControllerCallback challengeControllerCallback) {
        String idUser = DataLocalManager.getUser().get_id();
        int currentStep = DataLocalManager.getWalkingStep(CommonUtils.STEP_NUMBER_KEY) - DataLocalManager.getTempStep();

        apiService.updateChallengeStep(idUser, currentStep).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    challengeControllerCallback.onSuccess(response.message());
                    DataLocalManager.setTempStep(DataLocalManager.getWalkingStep(CommonUtils.STEP_NUMBER_KEY));
                } else {
                    challengeControllerCallback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                challengeControllerCallback.onError(t.getMessage());
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

    public interface PublicChallengeCallback {
        void onJoinChallenge(List<Challenge> challenges);

        void onNotJoinChallenge(List<Challenge> challenges);

        void onError(String error);
    }
}
