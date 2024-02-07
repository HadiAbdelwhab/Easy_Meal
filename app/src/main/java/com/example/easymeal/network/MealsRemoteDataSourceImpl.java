package com.example.easymeal.network;

import static com.example.easymeal.util.Constants.BASE_URL;

import android.content.Context;
import android.util.Log;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealsRemoteDataSourceImpl implements MealsRemoteDataSource {

    private static final String TAG = "MealsRemoteDataSourceImpl";
    private static MealsRemoteDataSourceImpl instance;
    private final MealsService service;


    private MealsRemoteDataSourceImpl(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(MealsService.class);
    }

    public static synchronized MealsRemoteDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new MealsRemoteDataSourceImpl(context);
        }
        return instance;
    }


    @Override
    public void getCategories(NetworkCallBack.CategoriesCallBack categoriesCallBack) {
        Call<CategoryResponse> mealCategoryCall = service.getAllCategories();
        mealCategoryCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                categoriesCallBack.onSuccessResult(response.body());
                Log.i(TAG, "onResponse: "+response.body());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                categoriesCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getMealDetails(NetworkCallBack.MealDetailsCallBack mealDetailsCallBack,
                               String mealId) {
        Call<MealDetailsResponse> mealDetailsResponseCall = service.getMealDetailsById(mealId);
        mealDetailsResponseCall.enqueue(new Callback<MealDetailsResponse>() {
            @Override
            public void onResponse(Call<MealDetailsResponse> call, Response<MealDetailsResponse> response) {
                mealDetailsCallBack.onSuccessMealDetails(response.body());
            }

            @Override
            public void onFailure(Call<MealDetailsResponse> call, Throwable t) {
                mealDetailsCallBack.onFailMealDetails(t.getMessage());
            }
        });
    }

    @Override
    public void getAllAreas(NetworkCallBack.AreasCallBack areasCallBack) {
        Call<AreaListResponse> areaListResponseCall = service.getAllAreas();
        areaListResponseCall.enqueue(new Callback<AreaListResponse>() {
            @Override
            public void onResponse(Call<AreaListResponse> call, Response<AreaListResponse> response) {
                areasCallBack.onSuccessAreaCallBack(response.body());
            }

            @Override
            public void onFailure(Call<AreaListResponse> call, Throwable t) {
                areasCallBack.onFailAreaCallBack(t.getMessage());
            }
        });
    }
}
