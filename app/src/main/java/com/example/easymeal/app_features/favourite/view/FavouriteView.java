package com.example.easymeal.app_features.favourite.view;

import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

public interface FavouriteView {
    public void showFavouriteMeals(List<MealDetailsResponse.MealDetails >mealDetails);
    public void showFavouriteMealsErrorMessage(String errorMessage);
}
