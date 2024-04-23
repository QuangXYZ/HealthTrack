package com.example.healthtrack.Views.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Models.Friends;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendViewHolder> {
    Context context;
    List<User> users;

    public FriendsAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(LayoutInflater.from(context).inflate(R.layout.single_friend, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.name.setText(users.get(position).getName());
        if (users.get(position).getProfilePicture() != null)
            Glide.with(context).load(users.get(position).getProfilePicture()).into(holder.friendAva);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView friendAva;
        TextView name;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            friendAva = itemView.findViewById(R.id.friendAva);
            name = itemView.findViewById(R.id.name);
        }
    }
}
