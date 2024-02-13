package com.example.easymeal.network;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("categories.php")
    public Call<CategoryResponse> getAllCategories();

    @GET("lookup.php")
    Call<MealDetailsResponse> getMealDetailsById(@Query("i") String mealId);

    @GET("list.php?a=list")
    Call<AreaListResponse> getAllAreas();

    @GET("random.php")
    Call<MealDetailsResponse> getRandomMeal();

    @GET("filter.php")
    Call<MealsResponse> getMealsByCategory(@Query("c") String categoryName);

    @GET("filter.php")
    Call<MealsResponse> getMealsByArea(@Query("a") String areaName);
}
