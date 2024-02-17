package com.example.easymeal.app_features.meal_details.presenter;

import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface MealDetailsPresenter {
    void getMealDetailsById(String mealId);
    void insertMeal(MealDetailsResponse.MealDetails mealDetails);
    void getMealIngredients();
}
