package com.example.healthtrack.Controller;

import com.example.healthtrack.Network.Api.ApiService;
import com.example.healthtrack.Network.Api.ApiUtils;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.Network.Request.FriendRequest;
import com.example.healthtrack.Network.Respone.BaseListResponse;
import com.example.healthtrack.Network.Respone.BaseResponse;
import com.example.healthtrack.Utils.DataLocalManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class FriendController {
    private ApiService apiService;

    public FriendController() {
        String token = DataLocalManager.getToken();
        apiService = ApiUtils.getApiService(token);
    }

    public void getFriendList(final FriendCallback callback) {
        String userId = DataLocalManager.getUser().get_id();
        apiService.getFriend(userId).enqueue(new Callback<BaseListResponse<User>>() {
            @Override
            public void onResponse(Call<BaseListResponse<User>> call, Response<BaseListResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<User>> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });
    }

    public void getDetailFriend(String friendId, final FriendCallback friendCallback) {
        apiService.getDetailFriend(friendId).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = new ArrayList<>();
                    users.add(response.body().getData());
                    friendCallback.onSuccess(users);
                } else {
                    friendCallback.onError("User not found");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                friendCallback.onError(t.getMessage());
            }
        });
    }


    public void addFriend(String friendId, final AddCallback friendCallback) {

        String userId = DataLocalManager.getUser().get_id();
        FriendRequest friendRequest = new FriendRequest(userId, friendId);
        apiService.addFriend(friendRequest).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        friendCallback.onSuccess(response.body().getData());
                    } else {
                        friendCallback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    friendCallback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                friendCallback.onError(t.getMessage());

            }
        });

    }

    public void getFriendRequest(final FriendCallback callback) {
        String userId = DataLocalManager.getUser().get_id();
        apiService.getFriendRequest(userId).enqueue(new Callback<BaseListResponse<User>>() {
            @Override
            public void onResponse(Call<BaseListResponse<User>> call, Response<BaseListResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<User>> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });
    }

    public void getMyFriendRequest(final FriendCallback callback) {
        String userId = DataLocalManager.getUser().get_id();
        apiService.getMyFriendRequest(userId).enqueue(new Callback<BaseListResponse<User>>() {
            @Override
            public void onResponse(Call<BaseListResponse<User>> call, Response<BaseListResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        callback.onSuccess(response.body().getData());
                    } else {
                        callback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    callback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseListResponse<User>> call, Throwable t) {
                callback.onError(t.getMessage());

            }
        });
    }

    public void acceptFriendRequest(String friendId, final FriendCallback friendCallback) {
        String userId = DataLocalManager.getUser().get_id();
        FriendRequest friendRequest = new FriendRequest(userId, friendId);
        apiService.acceptFriendRequest(friendRequest).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<User> users = new ArrayList<>();
                        users.add(response.body().getData());
                        friendCallback.onSuccess(users);
                    } else {
                        friendCallback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    friendCallback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                friendCallback.onError(t.getMessage());

            }
        });
    }

    public void declineFriendRequest(String friendId, final FriendCallback friendCallback) {
        String userId = DataLocalManager.getUser().get_id();
        FriendRequest friendRequest = new FriendRequest(userId, friendId);
        apiService.declineFriendRequest(friendRequest).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        List<User> users = new ArrayList<>();
                        users.add(response.body().getData());
                        friendCallback.onSuccess(users);
                    } else {
                        friendCallback.onError(response.body().getMessage());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    friendCallback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {

            }
        });
    }

    public interface FriendCallback {
        void onSuccess(List<User> users);

        void onError(String error);
    }

    public interface AddCallback {
        void onSuccess(User users);

        void onError(String error);
    }


}
