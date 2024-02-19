package com.example.easymeal.app_features.search.presenter;

public interface SearchPresenter {
    void getMealsByCategory(String categoryName);

    void getMealsByArea(String areaName);

    void getMealsByIngredient(String ingredientName);

    void searchMealByName(String mealName);

}
