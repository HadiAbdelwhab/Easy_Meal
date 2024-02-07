package com.example.easymeal.network;

public interface MealsRemoteDataSource {

    public void getCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack);
    public void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack,
                               String mealId);
    public void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack);
}
