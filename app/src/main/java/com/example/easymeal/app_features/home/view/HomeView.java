package com.example.easymeal.app_features.home.view;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;

public interface HomeView {
    public void showCategories(CategoryResponse mealCategoryList);
    public void showCategoriesErrorMessage(String errorMassage);
    public void showAreas(AreaListResponse areaListResponse);
    public void showAreasErrorMessage(String errorMessage);
    public void showRandomMeal(MealDetailsResponse mealDetails);
    public void showRandomMealErrorMessage(String errorMessage);

}
