package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.AmountWater;
import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.Models.HeartRate;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.AmountDrinkingAdapter;
import com.example.healthtrack.Views.Adapters.HeartRateAdapter;

import java.util.ArrayList;
import java.util.List;

public class HeartRateFragment extends Fragment {


    ImageView back;
    Button btn;
    RecyclerView recyclerView;
    HeartRateAdapter heartRateAdapter;
    List<HeartRate> heartRates;
    EditText editText;

    HealthActivity healthActivity = DataLocalManager.getHealthActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heart_rate, container, false);
        init(view);
        settingUpListeners();
        return view;
    }

    void init(View view) {
        back = view.findViewById(R.id.btnBack);
        btn = view.findViewById(R.id.heart_rate_fragment_btn);
        recyclerView = view.findViewById(R.id.heart_rate_list);
        editText = view.findViewById(R.id.heart_rate_fragment_rate);
        heartRates = new ArrayList<>();

        if (DataLocalManager.getHeartRateList() == null)
            DataLocalManager.setHeartRateList(heartRates);

        //them du lieu vao arraylist
        heartRates.addAll(DataLocalManager.getHeartRateList());
        heartRateAdapter = new HeartRateAdapter((Activity) getActivity(), heartRates);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(heartRateAdapter);
        recyclerView.setNestedScrollingEnabled(true);


        if (healthActivity != null) {
            try {
                editText.setText(healthActivity.getHearthRate().getRate() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    void settingUpListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) return;
                HeartRate heartRate = new HeartRate(Integer.parseInt(editText.getText().toString()));
                heartRates.add(heartRate);
                heartRateAdapter.notifyDataSetChanged();
                DataLocalManager.setHeartRateList(heartRates);
            }
        });
    }

    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HealthFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }


}

