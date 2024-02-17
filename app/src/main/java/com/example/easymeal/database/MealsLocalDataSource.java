package com.example.easymeal.database;

import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealsLocalDataSource {

    Flowable<List<MealDetailsResponse.MealDetails>> getFavouriteMeals();
    Completable insertMeal(MealDetailsResponse.MealDetails mealDetails);
    void deleteMeal(MealDetailsResponse.MealDetails mealDetails);
}
