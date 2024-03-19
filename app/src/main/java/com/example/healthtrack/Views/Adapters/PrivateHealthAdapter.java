package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrack.R;
import com.example.healthtrack.Views.Fragment.HealthFragment;

import java.util.List;

public class PrivateHealthAdapter extends RecyclerView.Adapter<PrivateHealthAdapter.MyViewHolder> {
    Activity context;
    List<Integer> health;
    TextView tv;
    ImageView iv;
    Button button;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick0();
        void onItemClick1();
        void onItemClick2();
        void onItemClick3();


    }
    public PrivateHealthAdapter(OnItemClickListener listener) {
        this.mListener = listener;
    }
    public PrivateHealthAdapter(Activity context, List<Integer> health, OnItemClickListener listener) {
        this.context = context;
        this.health = health;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_private_health,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        tv = holder.itemView.findViewById(R.id.textview);
        iv = holder.itemView.findViewById(R.id.imgview);
        button = holder.itemView.findViewById(R.id.btn);
        if(position==0) {
            tv.setText("0/2000ml");
            iv.setImageResource(R.drawable.glass);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick0(); // Gọi phương thức của Interface khi click xảy ra
                    }
                }
            });
        }
        if(position==1) {
            tv.setText("7 ngày nữa");
            iv.setImageResource(R.drawable.kinh);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick1(); // Gọi phương thức của Interface khi click xảy ra
                    }
                }
            });
        }
        if(position==2) {
            tv.setText("120/80 mmHg");
            iv.setImageResource(R.drawable.huyetap);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick2(); // Gọi phương thức của Interface khi click xảy ra
                    }
                }
            });
        }
        if(position==3) {
            tv.setText("0/1.655 kcal");
            iv.setImageResource(R.drawable.h12);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick3(); // Gọi phương thức của Interface khi click xảy ra
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return health.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
