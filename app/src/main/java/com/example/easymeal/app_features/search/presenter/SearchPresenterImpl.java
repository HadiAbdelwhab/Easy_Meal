package com.example.easymeal.app_features.search.presenter;

import com.example.easymeal.app_features.search.view.SearchView;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.network.meals.NetworkCallBack;

public class SearchPresenterImpl implements SearchPresenter, NetworkCallBack.CategoriesCallBack,
        NetworkCallBack.AreasCallBack,NetworkCallBack.IngredientsCallBack, NetworkCallBack.SearchMealCallBack{
    private SearchView view;
    private MealsRepository repository;

    public SearchPresenterImpl(SearchView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getCategories() {
        repository.getAllCategories(this);
    }

    @Override
    public void getAreas() {
        repository.getAllAreas(this);
    }

    @Override
    public void getIngredients() {
        repository.getIngredients(this);
    }

    @Override
    public void searchMealByName(String mealName) {
        repository.searchMealByName(this, mealName);

    }

    @Override
    public void onSuccessResult(CategoryResponse categories) {
        view.showCategories(categories);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showCategoriesErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessAreaCallBack(AreaListResponse areaListResponse) {
        view.showAreas(areaListResponse);

    }

    @Override
    public void onFailAreaCallBack(String errorMessage) {
        view.showAreasErrorMessage(errorMessage);

    }

    @Override
    public void onSuccessIngredients(IngredientsResponse ingredientsResponse) {
        view.showIngredients(ingredientsResponse);
    }

    @Override
    public void onFailIngredients(String errorMessage) {
        view.showIngredientsErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessSearchMeal(MealDetailsResponse mealDetailsResponse) {
        view.searchMealByNameResult(mealDetailsResponse);
    }

    @Override
    public void onFailSearchMeal(String errorMessage) {
        view.searchMealByNameErrorMessage(errorMessage);
    }
}
