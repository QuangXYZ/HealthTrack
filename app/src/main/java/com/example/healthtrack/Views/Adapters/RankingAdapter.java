package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Controller.UserController;
import com.example.healthtrack.Models.Record;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;
import com.google.android.play.integrity.internal.c;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    Activity context;
    List<Record> records;
    UserController userController;

    public RankingAdapter(Activity context, List<Record> records) {
        this.context = context;
        this.records = records;
        userController = new UserController();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_user_ranking, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Record record = records.get(position);
        userController.getDetailUser(record.getUserId(), new UserController.GetUserCallback() {
            @Override
            public void onSuccess(User user) {
                if (user.getProfilePicture() != null)
                    Glide.with(context).load(user.getProfilePicture()).into(holder.img);
            }

            @Override
            public void onError(String error) {

            }
        });
        if (position <= 2) {
            holder.rankingNumber.setVisibility(View.GONE);
            if (position == 0) {
                holder.rankingBadge.setImageResource(R.drawable.ranking1);

            }
            if (position == 1) {
                holder.rankingBadge.setImageResource(R.drawable.rankink2);

            }
            if (position == 2) {
                holder.rankingBadge.setImageResource(R.drawable.ranking3);

            }
        } else {
            holder.rankingNumber.setVisibility(View.VISIBLE);
            holder.rankingNumber.setText(String.valueOf(position + 1));
            holder.rankingBadge.setVisibility(View.GONE);
        }
        holder.name.setText(record.getUserName());
        holder.step.setText(String.valueOf(record.getStepTotal()));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rankingNumber;
        ImageView rankingBadge, img;
        TextView name, step;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rankingNumber = itemView.findViewById(R.id.single_ranking_number);
            rankingBadge = itemView.findViewById(R.id.single_ranking_badge);
            name = itemView.findViewById(R.id.single_ranking_name);
            step = itemView.findViewById(R.id.single_ranking_record);
            img = itemView.findViewById(R.id.single_ranking_img);
        }
    }
}
