package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthtrack.Controller.UserController;
import com.example.healthtrack.Models.AmountWater;
import com.example.healthtrack.Models.Record;
import com.example.healthtrack.Models.User;
import com.example.healthtrack.R;

import java.util.List;

public class AmountDrinkingAdapter extends RecyclerView.Adapter<AmountDrinkingAdapter.MyViewHolder> {
    Activity context;
    List<AmountWater> amountWaters;

    public AmountDrinkingAdapter(Activity context, List<AmountWater> amountWaters) {
        this.context = context;
        this.amountWaters = amountWaters;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_water, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        AmountWater amountWater = amountWaters.get(position);
        holder.time.setText(String.valueOf(amountWater.getTime()));

    }

    @Override
    public int getItemCount() {
        return amountWaters.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time, amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.single_water_time);
            amount = itemView.findViewById(R.id.single_water_amount);

        }
    }
}
