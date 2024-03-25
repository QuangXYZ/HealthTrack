package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dd.CircularProgressButton;
import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.R;

import java.util.List;

public class PublicNotJoinChallengeAdapter extends RecyclerView.Adapter<PublicNotJoinChallengeAdapter.MyViewHolder> {
    Activity context;
    List<Challenge> challenges;
    ChallengeController challengeController = new ChallengeController();

    public PublicNotJoinChallengeAdapter(Activity context, List<Challenge> challenges) {
        this.context = context;
        this.challenges = challenges;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_public_not_join_challenge,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Challenge challenge = challenges.get(position);
        holder.name.setText(challenges.get(position).getName());
        holder.date.setText("Ngày kết thúc: "+challenge.getDateEnd().split("T")[0]);
        holder.member.setText("Số người tham gia: "+challenge.getListMember().size());
        holder.target.setText("Mục tiêu: "+challenge.getTarget());

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.button.setIndeterminateProgressMode(true); // turn on indeterminate progress
                holder.button.setProgress(50);
                challengeController.joinChallenge(challenge.get_id(), new ChallengeController.ChallengeControllerCallback() {
                    @Override
                    public void onSuccess(String message) {
                        holder.button.setProgress(100);
                    }

                    @Override
                    public void onError(String error) {
                        holder.button.setProgress(-1);
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return challenges.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date, member, target;
        CircularProgressButton button;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.public_not_join_challenge_name);
            date = itemView.findViewById(R.id.public_not_join_challenge_date);
            member = itemView.findViewById(R.id.public_not_join_challenge_member);
            target = itemView.findViewById(R.id.public_not_join_challenge_description);
            button = itemView.findViewById(R.id.public_not_join_challenge_button);

        }
    }
}
