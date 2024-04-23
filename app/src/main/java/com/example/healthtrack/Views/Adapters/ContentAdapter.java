package com.example.healthtrack.Views.Adapters;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.R;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.MyViewHolder> {
    Activity context;
    List<String> listItem;

    public ContentAdapter(Activity context, List<String> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String item = listItem.get(position);
        holder.textView.setText(String.valueOf(item));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_content);
        }
    }
}
