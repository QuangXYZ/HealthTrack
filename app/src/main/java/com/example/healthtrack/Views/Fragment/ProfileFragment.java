package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.Models.Friends;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    RecyclerView listFriendRecyclerview;

    FriendsAdapter adapter;
    ArrayList<Friends> friends;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        innit(view);
        return view;
    }

    private void innit(View view){
        listFriendRecyclerview = view.findViewById(R.id.listFriends);
        List<Friends> friends = new ArrayList<Friends>();
        friends.add(new Friends("Như Y", R.drawable.avatar));
        friends.add(new Friends("Quang", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        adapter = new FriendsAdapter((Activity) getContext(),friends);
        listFriendRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        listFriendRecyclerview.setAdapter(adapter);
        listFriendRecyclerview.setNestedScrollingEnabled(true);
    }
}