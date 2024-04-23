package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Models.SmallExercises;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Activity.PreviewExerciseActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SmallExerciseAdapter extends RecyclerView.Adapter<SmallExerciseAdapter.MyViewHolder> {
    Activity context;
    List<SmallExercises> smallExercises;

    public SmallExerciseAdapter(Activity context, List<SmallExercises> smallExercises) {
        this.context = context;
        this.smallExercises = smallExercises;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_exercise2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SmallExercises smallExercises1 = smallExercises.get(position);
        Glide.with(context).load(smallExercises1.getExercisePicture()).into(holder.image);
        holder.title.setText(smallExercises1.getTitleSmall());
        holder.time.setText(smallExercises1.getTimeSmall());

        holder.smallExerciseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PreviewExerciseActivity.class);
                intent.putExtra("SmallExercise", smallExercises1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return smallExercises.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView title;
        private TextView time;
        private MaterialCardView smallExerciseLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_list);
            title = itemView.findViewById(R.id.title_list);
            image = itemView.findViewById(R.id.image_list);
            smallExerciseLayout = itemView.findViewById(R.id.small_exercise_layout);
        }
    }
}
