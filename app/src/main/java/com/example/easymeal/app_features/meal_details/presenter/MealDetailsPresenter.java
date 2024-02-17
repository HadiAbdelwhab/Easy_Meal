package com.example.easymeal.app_features.meal_details.presenter;

import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface MealDetailsPresenter {
    public void getMealDetailsById(String mealId);
    public void insertMeal(MealDetailsResponse.MealDetails mealDetails);
}
