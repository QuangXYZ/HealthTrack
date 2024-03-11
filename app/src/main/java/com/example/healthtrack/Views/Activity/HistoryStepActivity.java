package com.example.healthtrack.Views.Activity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                Toast.makeText(getBaseContext(),"Item 1",Toast.LENGTH_LONG).show();
                break;
            case R.id.item2:
                Toast.makeText(getBaseContext(),"Item 2",Toast.LENGTH_LONG).show();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
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

//                System.out.println(formattedDate);
                Toast.makeText(getApplicationContext(), "Ngày được chọn: " + formattedDate, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Selected time in millis: " + formattedDate);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

//    Date today = new Date("");
//        calendar.init(today, nextYear.getTime())
//            .inMode(CalendarPickerView.SelectionMode.SINGLE)
//                .withSelectedDate(new Date());
}