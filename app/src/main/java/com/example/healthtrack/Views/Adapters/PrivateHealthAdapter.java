package com.example.healthtrack.Views.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        void onItemClick4();


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
            tv.setText("1200/2000 ml");
            tv.setTextColor(Color.parseColor("#6FCEFA"));
            button.setBackgroundColor(Color.parseColor("#6FCEFA"));
            iv.setImageResource(R.drawable.waterbottle);
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
            tv.setText("12,39 %");
            tv.setTextColor(Color.parseColor("#F4CC57"));
            iv.setImageResource(R.drawable.bodyvariant);
            button.setBackgroundColor(Color.parseColor("#F4CC57"));
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
            tv.setText("71 bpm");
            tv.setTextColor(Color.parseColor("#F3673B"));
            iv.setImageResource(R.drawable.pulse);
            button.setBackgroundColor(Color.parseColor("#F3673B"));
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
            tv.setText("135 mmHg");
            tv.setTextColor(Color.parseColor("#D81506"));
            iv.setImageResource(R.drawable.bloodpressure);
            button.setBackgroundColor(Color.parseColor("#D81506"));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick3(); // Gọi phương thức của Interface khi click xảy ra
                    }
                }
            });
        }
        if(position==4) {
            tv.setText("7 ngày nữa");
            iv.setImageResource(R.drawable.menstrualcycle);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick4(); // Gọi phương thức của Interface khi click xảy ra
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
