package com.example.healthtrack.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.healthtrack.R;
import com.google.android.material.card.MaterialCardView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.shawnlin.numberpicker.NumberPicker;

import net.cachapa.expandablelayout.ExpandableLayout;

public class CreateChallengeActivity extends AppCompatActivity {

    ExpandableLayout calenderExpandedLayout,stepExpandedLayout;
    MaterialCalendarView calendarView;
    NumberPicker numberPicker;
    LinearLayout calenderLayout,stepLayout;
    TextView calenderTextView,stepTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        init();
        settingUpListeners();
    }
    void init(){
        calenderLayout = findViewById(R.id.create_challenge_calender_layout);
        calenderExpandedLayout = findViewById(R.id.create_challenge_expandable_calendar_layout);
        calendarView = findViewById(R.id.create_challenge_calender);
        calenderTextView = findViewById(R.id.create_challenge_date);
        calenderExpandedLayout.collapse();

        stepLayout = findViewById(R.id.create_challenge_step_layout);
        stepExpandedLayout = findViewById(R.id.create_challenge_expandable_step);
        numberPicker = findViewById(R.id.create_challenge_number_picker);
        stepTextView = findViewById(R.id.create_challenge_step);
        stepExpandedLayout.collapse();

        String[] data = {"10.000", "20.000", "30.000", "40.000", "50.000", "60.000", "70.000", "80.000", "90.000","100.000","110.000","120.000","130.000","140.000","150.000"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);

    }
    void settingUpListeners(){
        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderExpandedLayout.toggle();
            }
        });
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                calenderTextView.setText(date.getDay()+"-"+date.getMonth()+"-"+date.getYear());
            }
        });

        stepLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepExpandedLayout.toggle();
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                stepTextView.setText(newVal+"0.000 bước");
            }
        });
    }
}