package com.example.easymeal.model.repository;

import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealsRepository {
    void getAllCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack);

    void getMealDetailsById(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack, String idMeal);

    void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack);

    void getRandomMeal(NetworkCallBack.RandomMealCallBack randomMealCallBack);

    void getMealsByCategory(NetworkCallBack.MealsByCategoryCallBack mealsByCategoryCallBack
            , String categoryName);

    void getMealsByArea(NetworkCallBack.MealsByAreaCallBack mealsByAreaCallBack
            , String areaName);

    void getIngredients(NetworkCallBack.IngredientsCallBack ingredientsCallBack);

    void searchMealByName(NetworkCallBack.SearchMealCallBack searchMealCallBack,
                          String mealName);

    void getMealsByIngredient(NetworkCallBack.GetMealsByIngredient getMealsByIngredient,
                              String ingredientName);

    //local_methods
    Completable insertMeal(MealDetailsResponse.MealDetails mealDetails);

    Completable deleteMeal(MealDetailsResponse.MealDetails mealDetails);

    Flowable<List<MealDetailsResponse.MealDetails>> getFavouriteMeals();

    Flowable<List<MealDetailsResponse.MealDetails>> getPlanMeals(String date);

    Completable truncateMeals();
}
