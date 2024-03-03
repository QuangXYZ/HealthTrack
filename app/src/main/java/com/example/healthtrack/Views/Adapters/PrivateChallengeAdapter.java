package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.R;

import java.util.List;

public class PrivateChallengeAdapter extends RecyclerView.Adapter<PrivateChallengeAdapter.MyViewHolder> {
    Activity context;
    List<Integer> challenges;

    public PrivateChallengeAdapter(Activity context, List<Integer> challenges) {
        this.context = context;
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_private_challenge,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
