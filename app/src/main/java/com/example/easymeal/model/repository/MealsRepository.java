package com.example.easymeal.model.repository;

import com.example.easymeal.network.NetworkCallBack;

public interface MealsRepository {
    public void getAllCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack);
    public void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack, String idMeal);
    public void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack);
}
