package com.example.fitgain;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class UserOpenHelper extends SQLiteOpenHelper {

    // declare variable
    private static final String TAG = UserOpenHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String USER_TABLE = "users";
    private static final String DATABASE_NAME = "Fitgain.db";

    // column in database table
    private static final String USER_ID = "UserID";
    private static final String USER_NAME = "UserName";
    private static final String USER_PASSWORD = "Password";
    private static final String USER_FULL_NAME = "FullName";
    private static final String USER_AGE = "Age";
    private static final String USER_HEIGHT = "Height";
    private static final String USER_WEIGHT = "Weight";

    // array contain column
    private static final String[] COLUMNS = {USER_ID,
            USER_NAME,
            USER_PASSWORD,
            USER_FULL_NAME,
            USER_AGE,
            USER_HEIGHT,
            USER_WEIGHT};

    // query to create table
    private static final String USER_TABLE_CREATE = "CREATE TABLE " +
            USER_TABLE + " (" +
            USER_ID + " INTEGER PRIMARY KEY, " +
            USER_NAME + " TEXT NOT NULL, " +
            USER_PASSWORD + " TEXT NOT NULL, " +
            USER_FULL_NAME + " TEXT, " +
            USER_AGE + " REAL, " +
            USER_HEIGHT + " REAL, " +
            USER_WEIGHT + " REAL);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    public UserOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);

        ContentValues values = new ContentValues();
        values.put(USER_NAME, "dattu");
        values.put(USER_PASSWORD, "dattu");
        values.put(USER_FULL_NAME, "Dat Tu");
        values.put(USER_AGE, 26);
        values.put(USER_HEIGHT, 1);
        values.put(USER_WEIGHT, 80);

        db.insert(USER_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(com.example.fitgain.UserModel.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);
    }

    public void doRegister(com.example.fitgain.UserModel user) {
        long newId = 0;

        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getUserName());
        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FULL_NAME, user.getName());
        values.put(USER_AGE, user.getAge());
        values.put(USER_HEIGHT, user.getHeight());
        values.put(USER_WEIGHT, user.getWeight());

        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(USER_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
    }

    @SuppressLint("Range")
    public com.example.fitgain.UserModel getUser(int userId) {
        com.example.fitgain.UserModel user = new com.example.fitgain.UserModel();

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            // get count the number of record in db
            Cursor cursor = mReadableDB.rawQuery("Select * from " + USER_TABLE + " where " + USER_ID + "=?",
                    new String[]{String.valueOf(userId)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                user.setId(cursor.getInt(cursor.getColumnIndex(USER_ID)));
                user.setName(cursor.getString(cursor.getColumnIndex(USER_FULL_NAME)));
                user.setAge(cursor.getInt(cursor.getColumnIndex(USER_AGE)));
                user.setHeight(cursor.getInt(cursor.getColumnIndex(USER_HEIGHT)));
                user.setWeight(cursor.getInt(cursor.getColumnIndex(USER_WEIGHT)));
            }
        } catch (Exception e){
            Log.d(TAG, "EXCEPTION! " + e);
        }
        return user;
    }

    public boolean doUpdate(com.example.fitgain.UserModel user) {
        long result = 0;

        ContentValues values = new ContentValues();
//        values.put(USER_NAME, user.getUserName());
//        values.put(USER_PASSWORD, user.getPassword());
        values.put(USER_FULL_NAME, user.getName());
        values.put(USER_AGE, user.getAge());
        values.put(USER_HEIGHT, user.getHeight());
        values.put(USER_WEIGHT, user.getWeight());

        if (mWritableDB == null) {
            mWritableDB = getWritableDatabase();
        }

        // get count the number of record in db
        Cursor cursor = mWritableDB.rawQuery("Select * from " + USER_TABLE + " where " + USER_ID + "=?",
                new String[]{String.valueOf(user.getId())});

        /*
        checking if there is data record in database,
        if there is at least 1 record in db, do the update
        else return false
         */
        if (cursor.getCount() > 0) {
            result = mWritableDB.update(USER_TABLE,
                    values,
                    USER_ID + "=?",
                    new String[]{String.valueOf(user.getId())});

            /*
            checking if update successfully or not
            if -1 then it is not updated
            if > -1 then it is updated
             */
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public int doLogin(String username, String password) {
        int id = -1;

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }

            // get count the number of record in db
            Cursor cursor = mReadableDB.rawQuery("Select * from " + USER_TABLE + " where " + USER_NAME + "=? and " + USER_PASSWORD + "=?",
                    new String[]{username, password});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getInt(cursor.getColumnIndex(USER_ID));
            }
        } catch (Exception e){
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            return id;
        }
    }
}
