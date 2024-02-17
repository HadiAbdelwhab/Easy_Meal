package com.example.easymeal.network.meals;


import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("categories.php")
    public Observable<CategoryResponse> getAllCategories();

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
    @GET("list.php?i=list")
    Observable<IngredientsResponse> getIngredients();
}
