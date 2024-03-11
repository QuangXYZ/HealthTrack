package com.example.healthtrack.Views.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.R;

public class FriendViewHolder extends RecyclerView.ViewHolder {
    ImageView friendAva;
    TextView name;

    public FriendViewHolder(@NonNull View itemView) {
        super(itemView);
        friendAva = itemView.findViewById(R.id.friendAva);
        name = itemView.findViewById(R.id.name);


    }
}
