package com.example.fitgain;

import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealLogRecyclerAdapter extends RecyclerView.Adapter<MealLogRecyclerAdapter.ViewHolder>

{
    private List<MealLogCalories> mealCaloriesList;

    public MealLogRecyclerAdapter(List<MealLogCalories> mealCaloriesList)
    {
        this.mealCaloriesList = mealCaloriesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_log_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        DecimalFormat currency = new DecimalFormat("###,###");
        holder.name.setText(mealCaloriesList.get(position).getName());
        holder.number.setText(String.valueOf(mealCaloriesList.get(position).getNumber()));
        holder.type.setText(mealCaloriesList.get(position).getType());
        holder.calories.setText(String.valueOf(currency.format(mealCaloriesList.get(position).getCalories())));
    }

    @NonNull

    @Override
    public int getItemCount()
    {
        return mealCaloriesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView number;
        TextView type;
        TextView calories;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.name = itemView.findViewById(R.id.log_name);
            this.number = itemView.findViewById(R.id.log_number);
            this.type = itemView.findViewById(R.id.log_type);
            this.calories = itemView.findViewById(R.id.log_calories);
        }

    }
}