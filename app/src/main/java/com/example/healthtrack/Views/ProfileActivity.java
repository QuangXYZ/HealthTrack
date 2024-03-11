package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.healthtrack.Models.Friends;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.FriendsAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        RecyclerView recyclerView = findViewById(R.id.listFriends);

        List<Friends> friends = new ArrayList<Friends>();
        friends.add(new Friends("Như Y", R.drawable.avatar));
        friends.add(new Friends("Quang", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));
        friends.add(new Friends("Quân", R.drawable.avatar));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FriendsAdapter(getApplicationContext(), friends));
    }
}
