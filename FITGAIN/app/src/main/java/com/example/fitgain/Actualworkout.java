package com.example.fitgain;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Actualworkout extends AppCompatActivity {

    SQLiteDatabase db;
    final String TAG = "Database";
    private VideoView videoView;
    private Button play,save,pause;
    MediaController mediaController;
    TextView videoTitle;
    String title;
    String videoPath;
    int calories;

    private static final String WORKOUT_LOG_KEY_NAME="name";
    private static final String WORKOUT_LOG_KEY_CALORIES="calories";
    private static final String WORKOUT_LOG_TABLE="workout_log";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualworkout);

        videoTitle = findViewById(R.id.Workout_txtVideoTitle);
        videoView = new VideoView(this);
        videoView = findViewById(R.id.Workout_VideoPlayer);

        mediaController = new MediaController(this);
        play = findViewById(R.id.Workout_btnPlay);
        pause = findViewById(R.id.Workout_btnPause);
        save = findViewById(R.id.Workout_btnDone);


        play.setOnClickListener(new mClick());
        pause.setOnClickListener(new mClick());
        save.setOnClickListener(new mClick());

        getdata();
        setdata();
        createDB();
        //addWorkoutRecord(title,calories);

        videoView.setMediaController(mediaController);
        String uriPath ="android.resource://" + getPackageName() + "/" + videoPath;
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
        //videoView.setVideoURI(Uri.parse(videoPath));
        mediaController.setMediaPlayer(videoView);



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(Actualworkout.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(Actualworkout.this, WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(Actualworkout.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(Actualworkout.this,LogActivity.class));
                    break;

            }
            return true;
        });

    }



    class mClick implements View.OnClickListener {
        @Override
        public void onClick(View v){

            if (v == play){
                videoView.start();
            }else if (v== pause) {
                videoView.pause();
            }else if (v == save){
                videoView.stopPlayback();
                //send data to workout log
                addWorkoutRecord(title,calories);
                Toast.makeText(Actualworkout.this, "Data save to log", Toast.LENGTH_SHORT).show();
                save.setVisibility(View.GONE);
            }
        }
    }

    private void addWorkoutRecord(String name, int cal ){
        ContentValues values = new ContentValues();

        values.put(WORKOUT_LOG_KEY_NAME, name);
        values.put(WORKOUT_LOG_KEY_CALORIES, cal);
        db.insert(WORKOUT_LOG_TABLE, null, values);

    }

    private void getdata(){
        title = getIntent().getStringExtra("title");
        videoPath = getIntent().getStringExtra("videos");
        calories = getIntent().getIntExtra("calories",0);

    }

    private void setdata(){
        videoTitle.setText(title);

    }

    private void createDB() {
        try {
            db = openOrCreateDatabase("Fitgain.db", MODE_PRIVATE,null);
            //Toast.makeText(Actualworkout.this, "Database Creation", Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e(TAG,"createDB error");
        }
    }
}