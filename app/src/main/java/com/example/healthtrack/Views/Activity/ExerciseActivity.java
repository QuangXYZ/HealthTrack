package com.example.healthtrack.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.Models.Exercise;
import com.example.healthtrack.Models.SmallExercises;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.SmallExerciseAdapter;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    private Exercise exercise;
    private ArrayList<SmallExercises> smallExercisesList;
    private SmallExerciseAdapter adapter;
    private RecyclerView recyclerView;
    private TextView titleAppbar;
    private ImageView imgGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        init();
        settingUpListeners();
    }

    void init() {
        smallExercisesList = new ArrayList<>();
        titleAppbar = findViewById(R.id.title_appBar);
        Intent intent = getIntent();
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                exercise = intent.getSerializableExtra("exercise", Exercise.class);
                smallExercisesList.addAll(exercise.getSmallExercises());
            } else {
                exercise = (Exercise) intent.getSerializableExtra("exercise");
                smallExercisesList.addAll(exercise.getSmallExercises());
                titleAppbar.setText(exercise.getTitle());
            }
        }

        imgGoBack = findViewById(R.id.toolbar_exercise);
        recyclerView = findViewById(R.id.recyclerview_list_small_exercise);
        adapter = new SmallExerciseAdapter(this, smallExercisesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    private void settingUpListeners() {
        imgGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}