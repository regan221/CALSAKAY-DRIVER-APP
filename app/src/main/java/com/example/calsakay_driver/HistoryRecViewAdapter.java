package com.example.calsakay_driver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HistoryRecViewAdapter extends RecyclerView.Adapter<HistoryRecViewAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private List<String> data;
    private Context context;
    private ArrayList<HistoryModel> historyModels;
    private List<Histories> histories;



    HistoryRecViewAdapter(Context context, List<Histories> histories){
        this.layoutInflater = LayoutInflater.from(context);
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryRecViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvDate.setText("Date: " + histories.get(position).getRide_time_start().toString());
        holder.tvPickup.setText("Pickup point: " + histories.get(position).getPickup().toString());
        holder.tvDrop.setText("Destination: " + histories.get(position).getDrop());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HIstoryView.class);
                intent.putExtra("info", (Serializable) histories.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.histories.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        private TextView tvDate, tvPickup, tvDrop, tvView;
        private MaterialCardView rootView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvPickup = itemView.findViewById(R.id.tv_pickup);
            tvDrop = itemView.findViewById(R.id.tv_drop);
            tvView = itemView.findViewById(R.id.tv_view);

            rootView = itemView.findViewById(R.id.rlHistoryRoot);
        }
    }
}
