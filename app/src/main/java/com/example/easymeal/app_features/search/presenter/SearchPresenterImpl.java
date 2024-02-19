package com.example.easymeal.app_features.search.presenter;

import android.util.Log;

import com.example.easymeal.app_features.search.view.SearchView;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.network.NetworkCallBack;

public class SearchPresenterImpl implements SearchPresenter,
        NetworkCallBack.SearchMealCallBack, NetworkCallBack.MealsByCategoryCallBack, NetworkCallBack.MealsByAreaCallBack,NetworkCallBack.GetMealsByIngredient{
    private static final String TAG = "SearchPresenterImpl";
    private SearchView view;
    private MealsRepository repository;

    public SearchPresenterImpl(SearchView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        repository.getMealsByCategory(this,categoryName);
    }

    @Override
    public void getMealsByArea(String areaName) {
        repository.getMealsByArea(this,areaName);
    }

    @Override
    public void getMealsByIngredient(String ingredientName) {
        repository.getMealsByIngredient(this,ingredientName);
    }


    @Override
    public void searchMealByName(String mealName) {
        repository.searchMealByName(this, mealName);

    }



    @Override
    public void onSuccessMealsByCategory(MealsResponse mealsResponse) {
        view.showMealsByCategories(mealsResponse);
    }

    @Override
    public void onFailMealsByCategory(String errorMessage) {
        view.showMealsByCategoriesErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessSearchMeal(MealsResponse mealsResponse) {
       view.showMealBySearch(mealsResponse);
    }

    @Override
    public void onFailSearchMeal(String errorMessage) {
        view.showMealsByIngredientsErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessMealsByArea(MealsResponse mealsResponse) {
        view.showMealsByArea(mealsResponse);
    }

    @Override
    public void onFailMealsByArea(String errorMessage) {
        view.showMealsByAreaErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessMealsByIngredient(MealsResponse mealsResponse) {
        view.showMealsByIngredients(mealsResponse);
        Log.i(TAG, "onSuccessMealsByIngredient: "+mealsResponse.getMeals());
    }

    @Override
    public void onFailMealByIngredient(String errorMessage) {
        view.showMealsByIngredientsErrorMessage(errorMessage);
    }
}
