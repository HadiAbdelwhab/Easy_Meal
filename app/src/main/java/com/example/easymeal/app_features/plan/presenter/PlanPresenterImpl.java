package com.example.easymeal.app_features.plan.presenter;

import android.util.Log;

import com.example.easymeal.app_features.plan.view.PlanView;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImpl implements PlanPresenter {
    private static final String TAG = "PlanPresenterImpl";
    private MealsRepository repository;
    private PlanView view;

    public PlanPresenterImpl(PlanView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getPlanMeals() {
        repository.getPlanMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> view.showPlanMeals(items),
                        throwable -> view.showPlanMealsErrorMessage(throwable.getMessage()));
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
}
