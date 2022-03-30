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

public class WorkoutLogActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private WorkoutLogRecyclerAdapter recyclerAdapter;
    private Log_Workout_Database_Helper db;
    private List<WorkoutCalories> workoutCaloriesList;

    double totalCal=12001;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);

        db = new Log_Workout_Database_Helper(this);
        workoutCaloriesList=db.getAllRecords();
        initView();
        float total=0;
        DecimalFormat currency = new DecimalFormat("###,###");
        for (int i=0;i<=workoutCaloriesList.size()-1;i++){
            total=total+ Float.parseFloat(workoutCaloriesList.get(i).getCalories());
        }

        result=findViewById(R.id.result);
        String res=String.valueOf(currency.format(total));
        res = "Total: "+res;
        result.setText(res);

        setListener();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(WorkoutLogActivity.this,Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(WorkoutLogActivity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(WorkoutLogActivity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(WorkoutLogActivity.this,LogActivity.class));
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
        recyclerView = findViewById(R.id.rv_successive_dynasties_huoying);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerAdapter = new WorkoutLogRecyclerAdapter(workoutCaloriesList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void setListener()
    {

    }
}