package com.example.fitgain;

public class FoodIngredient {

    private int ingredientCode;
    private String ingredient;
    private String amountIng;
    private double calIng;
    private String category;
    private int foodIDIng;

    public FoodIngredient(int ingredientCode, String ingredient, String amount, double cal, String category, int foodID) {
        this.ingredientCode = ingredientCode;
        this.ingredient = ingredient;
        this.amountIng = amount;
        this.calIng = cal;
        this.category = category;
        this.foodIDIng = foodID;
    }

    public int getIngredientCode() {
        return ingredientCode;
    }

    public void setIngredientCode(int ingredientCode) {
        this.ingredientCode = ingredientCode;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getAmountIng() {
        return amountIng;
    }

    public void setAmountIng(String amount) {
        this.amountIng = amount;
    }

    public double getCalIng() {
        return calIng;
    }

    public void setCalIng(double cal) {
        this.calIng = cal;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFoodIDIng() {
        return foodIDIng;
    }

    public void setFoodIDIng(int foodID) {
        this.foodIDIng = foodID;
    }
}
