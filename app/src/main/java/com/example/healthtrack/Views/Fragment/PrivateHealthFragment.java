package com.example.healthtrack.Views.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Adapters.PrivateHealthAdapter;

import java.util.ArrayList;

public class PrivateHealthFragment extends Fragment implements PrivateHealthAdapter.OnItemClickListener{
    RecyclerView healthRecyclerview;
    PrivateHealthAdapter adapter;
    ArrayList<Integer> health;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_private_health, container, false);
        init(view);
        return view;
    }

    void init(View view) {
        healthRecyclerview = view.findViewById(R.id.private_health_recyclerview);
        health = new ArrayList<>();
        health.add(1);
        health.add(2);
        health.add(3);
        health.add(4);
        adapter = new PrivateHealthAdapter((Activity) getContext(), health, this);
        healthRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        healthRecyclerview.setAdapter(adapter);
        healthRecyclerview.setNestedScrollingEnabled(true);

    }

    @Override
    public void onItemClick0() {
        // Thay đổi Fragment khi sự kiện click xảy ra
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_health_frame, new Water());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onItemClick1() {
        // Thay đổi Fragment khi sự kiện click xảy ra
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_health_frame, new MenstrualCycle());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onItemClick2() {
        // Thay đổi Fragment khi sự kiện click xảy ra
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_health_frame, new HeartRate());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onItemClick3() {
        // Thay đổi Fragment khi sự kiện click xảy ra
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_health_frame, new BodyComposition());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
