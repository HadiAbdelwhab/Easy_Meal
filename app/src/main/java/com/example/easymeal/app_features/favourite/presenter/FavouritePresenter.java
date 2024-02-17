package com.example.easymeal.app_features.favourite.presenter;

import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface FavouritePresenter {
    void getFavouriteMeals();
    void deleteFavouriteMeal(MealDetailsResponse.MealDetails  mealDetails);
}
