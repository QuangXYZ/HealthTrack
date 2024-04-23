package com.example.healthtrack.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.shawnlin.numberpicker.NumberPicker;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

public class CreateChallengeActivity extends AppCompatActivity {

    ExpandableLayout calenderExpandedLayout, stepExpandedLayout;
    MaterialCalendarView calendarView;
    NumberPicker numberPicker;
    LinearLayout calenderLayout, stepLayout;
    TextView calenderTextView, stepTextView;
    MaterialToolbar toolbar;
    MaterialButton createButton;
    SpinKitView progress;
    ChallengeController challengeController;
    EditText challengeNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);
        init();
        settingUpListeners();
    }

    void init() {
        calenderLayout = findViewById(R.id.create_challenge_calender_layout);
        calenderExpandedLayout = findViewById(R.id.create_challenge_expandable_calendar_layout);
        calendarView = findViewById(R.id.create_challenge_calender);
        calenderTextView = findViewById(R.id.create_challenge_date);
        calenderExpandedLayout.collapse();
        calendarView.setSelectedDate(LocalDate.now().plusDays(1));
        calenderTextView.setText(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        stepLayout = findViewById(R.id.create_challenge_step_layout);
        stepExpandedLayout = findViewById(R.id.create_challenge_expandable_step);
        numberPicker = findViewById(R.id.create_challenge_number_picker);
        stepTextView = findViewById(R.id.create_challenge_step);
        stepExpandedLayout.collapse();

        String[] data = {"10.000", "20.000", "30.000", "40.000", "50.000", "60.000", "70.000", "80.000", "90.000", "100.000", "110.000", "120.000", "130.000", "140.000", "150.000"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);

        toolbar = findViewById(R.id.create_challenge_toolbar);
        createButton = findViewById(R.id.create_challenge_btn);
        progress = findViewById(R.id.create_challenge_progress);
        Sprite doubleBounce = new ThreeBounce();
        progress.setIndeterminateDrawable(doubleBounce);

        challengeNameEditText = findViewById(R.id.create_challenge_name);
        challengeController = new ChallengeController();

    }

    void settingUpListeners() {
        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calenderExpandedLayout.toggle();
            }
        });
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                calenderTextView.setText(date.getDay() + "-" + date.getMonth() + "-" + date.getYear());
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
                stepTextView.setText(newVal + "0.000 bước");
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                createButton.setVisibility(View.GONE);

                ;

                String challengeName = challengeNameEditText.getText().toString();

                String localDate = calendarView.getSelectedDate().getYear() + "-"
                        + calendarView.getSelectedDate().getMonth() + "-"
                        + calendarView.getSelectedDate().getDay();

                int target = numberPicker.getValue() * 10000;

                challengeController.createChallenge(challengeName, localDate, target, new ChallengeController.ChallengeControllerCallback() {
                    @Override
                    public void onSuccess(String message) {
                        new AlertDialog.Builder(CreateChallengeActivity.this)
                                .setTitle("Thành công")
                                .setMessage("Thử thách đã được tạo")
                                .setPositiveButton("OK", (dialog, which) -> {
                                    finish();
                                }).show();
                    }

                    @Override
                    public void onError(String error) {
                        new AlertDialog.Builder(CreateChallengeActivity.this)
                                .setTitle("Lỗi")
                                .setMessage(error)
                                .setPositiveButton("OK", (dialog, which) -> {
                                    finish();
                                }).show();
                    }
                });

            }
        });
    }

}