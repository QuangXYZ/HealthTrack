package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.Models.HeartRate;
import com.example.healthtrack.R;
import com.example.healthtrack.Views.Fragment.HeartRateFragment;

import java.util.List;

public class HeartRateAdapter extends RecyclerView.Adapter<HeartRateAdapter.MyViewHolder> {
    Activity context;
    List<HeartRate> heartRates;

    public HeartRateAdapter(Activity context, List<HeartRate> heartRates) {
        this.context = context;
        this.heartRates = heartRates;
    }

    @NonNull
    @Override
    public HeartRateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_heart_rate, parent, false);
        return new HeartRateAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HeartRateAdapter.MyViewHolder holder, int position) {

        HeartRate heartRate = heartRates.get(position);
        holder.time.setText(String.valueOf(heartRate.getTime()));
        holder.rate.setText(String.valueOf(heartRate.getRate()));

    }

    @Override
    public int getItemCount() {
        return heartRates.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time, rate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.single_heart_rate_time);
            rate = itemView.findViewById(R.id.single_heart_rate);

        }
    }
}
