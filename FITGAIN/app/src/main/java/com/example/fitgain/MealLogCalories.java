package com.example.fitgain;

public class MealLogCalories
{
    private String number;
    private String name;
    private String type;
    private double calories;
    public MealLogCalories()
    {

    }
    public MealLogCalories(String number, String name, String type, double cal)
    {
        this.number = number;
        this.name = name;
        this.type=type;
        this.calories = cal;

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getCalories()
    {
        return calories;
    }

    public void setCalories(double cal)
    {
        this.calories = cal;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Workout Calories{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", name='" + type + '\'' +
                ", calories='" + calories + '\'' +
                '}';
    }
}