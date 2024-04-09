package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.R;
import com.google.android.material.card.MaterialCardView;


public class HealthMenuFragment extends Fragment {

    MaterialCardView amountDrinking, bodyComposition, bloodPressure, heartRate;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health_menu, container, false);
        init(view);
        settingUpListener();
        return view;

    }
    void init(View view){
        amountDrinking = view.findViewById(R.id.fragment_health_amount_drinking);
        bodyComposition = view.findViewById(R.id.fragment_health_body_composition);
        bloodPressure = view.findViewById(R.id.fragment_health_blood_pressure);
        heartRate = view.findViewById(R.id.fragment_health_heart_rate);
    }
    void settingUpListener(){
        amountDrinking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_health_frame, new Water());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        bodyComposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_health_frame, new BodyComposition());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        bloodPressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_health_frame, new BloodPressure());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        heartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_health_frame, new HeartRate());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}