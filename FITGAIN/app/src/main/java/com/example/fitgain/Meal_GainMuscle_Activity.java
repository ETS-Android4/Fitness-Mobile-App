
package com.example.fitgain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Meal_GainMuscle_Activity extends AppCompatActivity {

    SQLiteDatabase db;
    final String TAG = "DB";

    List<Food> mealPlan;

    TextView meal_txtTitle;
    ImageView meal_Image;
    TextView meal_txtCaloriesValue;
    TextView meal_txtDisplay;

    List<FoodIngredient> foodIngredients;
    List<FoodIngredient> defaultIngredients;
    List<FoodIngredient> firstIngredients;
    List<FoodIngredient> secondIngredients;

    TextView meal_txtChoice1;
    TextView meal_txtChoice2;
    TextView meal_txtDisplayOptions;
    Spinner meal_spinnerOption1;
    Spinner meal_spinnerOption2;
    Button meal_btnCalculateCal;
    Button meal_btnSave;

    double meal_CalWithoutOptions;
    double meal_totalCalories;

    double meal_calOption1;
    double meal_calOption2;
    String nameOption1;
    String nameOption2;
    String veganPlan;
    //String noVeganPlan;
    String beef;
    String mayo;
    String milk;
    String proteinP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_gain_muscle);

        meal_txtTitle = (TextView) findViewById(R.id.meal_txtTitle);
        meal_Image = (ImageView) findViewById(R.id.meal_Image);

        meal_txtDisplay = (TextView) findViewById(R.id.meal_txtDisplay);

        meal_txtChoice1 = (TextView) findViewById(R.id.meal_txtChoice1);
        meal_txtChoice2 = (TextView) findViewById(R.id.meal_txtChoice2);
        meal_txtCaloriesValue = (TextView) findViewById(R.id.meal_txtCalResult);
        meal_txtDisplayOptions = (TextView) findViewById(R.id.meal_txtDisplayOptions);
        meal_spinnerOption1 = (Spinner) findViewById(R.id.meal_spinnerOption1);
        meal_spinnerOption2 = (Spinner) findViewById(R.id.meal_spinnerOption2);
        meal_btnCalculateCal = (Button) findViewById(R.id.meal_btnCalulateCal);
        meal_btnSave = (Button) findViewById(R.id.meal_btnSave);


        int foodID = 0;

        try {
            foodID = getIntent().getExtras().getInt("FOODID");

            Log.d(TAG,foodID + " FoodID");

        }catch(Exception e){
            Log.e(TAG,e.getMessage());
        }

        if(foodID == 1)
        {
            milk = getIntent().getExtras().getString("MILK");
            proteinP = getIntent().getExtras().getString("POWDER");

            meal_txtChoice1.setText(milk);
            meal_txtChoice2.setText(proteinP);
        }

        if(foodID == 2) {

            beef = getIntent().getExtras().getString("BEEF");
            mayo = getIntent().getExtras().getString("MAYONNAISE");

            meal_txtChoice1.setText(beef);
            meal_txtChoice2.setText(mayo);
        }

        createDB();

        if(foodID != 0)
        {
            String strFoodID = String.valueOf(foodID);
            getMealPlan(strFoodID);
            //getFoodIngredients(strFoodID);
            getDefaultIngredients(strFoodID);
            getOptionFirstIngredients(strFoodID);
            getOptionSecondIngredients(strFoodID);
        }
        //Log.d(TAG, mealPlan.size() + " mealPlan size");

        //display value from the database
        meal_txtTitle.setText(mealPlan.get(0).getFoodName());
        meal_txtCaloriesValue.setText(mealPlan.get(0).getCal() + " Calories");
        meal_Image.setImageResource(mealPlan.get(0).getImage());

        //display the spinner value
        String[] spinnerItems1 = new String[firstIngredients.size()];
        String[] spinnerItems2 = new String[secondIngredients.size()];

        for(int i = 0; i < firstIngredients.size(); i++)
        {
            spinnerItems1[i] = firstIngredients.get(i).getIngredient() + " " + "(" + firstIngredients.get(i).getAmountIng() + ")";
        }

        for(int i = 0; i < secondIngredients.size(); i++)
        {
            spinnerItems2[i] = secondIngredients.get(i).getIngredient()+ " " + "(" + secondIngredients.get(i).getAmountIng() + ")";
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerItems1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerItems2);

        meal_spinnerOption1.setAdapter(adapter1);
        meal_spinnerOption2.setAdapter(adapter2);

        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < (defaultIngredients.size() - 1 )){
            sb.append(defaultIngredients.get(i).getIngredient() + " (" + defaultIngredients.get(i).getAmountIng() + ") \n\n");

            i++;
        }
        if(i == (defaultIngredients.size() - 1)){
            sb.append(defaultIngredients.get(i).getIngredient() + " (" + defaultIngredients.get(i).getAmountIng() + ")");
        }

        meal_txtDisplay.setText(sb);


        //These two override the selection at the start of the app, preventing the toasts to show
        meal_spinnerOption1.setSelection(0, false);
        meal_spinnerOption2.setSelection(0, false);

        //To get spinner value of Option1
        meal_spinnerOption1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    meal_calOption1 = firstIngredients.get(0).getCalIng();
                    Log.d(TAG, meal_calOption1 + " Regular cal");
                    nameOption1 = firstIngredients.get(0).getIngredient();
                    Toast.makeText(Meal_GainMuscle_Activity.this, nameOption1 + " Added.", Toast.LENGTH_SHORT).show();
                    veganPlan = "No Vegan Meal Plan";
                    meal_txtDisplayOptions.setText(veganPlan);

                } else if (i == 1) {
                    meal_calOption1 = firstIngredients.get(1).getCalIng();
                    Log.d(TAG, meal_calOption1 + " coco milk cal");
                    nameOption1 = firstIngredients.get(1).getIngredient();
                    Toast.makeText(Meal_GainMuscle_Activity.this, nameOption1 + " Added.", Toast.LENGTH_SHORT).show();
                    veganPlan = "Vegan Meal Plan";
                    meal_txtDisplayOptions.setText(veganPlan);

                } else if (i == 2) {
                    meal_calOption1 = firstIngredients.get(2).getCalIng();
                    Log.d(TAG, meal_calOption1 + " Soy milk cal");
                    nameOption1 = firstIngredients.get(2).getIngredient();
                    Toast.makeText(Meal_GainMuscle_Activity.this, nameOption1 + " Added.", Toast.LENGTH_SHORT).show();
                    veganPlan = "Vegan Meal Plan";
                    meal_txtDisplayOptions.setText(veganPlan);

                } else if (i == 3) {
                    meal_calOption1 = firstIngredients.get(3).getCalIng();
                    Log.d(TAG, meal_calOption1 + " Oat milk cal");
                    nameOption1 = firstIngredients.get(3).getIngredient();
                    Toast.makeText(Meal_GainMuscle_Activity.this, nameOption1 + " Added.", Toast.LENGTH_SHORT).show();
                    veganPlan = "Vegan Meal Plan";
                    meal_txtDisplayOptions.setText(veganPlan);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //To get spinner value of Option2
        meal_spinnerOption2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i == 0) {
                    meal_calOption2 = secondIngredients.get(0).getCalIng();
                    Log.d(TAG, meal_calOption2  + " Option2");
                    nameOption2 = secondIngredients.get(0).getIngredient();
                    Toast.makeText(Meal_GainMuscle_Activity.this, nameOption2 + " Added.", Toast.LENGTH_SHORT).show();
                    veganPlan = "No Vegan Meal Plan";
                    meal_txtDisplayOptions.setText(veganPlan);

                } else if (i == 1) {
                    meal_calOption2  = secondIngredients.get(1).getCalIng();
                    Log.d(TAG, meal_calOption2  + " vegan Option2");
                    nameOption2 = secondIngredients.get(1).getIngredient();
                    Toast.makeText(Meal_GainMuscle_Activity.this, nameOption2 + " Added.", Toast.LENGTH_SHORT).show();
                    veganPlan = "Vegan Meal Plan";
                    meal_txtDisplayOptions.setText(veganPlan);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //To calculate the calories
        meal_btnCalculateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //displayCalCalculation();

                meal_CalWithoutOptions = 0;
                for (int i = 0; i < defaultIngredients.size(); i++){
                    meal_CalWithoutOptions += defaultIngredients.get(i).getCalIng();
                }
                Log.d(TAG, meal_CalWithoutOptions + " dafault sumCal");

                meal_totalCalories = meal_CalWithoutOptions + meal_calOption1 + meal_calOption2;

                meal_txtCaloriesValue.setText(meal_totalCalories + " calories");
                meal_txtDisplayOptions.setText(veganPlan);
                //meal_txtDisplayOptions.setText(milkName + ", " + proteinName);
                Toast.makeText(Meal_GainMuscle_Activity.this, "Total Calories is changed", Toast.LENGTH_LONG).show();

            }
        });

        //To save data to the datebase
        meal_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get value and save data in log table
                String foodName = meal_txtTitle.getText().toString();
                String foodType = meal_txtDisplayOptions.getText().toString();
                double calories = meal_totalCalories;

                ContentValues values = new ContentValues();
                values.put("name", foodName);
                values.put("type", foodType);
                values.put("calories", calories);
                db.insert("meal_log",null,values);
                Toast.makeText(Meal_GainMuscle_Activity.this,"Save data in the meal_log table",Toast.LENGTH_SHORT).show();

            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(Meal_GainMuscle_Activity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(Meal_GainMuscle_Activity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(Meal_GainMuscle_Activity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(Meal_GainMuscle_Activity.this,LogActivity.class));
                    break;

            }
            return true;
        });


    }// finish onCreate()


    private void getOptionFirstIngredients(String id){

        String select = "SELECT ingredientCode, ingredient, amount, cal, category, foodID  FROM FoodIngredient WHERE foodID = ? AND category = 'Option1';";
        firstIngredients = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery(select,new String[]{id});
            if(cursor != null){
                cursor.moveToFirst();

                while (!cursor.isAfterLast())
                {
                    int ingredientCode = cursor.getInt(0);
                    String ingredient = cursor.getString(1);
                    String amount = cursor.getString(2);
                    double cal = cursor.getDouble(3);
                    String category = cursor.getString(4);
                    int foodID = cursor.getInt(5);

                    FoodIngredient ingredientObj = new FoodIngredient(ingredientCode, ingredient, amount, cal, category, foodID);
                    Log.d(TAG,ingredientCode + ", " + ingredient + ", " + amount + ", " + cal + ", " + category + ", " + foodID + "FirstIng");

                    firstIngredients.add(ingredientObj);
                    cursor.moveToNext();

                }
            }

        }catch (Exception e) {
            Log.e(TAG, "selectSQL_firstIngredients");
        }
    }


    private void getOptionSecondIngredients(String id){

        String select = "SELECT ingredientCode, ingredient, amount, cal, category, foodID  FROM FoodIngredient WHERE foodID = ? AND category = 'Option2';";
        secondIngredients = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery(select,new String[]{id});
            if(cursor != null){
                cursor.moveToFirst();

                while (!cursor.isAfterLast())
                {
                    int ingredientCode = cursor.getInt(0);
                    String ingredient = cursor.getString(1);
                    String amount = cursor.getString(2);
                    double cal = cursor.getDouble(3);
                    String category = cursor.getString(4);
                    int foodID = cursor.getInt(5);

                    FoodIngredient ingredientObj = new FoodIngredient(ingredientCode, ingredient, amount, cal, category, foodID);
                    Log.d(TAG,ingredientCode + ", " + ingredient + ", " + amount + ", " + cal + ", " + category + ", " + foodID + " secondIng");

                    secondIngredients.add(ingredientObj);
                    cursor.moveToNext();

                }
            }

        }catch (Exception e) {
            Log.e(TAG, "selectSQL_secondIngredients");
        }
    }


    private void getDefaultIngredients(String id){

        String select = "SELECT ingredientCode, ingredient, amount, cal, category, foodID  FROM FoodIngredient WHERE foodID = ? AND category = 'Display';";
        defaultIngredients = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery(select,new String[]{id});
            if(cursor != null){
                cursor.moveToFirst();

                while (!cursor.isAfterLast())
                {
                    int ingredientCode = cursor.getInt(0);
                    String ingredient = cursor.getString(1);
                    String amount = cursor.getString(2);
                    double cal = cursor.getDouble(3);
                    String category = cursor.getString(4);
                    int foodID = cursor.getInt(5);

                    FoodIngredient ingredientObj = new FoodIngredient(ingredientCode, ingredient, amount, cal, category, foodID);
                    Log.d(TAG,ingredientCode + ", " + ingredient + ", " + amount + ", " + cal + ", " + category + ", " + foodID + "DefaultIng");

                    defaultIngredients.add(ingredientObj);
                    cursor.moveToNext();

                }
            }

        }catch (Exception e) {
            Log.e(TAG, "selectSQL_defaultIngredients");
        }
    }

    private void getAllFoodIngredients(String id){

        String select = "SELECT ingredientCode, ingredient, amount, cal, category, foodID  FROM FoodIngredient WHERE foodID = ?;";
        foodIngredients = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery(select,new String[]{id});
            if(cursor != null){
                cursor.moveToFirst();

                while (!cursor.isAfterLast())
                {
                    int ingredientCode = cursor.getInt(0);
                    String ingredient = cursor.getString(1);
                    String amount = cursor.getString(2);
                    double cal = cursor.getDouble(3);
                    String category = cursor.getString(4);
                    int foodID = cursor.getInt(5);

                    FoodIngredient ingredientObj = new FoodIngredient(ingredientCode, ingredient, amount, cal, category, foodID);
                    Log.d(TAG,ingredientCode + ", " + ingredient + ", " + amount + ", " + cal + ", " + category + ", " + foodID);

                    foodIngredients.add(ingredientObj);
                    cursor.moveToNext();

                }
            }

        }catch (Exception e) {
            Log.e(TAG, "selectSQL_foodIngredients");
        }
    }


    private void getMealPlan(String id) {


        String select = "SELECT foodID, foodName, cal, image, type FROM FOOD WHERE foodID = ?;";
        mealPlan = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery(select,new String[]{id});
            {
                cursor.moveToFirst();

                while (!cursor.isAfterLast())
                {
                    int foodId = cursor.getInt(0);
                    String foodName = cursor.getString(1);
                    double cal = cursor.getDouble(2);
                    int image = cursor.getInt(3);
                    String type = cursor.getString(4);

                    Food obj = new Food(foodId,foodName,cal,image,type);
                    Log.d(TAG,foodId + ", " + foodName+ ", " +cal+ ", " +image+ ", " + type);

                    mealPlan.add(obj);
                    cursor.moveToNext();
                }
            }

        }catch (Exception e) {
            Log.e(TAG, "selectSQL_mealPlan");
        }

    } //finish the getMealPlan()


    private void createDB() {

        try {
            db = openOrCreateDatabase("Fitgain.db", MODE_PRIVATE,null); //makig db
            Log.d(TAG, "Database Creation: detail mealPlan");
            //Toast.makeText(M_Smothie_Activity.this, "Database Creation",Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e(TAG,"createDB error");
        }

    } // finish the createDB()

}