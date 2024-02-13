package com.example.easymeal.model.repository;

import com.example.easymeal.network.MealsRemoteDataSource;
import com.example.easymeal.network.MealsRemoteDataSourceImpl;
import com.example.easymeal.network.NetworkCallBack;

public class MealsRepositoryImpl implements MealsRepository {

    private static MealsRepositoryImpl repository = null;
    private final MealsRemoteDataSource remoteDataSource;

    private MealsRepositoryImpl(MealsRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSourceImpl remoteDataSource) {
        if (repository == null)
            repository = new MealsRepositoryImpl(remoteDataSource);
        return repository;
    }



    @Override
    public void getAllCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack) {

        remoteDataSource.getCategories(categoriesCallBack);
    }

    @Override
    public void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack, String mealId) {
        remoteDataSource.getMealDetails(mealDetailsCallBack, mealId);
    }

    @Override
    public void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack) {
        remoteDataSource.getAllAreas(areasCallBack);
    }

    @Override
    public void getRandomMeal(NetworkCallBack.RandomMealCallBack randomMealCallBack) {
        remoteDataSource.getRandomMeal(randomMealCallBack);
    }

    @Override
    public void getMealsByCategory(NetworkCallBack.MealsByCategoryCallBack mealsByCategoryCallBack,
                                   String categoryName) {
        remoteDataSource.getMealsByCategory(mealsByCategoryCallBack, categoryName);
    }

    @Override
    public void getMealsByArea(NetworkCallBack.MealsByAreaCallBack mealsByAreaCallBack,
                               String areaName) {

        remoteDataSource.getMealsByArea(mealsByAreaCallBack, areaName);
    }
}
