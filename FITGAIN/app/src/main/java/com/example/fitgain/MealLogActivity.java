package com.example.fitgain;

import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MealLogActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MealLogRecyclerAdapter recyclerAdapter;
    private Log_Meal_Database_Helper db;
    private List<MealLogCalories> mealCaloriesList;
    double totalCal=12001;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_log);

        db = new Log_Meal_Database_Helper(this);
        mealCaloriesList=db.getAllRecords();

        initView();
        double total=0;
        DecimalFormat currency = new DecimalFormat("###,###");
        for (int i=0;i<=mealCaloriesList.size()-1;i++){
            total=total+ mealCaloriesList.get(i).getCalories();
        }

        result=findViewById(R.id.mealLogResult);
        String res="Total: "+ String.valueOf(currency.format(total));
        result.setText(res);

        initView();

        setListener();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(MealLogActivity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(MealLogActivity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(MealLogActivity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(MealLogActivity.this,LogActivity.class));
                    break;

            }
            return true;
        });
}
    private void initData()
    {
//        workoutCaloriesList = new ArrayList<>(7);
//        workoutCaloriesList.add(new WorkoutCalories(1, "Push-ups", "200"));
//        workoutCaloriesList.add(new WorkoutCalories(2, "Squats", "100"));
//        workoutCaloriesList.add(new WorkoutCalories(3, "Lunges", "200"));
    }

    private void initView()
    {
        recyclerView = findViewById(R.id.recyclerMealLog);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new MealLogRecyclerAdapter(mealCaloriesList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setListener()
    {

    }

}