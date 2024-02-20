package com.example.healthtrack.Api;
import com.example.healthtrack.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat(Constants.DATE_FORMAT)
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
}
