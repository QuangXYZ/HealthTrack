package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.R;
import com.google.android.material.card.MaterialCardView;

public class HealthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        init(view);
        settingUpListeners();

        return view;
    }

    void init(View view){

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_health_frame, new HealthMenuFragment());
        ft.addToBackStack(null);
        ft.commit();
    }

    void settingUpListeners() {

    }





}