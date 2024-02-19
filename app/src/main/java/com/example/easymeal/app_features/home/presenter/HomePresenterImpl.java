package com.example.easymeal.app_features.home.presenter;

import com.example.easymeal.app_features.home.view.HomeView;
import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.network.NetworkCallBack;


public class HomePresenterImpl implements HomePresenter, NetworkCallBack.CategoriesCallBack,
        NetworkCallBack.AreasCallBack, NetworkCallBack.RandomMealCallBack, NetworkCallBack.IngredientsCallBack {

    private final HomeView view;
    private final MealsRepository repository;

    public HomePresenterImpl(HomeView view, MealsRepository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void getAllCategories() {
        repository.getAllCategories(this);
    }

    @Override
    public void getAllAreas() {
        repository.getAllAreas(this);
    }

    @Override
    public void getRandomMeal() {
        repository.getRandomMeal(this);
    }



    //Categories
    @Override
    public void onSuccessResult(CategoryResponse categoryResponse) {
        view.showCategories(categoryResponse);
    }

    @Override
    public void onFailure(String errorMessage) {
        view.showCategoriesErrorMessage(errorMessage);
    }

    //Area
    @Override
    public void onSuccessAreaCallBack(AreaListResponse areaListResponse) {
        view.showAreas(areaListResponse);
    }

    @Override
    public void onFailAreaCallBack(String errorMessage) {
        view.showAreasErrorMessage(errorMessage);
    }

    //RandomMeal
    @Override
    public void onSuccessRandomMeal(MealDetailsResponse mealDetails) {
        view.showRandomMeal(mealDetails);
    }

    @Override
    public void onFailRandomMeal(String errorMessage) {
        view.showRandomMealErrorMessage(errorMessage);
    }


    @Override
    public void onSuccessIngredients(IngredientsResponse ingredientsResponse) {
        view.showIngredients(ingredientsResponse);
    }

    @Override
    public void onFailIngredients(String errorMessage) {
        view.showIngredientsErrorMessage(errorMessage);
    }
}
