package com.example.healthtrack.Views;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.healthtrack.Controller.FriendController;
import com.example.healthtrack.Controller.UserController;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.CaptureArt;
import com.example.healthtrack.Views.Adapters.FriendInviteAdapter;
import com.example.healthtrack.Views.Adapters.FriendsAdapter;
import com.example.healthtrack.Views.Fragment.UserInfoBottomSheetFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CAMERA = 1;
    ImageView qrBtn;
    MaterialToolbar toolbar;
    RecyclerView friendRequestRV, friendMyRequestRV, friendListRV;
    FriendsAdapter friendAdapter,myFriendInviteAdapter;
    FriendInviteAdapter friendInviteAdapter;

    List<User> friendsList, friendRequestList, myFriendRequestList;

    FriendController friendController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        init();
        settingUpListeners();
    }
    void init() {
        qrBtn = findViewById(R.id.friend_qr_btn);
        friendListRV = findViewById(R.id.friend_list_friend);
        friendRequestRV = findViewById(R.id.friend_request);
        friendMyRequestRV = findViewById(R.id.friend_my_request);
        toolbar = findViewById(R.id.friend_toolbar);

        friendRequestList = new ArrayList<>();
        friendsList = new ArrayList<>();
        myFriendRequestList = new ArrayList<>();
        friendAdapter = new FriendsAdapter(FriendActivity.this, friendsList);
        friendInviteAdapter = new FriendInviteAdapter(FriendActivity.this, friendRequestList);
        myFriendInviteAdapter = new FriendsAdapter(FriendActivity.this, myFriendRequestList);

        friendListRV.setLayoutManager(new LinearLayoutManager(FriendActivity.this));
        friendListRV.setAdapter(friendAdapter);
        friendListRV.setNestedScrollingEnabled(true);

        friendRequestRV.setLayoutManager(new LinearLayoutManager(FriendActivity.this));
        friendRequestRV.setAdapter(friendInviteAdapter);
        friendRequestRV.setNestedScrollingEnabled(true);

        friendMyRequestRV.setLayoutManager(new LinearLayoutManager(FriendActivity.this));
        friendMyRequestRV.setAdapter(myFriendInviteAdapter);
        friendMyRequestRV.setNestedScrollingEnabled(true);

        friendController = new FriendController();

        friendController.getFriendList(new FriendController.FriendCallback() {
            @Override
            public void onSuccess(List<User> users) {
                friendsList.clear();
                friendsList.addAll(users);
                friendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });

        friendController.getFriendRequest(new FriendController.FriendCallback() {
            @Override
            public void onSuccess(List<User> users) {
                friendRequestList.clear();
                friendRequestList.addAll(users);
                friendInviteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });

        friendController.getMyFriendRequest(new FriendController.FriendCallback() {
            @Override
            public void onSuccess(List<User> users) {
                myFriendRequestList.clear();
                myFriendRequestList.addAll(users);
                myFriendInviteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });



    }
    void settingUpListeners() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ((checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(FriendActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
                    } else {
                        initQRCodeScanner();
                    }
                } else {
                    initQRCodeScanner();
                }
            }
        });

    }
    private void initQRCodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(CaptureArt.class);
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQRCodeScanner();
            } else {
                Toast.makeText(FriendActivity.this, "Camera permission is required", Toast.LENGTH_LONG).show();

            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = null,scanFormat = null;
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }
            Toast.makeText(FriendActivity.this, scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();
            UserInfoBottomSheetFragment bottomSheetFragment = new UserInfoBottomSheetFragment();
            bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
        } else {
            Toast.makeText(FriendActivity.this, "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }

    void addFriend(String friendId) {
        friendController.addFriend(friendId, new FriendController.FriendCallback() {
            @Override
            public void onSuccess(List<User> users) {
                new AlertDialog.Builder(FriendActivity.this)
                        .setTitle("Thành công")
                        .setMessage("Da ket ban thanh cong")
                        .setPositiveButton("OK", (dialog, which) -> {

                        } ).show();
            }

            @Override
            public void onError(String error) {

            }
        });
    }



        public interface AddFriendListener {
            void addFriend();
        }

}