package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.Controller.HealthController;
import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HealthFragment extends Fragment {
    HealthController healthController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        init(view);
        settingUpListeners();

        return view;
    }

    void init(View view) {

        healthController = new HealthController();
        if (DataLocalManager.getHealthActivity() == null) {
            healthController.getHealthActivity(new HealthController.GetCallback() {
                @Override
                public void onSuccess(List<HealthActivity> healthActivity) {

                    if (healthActivity.size() == 0) {
                        HealthActivity activity = new HealthActivity();
                        healthController.insertHealthActivity(activity, new HealthController.InsertCallback() {
                            @Override
                            public void onSuccess(HealthActivity healthActivity) {
                                DataLocalManager.setHealthActivity(healthActivity);
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
                    } else {
                        DataLocalManager.setHealthActivity(healthActivity.get(0));
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_health_frame, new HealthMenuFragment());
        ft.addToBackStack(null);
        ft.commit();

    }

    void settingUpListeners() {

    }


}