package com.example.easymeal.app_features.meal_details.view;

import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface MealDetailsView {
    void showMealDetails(MealDetailsResponse mealDetailsResponse);

    void showErrorMessage(String errorMessage);

    void showIngredients(IngredientsResponse ingredientsResponse);

    void showIngredientsErrorMessage(String errorMessage);
}
