package com.example.healthtrack.Network.Api;

public class ApiUtils {

    public static ApiService getApiService(String token) {
        return RetrofitClientInstance.getInstance(token).create(ApiService.class);
    }
}
