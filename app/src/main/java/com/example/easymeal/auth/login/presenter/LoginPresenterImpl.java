package com.example.easymeal.auth.login.presenter;

import android.util.Log;

import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginPresenterImpl implements LoginPresenter{
    private static final String TAG = "LoginPresenterImpl";
    private MealsRepository repository;

    public LoginPresenterImpl( MealsRepository repository) {
        this.repository = repository;
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
                        Log.i(TAG, "onComplete: favourite inserted meal to database");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError: could not insert meal");
                    }
                });
    }

    @Override
    public void truncateMeals() {
        repository.truncateMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: meals truncated");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
