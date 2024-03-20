package com.example.healthtrack.Views.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtrack.Controller.SetGoalsController;
import com.example.healthtrack.Controller.StepController;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.Models.Step;
import com.example.healthtrack.R;
import com.example.healthtrack.Respone.SetGoalsResponse;
import com.example.healthtrack.Respone.StepResponse;
import com.example.healthtrack.SharedPreferences.SharedPrefUser;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HistoryStepActivity extends AppCompatActivity {
    ImageView settingImg;
    private StepController stepController;
    private SetGoalsController setGoalsController;
    private ArrayList<Step> mListStep;
    private ArrayList<SetGoals> mListSetGoals;
    private int step2, stepGoals;


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
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate2 = sdf2.format(date);

                Toast.makeText(getApplicationContext(), "Ngày được chọn: " + formattedDate, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Selected time in millis: " + formattedDate);

                openHistoryStepDialog(Gravity.BOTTOM, formattedDate, formattedDate2);
            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }

    private void openHistoryStepDialog(int gravity, String date, String dateHistory) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_history_step);

        TextView tvStep, tvCalo, tvTime, tvKm, tvStepGoals, tvCaloGoals, tvTimeGoals, tvKmGoals, tvDateHistory;
        CircularProgressIndicator progressStep, progressCalo, progressTime, progressKm;

        progressStep = (CircularProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_step);
//        progressCalo = (CircularProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_calo);
//        progressTime = (CircularProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_time);
//        progressKm = (CircularProgressIndicator) dialog.findViewById(R.id.circularProgressIndicator_history_km);
        tvDateHistory = dialog.findViewById(R.id.date_history);
        tvStep = dialog.findViewById(R.id.step_dialog_history);
        tvCalo = dialog.findViewById(R.id.calo_dialog_history);
        tvTime = dialog.findViewById(R.id.time_dialog_history);
        tvKm = dialog.findViewById(R.id.km_dialog_history);
        tvStepGoals = dialog.findViewById(R.id.step_goals_dialog_history);
        tvCaloGoals = dialog.findViewById(R.id.calo_goals_dialog_history);
        tvTimeGoals = dialog.findViewById(R.id.time_goals_dialog_history);
        tvKmGoals = dialog.findViewById(R.id.km_goals_dialog_history);

        tvDateHistory.setText(dateHistory);

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
                    step2 = mListStep.get(0).getNumberStep();
                    Log.d(TAG, "Phần trăm số bước chân step2: " + step2);
                    Log.d(TAG, "Phần trăm số bước chân mListStep: " + mListStep.get(0).getNumberStep());
                }
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
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(HistoryStepActivity.this, "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d(TAG, "Phần trăm số bước chân1: " + step2);
        Log.d(TAG, "Phần trăm số bước chân2: " + stepGoals);
//                progressStep.setProgress(Math.round(step2 * 100 / stepGoals));

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

    private int calculatePercentage(int number1, int number2) {
        return Math.round(number1 * 100 / number2);
    }

    private Float calculatePercentageFloat(Float number1, Float number2) {
        return (float) Math.round(number1 * 100 / number2);
    }

//    Date today = new Date("");
//        calendar.init(today, nextYear.getTime())
//            .inMode(CalendarPickerView.SelectionMode.SINGLE)
//                .withSelectedDate(new Date());
}

/*class MyAsyncTask extends AsyncTask<Void, Void, Void> {
            // Thực hiện tác vụ cần đợi
            @Override
            protected Void doInBackground(Void... params) {
                stepController.getStepHistory(HistoryStepActivity.this, idUser, date, new StepController.GetHistoryCallback() {
                    @Override
                    public void onSuccess(StepResponse<Step> step) {
                        mListStep = (ArrayList<Step>) step.getData();
                        for (int i = 0; i < mListStep.size(); i++) {
                            tvStep.setText(String.valueOf(mListStep.get(0).getNumberStep()));
                            tvCalo.setText(String.valueOf(mListStep.get(0).getCalo()));
                            tvTime.setText(String.valueOf(mListStep.get(0).getTime()));
                            tvKm.setText(String.valueOf(mListStep.get(0).getDistance()));
                            step2 = mListStep.get(0).getNumberStep();
                            Log.d(TAG, "Phần trăm số bước chân step2: " + step2);
                            Log.d(TAG, "Phần trăm số bước chân mListStep: " + mListStep.get(0).getNumberStep());
                        }
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
                        }
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(HistoryStepActivity.this, "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
                    }
                });
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // Thực hiện các hành động sau khi tác vụ hoàn thành
                try {
                    Thread.sleep(1000);
                    Log.d(TAG, "Phần trăm số bước chân1: " + step2);
                    Log.d(TAG, "Phần trăm số bước chân2: " + stepGoals);
                } catch (Exception e){

                }
//                progressStep.setProgress(Math.round(step2 * 100 / stepGoals));
            }
        }

        // Khởi tạo và thực thi AsyncTask
        MyAsyncTask task = new MyAsyncTask();
        task.execute();*/