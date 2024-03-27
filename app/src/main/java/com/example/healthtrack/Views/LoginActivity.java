package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthtrack.Controller.LoginController;
import com.example.healthtrack.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    ProgressBar loginProgressBar;
    TextView signupText,loginEmail, loginPass;
    private LoginController loginController;

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
        loginEmail = findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);


        Sprite doubleBounce = new ThreeBounce();
        loginProgressBar.setIndeterminateDrawable(doubleBounce);
        loginController = new LoginController(this);
    }

    void settingUpListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.GONE);
                loginProgressBar.setVisibility(View.VISIBLE);
                String email = loginEmail.getText().toString();
                String pass = loginPass.getText().toString();
                loginController.loginController(LoginActivity.this, email, pass);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);

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