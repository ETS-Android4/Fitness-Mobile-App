package com.example.fitgain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Log_Workout_Database_Helper extends SQLiteOpenHelper {

    private static final String TAG = Log_Workout_Database_Helper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String WORKOUT_LOG_TABLE="workout_log";
    private static final String DATABASE_NAME = "Fitgain.db";

    public static final String WORKOUT_LOG_KEY_ID="_id";
    public static final String WORKOUT_LOG_KEY_NAME="name";
    public static final String WORKOUT_LOG_KEY_CALORIES="calories";

    private static final String[] COLUMNS = {WORKOUT_LOG_KEY_ID, WORKOUT_LOG_KEY_NAME,WORKOUT_LOG_KEY_CALORIES};

    public static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORKOUT_LOG_TABLE + " (" +
                    WORKOUT_LOG_KEY_ID + " INTEGER PRIMARY KEY, " +
                    // id will auto-increment if no value passed
                    WORKOUT_LOG_KEY_NAME + " TEXT, " + WORKOUT_LOG_KEY_CALORIES + " TEXT"+")";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public Log_Workout_Database_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(Log_Workout_Database_Helper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WORKOUT_LOG_TABLE);
        onCreate(db);
    }

    public void addRecord(WorkoutCalories workoutCalories) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(WORKOUT_LOG_KEY_NAME, workoutCalories.getName());
        values.put(WORKOUT_LOG_KEY_CALORIES, workoutCalories.getCalories());

        // Inserting Row
        db.insert(WORKOUT_LOG_TABLE, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single student

    // code to get all students in a list view
    public List<WorkoutCalories> getAllRecords() {
        List<WorkoutCalories> workoutCaloriesList = new ArrayList<WorkoutCalories>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + WORKOUT_LOG_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Log.v("Insert: ", "Inserting activity55..");
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                WorkoutCalories workoutCalories = new WorkoutCalories();
                workoutCalories.setNumber(cursor.getString(0));
                workoutCalories.setName(cursor.getString(1));
                workoutCalories.setCalories(cursor.getString(2));
                Log.v("Insert: ", "Inserting activity55..");
                // Adding student to list
                workoutCaloriesList.add(workoutCalories);
            } while (cursor.moveToNext());
        }
        Log.v("Insert: ", "Inserting activity55..");

        // return student list
        return workoutCaloriesList;
    }
}