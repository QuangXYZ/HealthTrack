package com.example.healthtrack.Network.Api;

import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.Models.Exercise;
import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Network.Request.FriendRequest;
import com.example.healthtrack.Network.Request.JoinChallengeRequest;
import com.example.healthtrack.Network.Request.StepRequest;
import com.example.healthtrack.Network.Request.LeaveChallengeRequest;
import com.example.healthtrack.Network.Respone.BaseListResponse;
import com.example.healthtrack.Network.Respone.BaseResponse;
import com.example.healthtrack.Network.Respone.LoginBodyResponse;
import com.example.healthtrack.Network.Respone.SetGoalsResponse;
import com.example.healthtrack.Network.Respone.StepResponse;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("users/login")
    Call<BaseResponse<User>> login(@Body LoginBodyResponse loginBody);

    @POST("users/register")
    Call<BaseResponse<User>> signup(@Body User user);

    @GET("goals/{idUser}")
    Call<SetGoalsResponse<SetGoals>> getSetGoals(
            @Path("idUser") String idUser
    );

    @PATCH("goals/update/{idUser}")
    Call<ResponseBody> updateGoals(@Path("idUser") String idUser, @Body RequestBody body);

    @POST("goals")
    Call<SetGoals> insertSetGoals(@Body SetGoals setGoals);

    @GET("step/{idUser}/{date}")
    Call<StepResponse<Step>> getStepHistory(
            @Path("idUser") String idUser,
            @Path("date") String date
    );

    @POST("challenges/create")
    Call<BaseResponse<Challenge>> createChallenge(@Body Challenge challenge);

    @GET("challenges/user/{idUser}")
    Call<BaseListResponse<Challenge>> getChallengeUser(
            @Path("idUser") String idUser
    );

    @POST("users/challenge")
    Call<BaseResponse<Challenge>> joinChallenge(
            @Body JoinChallengeRequest joinChallengeRequest
    );

    @POST("users/update")
    Call<BaseResponse<User>> updateUser(
            @Body User User
    );

    @GET("users/{idUser}")
    Call<BaseResponse<User>> getDetailUser(
            @Path("idUser") String idUser
    );


    @GET("challenges/{idChallenge}")
    Call<Challenge> getChallenge(
            @Path("idChallenge") String idChallenge
    );

    @POST("users/challenge/leave")
    Call<BaseResponse<Challenge>> leaveChallenge(
            @Body LeaveChallengeRequest leaveChallengeRequest
    );

    @GET("users/friends/{idUser}")
    Call<BaseListResponse<User>> getFriend(
            @Path("idUser") String idUser
    );

    @POST("users/friends/add")
    Call<BaseResponse<User>> addFriend(
            @Body FriendRequest addFriendRequest
    );

    @GET("users/friends/request/{idUser}")
    Call<BaseListResponse<User>> getFriendRequest(
            @Path("idUser") String idUser
    );

    @GET("users/friends/myRequest/{idUser}")
    Call<BaseListResponse<User>> getMyFriendRequest(
            @Path("idUser") String idUser
    );

    @GET("users/{idUser}")
    Call<BaseResponse<User>> getDetailFriend(
            @Path("idUser") String idUser
    );

    @POST("users/friends/accept")
    Call<BaseResponse<User>> acceptFriendRequest(
            @Body FriendRequest friendRequest
    );

    @POST("users/friends/decline")
    Call<BaseResponse<User>> declineFriendRequest(
            @Body FriendRequest friendRequest
    );

    @PATCH("challenges/{id}/{step}")
    Call<ResponseBody> updateChallengeStep(@Path("id") String id,
                                           @Path("step") int step);

    @PATCH("challenges/")
    Call<Challenge> updateChallenge(@Body Challenge challenge);

    @PATCH("step/update/{idUser}/{date}")
    Call<ResponseBody> updateStep(@Path("idUser") String idUser,
                                  @Path("date") String date,
                                  @Body RequestBody body);

    @POST("step")
    Call<StepRequest> insertStep(@Body StepRequest stepRequest);

    @GET("challenges/")
    Call<BaseListResponse<Challenge>> getPublicChallenge(

    );

    @GET("exercise")
    Call<BaseListResponse<Exercise>> getExercise();

    @POST("healthActivity/")
    Call<BaseResponse<HealthActivity>> insertHealthActivity(
            @Body HealthActivity healthActivity
    );

    @GET("healthActivity/{idUser}/{date}")
    Call<BaseListResponse<HealthActivity>> getHealthActivity(
            @Path("idUser") String idUser,
            @Path("date") String date
    );

    @PATCH("healthActivity/update/{idUser}/{date}")
    Call<ResponseBody> updateHealthActivity(@Path("idUser") String idUser,
                                            @Path("date") String date,
                                            @Body RequestBody body);
}
