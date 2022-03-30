package com.example.fitgain;


import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WorkoutLogRecyclerAdapter extends RecyclerView.Adapter<WorkoutLogRecyclerAdapter.ViewHolder>

{
    private List<WorkoutCalories> workoutCaloriesList;

    public WorkoutLogRecyclerAdapter(List<WorkoutCalories> workoutCaloriesList)
    {
        this.workoutCaloriesList = workoutCaloriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_out_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        DecimalFormat currency = new DecimalFormat("###,###");
        holder.name.setText(workoutCaloriesList.get(position).getName());
        holder.number.setText(String.valueOf(workoutCaloriesList.get(position).getNumber()));
        holder.calories.setText(String.valueOf(currency.format(Integer.parseInt(workoutCaloriesList.get(position).getCalories()))));
    }

    @NonNull

    @Override
    public int getItemCount()
    {
        return workoutCaloriesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView number;
        TextView calories;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.name = itemView.findViewById(R.id.log_name);
            this.number = itemView.findViewById(R.id.log_number);
            this.calories = itemView.findViewById(R.id.log_type);
        }

    }
}