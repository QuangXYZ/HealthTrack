package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dd.CircularProgressButton;
import com.example.healthtrack.Controller.FriendController;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;

import java.util.List;

public class FriendInviteAdapter extends RecyclerView.Adapter<FriendInviteAdapter.MyViewHolder> {
    Activity context;
    List<User> users;
    FriendController friendController;

    public FriendInviteAdapter(Activity context, List<User> users) {
        this.context = context;
        this.users = users;
        friendController = new FriendController();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_friend_invite, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getName());
        holder.decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.decline.setIndeterminateProgressMode(true); // turn on indeterminate progress
                holder.decline.setProgress(50);
                friendController.declineFriendRequest(user.get_id(), new FriendController.FriendCallback() {
                    @Override
                    public void onSuccess(List<User> users) {
                        removeItem(holder.getAdapterPosition());
                        holder.decline.setProgress(100);

                    }

                    @Override
                    public void onError(String error) {
                        holder.decline.setProgress(-1);
                    }
                });

            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.accept.setIndeterminateProgressMode(true); // turn on indeterminate progress
                holder.accept.setProgress(50);
                friendController.acceptFriendRequest(user.get_id(), new FriendController.FriendCallback() {
                    @Override
                    public void onSuccess(List<User> users) {
                        removeItem(holder.getAdapterPosition());
                        holder.accept.setProgress(100);
                    }

                    @Override
                    public void onError(String error) {
                        holder.accept.setProgress(-1);
                    }
                });
            }
        });
    }

    public void removeItem(int position) {
        users.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        CircularProgressButton decline, accept;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.single_friend_invite_name);
            decline = itemView.findViewById(R.id.single_friend_invite_decline);
            accept = itemView.findViewById(R.id.single_friend_invite_accept);
        }
    }
}
