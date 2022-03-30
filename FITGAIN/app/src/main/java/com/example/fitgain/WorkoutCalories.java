package com.example.fitgain;

public class WorkoutCalories
{
    private String number;
    private String name;
    private String calories;
    public WorkoutCalories()
    {

    }
    public WorkoutCalories(String number, String name, String cal)
    {
        this.number = number;
        this.name = name;
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

    public String getCalories()
    {
        return calories;
    }

    public void setCalories(String cal)
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

//    @Override
//    public String toString()
//    {
//        return "Workout Calories{" +
//                "number=" + number +
//                ", name='" + name + '\'' +
//                ", calories='" + calories + '\'' +
//                '}';
//    }
}