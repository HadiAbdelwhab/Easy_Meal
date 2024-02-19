package com.example.easymeal.auth.login.presenter;

import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface LoginPresenter {
    void insertMeal(MealDetailsResponse.MealDetails mealDetails);
    void truncateMeals();

}
