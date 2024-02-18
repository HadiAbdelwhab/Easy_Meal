package com.example.easymeal.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.easymeal.model.pojo.MealDetailsResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meals Where databaseKey = 'favourite'")
    Flowable<List<MealDetailsResponse.MealDetails>> getFavouriteMeals();
    @Query("SELECT * FROM meals WHERE planDate = :date")
    Flowable<List<MealDetailsResponse.MealDetails>> getPlanMeals(String date);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(MealDetailsResponse.MealDetails mealDetails);
    @Delete
    Completable delete(MealDetailsResponse.MealDetails mealDetails);
    @Query("DELETE FROM meals")
    Completable truncateMeals();

}
