package com.example.healthtrack.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.Friends;
import com.example.healthtrack.R;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    Context context;
    List<Friends> friends;

    public FriendsAdapter(Context context, List<Friends> friends) {
        this.context = context;
        this.friends = friends;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.name.setText(friends.get(position).getName());
        holder.friendAva.setImageResource(friends.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
