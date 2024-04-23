package com.example.healthtrack.Controller;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Network.Respone.BaseResponse;
import com.example.healthtrack.Network.Respone.LoginBodyResponse;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.example.healthtrack.Utils.SharedPreferences.SharedPreferencesUtil;
import com.example.healthtrack.Utils.Constants;
import com.example.healthtrack.Utils.DataLocalManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    ApiService iLoginService = retrofit.create(ApiService.class);

    public void loginController(Context context, String email, String password, final CallbackFirebase callback) {
        LoginBodyResponse loginBody = new LoginBodyResponse(email, password);
        Call<BaseResponse<User>> call = iLoginService.login(loginBody);

        call.enqueue(new Callback<BaseResponse<User>>() {

            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                Log.d("loginreponse", "đã vào hàm onResponse");
                try {
                    if (response.isSuccessful()) {
                        Log.d("loginResponse", "đã vào hàm onResponse");
                        String token = response.body().getData().getToken();
                        String idUser = response.body().getData().get_id();
                        // Lưu token vào SharedPreferences
                        SharedPreferencesUtil.saveToken(context, token);
                        SharedPrefUser.SaveId(context, idUser);


                        // t lưu tạm cái token vào SharedPreferences
                        DataLocalManager.setToken(token);
                        // luu user
                        User user = response.body().getData();
                        DataLocalManager.setUser(user);

                        loginFirebase(email, password, new CallbackFirebase() {
                            @Override
                            public void onSuccess(FirebaseUser user) {
                                callback.onSuccess(user);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                callback.onFailure(e);
                            }
                        });


                    } else {
                        callback.onFailure(new Exception("Can not login"));
                    }
                } catch (Exception e) {
                    Log.e("ExceptionLoginreponse", "error" + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                showToast("Lỗi kết nối" + t.getMessage());
                Log.e("ExceptionLoginreponse", "errorConnect" + t.getMessage());
            }
        });
    }

    void loginFirebase(String email, String password, final CallbackFirebase callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Đăng nhập thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user.isEmailVerified()) callback.onSuccess(user);
                        else callback.onFailure(new Exception("Email chưa được xác thực"));
                    } else {
                        // Đăng nhập thất bại
                        callback.onFailure(task.getException());
                    }
                });
    }

    void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public interface CallbackFirebase {
        void onSuccess(FirebaseUser user);

        void onFailure(Exception e);
    }
}
