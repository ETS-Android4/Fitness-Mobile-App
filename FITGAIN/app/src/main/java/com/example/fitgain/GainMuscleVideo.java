package com.example.fitgain;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GainMuscleVideo extends AppCompatActivity {

    RecyclerView recyclerView;
    int [] calories = {200, 100, 150, 160, 220, 260, 140};
    String[] title, description;
    int[] images = {R.drawable.workout_chest,R.drawable.workout_legs,R.drawable.workout_abs,
            R.drawable.workout_standing_abs,R.drawable.workout_fullbodystretch,
            R.drawable.workout_fullbodystretch2,R.drawable.workout_fullbodystretch3,};
    long[] videos = {R.raw.chest,R.raw.workoutleg,R.raw.sixpacksuicide,R.raw.standingabs,
    R.raw.fullbodystrech1,R.raw.fullbodystrech2,R.raw.fullbodystrech3};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gain_muscle_video);

        recyclerView = findViewById(R.id.recyclerView);

        title = getResources().getStringArray(R.array.gain_muscle_video);
        description = getResources().getStringArray(R.array.gain_muscle_description);
        //videos = getResources().getStringArray(R.array.workout_videos_path);

        WorkoutAdapter workoutAdapter = new WorkoutAdapter(this,title,description,images,videos,calories);
        recyclerView.setAdapter(workoutAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        //bottomNavigationView.getMenu().getItem(1).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(GainMuscleVideo.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(GainMuscleVideo.this, WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(GainMuscleVideo.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(GainMuscleVideo.this,LogActivity.class));
                    break;

            }
            return true;
        });
    }


}