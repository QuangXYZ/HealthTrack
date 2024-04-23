package com.example.healthtrack.Views.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.dd.CircularProgressButton;
import com.example.healthtrack.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class BloodPressure extends Fragment {
    ImageView back;
    ImageView img1, img2, img3, img4, img5;
    List<ImageView> img;
    TextInputEditText sys, dia;
    CircularProgressButton btn;
    TextView textSys, text, advise;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.blood_pressure, container, false);

        init(view);
        settingUpListeners();
        return view;
    }

    void init(View view) {
        back = view.findViewById(R.id.btnBack);
        img1 = view.findViewById(R.id.blood_pressure_1);
        img2 = view.findViewById(R.id.blood_pressure_2);
        img3 = view.findViewById(R.id.blood_pressure_3);
        img4 = view.findViewById(R.id.blood_pressure_4);
        img5 = view.findViewById(R.id.blood_pressure_5);
        img = new ArrayList<>();
        img.add(img1);
        img.add(img2);
        img.add(img3);
        img.add(img4);
        img.add(img5);
        sys = view.findViewById(R.id.blood_pressure_sys);
        dia = view.findViewById(R.id.blood_pressure_dia);
        btn = view.findViewById(R.id.blood_pressure_btn);
        textSys = view.findViewById(R.id.blood_pressure_sys_dia);
        text = view.findViewById(R.id.blood_pressure_text);
        advise = view.findViewById(R.id.blood_pressure_advise);
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
                if (sys.getText().toString().equals("") || dia.getText().toString().equals("")) {
                    return;
                }
                btn.setIndeterminateProgressMode(true); // turn on indeterminate progress
                btn.setProgress(50);
                int sys_int = Integer.parseInt(sys.getText().toString());
                int dia_int = Integer.parseInt(dia.getText().toString());
                if (sys_int < 120 && dia_int < 80) {
                    showArrow(0);
                    text.setText("Huyết áp bình thường");
                    textSys.setText("Huyết áp tâm thu dưới 120 mmHg và huyết áp tâm trương dưới 80 mmHg");
                    advise.setText("Huyết áp của bạn đang ở mức bình thường");
                } else if (sys_int < 129 && dia_int < 80) {
                    showArrow(1);
                    text.setText("Huyết áp cao hơn mức bình thường");
                    textSys.setText("Huyết áp tâm thu từ 120 - 129 mmHg và huyết áp tâm trương dưới 80 mmHg ");
                    advise.setText("Huyết áp của bạn đang ở mức bình thường");

                } else if ((sys_int < 139 && !(dia_int >= 80 && dia_int < 90)) || !(sys_int < 139) && (dia_int >= 80 && dia_int < 90)) {
                    showArrow(2);
                    text.setText("Tăng huyết áp mức độ 1");
                    textSys.setText("Huyết áp tâm thu từ 130 - 139 mmHg hoặc huyết áp tâm trương từ 80-89 mmHg ");
                    advise.setText("Bạn nên theo dõi huyết áp thường xuyên");
                } else if ((sys_int >= 140 && !(dia_int >= 90)) || (!(sys_int >= 140) && (dia_int >= 90))) {
                    showArrow(3);
                    text.setText("Tăng huyết áp mức độ 2");
                    textSys.setText("Huyết áp tâm thu từ 140 mmHg hoặc huyết áp tâm trương từ 90 mmHg ");
                    advise.setText("Nếu bạn có 3 kết quả trở lên với tình trạng này, đã đến lúc hỏi bác sĩ về đơn thuốc liên quan đên việc cải thiện lối sống");

                } else if (sys_int >= 180 || dia_int >= 120) {
                    showArrow(4);
                    text.setText("Huyết áp cao nghiêm trọng");
                    textSys.setText("Huyết áp tâm thu từ 180 mmHg và/hoặc huyết áp tâm trương từ 120 mmHg ");
                    advise.setText("Bạn nên liên hệ bác sĩ ngay lập tức");

                }

                btn.setProgress(0);
            }
        });
    }

    void showArrow(int i) {
        for (int j = 0; j < 5; j++) {
            if (j == i) img.get(j).setVisibility(View.VISIBLE);
            else img.get(j).setVisibility(View.INVISIBLE);
        }
    }

    public void replaceFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HealthFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
