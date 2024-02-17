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
    @Query("SELECT * FROM meals")
    Flowable<List<MealDetailsResponse.MealDetails>> getFavouriteMeals();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(MealDetailsResponse.MealDetails mealDetails);
    @Delete
    Completable delete(MealDetailsResponse.MealDetails mealDetails);

}
