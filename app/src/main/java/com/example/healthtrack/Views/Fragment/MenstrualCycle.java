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
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.example.healthtrack.R;

import java.util.ArrayList;
public class MenstrualCycle extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button bt;
    public MenstrualCycle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenstrualCycle newInstance(String param1, String param2) {
        MenstrualCycle menstrualCycle = new MenstrualCycle();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        menstrualCycle.setArguments(args);
        return menstrualCycle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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

        BarChart barChart = view.findViewById(R.id.bar_chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 100f));
        entries.add(new BarEntry(2, 150f));
        entries.add(new BarEntry(3, 250f));
        entries.add(new BarEntry(4, 300f));
        entries.add(new BarEntry(5, 100f));

        BarDataSet barDataSet = new BarDataSet(entries, "Lượng nước uống trong ngày");
        barDataSet.setColor(Color.rgb(0, 155, 0)); // Set color of bars
        barDataSet.setValueTextColor(Color.rgb(0, 155, 0)); // Set color of value text

        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.setFitBars(true); // Make the bars fit to the full width of the x-axis
        barChart.setMinimumHeight(500);
        barChart.invalidate(); // Refresh the chart
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        return view;
    }

    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_health_frame, new HealthFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}

