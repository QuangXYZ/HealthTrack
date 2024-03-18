package com.example.healthtrack.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtrack.Controller.UserController;
import com.example.healthtrack.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextView loginText;
    TextInputEditText email,username, password, repassword;
    Button signUpButton;
    UserController userController;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        settingUpListeners();
    }
    void init(){
        loginText = findViewById(R.id.loginText);
        email = findViewById(R.id.signup_email);
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        repassword = findViewById(R.id.signup_repassword);
        signUpButton = findViewById(R.id.signupButton);
        progressBar = findViewById(R.id.signupProgressBar);

        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        userController = new UserController();
    }
    void settingUpListeners (){
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                finish();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signUpButton.setVisibility(View.GONE);

                String emailText = email.getText().toString();
                String usernameText = username.getText().toString();
                String passwordText = password.getText().toString();
                String repasswordText = repassword.getText().toString();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (emailText.isEmpty()) {
                    email.setError("Nhập email");
                    email.requestFocus();
                }
                else if (usernameText.isEmpty()){
                    username.setError("Nhập username");
                    username.requestFocus();
                }
                else if (passwordText.isEmpty()){
                    password.setError("Nhập password");
                    password.requestFocus();
                }

                else if (!emailText.matches(emailPattern)) {
                    email.setError("Email không đúng định dạng");
                    email.requestFocus();
                    Toast.makeText(getApplicationContext(), "Email không đúng định dạng", Toast.LENGTH_SHORT).show();
                }
                else if (usernameText.length()<5) {
                    username.setError("Password chưa đủ 5 kí tự");
                    username.requestFocus();
                    Toast.makeText(getApplicationContext(), "Password chưa đủ 8 kí tự", Toast.LENGTH_SHORT).show();
                }
                else if (passwordText.length()<8) {
                    password.setError("Password chưa đủ 8 kí tự");
                    password.requestFocus();
                    Toast.makeText(getApplicationContext(), "Password chưa đủ 8 kí tự", Toast.LENGTH_SHORT).show();
                }
                else if (!repasswordText.equals(passwordText)){
                    repassword.setError("Password không khớp");
                    repassword.requestFocus();
                }
                else {
                    userController.signup(
                            emailText, usernameText, passwordText, new UserController.UserControllerCallback() {
                                @Override
                                public void onSuccess(String message) {
                                    Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_SHORT).show();

                                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                    builder.setTitle("Thông báo")
                                            .setMessage("Đăng ký thành công")
                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                                                    finish();
                                                }
                                            }).show();

                                }

                                @Override
                                public void onError(String error) {
                                    progressBar.setVisibility(View.GONE);
                                    signUpButton.setVisibility(View.VISIBLE);
                                }
                            });
                }

            }

        });

    }
}