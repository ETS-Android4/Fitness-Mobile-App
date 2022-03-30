package com.example.fitgain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Meal_MenuAdapter extends BaseAdapter {

    Context context;
    List<Food> mealPlanList;

    public Meal_MenuAdapter(Context context, List<Food> mealPlanList) {
        this.context = context;
        this.mealPlanList = mealPlanList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Food> getMealPlanList() {
        return mealPlanList;
    }

    public void setMealPlanList(List<Food> mealPlanList) {
        this.mealPlanList = mealPlanList;
    }

    @Override
    public int getCount() {
        return mealPlanList.size();
    }

    @Override
    public Object getItem(int i) {
        return mealPlanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
            view = layoutInflater.inflate(R.layout.meal_menu_layout,viewGroup,false);
        }

        ImageView imageFood1 = view.findViewById(R.id.imageFood1);
        TextView txtMenuName = view.findViewById(R.id.txtMenuName);

        imageFood1.setImageResource(mealPlanList.get(i).getImage());
        txtMenuName.setText(mealPlanList.get(i).getFoodName());

        return view;
    }


}
