package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Controller.LoginController;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.RankingAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class PrivateChallengeDetail extends AppCompatActivity {

    RecyclerView rankingRecyclerview;
    RankingAdapter  adapter;
    ArrayList<Integer> rankings;
    Button inviteBtn;
    ImageView imageCode;
    MaterialToolbar toolbar;
    Challenge challenge;
    ChallengeController challengeController;
    TextView name, description, step, member, date;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_challenge_detail);
        init();
        settingUpListener();
    }
    void init(){
        rankingRecyclerview = findViewById(R.id.challenge_detail_recylerview);
        inviteBtn = findViewById(R.id.fragment_challenge_create_button);
        imageCode = findViewById(R.id.Qrcode);
        toolbar = findViewById(R.id.challenge_detail_toolbar);
        name = findViewById(R.id.private_challenge_name);
        description = findViewById(R.id.private_challenge_description);
        step = findViewById(R.id.private_challenge_target);
        member = findViewById(R.id.private_challenge_member);
        date = findViewById(R.id.private_challenge_date);


        rankings = new ArrayList<>();
        challengeController = new ChallengeController();

        Intent intent = getIntent();
        if (intent!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                challenge = intent.getSerializableExtra("Challenge", Challenge.class);
            }
            else {
                challenge = (Challenge) intent.getSerializableExtra("Challenge");
            }
        }

        name.setText(challenge.getName());
        description.setText(challenge.getDescription());
        step.setText("Số bước mục tiêu: "+challenge.getTarget());
        member.setText("Số người tham gia "+challenge.getListMember().size());
        date.setText("Thử thách bắt đầu từ "+challenge.getDateStart());




        adapter = new RankingAdapter(this, challenge.getUserRecords());
        rankingRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        rankingRecyclerview.setAdapter(adapter);
        rankingRecyclerview.setNestedScrollingEnabled(true);
    }
    void settingUpListener(){
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.exit_challenge) {
                    new MaterialAlertDialogBuilder(PrivateChallengeDetail.this)
                            .setTitle("Confirm")
                            .setMessage("Bạn có chắc chắn")
                            .setNeutralButton("Có", (dialog, which) -> {
                                challengeController.deleteChallenge(challenge.get_id());
                            })
                            .setPositiveButton("Không", (dialog, which) -> {} ).show();
                }
                return false;
            }
        });
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCode.setVisibility(View.VISIBLE);
                String qrContent = challenge.get_id();
                //initializing MultiFormatWriter for QR code
                MultiFormatWriter mWriter = new MultiFormatWriter();
                try {
                    //BitMatrix class to encode entered text and set Width & Height
                    BitMatrix mMatrix = mWriter.encode(qrContent, BarcodeFormat.QR_CODE, 400,400);
                    BarcodeEncoder mEncoder = new BarcodeEncoder();
                    Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                    imageCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}