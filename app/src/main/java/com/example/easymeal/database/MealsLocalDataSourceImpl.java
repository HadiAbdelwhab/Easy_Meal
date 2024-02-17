package com.example.easymeal.database;

import android.content.Context;

import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealsLocalDataSourceImpl implements MealsLocalDataSource {
    private MealDao mealDao;
    private MealsDatabase database;
    private Flowable<List<MealDetailsResponse.MealDetails>> favouriteMeals;
    private static MealsLocalDataSourceImpl localDataSource;

    private MealsLocalDataSourceImpl(Context context) {
        database = MealsDatabase.getInstance(context.getApplicationContext());
        mealDao = database.getMealDAO();
        favouriteMeals = mealDao.getFavouriteMeals();
    }

    public static MealsLocalDataSourceImpl getInstance(Context context) {
        if (localDataSource == null)
            localDataSource = new MealsLocalDataSourceImpl(context);
        return localDataSource;
    }


    @Override
    public Flowable<List<MealDetailsResponse.MealDetails>> getFavouriteMeals() {
        return mealDao.getFavouriteMeals();
    }

    @Override
    public Completable insertMeal(MealDetailsResponse.MealDetails mealDetails) {
        return mealDao.insertMeal(mealDetails);
    }

    @Override
    public Completable deleteMeal(MealDetailsResponse.MealDetails mealDetails) {
       return mealDao.delete(mealDetails);
    }
}
