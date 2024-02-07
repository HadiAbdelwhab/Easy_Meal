package com.example.easymeal.meal_details.presenter;

import com.example.easymeal.meal_details.view.MealDetailsView;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.NetworkCallBack;

public class MealDetailsPresenterImpl implements MealDetailsPresenter, NetworkCallBack.MealDetailsCallBack {
    private MealDetailsView view;
    private MealsRepository repository;

    public MealDetailsPresenterImpl(MealDetailsView view,MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getMealDetailsById(String mealId) {
        repository.getMealDetails(this,mealId);
    }

    @Override
    public void onSuccessMealDetails(MealDetailsResponse mealDetailsResponse) {
        view.showMealDetails(mealDetailsResponse);
    }

    @Override
    public void onFailMealDetails(String errorMessage) {
        view.showErrorMessage(errorMessage);
    }
}
