package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.R;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {
    Activity context;
    List<Integer> rankings;

    public RankingAdapter(Activity context, List<Integer> rankings) {
        this.context = context;
        this.rankings = rankings;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_user_ranking,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (position<=2) {
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
        }
        else {
            holder.rankingNumber.setVisibility(View.VISIBLE);
            holder.rankingNumber.setText(String.valueOf(position+1));
            holder.rankingBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return rankings.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView rankingNumber;
        ImageView rankingBadge;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rankingNumber = itemView.findViewById(R.id.single_ranking_number);
            rankingBadge = itemView.findViewById(R.id.single_ranking_badge);
        }
    }
}
