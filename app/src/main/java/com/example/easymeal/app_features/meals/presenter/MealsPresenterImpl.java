package com.example.easymeal.app_features.meals.presenter;

import com.example.easymeal.app_features.meals.view.MealsView;
import com.example.easymeal.model.pojo.MealsResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.network.NetworkCallBack;

public class MealsPresenterImpl implements MealsPresenter, NetworkCallBack.MealsByAreaCallBack, NetworkCallBack.MealsByCategoryCallBack {
    private final MealsView view;
    private final MealsRepository repository;

    public MealsPresenterImpl(MealsView view, MealsRepository repository) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void getMealsByCategory(String categoryName) {
        repository.getMealsByCategory(this, categoryName);
    }

    @Override
    public void getMealsByArea(String areaName) {
        repository.getMealsByArea(this,areaName);
    }

    @Override
    public void onSuccessMealsByCategory(MealsResponse mealsResponse) {
        view.showMealsByCategory(mealsResponse);
    }

    @Override
    public void onFailMealsByCategory(String errorMessage) {
        view.showMealsByAreaErrorMessage(errorMessage);
    }

    @Override
    public void onSuccessMealsByArea(MealsResponse mealsResponse) {
        view.showMealsByArea(mealsResponse);
    }

    @Override
    public void onFailMealsByArea(String errorMessage) {
        view.showMealsByAreaErrorMessage(errorMessage);
    }
}
