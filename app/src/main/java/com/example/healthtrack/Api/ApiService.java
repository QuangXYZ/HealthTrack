package com.example.healthtrack.Api;

import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Request.UpdateSetGoalsRequest;
import com.example.healthtrack.Respone.BaseResponse;
import com.example.healthtrack.Respone.LoginBodyResponse;
import com.example.healthtrack.Respone.SetGoalsResponse;

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
    Call<SetGoals> updateSetGoals(@Path("idUser") String idUser, @Body UpdateSetGoalsRequest newData);

    @PATCH("goals/update/{idUser}")
    Call<ResponseBody> updateGoals(@Path("idUser") String idUser, @Body RequestBody body);
}
