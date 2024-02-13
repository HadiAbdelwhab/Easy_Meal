package com.example.easymeal.app_features.meals.view;

import com.example.easymeal.model.pojo.MealsResponse;

public interface MealsView {
    public void showMealsByCategory(MealsResponse mealsResponse);
    public void showMealsByCategoryErrorMessage(String errorMessage);
    public void showMealsByArea(MealsResponse mealsResponse);
    public void showMealsByAreaErrorMessage(String errorMessage);
}
