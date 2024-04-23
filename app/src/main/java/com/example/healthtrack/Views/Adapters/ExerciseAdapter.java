package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.Exercise;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Activity.ExerciseActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {
    Activity context;
    List<Exercise> exercises;

    public ExerciseAdapter(Activity context, List<Exercise> exercises) {
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_exercise, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.title.setText(exercise.getTitle());
        holder.time.setText(String.valueOf(exercise.getTime() + " Phút"));
        holder.calo.setText(String.valueOf(exercise.getCalo() + " Calo"));

        holder.exerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ExerciseActivity.class);
                intent.putExtra("exercise", exercise);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, time, calo;
        private MaterialCardView exerciseLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            title = itemView.findViewById(R.id.title);
            calo = itemView.findViewById(R.id.calo);
            exerciseLayout = itemView.findViewById(R.id.exercise_layout);
        }
    }
}
