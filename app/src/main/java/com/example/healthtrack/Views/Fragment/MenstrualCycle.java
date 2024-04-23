package com.example.healthtrack.Views.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class MenstrualCycle extends Fragment {

    private CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menstrual_cycle, container, false);
        Button bt = view.findViewById(R.id.btnBack);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // Xử lý khi ngày được chọn
                view.setBackgroundColor(233);
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(getContext(), "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HealthFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

