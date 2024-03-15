package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthtrack.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    ProgressBar loginProgressBar;
    TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        settingUpListeners();

    }

    void init() {
        loginBtn = findViewById(R.id.loginButton);
        loginProgressBar = findViewById(R.id.loginProgressBar);
        signupText = findViewById(R.id.signupText);

        Sprite doubleBounce = new ThreeBounce();
        loginProgressBar.setIndeterminateDrawable(doubleBounce);
    }

    void settingUpListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.GONE);
                loginProgressBar.setVisibility(View.VISIBLE);
                Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                finish();
            }
        });
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                finish();
            }
        });
    }
}