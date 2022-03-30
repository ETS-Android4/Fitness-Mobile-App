package com.example.fitgain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Log_Meal_Database_Helper extends SQLiteOpenHelper {

    private static final String TAG = Log_Meal_Database_Helper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Fitgain.db";

    private static final String MEAL_LOG_KEY_ID="_id";
    private static final String MEAL_LOG_KEY_NAME="name";
    private static final String MEAL_LOG_KEY_TYPE="type";
    private static final String MEAL_LOG_KEY_CALORIES="calories";
    private static final String MEAL_LOG_TABLE="meal_log";
    public static final String  MEAL_LOG_TABLE_CREATE =
            "CREATE TABLE " + MEAL_LOG_TABLE + " (" +
                    MEAL_LOG_KEY_ID + " Integer PRIMARY KEY, " +
                    MEAL_LOG_KEY_NAME + " TEXT, " +
                    MEAL_LOG_KEY_TYPE+ " TEXT, "+
                    MEAL_LOG_KEY_CALORIES + " NUMERIC"+")";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public Log_Meal_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a container for the data.
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Log_Meal_Database_Helper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MEAL_LOG_TABLE);
        onCreate(db);
    }

    // code to get all students in a list view
public List<MealLogCalories> getAllRecords() {
        List<MealLogCalories> mealLogCaloriesList = new ArrayList<MealLogCalories>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + MEAL_LOG_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                MealLogCalories mealLogCalories = new MealLogCalories();
                mealLogCalories.setNumber(cursor.getString(0));
                mealLogCalories.setName(cursor.getString(1));
                mealLogCalories.setType(cursor.getString(2));
                mealLogCalories.setCalories(cursor.getDouble(3));

                // Adding student to list
                mealLogCaloriesList.add(mealLogCalories);
            } while (cursor.moveToNext());
        }

        // return student list
        return mealLogCaloriesList;
    }
}