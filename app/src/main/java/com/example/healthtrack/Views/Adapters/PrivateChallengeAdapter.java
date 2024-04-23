package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.Models.Record;
import com.example.healthtrack.R;
import com.example.healthtrack.Utils.DataLocalManager;
import com.example.healthtrack.Views.Activity.PrivateChallengeDetail;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class PrivateChallengeAdapter extends RecyclerView.Adapter<PrivateChallengeAdapter.MyViewHolder> {
    Activity context;
    List<Challenge> challenges;

    public PrivateChallengeAdapter(Activity context, List<Challenge> challenges) {
        this.context = context;
        this.challenges = challenges;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_private_challenge, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Challenge challenge = challenges.get(position);
        holder.name.setText(challenge.getName());
        holder.users.setText("Số người tham gia: " + challenge.getListMember().size());

        String userId = DataLocalManager.getUser().get_id();
        for (Record record : challenge.getUserRecords()) {
            if (record.getUserId().equals(userId)) {
                holder.step.setText(record.getStepTotal() + "");
                holder.progressBar.setProgress((int) (record.getStepTotal() * 100 / challenge.getTarget()));
            }
        }

        holder.challengeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrivateChallengeDetail.class);
                intent.putExtra("Challenge", challenge);
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
        TextView name, date, target, users, step;
        ProgressBar progressBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            challengeLayout = itemView.findViewById(R.id.single_private_challenge_layout);
            name = itemView.findViewById(R.id.single_private_challenge_name);
            target = itemView.findViewById(R.id.single_private_challenge_target);
            users = itemView.findViewById(R.id.single_private_challenge_user);
            step = itemView.findViewById(R.id.single_private_challenge_step);
            progressBar = itemView.findViewById(R.id.single_private_challenge_progress);
        }
    }
}
