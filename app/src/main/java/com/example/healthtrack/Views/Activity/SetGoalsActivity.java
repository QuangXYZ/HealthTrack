package com.example.healthtrack.Views.Activity;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.JsonObject;
import com.shawnlin.numberpicker.NumberPicker;

import android.widget.TextView;
import android.widget.Toast;

import com.example.healthtrack.Controller.SetGoalsController;
import com.example.healthtrack.Models.SetGoals;
import com.example.healthtrack.R;
import com.example.healthtrack.Network.Respone.SetGoalsResponse;
import com.example.healthtrack.Utils.SharedPreferences.SharedPrefUser;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public class SetGoalsActivity extends AppCompatActivity {

    private LinearLayout layoutStep, layoutCalo, layoutTime, layoutKm;
    private ImageView imgGoBack;

    private TextView tvNumberStepGoals, tvCaloGoals, tvDistanceGoals, tvTimeGoals;
    private SetGoalsController setGoalsController;
    private ArrayList<SetGoals> mListSetGoals;
    final int[] oldValueKm = new int[1];
    final String[] oldValueTime = new String[1];
    final int[] oldValueStep = new int[1];
    final int[] oldValueCalo = new int[1];

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
        imgGoBack = findViewById(R.id.toolbar_set_goal);
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

        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setDialogKm(int gravity) {
        final Dialog dialog = new Dialog(SetGoalsActivity.this);
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

        NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.number_picker_km);
        String[] data = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);
        final int[] newValue = new int[1];
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newValue[0] = Integer.parseInt(newVal + "0");
            }
        });

        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_set_goals_km);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUser = SharedPrefUser.getId(SetGoalsActivity.this);

                JsonObject newData = new JsonObject();
                if (newValue[0] == 0) {
                    newData.addProperty("distanceGoals", 50000);
                } else {
                    newData.addProperty("distanceGoals", newValue[0]);
                }
                Log.d("TAG", "Giá trị newValue2: " + oldValueKm[0]);
                JsonObject requestBody = new JsonObject();
                requestBody.add("newData", newData);
                class MyAsyncTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Thực hiện tác vụ cần đợi
                        updateSetGoals(idUser, requestBody);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Thực hiện các hành động sau khi tác vụ hoàn thành
                        fetchSetGoals(idUser);
                    }
                }

                // Khởi tạo và thực thi AsyncTask
                MyAsyncTask task = new MyAsyncTask();
                task.execute();
                //------------------------//
                //test insert set goals
//                SetGoals setGoals = new SetGoals();
//                setGoals.setIdUser(idUser);
//                setGoals.setDistanceGoals(5);
//                setGoals.setCaloGoals(78);
//                setGoals.setTimeGoals("57");
//                setGoals.setNumberStepGoals(1000);
//                setGoalsController.insertSetGoals(setGoals);
                //------------------------//

                dialog.dismiss();
            }
        });

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

        NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.number_picker_time);
        String[] data = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120",
                "130", "140", "150", "160", "170", "180", "190"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);
        final String[] newValue = new String[1];
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newValue[0] = String.valueOf(newVal + "0");
                Log.d(TAG, "GiaTriTime: " + newValue[0]);
            }
        });

        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_set_goals_Time);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUser = SharedPrefUser.getId(SetGoalsActivity.this);

                JsonObject newData = new JsonObject();
                if (newValue[0] == "0") {
                    newData.addProperty("timeGoals", "50");
                } else {
                    newData.addProperty("timeGoals", newValue[0]);
                }
                JsonObject requestBody = new JsonObject();
                requestBody.add("newData", newData);
                class MyAsyncTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Thực hiện tác vụ cần đợi
                        updateSetGoals(idUser, requestBody);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Thực hiện các hành động sau khi tác vụ hoàn thành
                        fetchSetGoals(idUser);
                    }
                }

                // Khởi tạo và thực thi AsyncTask
                MyAsyncTask task = new MyAsyncTask();
                task.execute();

                dialog.dismiss();
            }
        });
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

        NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.number_picker_calo);
        String[] data = {"10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120",
                "130", "140", "150", "160", "170", "180", "190"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);
        final int[] newValue = new int[1];
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newValue[0] = Integer.parseInt(newVal + "0");
            }
        });

        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_set_goals_Calo);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUser = SharedPrefUser.getId(SetGoalsActivity.this);

                JsonObject newData = new JsonObject();
                if (newValue[0] == 0) {
                    newData.addProperty("caloGoals", 50);
                } else {
                    newData.addProperty("caloGoals", newValue[0]);
                }
                Log.d("TAG", "Giá trị newValue4: " + oldValueCalo[0]);
                JsonObject requestBody = new JsonObject();
                requestBody.add("newData", newData);
                class MyAsyncTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Thực hiện tác vụ cần đợi
                        updateSetGoals(idUser, requestBody);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Thực hiện các hành động sau khi tác vụ hoàn thành
                        fetchSetGoals(idUser);
                    }
                }

                // Khởi tạo và thực thi AsyncTask
                MyAsyncTask task = new MyAsyncTask();
                task.execute();

                dialog.dismiss();
            }
        });

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

        NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.number_picker_step);
        String[] data = {"1000", "2000", "3000", "4000", "5000", "6000", "7000", "8000", "9000", "10000", "11000"};
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(data.length);
        numberPicker.setDisplayedValues(data);
        numberPicker.setValue(5);
        final int[] newValue = new int[1];
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newValue[0] = Integer.parseInt(newVal + "000");
            }
        });

        Button btnSave = dialog.findViewById(R.id.btn_save_dialog_set_goals_step);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idUser = SharedPrefUser.getId(SetGoalsActivity.this);

                JsonObject newData = new JsonObject();
                if (newValue[0] == 0) {
                    newData.addProperty("numberStepGoals", 50000);
                } else {
                    newData.addProperty("numberStepGoals", newValue[0]);
                }
                JsonObject requestBody = new JsonObject();
                requestBody.add("newData", newData);
                class MyAsyncTask extends AsyncTask<Void, Void, Void> {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Thực hiện tác vụ cần đợi
                        updateSetGoals(idUser, requestBody);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        // Thực hiện các hành động sau khi tác vụ hoàn thành
                        fetchSetGoals(idUser);
                    }
                }

                // Khởi tạo và thực thi AsyncTask
                MyAsyncTask task = new MyAsyncTask();
                task.execute();

                dialog.dismiss();
            }
        });

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
                    tvTimeGoals.setText(String.valueOf(mListSetGoals.get(0).getTimeGoals()) + " phút");
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(SetGoalsActivity.this, "Lỗi kết nô api", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateSetGoals(String idUser, JsonObject requestBody) {
        setGoalsController.updateGoals(SetGoalsActivity.this, idUser, requestBody, new SetGoalsController.UpdateCallback() {
            @Override
            public void onSuccess(ResponseBody setGoals) {
                Toast.makeText(SetGoalsActivity.this, "update success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(SetGoalsActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}