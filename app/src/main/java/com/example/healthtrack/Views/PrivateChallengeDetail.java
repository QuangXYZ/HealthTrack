package com.example.healthtrack.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.PrivateChallengeAdapter;
import com.example.healthtrack.Views.Adapters.RankingAdapter;

import java.util.ArrayList;

public class PrivateChallengeDetail extends AppCompatActivity {

    RecyclerView rankingRecyclerview;
    RankingAdapter  adapter;
    ArrayList<Integer> rankings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_challenge_detail);
        init();
    }
    void init(){
        rankingRecyclerview = findViewById(R.id.challenge_detail_recylerview);
        rankings = new ArrayList<>();
        rankings.add(1);
        rankings.add(2);
        rankings.add(3);
        rankings.add(4);
        adapter = new RankingAdapter(this, rankings);
        rankingRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        rankingRecyclerview.setAdapter(adapter);
        rankingRecyclerview.setNestedScrollingEnabled(true);
    }
}