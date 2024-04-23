package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.healthtrack.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 1000);
    }

    private void nextActivity() {
        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
        finish();
    }
}