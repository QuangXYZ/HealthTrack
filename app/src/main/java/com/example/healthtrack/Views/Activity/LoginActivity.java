package com.example.healthtrack.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.example.healthtrack.Controller.LoginController;
import com.example.healthtrack.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn;
    ProgressBar loginProgressBar;
    TextView signupText, loginEmail, loginPass;
    private LoginController loginController;
    CircleImageView google;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth firebaseAuth;

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
//        google = findViewById(R.id.login_google);


        Sprite doubleBounce = new ThreeBounce();
        loginProgressBar.setIndeterminateDrawable(doubleBounce);
        loginController = new LoginController(this);

//        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("438431947620-ecpi41uk3dhhf4mv8g8q993k3vs49ltm.apps.googleusercontent.com")
//                .requestEmail()
//                .build();
//
//        // Initialize sign in client
//        googleSignInClient = GoogleSignIn.getClient(LoginActivity.this, googleSignInOptions);
//        firebaseAuth = FirebaseAuth.getInstance();
//        // Initialize firebase user

    }

    void settingUpListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setVisibility(View.GONE);
                loginProgressBar.setVisibility(View.VISIBLE);
                String email = loginEmail.getText().toString();
                String pass = loginPass.getText().toString();
                loginController.loginController(LoginActivity.this, email, pass, new LoginController.CallbackFirebase() {
                    @Override
                    public void onSuccess(FirebaseUser user) {
                        Intent intent = new Intent(LoginActivity.this, MainHomeActivity.class);
                        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Đăng nhập thất bại")
                                .setMessage("Vui lòng xác nhận email trước khi đăng nhập")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    loginBtn.setVisibility(View.VISIBLE);
                                    loginProgressBar.setVisibility(View.GONE);
                                }).show();

                    }
                });

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
//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    Intent intent = googleSignInClient.getSignInIntent();
//                    // Start activity for result
//                    startActivityForResult(intent, 100);
//
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check condition
        if (requestCode == 100) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Check condition
                                if (task.isSuccessful()) {
                                    // When task is successful redirect to profile activity display Toast
//                                    startActivity(new Intent(MainActivity.this, ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                                    displayToast("Firebase authentication successful");
                                } else {
                                    // When task is unsuccessful display Toast
                                    displayToast("Authentication Failed :" + task.getException().getMessage());
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}