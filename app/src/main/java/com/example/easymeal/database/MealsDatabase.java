package com.example.easymeal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.easymeal.model.pojo.Meal;
import com.example.easymeal.model.pojo.MealDetailsResponse;

@Database(entities = {MealDetailsResponse.MealDetails.class},version = 1)
public abstract class MealsDatabase extends RoomDatabase {
    private static MealsDatabase instance = null;
    public abstract MealDao getMealDAO();
    public static synchronized MealsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MealsDatabase.class, "mealDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
