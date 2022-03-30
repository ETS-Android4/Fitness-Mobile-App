package com.example.fitgain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    public UserOpenHelper userDB;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userDB = new UserOpenHelper(this);
        user = new UserModel();

        // get some share space in the device
        final SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(this);

        final TextView name = (TextView) findViewById(R.id.profile_txtFullName);
        final TextView age = (TextView) findViewById(R.id.profile_txtAge);
        final TextView height = (TextView) findViewById(R.id.profile_txtHeight);
        final TextView weight = (TextView) findViewById(R.id.profile_txtWeight);

        int userID = sharePref.getInt("id", 0);
        user = userDB.getUser(userID);

        name.setText(user.getName());
        age.setText(user.getAge() + "");
        height.setText(user.getHeight() + " m");
        weight.setText(user.getWeight() + " kg");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(ProfileActivity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(ProfileActivity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(ProfileActivity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(ProfileActivity.this,LogActivity.class));
                    break;

            }
            return true;
        });

        Button updateButton = (Button) findViewById(R.id.profile_btnUpdate);
        TextView logoutText = (TextView) findViewById(R.id.profile_txtLogout);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}