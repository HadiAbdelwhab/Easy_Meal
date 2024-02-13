package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Meal {
    @SerializedName("strMeal")
    private String mealName;
    @SerializedName("strMealThumb")
    private String mealImage;
    private String idMeal;

    public Meal(String mealName, String mealImage, String idMeal) {
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.idMeal = idMeal;
    }

    @Override
    public String toString() {
        return String.format("Meal: %s\nMeal Thumbnail: %s\nMeal ID: %s\n", mealName, mealImage, idMeal);
    }

    public String getMealName() {
        return mealName;
    }

    public String getMealImage() {
        return mealImage;
    }

    public String getIdMeal() {
        return idMeal;
    }
}