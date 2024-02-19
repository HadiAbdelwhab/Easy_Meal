package com.example.easymeal.model.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsResponse {
    @SerializedName("meals")
    private List<Meal> meals;

    public MealsResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }
}
