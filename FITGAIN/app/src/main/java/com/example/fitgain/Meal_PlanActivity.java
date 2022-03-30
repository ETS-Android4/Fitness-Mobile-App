package com.example.fitgain;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Meal_PlanActivity extends AppCompatActivity {

    SQLiteDatabase db;
    final String TAG = "DB";
    Typewriter writer;

    TextView txtMealPlanTitle;
    ListView listViewMealPlan;
    Button btnGM;
    Button btnLW;
    TextView txtResult;

    List<Food> foodList;
    List<FoodIngredient> foodIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        //Database
        createDB();

        writer = findViewById(R.id.txtReaultMealPlan);

        foodList = readFoodData();
        foodIngredients = readFoodIngredientData();

        //check point
        boolean check =  checkDBData();
        Log.d(TAG, check + " check the data or not");


        if(!check){
            for(int i = 0; i <foodList.size(); i++) {
                insertFoodData(foodList.get(i).getFoodID(), foodList.get(i).getFoodName(), foodList.get(i).getCal(), foodList.get(i).getImage(), foodList.get(i).getType());

                Log.d(TAG, "insert Food data in DB");
                //Toast.makeText(M_MealPlanActivity.this, "insert Food data in DB",Toast.LENGTH_SHORT).show();
            }

            for(int i = 0; i < foodIngredients.size(); i++){
                insertFoodIngredientData(foodIngredients.get(i).getIngredientCode(), foodIngredients.get(i).getIngredient(), foodIngredients.get(i).getAmountIng(),
                        foodIngredients.get(i).getCalIng(), foodIngredients.get(i).getCategory(), foodIngredients.get(i).getFoodIDIng());

                Log.d(TAG, "insert Food Ingredients data in DB");
                //Toast.makeText(M_MealPlanActivity.this, "insert Food Ingredients data in DB",Toast.LENGTH_SHORT).show();
            }
        }

        txtMealPlanTitle = (TextView) findViewById(R.id.txtMealPlanTitle);
        listViewMealPlan = (ListView) findViewById(R.id.listViewMealPlan);
        btnGM = (Button) findViewById(R.id.btnGainMuscle);
        btnLW = (Button) findViewById(R.id.btnLossWeight);
        txtResult = (TextView) findViewById(R.id.txtReaultMealPlan);

        Meal_MenuAdapter dapterAllMenu = new Meal_MenuAdapter(this, foodList);
        listViewMealPlan.setAdapter(dapterAllMenu);

        listViewMealPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {
                    case 0:
                        Intent intent1 = new Intent(Meal_PlanActivity.this, Meal_GainMuscle_Activity.class);
                        Log.d(TAG, i + " i value");
                        Bundle mybundle1 = new Bundle();
                        mybundle1.putInt("FOODID", i + 1);
                        mybundle1.putString("MILK", "Milk");
                        mybundle1.putString("POWDER", "Protein Powder");
                        Log.d(TAG, (i + 1) + "FOODID");
                        intent1.putExtras(mybundle1);
                        startActivity(intent1);
                        break;
                    case 1:
                        Intent intent2 = new Intent(Meal_PlanActivity.this, Meal_GainMuscle_Activity.class);
                        Log.d(TAG, i + " i value");
                        Bundle mybundle2 = new Bundle();
                        mybundle2.putInt("FOODID", i + 1);
                        mybundle2.putString("BEEF", "Beef");
                        mybundle2.putString("MAYONNAISE", "Mayonnaise");
                        Log.d(TAG, (i + 1) + "FOODID");
                        intent2.putExtras(mybundle2);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(Meal_PlanActivity.this, Meal_LossWeight_Activity.class);
                        Log.d(TAG, i + " i value");
                        Bundle mybundle3 = new Bundle();
                        mybundle3.putInt("FOODID", i + 1);
                        Log.d(TAG, (i + 1) + "FOODID");
                        intent3.putExtras(mybundle3);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(Meal_PlanActivity.this, Meal_LossWeight_Activity.class);
                        Log.d(TAG, i + " i value");
                        Bundle mybundle4 = new Bundle();
                        mybundle4.putInt("FOODID", i + 1);
                        Log.d(TAG, (i + 1) + "FOODID");
                        intent4.putExtras(mybundle4);
                        startActivity(intent4);
                        break;
                }
            }
        });

        List<Food> listGM = new ArrayList<>();
        List<Food> listLW = new ArrayList<>();

        for(int i = 0; i < foodList.size(); i++){

            if(foodList.get(i).getType().equals("Gain Muscle"))
            {
                listGM.add(foodList.get(i));
            } else if (foodList.get(i).getType().equals("Loss Weight"))
            {
                listLW.add(foodList.get(i));
            }
        }

        Meal_MenuAdapter menuAdapter1 = new Meal_MenuAdapter(this, listGM);
        Meal_MenuAdapter menuAdapter2 = new Meal_MenuAdapter(this, listLW);

        //Toast.makeText(M_MealPlanActivity.this, "meal plan list view", Toast.LENGTH_SHORT).show();

        btnGM.setText(listGM.get(0).getType());
        btnLW.setText(listLW.get(0).getType());

        btnGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtMealPlanTitle.setText(listGM.get(0).getType());

                ViewGroup.LayoutParams params = listViewMealPlan.getLayoutParams();
                params.height = 700;
                listViewMealPlan.setLayoutParams(params);
                listViewMealPlan.requestLayout();

                String result = "It is the meal plan based on a typical active person. " + "\n" +
                        "However, you can adjust the number of calories or ingredients you need to suit your taste.";

                writer.setText("");
                writer.setCharacterDelay(80);
                writer.animateText(result);

                //txtResult.setText(result);

                listViewMealPlan.setAdapter(menuAdapter1);

                listViewMealPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i)
                        {
                            case 0:
                                Intent intent1 = new Intent(Meal_PlanActivity.this, Meal_GainMuscle_Activity.class);
                                Log.d(TAG, i + " i value");
                                Bundle mybundle1 = new Bundle();
                                mybundle1.putInt("FOODID", i + 1);
                                mybundle1.putString("MILK", "Milk");
                                mybundle1.putString("POWDER", "Protein Powder");
                                Log.d(TAG, (i + 1) + "FOODID");
                                intent1.putExtras(mybundle1);
                                startActivity(intent1);
                                break;
                            case 1:
                                Intent intent2 = new Intent(Meal_PlanActivity.this, Meal_GainMuscle_Activity.class);
                                //Intent intent2 = new Intent(M_MealPlanActivity.this, M_Smothie_Activity.class);
                                Log.d(TAG, i + " i value");
                                Bundle mybundle2 = new Bundle();
                                mybundle2.putInt("FOODID", i + 1);
                                mybundle2.putString("BEEF", "Beef");
                                mybundle2.putString("MAYONNAISE", "Mayonnaise");
                                Log.d(TAG, (i + 1) + "FOODID");
                                intent2.putExtras(mybundle2);
                                startActivity(intent2);
                                break;
                        }
                    }
                });
            }
        });

        btnLW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtMealPlanTitle.setText(listLW.get(0).getType());

                ViewGroup.LayoutParams params = listViewMealPlan.getLayoutParams();
                params.height = 700;
                listViewMealPlan.setLayoutParams(params);
                listViewMealPlan.requestLayout();

                String result = "Specific diet plans contribute to weight loss. " + "\n" +
                        "Don't lose more than 2 pounds a week or reduce calories to less than 1,200  a day. It can damage your metabolism.";

                writer.setText("");
                writer.setCharacterDelay(80);
                writer.animateText(result);


                listViewMealPlan.setAdapter(menuAdapter2);

                listViewMealPlan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        switch (i)
                        {
                            case 0:
                                Intent intent3 = new Intent(Meal_PlanActivity.this, Meal_LossWeight_Activity.class);
                                Log.d(TAG, i + " i value");
                                Bundle mybundle1 = new Bundle();
                                mybundle1.putInt("FOODID", i + 3);
                                Log.d(TAG, (i + 3) + "FOODID");
                                intent3.putExtras(mybundle1);
                                startActivity(intent3);
                                break;
                            case 1:
                                Intent intent4 = new Intent(Meal_PlanActivity.this, Meal_LossWeight_Activity.class);
                                Log.d(TAG, i + " i value");
                                Bundle mybundle2 = new Bundle();
                                mybundle2.putInt("FOODID", i + 3);
                                Log.d(TAG, (i + 3) + "FOODID");
                                intent4.putExtras(mybundle2);
                                startActivity(intent4);
                                break;
                        }
                    }
                });
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.getMenu().setGroupCheckable(0,false,false);

        bottomNavigationView.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_meal:
                    startActivity(new Intent(Meal_PlanActivity.this, Meal_PlanActivity.class));
                    break;
                case R.id.navigation_workout:
                    startActivity(new Intent(Meal_PlanActivity.this,WorkoutActivity.class));
                    break;
                case R.id.navigation_bodyInfo:
                    startActivity(new Intent(Meal_PlanActivity.this,ProfileActivity.class));
                    break;
                case R.id.navigation_log:
                    startActivity(new Intent(Meal_PlanActivity.this,LogActivity.class));
                    break;

            }
            return true;
        });

    }//finish onCreate();


    private boolean checkDBData(){

        String sql = "SELECT * FROM FOOD";

        try{
            Cursor cursor = db.rawQuery(sql,null);

            if(cursor != null){
                cursor.moveToFirst();

                    int foodId = cursor.getInt(0);

                    if(foodId == 1){
                        return true;
                    }
                }
        }catch (Exception e) {
            Log.e(TAG, " checkDBData error");
        }
        return false;
    }


    private void insertFoodIngredientData(int ingredientCode, String ingredient, String amount, double calIngredient, String category, int foodIDIng) {

        ContentValues contentVal = new ContentValues();
        contentVal.put("ingredientCode",ingredientCode);
        contentVal.put("ingredient",ingredient);
        contentVal.put("amount",amount);
        contentVal.put("cal",calIngredient);
        contentVal.put("category",category);
        contentVal.put("foodID",foodIDIng);

        long result;
        result = db.insert("FoodIngredient",null,contentVal);
        Log.d(TAG,"addData " + ingredientCode);

    }


    private void insertFoodData(int foodID, String foodName, double cal, int image, String type) {

        ContentValues contentVal = new ContentValues();
        contentVal.put("foodID",foodID);
        contentVal.put("foodName",foodName);
        contentVal.put("cal",cal);
        contentVal.put("image",image);
        contentVal.put("type",type);

        long result;
        result = db.insert("Food",null,contentVal);
        Log.d(TAG,"addData " + foodID);

    }

    private List<FoodIngredient> readFoodIngredientData() {

        List<FoodIngredient> listFoodIngredient = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.foodingredient);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String rec;

        try {
            rec = reader.readLine();
            //Toast.makeText(MainActivity.this, rec + " rec values",Toast.LENGTH_SHORT).show();
            while ((rec = reader.readLine()) != null)
            {

                String[] columns = rec.split(",");
                int ingredientCode = Integer.parseInt(columns[0]);
                //Toast.makeText(M_Smothie_Activity.this, ingredientCode + " ingredientCode values",Toast.LENGTH_SHORT).show();
                String ingredient = columns[1];
                String amount = columns[2];
                double cal = Double.parseDouble(columns[3]);
                String category = columns[4];
                int foodID = Integer.parseInt(columns[5]);

                FoodIngredient ingredientObj = new FoodIngredient(ingredientCode, ingredient, amount,cal,category,foodID);

                listFoodIngredient.add(ingredientObj);

            }

        }catch (Exception e)
        {
            Log.e(TAG,"readData error");
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, listFoodIngredient.size() + " listFoodIngredient values");
        //Toast.makeText(M_MealPlanActivity.this, listFoodIngredient.size() + " values",Toast.LENGTH_SHORT).show();
        return listFoodIngredient;
    }


    private List<Food> readFoodData() {

        List<Food> listFood = new ArrayList<>();

        InputStream inputStream = getResources().openRawResource(R.raw.food);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String rec;
        try {
            rec = reader.readLine();
            //Toast.makeText(MainActivity.this, rec + " rec values",Toast.LENGTH_SHORT).show();
            while ((rec = reader.readLine()) != null)
            {
                String[] columns = rec.split(",");
                int id = Integer.parseInt(columns[0]);
                //Toast.makeText(MainActivity.this, id + " id values",Toast.LENGTH_SHORT).show();
                String foodName = columns[1];
                double cal = Double.parseDouble(columns[2]);
                int pic = getResources().getIdentifier(columns[3],"drawable",getPackageName());
                String type = columns[4];

                Food foodObj = new Food(id, foodName, cal, pic,type);

                listFood.add(foodObj);

            }

        }catch (Exception e)
        {
            Log.e(TAG,"readData error");
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, listFood.size() + "listFood values");
        //Toast.makeText(M_MealPlanActivity.this, listFood.size() + " values",Toast.LENGTH_SHORT).show();
        return listFood;
    }


    private void createDB() {

        try {
            db = openOrCreateDatabase("Fitgain.db", MODE_PRIVATE,null);
            Log.d(TAG, "Database Creation");
            //Toast.makeText(M_MealPlanActivity.this, "Database Creation",Toast.LENGTH_SHORT).show();

        } catch (Exception e)
        {
            Log.e(TAG,"createDB error");
        }

    }

}