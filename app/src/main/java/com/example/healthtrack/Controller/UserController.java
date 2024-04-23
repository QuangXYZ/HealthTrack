package com.example.healthtrack.Controller;

import android.util.Log;

import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Network.Api.ApiUtils;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Network.Respone.BaseResponse;
import com.example.healthtrack.Utils.Constants;
import com.example.healthtrack.Utils.DataLocalManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserController {
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    ApiService apiService = retrofit.create(ApiService.class);


    public void login(String email, String password) {

    }

    public void logout() {

    }

    public void signup(String email, String username, String password, final UserControllerCallback userControllerCallback) {
        User user = new User(email, username, password);
        Call<BaseResponse<User>> call = apiService.signup(user);
        call.enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        String idUser = response.body().getData().get_id();
                        Log.e("User", idUser);
                        registerFirebase(email, username, password);
                        userControllerCallback.onSuccess(response.body().getMessage());
                    } else {
                        userControllerCallback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    userControllerCallback.onError(e.toString());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
            }
        });
    }

    void registerFirebase(String email, String username, String password) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Đăng ký thành công
                        FirebaseUser user = mAuth.getCurrentUser();
                        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .build();
                        user.updateProfile(userProfileChangeRequest);
                        user.sendEmailVerification();
                    } else {
                        // Đăng ký thất bại
                    }
                });
    }

    public void getDetailUser(String idUser, final GetUserCallback callback) {
        String token = DataLocalManager.getToken();
        ApiService apiService = ApiUtils.getApiService(token);
        apiService.getDetailUser(idUser)
                .enqueue(new Callback<BaseResponse<User>>() {
                    @Override
                    public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                        try {
                            if (response.isSuccessful()) {
                                callback.onSuccess(response.body().getData());
                            } else {
                                callback.onError(response.body().getMessage());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onError(response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

                    }
                });
    }


    public void updateUser(User user, final UserControllerCallback userControllerCallback) {
        String token = DataLocalManager.getToken();
        ApiService apiServiceUpdate = ApiUtils.getApiService(token);

        apiServiceUpdate.updateUser(user).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        userControllerCallback.onSuccess(response.body().getMessage());
                    } else {
                        userControllerCallback.onError("Can not update user");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    userControllerCallback.onError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

            }
        });
    }

    public interface UserControllerCallback {
        void onSuccess(String message);

        void onError(String error);
    }

    public interface GetUserCallback {
        void onSuccess(User user);

        void onError(String error);
    }


}
