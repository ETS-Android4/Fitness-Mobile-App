package com.example.fitgain;
public class Food {

    private int foodID;
    private String foodName;
    private double cal;
    private int image;
    private  String type;

    public Food(int foodID, String foodName, double cal, int image, String type) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.cal = cal;
        this.image = image;
        this.type = type;
    }

    public int getFoodID() {
        return foodID;
    }

    public void setFoodID(int foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getCal() {
        return cal;
    }

    public void setCal(double cal) {
        this.cal = cal;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
