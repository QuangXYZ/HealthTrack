package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.healthtrack.Controller.SetGoalsController;
import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.R;
import com.example.healthtrack.Network.Respone.SetGoalsResponse;
import com.example.healthtrack.Network.Respone.StepResponse;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryStepActivity extends AppCompatActivity {
    ImageView settingImg, toolbar;
    private StepController stepController;
    private SetGoalsController setGoalsController;
    private ArrayList<Step> mListStep;
    private ArrayList<SetGoals> mListSetGoals;
    private int step1, calo, stepGoals, caloGoals, kmGoals, km;
    private int time, timeGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_step);

        calendar();
        init();
        settingUpListeners();
    }

    private void init() {
        settingImg = findViewById(R.id.setting);
        toolbar = findViewById(R.id.toolbar_history);
        stepController = new StepController(this);
        setGoalsController = new SetGoalsController(this);
        mListStep = new ArrayList<>();
        mListSetGoals = new ArrayList<>();
    }

    private void settingUpListeners() {
        settingImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryStepActivity.this, SetGoalsActivity.class);
                startActivity(intent);
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void calendar() {
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = sdf.format(date);
                //Hiển thị ngày lên dialog
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate2 = sdf2.format(date);
                try {
                    openHistoryStepDialog(Gravity.BOTTOM, formattedDate, formattedDate2);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void openHistoryStepDialog(int gravity, String date, String dateHistory) throws InterruptedException {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_history_step);

        TextView tvStep, tvCalo, tvTime, tvKm, tvStepGoals, tvCaloGoals, tvTimeGoals, tvKmGoals, tvDateHistory;
        CircularProgressIndicator progressStep;
        LinearProgressIndicator progressCalo, progressTime, progressKm;

        progressStep = (CircularProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_step);
        progressCalo = (LinearProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_calo);
        progressTime = (LinearProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_time);
        progressKm = (LinearProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_km);
        tvDateHistory = dialog.findViewById(R.id.date_history);
        tvStep = dialog.findViewById(R.id.step_dialog_history);
        tvCalo = dialog.findViewById(R.id.calo_dialog_history);
        tvTime = dialog.findViewById(R.id.time_dialog_history);
        tvKm = dialog.findViewById(R.id.km_dialog_history);
        tvStepGoals = dialog.findViewById(R.id.step_goals_dialog_history);
        tvCaloGoals = dialog.findViewById(R.id.calo_goals_dialog_history);
        tvTimeGoals = dialog.findViewById(R.id.time_goals_dialog_history);
        tvKmGoals = dialog.findViewById(R.id.km_goals_dialog_history);

        tvDateHistory.setText("Ngày " + dateHistory);

        String idUser = SharedPrefUser.getId(HistoryStepActivity.this);
        stepController.getStepHistory(HistoryStepActivity.this, idUser, date, new StepController.GetHistoryCallback() {
            @Override
            public void onSuccess(StepResponse<Step> step) {
                mListStep = (ArrayList<Step>) step.getData();
                for (int i = 0; i < mListStep.size(); i++) {
                    tvStep.setText(String.valueOf(mListStep.get(0).getNumberStep()));
                    tvCalo.setText(String.valueOf(mListStep.get(0).getCalo()));
                    tvTime.setText(String.valueOf(mListStep.get(0).getTime()));
                    tvKm.setText(String.valueOf(mListStep.get(0).getDistance()));
                    step1 = mListStep.get(0).getNumberStep();
                    calo = mListStep.get(0).getCalo();
                    time = Integer.valueOf(mListStep.get(0).getTime());
                    km = Integer.valueOf((int) mListStep.get(0).getDistance());
                }
                calculateProgressStep(progressStep);
                calculateProgressCalo(progressCalo);
                calculateProgressTime(progressTime);
                calculateProgressDistance(progressKm);
            }

            @Override
            public void onFailure() {
                Toast.makeText(HistoryStepActivity.this, "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
            }
        });

        setGoalsController.getSetGoals(HistoryStepActivity.this, idUser, new SetGoalsController.Callback() {
            @Override
            public void onSetGoals(SetGoalsResponse<SetGoals> setGoals) {
                mListSetGoals = (ArrayList<SetGoals>) setGoals.getData();
                for (int i = 0; i < mListSetGoals.size(); i++) {
                    tvStepGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getNumberStepGoals()) + "bước");
                    tvKmGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getDistanceGoals()) + "km");
                    tvCaloGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getCaloGoals()) + "kcal");
                    tvTimeGoals.setText(String.valueOf("/" + mListSetGoals.get(0).getTimeGoals()) + "phút");
                    stepGoals = mListSetGoals.get(0).getNumberStepGoals();
                    kmGoals = mListSetGoals.get(0).getDistanceGoals();
                    caloGoals = mListSetGoals.get(0).getCaloGoals();
                    timeGoals = Integer.valueOf(mListSetGoals.get(0).getCaloGoals());
                }
                calculateProgressStep(progressStep);
                calculateProgressDistance(progressKm);
                calculateProgressCalo(progressCalo);
                calculateProgressTime(progressTime);
            }

            @Override
            public void onFailure() {
                Toast.makeText(HistoryStepActivity.this, "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
            }
        });

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        //xử lý khi click ra ngoài dialog
        if (Gravity.BOTTOM == gravity) {
            step1 = 0;
            stepGoals = 0;
            calo = 0;
            caloGoals = 0;
            km = 0;
            kmGoals = 0;
            time = 0;
            timeGoals = 0;
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        ImageView canCelDialog = dialog.findViewById(R.id.cancel_dialog);
        canCelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                step1 = 0;
                stepGoals = 0;
                calo = 0;
                caloGoals = 0;
                km = 0;
                kmGoals = 0;
                time = 0;
                timeGoals = 0;
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void calculateProgressStep(CircularProgressIndicator progressStep) {
        if (step1 != 0 && stepGoals != 0) {
            progressStep.setProgress(Math.round(step1 * 100 / stepGoals));
        }
    }

    private void calculateProgressCalo(LinearProgressIndicator progressCalo) {
        if (calo != 0 && caloGoals != 0) {
            progressCalo.setProgress(Math.round(calo * 100 / caloGoals));
        }
    }

    private void calculateProgressTime(LinearProgressIndicator progressTime) {
        if (time != 0 && timeGoals != 0) {
            progressTime.setProgress(Integer.valueOf(Math.round(time * 100 / timeGoals)));
        }
    }

    private void calculateProgressDistance(LinearProgressIndicator progressDistance) {
        if (km != 0 && kmGoals != 0) {
            progressDistance.setProgress(Integer.valueOf((int) Math.round(km * 100 / kmGoals)));
        }
    }


    private Float calculatePercentageFloat(Float number1, Float number2) {
        return (float) Math.round(number1 * 100 / number2);
    }

//    Date today = new Date("");
//        calendar.init(today, nextYear.getTime())
//            .inMode(CalendarPickerView.SelectionMode.SINGLE)
//                .withSelectedDate(new Date());
}

