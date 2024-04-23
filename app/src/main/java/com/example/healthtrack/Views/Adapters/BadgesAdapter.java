package com.example.healthtrack.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.Badges;
import com.example.healthtrack.R;

import java.util.List;

public class BadgesAdapter extends RecyclerView.Adapter<BadgesAdapter.BadgeViewHolder> {
    Context context;
    List<Badges> badges;

    public BadgesAdapter(Context context, List<Badges> badges) {
        this.context = context;
        this.badges = badges;
    }

    @NonNull
    @Override
    public BadgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BadgeViewHolder(LayoutInflater.from(context).inflate(R.layout.badge_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeViewHolder holder, int position) {
        holder.badgeName.setText(badges.get(position).getTitle());
        holder.badgeAva.setImageResource(badges.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    public class BadgeViewHolder extends RecyclerView.ViewHolder {
        ImageView badgeAva;
        TextView badgeName;

        public BadgeViewHolder(@NonNull View itemView) {
            super(itemView);
            badgeAva = itemView.findViewById(R.id.badgeAva);
            badgeName = itemView.findViewById(R.id.badgeName);
        }
    }
}
