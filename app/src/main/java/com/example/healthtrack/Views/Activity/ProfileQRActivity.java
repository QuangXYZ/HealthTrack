package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileQRActivity extends AppCompatActivity {

    ImageView qrcode;
    MaterialToolbar toolbar;
    TextView name;
    CircleImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_qractivity);
        init();
        settingUpListener();
    }

    void init() {
        qrcode = findViewById(R.id.profile_my_qr);
        toolbar = findViewById(R.id.profile_qr_toolbar);
        name = findViewById(R.id.profile_my_qr_name);
        img = findViewById(R.id.profile_my_qr_img);

        String userId = DataLocalManager.getUser().get_id();

        name.setText(DataLocalManager.getUser().getName());
        if (DataLocalManager.getUser().getProfilePicture() != null)
            Glide.with(this).load(DataLocalManager.getUser().getProfilePicture()).into(img);

        //initializing MultiFormatWriter for QR code
        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(userId, BarcodeFormat.QR_CODE, 400, 400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            qrcode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    void settingUpListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}