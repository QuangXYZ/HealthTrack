package com.example.healthtrack.Views.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.Models.Badges;
import com.example.healthtrack.Models.Friends;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.BadgesAdapter;
import com.example.healthtrack.Views.Adapters.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    RecyclerView listFriendRecyclerview;
    RecyclerView listBadgeRecyclerview;

    FriendsAdapter adapter;
    BadgesAdapter adapter_1;
    ArrayList<Friends> friends;
    ArrayList<Badges> badges;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        innit(view);
        return view;
    }

    private void innit(View view) {
        listFriendRecyclerview = view.findViewById(R.id.listFriends);
        List<Friends> friends = new ArrayList<>();
        friends.add(new Friends("Như Y", R.drawable.avatar));
        friends.add(new Friends("Quang", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Toàn", R.drawable.avatar));
        friends.add(new Friends("1", R.drawable.avatar));
        friends.add(new Friends("2", R.drawable.avatar));
        friends.add(new Friends("3", R.drawable.avatar));
        friends.add(new Friends("4", R.drawable.avatar));
        adapter = new FriendsAdapter(getContext(), friends);
        listFriendRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listFriendRecyclerview.setAdapter(adapter);
        listFriendRecyclerview.setNestedScrollingEnabled(true);

        listBadgeRecyclerview = view.findViewById(R.id.listBadges);
        List<Badges> badges = new ArrayList<>();
        badges.add(new Badges("Badge 1", R.drawable.avatar));
        badges.add(new Badges("Badge 2", R.drawable.avatar));
        badges.add(new Badges("Badge 3", R.drawable.avatar));
        badges.add(new Badges("Badge 4", R.drawable.avatar));
        badges.add(new Badges("Badge 5", R.drawable.avatar));
        badges.add(new Badges("Badge 6", R.drawable.avatar));
        adapter_1 = new BadgesAdapter(getContext(), badges);
        listBadgeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        listBadgeRecyclerview.setAdapter(adapter_1);
        listBadgeRecyclerview.setNestedScrollingEnabled(true);
    }
}