package com.example.easymeal.app_features.favourite.presenter;

import android.util.Log;

import com.example.easymeal.app_features.favourite.view.FavouriteView;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.repository.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenterImpl implements FavouritePresenter {

    private static final String TAG = "FavouritePresenterImpl";
    private FavouriteView view;
    private MealsRepository repository;

    public FavouritePresenterImpl(FavouriteView view, MealsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getFavouriteMeals() {
        repository.getFavouriteMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> view.showFavouriteMeals(items),
                        throwable -> view.showFavouriteMealsErrorMessage(throwable.getMessage()));
    }

    @Override
    public void deleteFavouriteMeal(MealDetailsResponse.MealDetails mealDetails) {
        repository.deleteMeal(mealDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: deleted meal");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
