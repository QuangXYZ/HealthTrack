package com.example.healthtrack.Views.Fragment;
import android.annotation.SuppressLint;
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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.healthtrack.R;

import java.util.ArrayList;

import cjh.WaveProgressBarlibrary.WaveProgressBar;

public class Water extends Fragment {


    Button amountDrink;
    ImageView back;
    WaveProgressBar wv;
    TextView amount,tip;
    float waterDrink = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.water, container, false);

        init(view);
        settingUpListeners();






        BarChart barChart = view.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 1200f));
        entries.add(new BarEntry(2, 1400f));
        entries.add(new BarEntry(3, 1600f));
        entries.add(new BarEntry(4, 1200f));
        entries.add(new BarEntry(5, 2000f));
        entries.add(new BarEntry(6, 1800f));
        entries.add(new BarEntry(7, 1400f));


        BarDataSet barDataSet = new BarDataSet(entries, "Lượng nước uống trong ngày (ml)");
        barDataSet.setColor(Color.rgb(0, 180, 255)); // Set color of bars
        barDataSet.setValueTextColor(Color.rgb(0, 0, 200)); // Set color of value text

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setFitBars(true); // Make the bars fit to the full width of the x-axis
        barChart.setMinimumHeight(800);
        barChart.invalidate(); // Refresh the chart
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        return view;
    }
    void init(View view) {
       back = view.findViewById(R.id.btnBack);
       wv = view.findViewById(R.id.waveProgressbar);
       amountDrink = view.findViewById(R.id.Amount_drinking_btn);
       amount = view.findViewById(R.id.amount_drinking_text);
       tip = view.findViewById(R.id.amount_drinking_tip);
    }
    void settingUpListeners(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        amountDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waterDrink+=200;
                amount.setText(String.valueOf(waterDrink)+" /2000ml");
                if (waterDrink<=2000)
                     wv.setProgress((int) ((waterDrink/2000)*100));
                if(waterDrink>=2000) {
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

