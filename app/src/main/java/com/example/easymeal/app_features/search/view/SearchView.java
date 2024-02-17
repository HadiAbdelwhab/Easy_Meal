package com.example.easymeal.app_features.search.view;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface SearchView {
    void showCategories(CategoryResponse categoryResponse);
    void showCategoriesErrorMessage(String ErrorMassage);
    void showIngredients(IngredientsResponse ingredientsResponse);
    void showIngredientsErrorMessage(String ErrorMassage);
    void showAreas(AreaListResponse areaListResponse);
    void showAreasErrorMessage(String ErrorMassage);
    void searchMealByNameResult(MealDetailsResponse mealDetailsResponse);
    void searchMealByNameErrorMessage(String errorMessage);

}
