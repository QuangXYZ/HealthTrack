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

import com.bumptech.glide.Glide;
import com.example.healthtrack.Models.Exercise;
import com.example.healthtrack.Models.SmallExercises;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.ContentAdapter;

import java.util.ArrayList;
import java.util.List;

public class PreviewExerciseActivity extends AppCompatActivity {

    private SmallExercises smallExercises;
    private ArrayList<String> listItem;
    private TextView title;
    private ImageView imageView, imgGoBack;
    private RecyclerView recyclerView;
    private ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_exercise);

        init();
        settingUpListeners();
    }

    void init() {
        listItem = new ArrayList<>();
        title = findViewById(R.id.Title_Preview);
        imageView = findViewById(R.id.image_preview);
        imgGoBack = findViewById(R.id.toolbar_preview_exercise);
        recyclerView = findViewById(R.id.recyclerview_item);
        Intent intent = getIntent();
        if (intent != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                smallExercises = intent.getSerializableExtra("SmallExercise", SmallExercises.class);
                listItem.addAll(smallExercises.getListContent());
            } else {
                smallExercises = (SmallExercises) intent.getSerializableExtra("SmallExercise");
                listItem.addAll(smallExercises.getListContent());
                title.setText(smallExercises.getTitleSmall());
                Glide.with(this).load(smallExercises.getExercisePicture()).into(imageView);
                adapter = new ContentAdapter(this, listItem);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
                recyclerView.setAdapter(adapter);
                recyclerView.setNestedScrollingEnabled(true);
            }
        }
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