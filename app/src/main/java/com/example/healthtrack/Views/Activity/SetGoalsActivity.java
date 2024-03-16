package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtrack.Controller.SetGoalsController;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.R;
import com.example.healthtrack.Respone.SetGoalsResponse;
import com.example.healthtrack.SharedPreferences.SharedPrefUser;

import java.util.ArrayList;

public class SetGoalsActivity extends AppCompatActivity {

    LinearLayout layoutStep, layoutCalo, layoutTime, layoutKm;

    private TextView tvNumberStepGoals, tvCaloGoals, tvDistanceGoals, tvTimeGoals;
    private SetGoalsController setGoalsController;
    private ArrayList<SetGoals> mListSetGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        init();
        settingUpListeners();
        String idUser = SharedPrefUser.getId(SetGoalsActivity.this);
        fetchSetGoals(idUser);
    }

    private void init() {
        layoutStep = findViewById(R.id.layout_step);
        layoutCalo = findViewById(R.id.layout_calo);
        layoutTime = findViewById(R.id.layout_time);
        layoutKm = findViewById(R.id.layout_km);
        tvNumberStepGoals = findViewById(R.id.tv_number_step_goals);
        tvCaloGoals = findViewById(R.id.tv_calo_goals);
        tvDistanceGoals = findViewById(R.id.tv_distance_goals);
        tvTimeGoals = findViewById(R.id.time_goals);

        setGoalsController = new SetGoalsController(this);
        mListSetGoals = new ArrayList<>();
    }

    private void settingUpListeners() {
        layoutStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogStep(Gravity.CENTER);
            }
        });

        layoutCalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogCalo(Gravity.CENTER);
            }
        });

        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogTime(Gravity.CENTER);
            }
        });

        layoutKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialogKm(Gravity.CENTER);
            }
        });
    }

    private void setDialogKm(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_goals_km);

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
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnCancel = dialog.findViewById(R.id.cancel_dialog_set_goals_km);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker_km);
        String[] data = {"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);

        dialog.show();
    }

    private void setDialogTime(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_goals_time);

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
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnCancel = dialog.findViewById(R.id.cancel_dialog_set_goals_time);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker_time);

        dialog.show();
    }

    private void setDialogCalo(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_goals_calo);

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
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnCancel = dialog.findViewById(R.id.cancel_dialog_set_goals_calo);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker_calo);

        dialog.show();
    }

    private void setDialogStep(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_goals_step);

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
        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnCancel = dialog.findViewById(R.id.cancel_dialog_set_goals_step);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        //------------------------------------//
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.number_picker_step);


//// OnClickListener
//        numberPicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "Click on current value");
//            }
//        });
//
//// OnValueChangeListener
//        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                Log.d(TAG, String.format(Locale.US, "oldVal: %d, newVal: %d", oldVal, newVal));
//            }
//        });
//
//// OnScrollListener
//        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
//            @Override
//            public void onScrollStateChange(NumberPicker picker, int scrollState) {
//                if (scrollState == SCROLL_STATE_IDLE) {
//                    Log.d(TAG, String.format(Locale.US, "newVal: %d", picker.getValue()));
//                }
//            }
//        });
        //-----------------------------------//

        dialog.show();
    }

    private void fetchSetGoals(String idUser) {
        setGoalsController.getSetGoals(SetGoalsActivity.this, idUser, new SetGoalsController.Callback() {
            @Override
            public void onSetGoals(SetGoalsResponse<SetGoals> setGoals) {
                mListSetGoals = (ArrayList<SetGoals>) setGoals.getData();
                for (int i = 0; i < mListSetGoals.size(); i++) {
                    tvNumberStepGoals.setText(String.valueOf(mListSetGoals.get(0).getNumberStepGoals()));
                    tvDistanceGoals.setText(String.valueOf(mListSetGoals.get(0).getDistanceGoals()) + " km");
                    tvCaloGoals.setText(String.valueOf(mListSetGoals.get(0).getCaloGoals()) + " kcal");
                    tvTimeGoals.setText(String.valueOf(mListSetGoals.get(0).getTimeGoals()) + "phút");
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(SetGoalsActivity.this, "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
            }
        });
    }
}