package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dd.CircularProgressButton;
import com.example.healthtrack.Models.BodyComposition;
import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class BodyCompositionFragment extends Fragment {
    ImageView man, woman;
    ImageView back;
    TextInputEditText height, weight;
    CircularProgressButton button;
    TextView bmi, ideal;
    boolean gender = true;
    TextView bmi_1, bmi_2, bmi_3, bmi_4, bmi_5;
    HealthActivity healthActivity = DataLocalManager.getHealthActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.body_composition, container, false);
        init(view);
        settingUpListeners();
        return view;
    }

    void init(View view) {
        man = view.findViewById(R.id.body_composition_man);
        woman = view.findViewById(R.id.body_composition_woman);
        back = view.findViewById(R.id.btnBack);
        height = view.findViewById(R.id.body_composition_height);
        weight = view.findViewById(R.id.body_composition_weight);
        button = view.findViewById(R.id.body_composition_btn);
        bmi = view.findViewById(R.id.body_composition_bmi);
        bmi_1 = view.findViewById(R.id.body_composition_bmi_1);
        bmi_2 = view.findViewById(R.id.body_composition_bmi_2);
        bmi_3 = view.findViewById(R.id.body_composition_bmi_3);
        bmi_4 = view.findViewById(R.id.body_composition_bmi_4);
        bmi_5 = view.findViewById(R.id.body_composition_bmi_5);
        ideal = view.findViewById(R.id.body_composition_ideal);

        if (healthActivity != null) {
            weight.setText(healthActivity.getBodyComposition().getWeight() + "");
            height.setText(healthActivity.getBodyComposition().getHeight() + "");
            bmi_progress();
        }

    }

    void settingUpListeners() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi_progress();
                float w = Float.parseFloat(weight.getText().toString());
                float h = Float.parseFloat(height.getText().toString());
                BodyComposition bodyComposition = new BodyComposition((int) w, (int) h);
                healthActivity.setBodyComposition(bodyComposition);
                DataLocalManager.setHealthActivity(healthActivity);

            }
        });
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = true;
                man.setImageResource(R.drawable.man);
                woman.setImageResource(R.drawable.woman1);
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = false;
                man.setImageResource(R.drawable.man_1);
                woman.setImageResource(R.drawable.woman);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
    }


    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HealthFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void bmi_progress() {
        if (weight.getText().toString().isEmpty() || height.getText().toString().isEmpty()) return;
        button.setIndeterminateProgressMode(true); // turn on indeterminate progress
        button.setProgress(50);
        float w = Float.parseFloat(weight.getText().toString());
        float h = Float.parseFloat(height.getText().toString()) / 100;
        float bmi_float = w / (h * h);
        float d = (h * 100 - ((int) h) * 100) * 9 / 10;
        DecimalFormat df = new DecimalFormat("#.##");

        // Sử dụng DecimalFormat để lấy 2 số thập phân
        // Hiện thông tin theo phần trăm

        ideal.setText(d + " Kg");
        bmi.setText(df.format(bmi_float));
        if (bmi_float < 18.5) {
            bmi_1.setTextColor(getResources().getColor(R.color.orange));
            bmi_2.setTextColor(getResources().getColor(R.color.text_color));
            bmi_3.setTextColor(getResources().getColor(R.color.text_color));
            bmi_4.setTextColor(getResources().getColor(R.color.text_color));
            bmi_5.setTextColor(getResources().getColor(R.color.text_color));
        } else if (bmi_float >= 18.5 && bmi_float <= 24.9) {
            bmi_1.setTextColor(getResources().getColor(R.color.text_color));
            bmi_2.setTextColor(getResources().getColor(R.color.orange));
            bmi_3.setTextColor(getResources().getColor(R.color.text_color));
            bmi_4.setTextColor(getResources().getColor(R.color.text_color));
            bmi_5.setTextColor(getResources().getColor(R.color.text_color));
        } else if (bmi_float >= 25 && bmi_float <= 29.9) {
            bmi_1.setTextColor(getResources().getColor(R.color.text_color));
            bmi_2.setTextColor(getResources().getColor(R.color.text_color));
            bmi_3.setTextColor(getResources().getColor(R.color.orange));
            bmi_4.setTextColor(getResources().getColor(R.color.text_color));
            bmi_5.setTextColor(getResources().getColor(R.color.text_color));
        } else if (bmi_float >= 30 && bmi_float <= 34.9) {
            bmi_1.setTextColor(getResources().getColor(R.color.text_color));
            bmi_2.setTextColor(getResources().getColor(R.color.text_color));
            bmi_3.setTextColor(getResources().getColor(R.color.text_color));
            bmi_4.setTextColor(getResources().getColor(R.color.orange));
            bmi_5.setTextColor(getResources().getColor(R.color.text_color));

        } else if (bmi_float >= 35) {
            bmi_1.setTextColor(getResources().getColor(R.color.text_color));
            bmi_2.setTextColor(getResources().getColor(R.color.text_color));
            bmi_3.setTextColor(getResources().getColor(R.color.text_color));
            bmi_4.setTextColor(getResources().getColor(R.color.text_color));
            bmi_5.setTextColor(getResources().getColor(R.color.orange));

        }
        button.setProgress(0);

    }
}

