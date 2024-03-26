package com.example.healthtrack.Views.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.healthtrack.R;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
public class HeartRate extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.heart_rate, container, false);
        Button bt = view.findViewById(R.id.btnBack);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment();
            }
        });

        LineChart lineChart = view.findViewById(R.id.line);

        // Tạo dữ liệu mẫu
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, 60));
        entries.add(new Entry(2, 78));
        entries.add(new Entry(3, 69));
        entries.add(new Entry(4, 82));
        entries.add(new Entry(5, 74));
        entries.add(new Entry(6, 64));
        entries.add(new Entry(7, 85));

        // Tạo dataset và thiết lập màu và các thuộc tính khác
        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLUE);

        // Tạo dữ liệu biểu đồ và thiết lập nó vào biểu đồ
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        // Tạo mô tả cho biểu đồ
        Description description = new Description();
        description.setText("Biểu đồ đường mẫu");
        lineChart.setDescription(description);

        // Cập nhật biểu đồ
        lineChart.invalidate();

        return view;
    }

    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HealthFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

