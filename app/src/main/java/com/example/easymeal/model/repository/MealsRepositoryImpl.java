package com.example.easymeal.model.repository;

import com.example.easymeal.database.MealsLocalDataSource;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.network.meals.MealsRemoteDataSource;
import com.example.easymeal.network.meals.MealsRemoteDataSourceImpl;
import com.example.easymeal.network.meals.NetworkCallBack;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealsRepositoryImpl implements MealsRepository {

    private static MealsRepositoryImpl repository = null;
    private final MealsRemoteDataSource remoteDataSource;
    private final MealsLocalDataSource localDataSource;
    private MealsRepositoryImpl(MealsRemoteDataSource remoteDataSource,MealsLocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource=localDataSource;
    }

    public static MealsRepositoryImpl getInstance(MealsRemoteDataSourceImpl remoteDataSource,
                                                  MealsLocalDataSource localDataSource) {
        if (repository == null)
            repository = new MealsRepositoryImpl(remoteDataSource,localDataSource);
        return repository;
    }



    @Override
    public void getAllCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack) {

        remoteDataSource.getCategories(categoriesCallBack);
    }

    @Override
    public void getMealDetailsById(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack, String mealId) {
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

    @Override
    public void getIngredients(NetworkCallBack.IngredientsCallBack ingredientsCallBack) {
        remoteDataSource.getIngredients(ingredientsCallBack);
    }

    @Override
    public void searchMealByName(NetworkCallBack.SearchMealCallBack searchMealCallBack,String mealName) {
        remoteDataSource.searchMealByName(searchMealCallBack,mealName);
    }

    @Override
    public Completable insertMeal(MealDetailsResponse.MealDetails mealDetails) {
        return localDataSource.insertMeal(mealDetails);
    }

    @Override
    public Completable deleteMeal(MealDetailsResponse.MealDetails mealDetails) {
        return localDataSource.deleteMeal(mealDetails);
    }

    @Override
    public Flowable<List<MealDetailsResponse.MealDetails>> getFavouriteMeals() {
        return localDataSource.getFavouriteMeals();
    }

    @Override
    public Flowable<List<MealDetailsResponse.MealDetails>> getPlanMeals() {
        return localDataSource.getPlanMeals();
    }

    @Override
    public Completable truncateMeals() {
        return localDataSource.truncateMeals();
    }
}
