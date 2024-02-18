package com.example.easymeal.app_features.plan.view;

import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

public interface PlanView {
    void showPlanMeals(List<MealDetailsResponse.MealDetails > mealDetails);

    void showPlanMealsErrorMessage(String errorMessage);
}
