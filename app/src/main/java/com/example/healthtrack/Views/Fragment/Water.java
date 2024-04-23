package com.example.healthtrack.Views.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.AmountWater;
import com.example.healthtrack.Models.HealthActivity;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Views.Adapters.AmountDrinkingAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.healthtrack.R;

import java.util.ArrayList;
import java.util.List;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class Water extends Fragment {


    Button amountDrink;
    ImageView back;
    WaveProgressBar wv;
    TextView amount, tip;
    float waterDrink = 0;
    RecyclerView recyclerView;
    AmountDrinkingAdapter amountDrinkingAdapter;
    List<AmountWater> amountWaterList;
    HealthActivity healthActivity = DataLocalManager.getHealthActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.water, container, false);

        init(view);
        settingUpListeners();


        return view;
    }

    void init(View view) {
        back = view.findViewById(R.id.btnBack);
        wv = view.findViewById(R.id.waveProgressbar);
        amountDrink = view.findViewById(R.id.Amount_drinking_btn);
        amount = view.findViewById(R.id.amount_drinking_text);
        tip = view.findViewById(R.id.amount_drinking_tip);
        recyclerView = view.findViewById(R.id.water_list);
        amountWaterList = new ArrayList<>();
        if (DataLocalManager.getAmountDrinkingList() == null)
            DataLocalManager.saveAmountDrinkingList(amountWaterList);

        amountWaterList.addAll(DataLocalManager.getAmountDrinkingList());
        amountDrinkingAdapter = new AmountDrinkingAdapter((Activity) getActivity(), amountWaterList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(amountDrinkingAdapter);
        recyclerView.setNestedScrollingEnabled(true);


        if (healthActivity != null) {
            waterDrink = healthActivity.getAmountWater().getAmountDrinking();
            amount.setText(String.valueOf(waterDrink) + " /2000ml");
            wv.setProgress((int) ((waterDrink / 2000) * 100));
            if (waterDrink >= 2000) {
                tip.setText("Bạn đã uống đủ lượng nước cần thiết");
                tip.setTextColor(getResources().getColor(R.color.text_color));
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

        amountDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterDrink += 200;
                healthActivity.getAmountWater().setAmountDrinking((int) waterDrink);
                DataLocalManager.setHealthActivity(healthActivity);
                AmountWater amountWater = new AmountWater(200);
                amountWaterList.add(amountWater);
                amountDrinkingAdapter.notifyDataSetChanged();
                DataLocalManager.saveAmountDrinkingList(amountWaterList);
                amount.setText(String.valueOf(waterDrink) + " /2000ml");
                if (waterDrink <= 2000)
                    wv.setProgress((int) ((waterDrink / 2000) * 100));
                if (waterDrink >= 2000) {
                    tip.setText("Bạn đã uống đủ lượng nước cần thiết");
                    tip.setTextColor(getResources().getColor(R.color.text_color));
                }
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

