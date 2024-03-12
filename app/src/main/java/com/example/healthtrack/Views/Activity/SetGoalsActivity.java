package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.example.healthtrack.R;

public class SetGoalsActivity extends AppCompatActivity {

    LinearLayout layoutStep, layoutCalo, layoutTime, layoutKm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goals);

        innit();
        settingUpListeners();
    }

    private void innit() {
        layoutStep = findViewById(R.id.layout_step);
        layoutCalo = findViewById(R.id.layout_calo);
        layoutTime = findViewById(R.id.layout_time);
        layoutKm = findViewById(R.id.layout_km);
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
        if (window == null){
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

        dialog.show();
    }

    private void setDialogTime(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_goals_time);

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
        if (window == null){
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

    private void setDialogStep(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_set_goals_step);

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
}