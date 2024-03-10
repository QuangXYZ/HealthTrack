package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.PrivateChallengeAdapter;
import com.example.healthtrack.Views.CreateChallengeActivity;
import com.example.healthtrack.Views.PrivateChallengeDetail;

import java.util.ArrayList;

public class PrivateChallengeFragment extends Fragment {
    RecyclerView challengeRecyclerview;
    PrivateChallengeAdapter adapter;
    ArrayList<Integer> challenges;
    Button createChallengeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_private_challenge, container, false);
        init(view);
        settingUpListener();
        return view;
    }

    void init(View view) {
        challengeRecyclerview = view.findViewById(R.id.private_challenge_recyclerview);
        createChallengeBtn = view.findViewById(R.id.fragment_challenge_create_button);
        challenges = new ArrayList<>();
        challenges.add(1);
        challenges.add(2);
        challenges.add(3);
        challenges.add(4);
        adapter = new PrivateChallengeAdapter((Activity) getContext(), challenges);
        challengeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        challengeRecyclerview.setAdapter(adapter);
        challengeRecyclerview.setNestedScrollingEnabled(true);

    }
    void settingUpListener() {
        createChallengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Intent intent = new Intent(context, CreateChallengeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }

}