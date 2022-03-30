package com.example.fitgain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class UpdateProfileActivity extends AppCompatActivity {

    public UserOpenHelper userDB;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        userDB = new UserOpenHelper(this);
        user = new UserModel();

        // get some share space in the device
        final SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(this);

        EditText name = (EditText) findViewById(R.id.update_profile_txtFullName);
        EditText age = (EditText) findViewById(R.id.update_profile_txtAge);
        EditText height = (EditText) findViewById(R.id.update_profile_txtHeight);
        EditText weight = (EditText) findViewById(R.id.update_profile_txtWeight);
        Button updateButton = (Button) findViewById(R.id.updateProfile_btnUpdate);

        user = userDB.getUser(sharePref.getInt("id", 0));

        name.setText(user.getName());
        age.setText(user.getAge() + "");
        height.setText(user.getHeight() + "");
        weight.setText(user.getWeight() + "");

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user.setName(name.getText().toString());
                user.setAge(Integer.parseInt(age.getText().toString()));
                user.setHeight(Float.parseFloat(height.getText().toString()));
                user.setWeight(Float.parseFloat(weight.getText().toString()));
                userDB.doUpdate(user);

                Intent intent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}