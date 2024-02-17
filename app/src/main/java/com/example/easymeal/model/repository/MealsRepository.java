package com.example.easymeal.model.repository;

import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.meals.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface MealsRepository {
    public void getAllCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack);

    public void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack, String idMeal);

    public void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack);

    public void getRandomMeal(NetworkCallBack.RandomMealCallBack randomMealCallBack);

    public void getMealsByCategory(NetworkCallBack.MealsByCategoryCallBack mealsByCategoryCallBack
            , String categoryName);

    public void getMealsByArea(NetworkCallBack.MealsByAreaCallBack mealsByAreaCallBack
            , String areaName);
    public void getIngredients(NetworkCallBack.IngredientsCallBack ingredientsCallBack);
    //local_methods
    public Completable insertMeal(MealDetailsResponse.MealDetails mealDetails);
    public void deleteMeal(MealDetailsResponse.MealDetails mealDetails);
    public Flowable<List<MealDetailsResponse.MealDetails>> getSavedMeals();
}
