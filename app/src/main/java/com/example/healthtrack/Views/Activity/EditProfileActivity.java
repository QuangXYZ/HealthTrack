package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Controller.UserController;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Utils.Upload;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    MaterialToolbar toolbar;
    TextInputEditText name, email, dateOfBirth;
    MaterialDatePicker<Long> materialDatePicker;
    CircleImageView imgUser, uploadImg;
    Button updateButton;
    TextView cancelButton;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath = null;
    UserController userController;
    User user;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
        settingUpListener();
    }

    void init() {
        toolbar = findViewById(R.id.edit_profile_toolar);
        name = findViewById(R.id.edit_profile_name);
        email = findViewById(R.id.edit_profile_email);
        dateOfBirth = findViewById(R.id.edit_profile_date_of_birth);
        imgUser = findViewById(R.id.edit_profile_img);
        uploadImg = findViewById(R.id.edit_profile_upload_img);
        updateButton = findViewById(R.id.edit_profile_update);
        cancelButton = findViewById(R.id.edit_profile_cancel);
        userController = new UserController();
        user = DataLocalManager.getUser();
        name.setText(user.getName());
        email.setText(user.getEmail());
        dateOfBirth.setText(user.getDateOfBirth());
        if (user.getProfilePicture() != null)
            Glide.with(this).load(user.getProfilePicture()).into(imgUser);
        pd = new ProgressDialog(this);


        materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();


    }

    void settingUpListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener((date) -> {
            Date dob = new Date(date);
            // Định dạng ngày thành chuỗi
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String formattedDate = sdf.format(dob);
            dateOfBirth.setText(formattedDate);

        });
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!materialDatePicker.isAdded())
                    materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().length() < 5) {
                    name.setError("Tên ít nhất 5 kí tự");
                    return;
                }
                pd.show(EditProfileActivity.this, "Please Wait..", "Đang cập nhật");

                if (filePath != null) {
                    Upload upload = new Upload();
                    upload.UploadImage(filePath, new Upload.UploadCallback() {
                        @Override
                        public void onSuccess(String downloadUrl) {
                            user.setProfilePicture(downloadUrl);
                            updateUser();

                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                } else updateUser();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(filePath));
                imgUser.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void updateUser() {
        user.setName(name.getText().toString());
        if (!dateOfBirth.getText().toString().isEmpty()) {
            String dob = dateOfBirth.getText().toString().split("-")[0] + "-" + dateOfBirth.getText().toString().split("-")[1] + "-" + dateOfBirth.getText().toString().split("-")[2];
            user.setDateOfBirth(dob);
        }


        userController.updateUser(user, new UserController.UserControllerCallback() {
            @Override
            public void onSuccess(String message) {
                DataLocalManager.setUser(user);
                pd.dismiss();
                new AlertDialog.Builder(EditProfileActivity.this)
                        .setTitle("Thành công")
                        .setMessage(message)
                        .setPositiveButton("OK", (dialog, which) -> {
                            finish();
                        }).show();
            }

            @Override
            public void onError(String error) {
                pd.dismiss();
                new AlertDialog.Builder(EditProfileActivity.this)
                        .setTitle("Lỗi")
                        .setMessage(error)
                        .setPositiveButton("OK", (dialog, which) -> {

                        }).show();
            }
        });
    }
}