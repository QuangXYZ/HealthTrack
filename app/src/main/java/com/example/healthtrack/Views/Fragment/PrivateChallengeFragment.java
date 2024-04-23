package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.Manifest;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.CaptureArt;
import com.example.healthtrack.Views.Adapters.PrivateChallengeAdapter;
import com.example.healthtrack.Views.Activity.CreateChallengeActivity;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class PrivateChallengeFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CAMERA = 1;
    RecyclerView challengeRecyclerview;
    PrivateChallengeAdapter adapter;
    Button createChallengeBtn, qrScanBtn;
    TextView textview;
    LinearLayout noChallenge;
    ChallengeController challengeController;
    List<Challenge> challengeList;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_private_challenge, container, false);
        init(view);
        settingUpListener();
        return view;
    }

    void init(View view) {

        textview = view.findViewById(R.id.test_qr);
        challengeRecyclerview = view.findViewById(R.id.private_challenge_recyclerview);
        createChallengeBtn = view.findViewById(R.id.fragment_challenge_create_button);
        qrScanBtn = view.findViewById(R.id.fragment_challenge_qr_scan);
        progressBar = view.findViewById(R.id.private_challenge_progress);
        noChallenge = view.findViewById(R.id.private_join_challenge_no_challenge);
        Sprite doubleBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        challengeController = new ChallengeController();
        challengeList = new ArrayList<>();


        adapter = new PrivateChallengeAdapter((Activity) getContext(), challengeList);
        challengeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        challengeRecyclerview.setAdapter(adapter);
        challengeRecyclerview.setNestedScrollingEnabled(true);


        progressBar.setVisibility(View.VISIBLE);
        challengeController.getPrivateChallengeUser(new ChallengeController.GetChallengeCallback() {
            @Override
            public void onSuccess(List<Challenge> challenges) {
                challengeList.addAll(challenges);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                if (challengeList.size() == 0) {
                    noChallenge.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String error) {
                noChallenge.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    void settingUpListener() {
        createChallengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, CreateChallengeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
        qrScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
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
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(PrivateChallengeFragment.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(CaptureArt.class);
        integrator.setPrompt("Scan a QR code");
        integrator.initiateScan();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initQRCodeScanner();
            } else {
                Toast.makeText(getContext(), "Camera permission is required", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = null, scanFormat = null;
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }
            Toast.makeText(getActivity(), scanContent + "   type:" + scanFormat, Toast.LENGTH_SHORT).show();
            joinChallenge(scanContent);
        } else {
            Toast.makeText(getActivity(), "Nothing scanned", Toast.LENGTH_SHORT).show();
        }
    }

    private void joinChallenge(String idChallenge) {
        challengeController.joinChallenge(idChallenge, new ChallengeController.ChallengeControllerCallback() {
            @Override
            public void onSuccess(String message) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Thành công")
                        .setMessage("Đã tham gia thử thách")
                        .setPositiveButton("OK", (dialog, which) -> {

                        }).show();
            }

            @Override
            public void onError(String error) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Lỗi")
                        .setMessage(error)
                        .setPositiveButton("OK", (dialog, which) -> {

                        }).show();
            }
        });
    }


}