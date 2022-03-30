package com.example.fitgain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WorkoutActivity extends AppCompatActivity {

private  Button gainMuscle, loseWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        gainMuscle = findViewById(R.id.Workout_btnGainMuscle);
        loseWeight = findViewById(R.id.Workout_btnLoseWeight);

        //BUTTON LISTENER
        gainMuscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkoutActivity.this,GainMuscleVideo.class));
            }
        });

        loseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkoutActivity.this,LoseWeightVideo.class));
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(WorkoutActivity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(WorkoutActivity.this, WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(WorkoutActivity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(WorkoutActivity.this,LogActivity.class));
                    break;

            }
            return true;
        });


    }
}