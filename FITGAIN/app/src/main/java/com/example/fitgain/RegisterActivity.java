package com.example.fitgain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private UserOpenHelper userDB;
    private UserModel user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userDB = new UserOpenHelper(this);

        EditText username = (EditText) findViewById(R.id.register_txtUserName);
        EditText password = (EditText) findViewById(R.id.register_txtPassword);
        EditText fullname = (EditText) findViewById(R.id.register_txtFullName);
        EditText age = (EditText) findViewById(R.id.register_txtAge);
        EditText height = (EditText) findViewById(R.id.register_txtHeight);
        EditText weight = (EditText) findViewById(R.id.register_txtWeight);
        Button registerButton = (Button) findViewById(R.id.register_btnSubmit);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new UserModel();
                boolean success = true;

                if (username.getText().toString().length() == 0){
                    Toast.makeText(RegisterActivity.this, "Please enter UserName",
                            Toast.LENGTH_LONG).show();
                    success = false;
                } else if (password.getText().toString().length() == 0){
                    Toast.makeText(RegisterActivity.this, "Please enter Password",
                            Toast.LENGTH_LONG).show();
                    success = false;
                } else if (fullname.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter your full name",
                            Toast.LENGTH_LONG).show();
                    success = false;
                } else if (weight.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter weight",
                            Toast.LENGTH_LONG).show();
                    success = false;
                } else if (height.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter your height",
                            Toast.LENGTH_LONG).show();
                    success = false;
                } else if (age.getText().toString().length() == 0) {
                    Toast.makeText(RegisterActivity.this, "Please enter your age",
                            Toast.LENGTH_LONG).show();
                    success = false;
                }

                if (success){
                    user.setUserName(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.setName(fullname.getText().toString());
                    user.setAge(Integer.parseInt(age.getText().toString()));
                    user.setHeight(Float.parseFloat(height.getText().toString()));
                    user.setWeight(Float.parseFloat(weight.getText().toString()));
                    userDB.doRegister(user);
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}