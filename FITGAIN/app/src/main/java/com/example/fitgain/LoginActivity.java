package com.example.fitgain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class LoginActivity extends AppCompatActivity {

    public UserOpenHelper userDB;
    private UserModel user;

    SQLiteDatabase db;
    final String TAG = "DB";

    private static final String WORKOUT_LOG_KEY_ID="_id";
    private static final String WORKOUT_LOG_KEY_NAME="name";
    private static final String WORKOUT_LOG_KEY_CALORIES="calories";
    private static final String WORKOUT_LOG_TABLE="workout_log";
    public static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + WORKOUT_LOG_TABLE + " (" +
                    WORKOUT_LOG_KEY_ID + " Integer PRIMARY KEY, " +
                    WORKOUT_LOG_KEY_NAME + " TEXT, " +
                    WORKOUT_LOG_KEY_CALORIES + " TEXT"+")";

    private static final String MEAL_LOG_KEY_ID="_id";
    private static final String MEAL_LOG_KEY_NAME="name";
    private static final String MEAL_LOG_KEY_TYPE="type";
    private static final String MEAL_LOG_KEY_CALORIES="calories";
    private static final String MEAL_LOG_TABLE="meal_log";
    public static final String  MEAL_LOG_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS  " + MEAL_LOG_TABLE + " (" +
                    MEAL_LOG_KEY_ID + " Integer PRIMARY KEY, " +
                    MEAL_LOG_KEY_NAME + " TEXT, " +
                    MEAL_LOG_KEY_TYPE+ " TEXT, "+
                    MEAL_LOG_KEY_CALORIES + " NUMERIC"+")";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDB = new UserOpenHelper(this);
        user = new UserModel();

        EditText username = (EditText) findViewById(R.id.login_txtUserName);
        EditText password = (EditText) findViewById(R.id.login_txtPassword);
        Button loginButton = (Button) findViewById(R.id.login_btnLogin);
        TextView registerText = (TextView) findViewById(R.id.login_txtRegister);

        createDB();
        createFoodTable();

        db.execSQL(WORD_LIST_TABLE_CREATE);
        db.execSQL(MEAL_LOG_TABLE_CREATE);

        // get some share space in the device
        final SharedPreferences sharePref = PreferenceManager.getDefaultSharedPreferences(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = userDB.doLogin(username.getText().toString(), password.getText().toString());
                if (userId > -1){
                    user = userDB.getUser(userId);

                    // to save the value
                    // 1st get the editor
                    SharedPreferences.Editor editor = sharePref.edit();
                    // 2nd put data into editor
                    editor.putInt("id", user.getId());
                    // flush all data from memory to sharepreference space
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Your either username or password is invalid",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //create Food Table and FoodIngredient Table
    private void createFoodTable()
    {
        try{
            String setPRAGMAForeignKeysOn = "PRAGMA foreign_keys=ON;"; //create foreign key

            String dropFoodIngredientTableCmd = "DROP TABLE IF EXISTS FoodIngredient;";
            String dropFoodTableCmd = "DROP TABLE IF EXISTS Food;";

            String createFoodTableCmd = "CREATE TABLE Food(foodID INTEGER PRIMARY KEY, foodName TEXT, cal REAL, image TEXT, type TEXT);";
            String createFoodIngredientTableCmd = "CREATE TABLE FoodIngredient(ingredientCode INTEGER PRIMARY KEY, ingredient TEXT, amount TEXT, cal REAL, category TEXT, foodID REAL, FOREIGN KEY (foodID) REFERENCES Food(foodID));";

            db.execSQL(setPRAGMAForeignKeysOn);

            db.execSQL(dropFoodIngredientTableCmd);
            db.execSQL(dropFoodTableCmd);

            db.execSQL(createFoodTableCmd);
            db.execSQL(createFoodIngredientTableCmd);

            Log.d(TAG, "Food Table Creation");
            //Toast.makeText(LoginActivity.this, "Food Table Creation",Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e(TAG,"createTable error");
        }

    }

    private void createDB() {

        try {
            db = openOrCreateDatabase("Fitgain.db", MODE_PRIVATE,null); //makig db

            Log.d(TAG, "Database Creation: Login");
            //Toast.makeText(LoginActivity.this, "Database Creation",Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e(TAG,"createDB error");
        }

    } // finish the createDB()

}