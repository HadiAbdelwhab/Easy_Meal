package com.example.easymeal.network;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;

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


}
