package com.example.easymeal.meal_details.view;

import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface MealDetailsView {
    public void showMealDetails(MealDetailsResponse mealDetailsResponse);
    public void showErrorMessage(String errorMessage);
}
