package com.example.easymeal.app_features.profile.presenter;

import android.util.Log;

import com.example.easymeal.model.repository.MealsRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenterImpl implements ProfilePresenter{
    private static final String TAG = "ProfilePresenterImpl";
    private MealsRepository repository;

    public ProfilePresenterImpl(MealsRepository repository) {
        this.repository = repository;
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
