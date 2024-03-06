package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.ExerciseAdapter;
import com.example.healthtrack.Views.Adapters.PrivateChallengeAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    RecyclerView exerciseRecyclerview;
    ExerciseAdapter adapter;

    ArrayList<Integer> exercise;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        innit(view);
        return view;
    }

    private void innit(View view) {
        exerciseRecyclerview = view.findViewById(R.id.exercise_recyclerview);
        exercise = new ArrayList<>();
        exercise.add(1);
        exercise.add(2);
        exercise.add(3);
        exercise.add(4);
        adapter = new ExerciseAdapter((Activity) getContext(), exercise);
        exerciseRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        exerciseRecyclerview.setAdapter(adapter);
        exerciseRecyclerview.setNestedScrollingEnabled(true);
    }
}