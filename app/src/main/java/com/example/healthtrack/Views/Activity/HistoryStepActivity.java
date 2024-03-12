package com.example.healthtrack.Views.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.healthtrack.R;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoryStepActivity extends AppCompatActivity {
    ImageView settingImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_step);

        calendar();
        innit();
        settingUpListeners();
    }
    private void innit() {
        settingImg = findViewById(R.id.setting);
    }
    private void settingUpListeners() {
        settingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryStepActivity.this, SetGoalsActivity.class);
                startActivity(intent);
            }
        });
    }
    public void calendar(){
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
        Date today = new Date("2023/03/1");
        calendar.init(today, nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(new Date());
//        Date ca;
//        ca = calendar.getSelectedDate();
//        Toast.makeText(HistoryStepActivity.this, "Ngày: "+ ca, Toast.LENGTH_SHORT).show();
//        Log.d(TAG, "Selected time in millis: " + ca);
        calendar.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                // Hiển thị ngày được chọn
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String formattedDate = sdf.format(date);

                Toast.makeText(getApplicationContext(), "Ngày được chọn: " + formattedDate, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Selected time in millis: " + formattedDate);

                openHistoryStepDialog(Gravity.BOTTOM);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void openHistoryStepDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_history_step);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //xử lý khi click ra ngoài dialog
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        ImageView canCelDialog = dialog.findViewById(R.id.cancel_dialog);
        canCelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

//    Date today = new Date("");
//        calendar.init(today, nextYear.getTime())
//            .inMode(CalendarPickerView.SelectionMode.SINGLE)
//                .withSelectedDate(new Date());
}