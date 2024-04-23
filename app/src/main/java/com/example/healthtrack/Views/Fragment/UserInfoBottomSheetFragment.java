package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Controller.FriendController;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoBottomSheetFragment extends BottomSheetDialogFragment {


    String friendId;
    TextView cancel, name, lv;
    Button addFriend;
    LinearLayout addFriendLayout, noDataLayout;
    FriendController friendController;
    CircleImageView img;

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        cancel = view.findViewById(R.id.bottom_sheet_cancel);
        addFriend = view.findViewById(R.id.bottom_sheet_add_friend);
        addFriendLayout = view.findViewById(R.id.bottom_sheet_layout);
        noDataLayout = view.findViewById(R.id.bottom_sheet_no_data);
        name = view.findViewById(R.id.bottom_sheet_name);
        lv = view.findViewById(R.id.bottom_sheet_lv);
        img = view.findViewById(R.id.bottom_sheet_img);


        friendController = new FriendController();

        friendController.getDetailFriend(friendId, new FriendController.FriendCallback() {
            @Override
            public void onSuccess(List<User> users) {
                User user = users.get(0);
                name.setText(user.getName());
                lv.setText("Level " + user.getLevel());
                if (user.getProfilePicture() != null)
                    Glide.with(getContext()).load(user.getProfilePicture()).into(img);
                addFriendLayout.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);

            }

            @Override
            public void onError(String error) {
                addFriendLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendController.addFriend(friendId, new FriendController.AddCallback() {
                    @Override
                    public void onSuccess(User users) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Thành công")
                                .setMessage("Đã gửi lời mời")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    dismiss();
                                }).show();
                    }

                    @Override
                    public void onError(String error) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Lỗi")
                                .setMessage(error.toString())
                                .setPositiveButton("OK", (dialog, which) -> {
                                    dismiss();
                                }).show();
                    }
                });
            }
        });

        return view;
    }


}