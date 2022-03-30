package com.example.fitgain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Meal_LossWeight_Activity extends AppCompatActivity {

    SQLiteDatabase db;
    final String TAG = "DB";

    List<Food> mealPlan;

    TextView meal_txtTitle;
    ImageView meal_Image;
    TextView meal_OtxtCalValue;
    TextView meal_txtODisplay;

    List<FoodIngredient> foodIngredients;
    List<FoodIngredient> defaultIngredients;
    List<FoodIngredient> firstIngredients;
    List<FoodIngredient> secondIngredients;

    RadioGroup radioGroup;
    RadioButton meal_radVegan;
    RadioButton meal_radNoVegan;
    TextView meal_OtxtDisplayOptions;
    TextView meal_LChoice1;
    TextView meal_LChoice2;
    Button meal_ObtnCalulateCal;
    Button meal_ObtnSave;

    double sumCalWithout = 264;
    double totalCalOverOat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_loss_weight);

        meal_txtTitle = (TextView) findViewById(R.id.meal_OtxtTitle);
        meal_Image = (ImageView) findViewById(R.id.Meal_Oimage);
        meal_OtxtCalValue = (TextView) findViewById(R.id.meal_OtxtCalValue);
        meal_LChoice1 = (TextView) findViewById(R.id.meal_LChoice1);
        meal_LChoice2 = (TextView) findViewById(R.id.meal_LChoice2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        meal_txtODisplay = (TextView) findViewById(R.id.meal_txtODisplay);

        meal_radVegan = (RadioButton) findViewById(R.id.meal_OradVegan);
        meal_radNoVegan = (RadioButton) findViewById(R.id.meal_OradNoVegan);
        meal_OtxtDisplayOptions = (TextView) findViewById(R.id.meal_OtxtDisplayOptions);
        meal_ObtnCalulateCal = (Button) findViewById(R.id.meal_ObtnCalulateCal);
        meal_ObtnSave = (Button) findViewById(R.id.meal_ObtnSave);

        int foodID = 0;

        try {
            foodID = getIntent().getExtras().getInt("FOODID");

            Log.d(TAG,foodID + " FoodID");

        }catch(Exception e){
            Log.e(TAG,e.getMessage());
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

        meal_txtTitle.setText(mealPlan.get(0).getFoodName());
        meal_OtxtCalValue.setText(mealPlan.get(0).getCal() + " Calories");
        meal_Image.setImageResource(mealPlan.get(0).getImage());


        if(foodID == 3)
        {
            meal_LChoice1.setText(firstIngredients.get(0).getIngredient() + " " + "(" +firstIngredients.get(0).getAmountIng() + ")");
            meal_LChoice2.setText(secondIngredients.get(0).getIngredient() + " " + "(" + secondIngredients.get(0).getAmountIng() + ")");
        }

        if(foodID == 4)
        {
            meal_LChoice1.setText(firstIngredients.get(0).getIngredient() + " " + "(" +firstIngredients.get(0).getAmountIng() + ")");
            meal_LChoice2.setText(secondIngredients.get(0).getIngredient() + " " + "(" + secondIngredients.get(0).getAmountIng() + ")");
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if(meal_radNoVegan.isChecked())
                {
                    meal_OtxtDisplayOptions.setText("No Vegan Meal Plan");
                    //meal_nonfatMilk.setText(R.string.meal_nonfatMilk);
                    meal_LChoice1.setText(firstIngredients.get(0).getIngredient() + " " + "(" +firstIngredients.get(0).getAmountIng() + ")");
                    //meal_lowfatGreekYogurt.setText(R.string.meal_lowFatGreekYogurt);
                    meal_LChoice2.setText(secondIngredients.get(0).getIngredient() + " " + "(" + secondIngredients.get(0).getAmountIng() + ")");
                    Toast.makeText(Meal_LossWeight_Activity.this,"The meal option is changed", Toast.LENGTH_SHORT).show();
                }

                if(meal_radVegan.isChecked())
                {
                    meal_OtxtDisplayOptions.setText("Vegan Meal Plan");
                    //meal_nonfatMilk.setText(R.string.meal_soyMilk);
                    meal_LChoice1.setText(firstIngredients.get(1).getIngredient() + " " + "(" +firstIngredients.get(1).getAmountIng() + ")");
                    //meal_lowfatGreekYogurt.setText(R.string.meal_coconutGreekMilk);
                    meal_LChoice2.setText(secondIngredients.get(1).getIngredient() + " " + "(" + secondIngredients.get(1).getAmountIng() + ")");
                    Toast.makeText(Meal_LossWeight_Activity.this,"The meal option is changed", Toast.LENGTH_SHORT).show();

                }

            }
        });

        StringBuilder sb = new StringBuilder();

        int i = 0;
        while (i < (defaultIngredients.size() - 1 )){
            sb.append(defaultIngredients.get(i).getIngredient() + " (" + defaultIngredients.get(i).getAmountIng() + ") \n\n");

            i++;
        }
        if(i == (defaultIngredients.size() - 1)){
            sb.append(defaultIngredients.get(i).getIngredient() + " (" + defaultIngredients.get(i).getAmountIng() + ")");
        }

        meal_txtODisplay.setText(sb);

        meal_ObtnCalulateCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!meal_radVegan.isChecked() && !meal_radNoVegan.isChecked())
                {
                    Toast.makeText(Meal_LossWeight_Activity.this,"Please choose at least one value.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(meal_radVegan.isChecked())
                {
                    meal_OtxtDisplayOptions.setText("Vegan Meal Plan");

                    sumCalWithout = 0;
                    for (int i = 0; i < defaultIngredients.size(); i ++){
                        sumCalWithout += defaultIngredients.get(i).getCalIng();
                    }
                    Log.d(TAG, sumCalWithout + " default sumCal");


                    totalCalOverOat = firstIngredients.get(1).getCalIng() + secondIngredients.get(1).getCalIng() + sumCalWithout;
                    Log.d(TAG, firstIngredients.get(1).getCalIng() + " firstCal");
                    Log.d(TAG, secondIngredients.get(1).getCalIng() + " secondCal");
                    Log.d(TAG, totalCalOverOat + " TotalCal");

                    meal_OtxtCalValue.setText(totalCalOverOat + " calories");

                    String choiceName1 = firstIngredients.get(1).getIngredient();
                    String choiceName2 = secondIngredients.get(1).getIngredient();

                    Toast.makeText(Meal_LossWeight_Activity.this,choiceName1 + " & " + choiceName2 + " are selected",Toast.LENGTH_LONG).show();
                }

                if(meal_radNoVegan.isChecked())
                {
                    meal_OtxtDisplayOptions.setText("No Vegan Meal Plan");

                    sumCalWithout = 0;
                    for (int i = 0; i < defaultIngredients.size(); i ++){
                        sumCalWithout += defaultIngredients.get(i).getCalIng();
                    }
                    Log.d(TAG, sumCalWithout + " default sumCal");

                    totalCalOverOat = firstIngredients.get(0).getCalIng() + secondIngredients.get(0).getCalIng() + sumCalWithout;
                    Log.d(TAG, firstIngredients.get(0).getCalIng() + " firstCal");
                    Log.d(TAG, secondIngredients.get(0).getCalIng() + " secondCal");
                    Log.d(TAG, totalCalOverOat + " TotalCal");

                    meal_OtxtCalValue.setText(totalCalOverOat + " calories");

                    String choiceName1 = firstIngredients.get(0).getIngredient();
                    String choiceName2 = secondIngredients.get(0).getIngredient();

                    Toast.makeText(Meal_LossWeight_Activity.this,choiceName1 + " & " + choiceName2 + " are selected",Toast.LENGTH_LONG).show();
                }

            }
        });

        //To save data to the datebase
        meal_ObtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get value and save data in Log table
                String foodName = meal_txtTitle.getText().toString();
                String foodType = meal_OtxtDisplayOptions.getText().toString();
                double calories = totalCalOverOat;

                ContentValues values = new ContentValues();
                values.put("name", foodName);
                values.put("type", foodType);
                values.put("calories", calories);
                db.insert("meal_log",null,values);
                Toast.makeText(Meal_LossWeight_Activity.this,"Save data in the meal_log table",Toast.LENGTH_SHORT).show();

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(Meal_LossWeight_Activity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(Meal_LossWeight_Activity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(Meal_LossWeight_Activity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(Meal_LossWeight_Activity.this,LogActivity.class));
                    break;

            }
            return true;
        });

    } //Finished onCreate()


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
                    Log.d(TAG,ingredientCode + ", " + ingredient + ", " + amount + ", " + cal + ", " + category + ", " + foodID + " FirstIng");

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
                    Log.d(TAG,ingredientCode + ", " + ingredient + ", " + amount + ", " + cal + ", " + category + ", " + foodID + " DefaultIng");

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
            if(cursor != null)
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
            Log.d(TAG,"Database Creation");
            //Toast.makeText(M_OvernightOat_Activity.this, "Database Creation",Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e(TAG,"createDB error");
        }

    }

}