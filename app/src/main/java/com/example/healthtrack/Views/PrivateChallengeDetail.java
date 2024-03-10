package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.RankingAdapter;
import com.google.android.material.appbar.MaterialToolbar;
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
        rankings = new ArrayList<>();
        rankings.add(1);
        rankings.add(2);
        rankings.add(3);
        rankings.add(4);
        adapter = new RankingAdapter(this, rankings);
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
        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCode.setVisibility(View.VISIBLE);
                String myText = "Quang12345";
                //initializing MultiFormatWriter for QR code
                MultiFormatWriter mWriter = new MultiFormatWriter();
                try {
                    //BitMatrix class to encode entered text and set Width & Height
                    BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400,400);
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