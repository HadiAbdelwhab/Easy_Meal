package com.example.easymeal.model.repository;

import com.example.easymeal.network.meals.NetworkCallBack;

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
}
