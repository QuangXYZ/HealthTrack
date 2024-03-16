package com.example.healthtrack.Api;

import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Respone.BaseResponse;
import com.example.healthtrack.Respone.LoginBodyResponse;
import com.example.healthtrack.Respone.SetGoalsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

    @POST("users/login")
    Call<BaseResponse<User>> login(@Body LoginBodyResponse loginBody);

    @GET("goals/{idUser}")
    Call<SetGoalsResponse<SetGoals>> getSetGoals(
            @Path("idUser") String idUser
    );
}
