package com.example.healthtrack.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.healthtrack.Api.ApiService;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Respone.BaseResponse;
import com.example.healthtrack.Respone.LoginBodyResponse;
import com.example.healthtrack.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.SharedPreferences.SharedPreferencesUtil;
import com.example.healthtrack.Views.MainHomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class LoginController {

    private Context context;

    public LoginController(Context context) {
        this.context = context;
    }

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://healthtrack2.cyclic.app/")
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    ApiService iLoginService = retrofit.create(ApiService.class);

    public void loginController(Context context, String email, String password){
        LoginBodyResponse loginBody = new LoginBodyResponse(email, password);
        Call<BaseResponse<User>> call = iLoginService.login(loginBody);

        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                Log.d("loginreponse","đã vào hàm onResponse");
                try {
                    if (response.isSuccessful()){
                        Log.d("loginResponse","đã vào hàm onResponse");
                        String token = response.body().getData().getToken();
                        String idUser = response.body().getData().get_id();
                        // Lưu token vào SharedPreferences
                        SharedPreferencesUtil.saveToken(context, token);
                        SharedPrefUser.SaveId(context, idUser);

                        Log.d("idUserResponse","idUser:" + idUser);
                        Intent intent = new Intent(context, MainHomeActivity.class);
                        showToast("Đăng nhập thành công");
                        context.startActivity(intent);
                        ((Activity) context).finish();
                    } else {
                        Log.e("loginreponse2", "Đăng nhập thất bại");
                        showToast("Đăng nhập thất bại");
                    }
                } catch (Exception e){
                    Log.e("ExceptionLoginreponse", "error"+ e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                showToast("Lỗi kết nối" + t.getMessage());
                Log.e("ExceptionLoginreponse", "errorConnect"+ t.getMessage());
            }
        });
    }

    void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
