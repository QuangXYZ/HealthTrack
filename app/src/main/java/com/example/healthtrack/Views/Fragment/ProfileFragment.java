package com.example.healthtrack.Views.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;

import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Views.Activity.FriendActivity;
import com.example.healthtrack.Views.Activity.ProfileQRActivity;
import com.example.healthtrack.Views.Activity.SplashScreen;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    Button friendBtn, qrBtn;
    LinearLayout logout;
    User user;
    TextView name, email, gender, dateOfBirth;
    CircleImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        init(view);
        settingUpListener();
        return view;
    }

    void init(View view) {
        friendBtn = view.findViewById(R.id.profile_fragment_friend_btn);
        qrBtn = view.findViewById(R.id.profile_fragment_qr_btn);
        logout = view.findViewById(R.id.profile_fragment_logout_btn);
        user = DataLocalManager.getUser();
        name = view.findViewById(R.id.fragment_profile_name);
        email = view.findViewById(R.id.fragment_profile_email);
        gender = view.findViewById(R.id.fragment_profile_gender);
        dateOfBirth = view.findViewById(R.id.fragment_profile_date_of_birth);
        imageView = view.findViewById(R.id.fragment_profile_img);

        name.setText(user.getName());
        email.setText(user.getEmail());
        gender.setText(user.getGender() != null ? user.getGender() : "unknown");
        if (user.getProfilePicture() != null)

            Glide.with(this).load(user.getProfilePicture()).into(imageView);
        dateOfBirth.setText(user.getDateOfBirth());
    }

    void settingUpListener() {
        friendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, FriendActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, ProfileQRActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();

                new AlertDialog.Builder(context)
                        .setTitle("Xác nhận")
                        .setMessage("Bạn có muốn đăng xuất")
                        .setPositiveButton("OK", (dialog, which) -> {
                            Intent intent = new Intent(context, SplashScreen.class);
                            startActivity(intent);
                            getActivity().overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
                            getActivity().finish();
                        }).setNegativeButton("Hủy", (dialog, which) -> {
                        }).show();
            }
        });
    }
}