package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.R;

public class PublicChallengeDetailActivity extends AppCompatActivity {

    ImageView icon_achieve;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_challenge);
        init();
        settingUpListeners();
    }
    void init(){

          }
    void settingUpListeners(){

    }
}