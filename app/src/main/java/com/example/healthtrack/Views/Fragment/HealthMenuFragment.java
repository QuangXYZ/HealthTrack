package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;


public class HealthMenuFragment extends Fragment {

    MaterialCardView amountDrinking, bodyComposition, bloodPressure, heartRate;
    TextView water, bmi;
    ProgressBar progressBar;
    HealthActivity healthActivity = DataLocalManager.getHealthActivity();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_menu, container, false);
        init(view);
        settingUpListener();
        return view;

    }

    void init(View view) {
        amountDrinking = view.findViewById(R.id.fragment_health_amount_drinking);
        bodyComposition = view.findViewById(R.id.fragment_health_body_composition);
        bloodPressure = view.findViewById(R.id.fragment_health_blood_pressure);
        heartRate = view.findViewById(R.id.fragment_health_heart_rate);
        water = view.findViewById(R.id.health_menu_water);
        progressBar = view.findViewById(R.id.health_menu_progressbar);
        bmi = view.findViewById(R.id.fragment_health_menu_bmi);


        if (healthActivity != null) {
            try {
                water.setText(healthActivity.getAmountWater().getAmountDrinking() + "/2000ml");
                progressBar.setProgress((int) (healthActivity.getAmountWater().getAmountDrinking() * 100 / 2000));
                float w = (float) (healthActivity.getBodyComposition().getWeight() * 1.0);
                float h = (float) (healthActivity.getBodyComposition().getHeight() * 1.0) / 100;
                float bmi_float = w / (h * h);
                DecimalFormat df = new DecimalFormat("#.##");
                bmi.setText(df.format(bmi_float));
            } catch (Exception e) {

            }


        }
    }

    void settingUpListener() {
        amountDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                ft.replace(R.id.fragment_health_frame, new Water());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        bodyComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                ft.replace(R.id.fragment_health_frame, new BodyCompositionFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        bloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                ft.replace(R.id.fragment_health_frame, new BloodPressure());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        heartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                ft.replace(R.id.fragment_health_frame, new HeartRateFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}