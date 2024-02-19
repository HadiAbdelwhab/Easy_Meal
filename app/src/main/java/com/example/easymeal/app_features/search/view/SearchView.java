package com.example.easymeal.app_features.search.view;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.Meal;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

import java.util.List;

public interface SearchView {
    void showMealsByCategories(MealsResponse mealsResponse);

    void showMealsByCategoriesErrorMessage(String ErrorMassage);

    void showMealsByArea(MealsResponse mealsResponse);

    void showMealsByAreaErrorMessage(String errorMessage);

    void showMealsByIngredients(MealsResponse mealsResponse);

    void showMealsByIngredientsErrorMessage(String errorMessage);

    void showMealBySearch(MealsResponse mealsResponse);
    void showMealBySearchErrorMessage(String errorMessage);

}
