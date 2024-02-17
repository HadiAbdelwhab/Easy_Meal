package com.example.easymeal.app_features.search.presenter;

public interface SearchPresenter {
    void getCategories();
    void getAreas();
    void getIngredients();
    void searchMealByName(String mealName);

}
