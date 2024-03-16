package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.PrivateChallengeDetail;
import com.example.healthtrack.Views.PublicChallengeDetailActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PublicJoinChallengeAdapter extends RecyclerView.Adapter<PublicJoinChallengeAdapter.MyViewHolder> {
    Activity context;
    List<Integer> challenges;

    public PublicJoinChallengeAdapter(Activity context, List<Integer> challenges) {
        this.context = context;
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_public_join_challenge,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.challengeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PublicChallengeDetailActivity.class);
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView challengeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            challengeLayout = itemView.findViewById(R.id.single_public_challenge_layout);
        }
    }
}
