package com.example.easymeal.model.pojo;

import java.util.List;

public class MealsResponse {
    private List<Meal> meals;

    public MealsResponse(List<Meal> meals) {
        this.meals = meals;
    }
}
