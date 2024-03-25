package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.Controller.ChallengeController;
import com.example.healthtrack.Models.Challenge;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.PrivateChallengeAdapter;
import com.example.healthtrack.Views.Adapters.PublicJoinChallengeAdapter;
import com.example.healthtrack.Views.Adapters.PublicNotJoinChallengeAdapter;

import java.util.ArrayList;
import java.util.List;

public class PublicChallengeFragment extends Fragment {

    RecyclerView challengeJoinRecyclerview, challengeNotJoinRecyclerview;
    PublicJoinChallengeAdapter publicJoinChallengeAdapter;
    PublicNotJoinChallengeAdapter publicNotJoinChallengeAdapter;
    ArrayList<Challenge> joinChallenges;
    ArrayList<Challenge> notJoinChallenges;


    ChallengeController challengeController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_public_challenge, container, false);
        init(view);
        return view;
    }

    void init(View view) {
        challengeJoinRecyclerview = view.findViewById(R.id.public_join_challenge_recyclerview);
        challengeNotJoinRecyclerview = view.findViewById(R.id.public_not_join_challenge_recyclerview);

        notJoinChallenges = new ArrayList<>();
        joinChallenges = new ArrayList<>();


        publicJoinChallengeAdapter = new PublicJoinChallengeAdapter((Activity) getContext(), joinChallenges);
        challengeJoinRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        challengeJoinRecyclerview.setAdapter(publicJoinChallengeAdapter);
        challengeJoinRecyclerview.setNestedScrollingEnabled(true);

        publicNotJoinChallengeAdapter = new PublicNotJoinChallengeAdapter((Activity) getContext(), notJoinChallenges);
        challengeNotJoinRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        challengeNotJoinRecyclerview.setAdapter(publicNotJoinChallengeAdapter);
        challengeNotJoinRecyclerview.setNestedScrollingEnabled(true);
        challengeController = new ChallengeController();
        challengeController.getPublicChallenge(new ChallengeController.PublicChallengeCallback() {
            @Override
            public void onJoinChallenge(List<Challenge> challenges) {
                joinChallenges.addAll(challenges);
                publicJoinChallengeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNotJoinChallenge(List<Challenge> challenges) {
                notJoinChallenges.addAll(challenges);
                publicNotJoinChallengeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String error) {

            }
        });

    }
}