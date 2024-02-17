package com.example.easymeal.app_features.meal_details.presenter;


import android.util.Log;

import com.example.easymeal.app_features.meal_details.view.MealDetailsView;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.repository.MealsRepository;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.meals.NetworkCallBack;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;



public class MealDetailsPresenterImpl implements MealDetailsPresenter, NetworkCallBack.MealDetailsCallBack, NetworkCallBack.IngredientsCallBack {
    private static final String TAG = "MealDetailsPresenterImpl";
    private MealDetailsView view;
    private MealsRepository repository;

    public MealDetailsPresenterImpl(MealDetailsView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getMealDetailsById(String mealId) {
        repository.getMealDetailsById(this, mealId);

    }

    @Override
    public void insertMeal(MealDetailsResponse.MealDetails mealDetails) {

        repository.insertMeal(mealDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: inserted meal to database");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: could not insert meal");
                    }
                });
    }

    @Override
    public void getMealIngredients() {
        repository.getIngredients(this);
    }

    @Override
    public void onSuccessMealDetails(MealDetailsResponse mealDetailsResponse) {
        view.showMealDetails(mealDetailsResponse);
    }

    @Override
    public void onFailMealDetails(String errorMessage) {
        view.showErrorMessage(errorMessage);
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
