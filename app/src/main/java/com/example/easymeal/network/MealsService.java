package com.example.easymeal.network;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsService {

    @GET("api/json/v1/1/categories.php")
    Observable<CategoryResponse> getAllCategories();

    @GET("api/json/v1/1/lookup.php")
    Observable<MealDetailsResponse> getMealDetailsById(@Query("i") String mealId);

    @GET("api/json/v1/1/list.php?a=list")
    Observable<AreaListResponse> getAllAreas();

    @GET("api/json/v1/1/random.php")
    Observable<MealDetailsResponse> getRandomMeal();

    @GET("api/json/v1/1/filter.php")
    Observable<MealsResponse> getMealsByCategory(@Query("c") String categoryName);

    @GET("api/json/v1/1/filter.php")
    Observable<MealsResponse> getMealsByArea(@Query("a") String areaName);

    @GET("api/json/v1/1/list.php?i=list")
    Observable<IngredientsResponse> getIngredients();
    @GET("api/json/v1/1/search.php")
    Observable<MealDetailsResponse> searchMealByName(@Query("s") String mealName);
    @GET("api/json/v1/1/filter.php")
    Observable<MealsResponse> getMealsByIngredient(@Query("i") String ingredientName);
}
