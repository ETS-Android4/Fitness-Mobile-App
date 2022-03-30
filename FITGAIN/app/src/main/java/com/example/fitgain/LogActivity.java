package com.example.fitgain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(LogActivity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(LogActivity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(LogActivity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(LogActivity.this,LogActivity.class));
                    break;

            }
            return true;
        });
        Button buttonWorkoutLog=(Button) findViewById(R.id.log_btnWorkoutlog);
        buttonWorkoutLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LogActivity.this, WorkoutLogActivity.class)
                );

            }
        });
        Button buttonMealLog=(Button) findViewById(R.id.log_btnMealLog);
        buttonMealLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LogActivity.this, MealLogActivity.class)
                );

            }
        });

    }
}