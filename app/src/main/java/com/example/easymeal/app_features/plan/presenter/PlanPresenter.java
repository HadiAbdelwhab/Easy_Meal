package com.example.easymeal.app_features.plan.presenter;

import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface PlanPresenter {

    void getPlanMeals();
    void insertMeal(MealDetailsResponse.MealDetails mealDetails);

}
