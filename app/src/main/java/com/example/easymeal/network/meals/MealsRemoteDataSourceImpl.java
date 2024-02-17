package com.example.easymeal.network.meals;

import static com.example.easymeal.util.Constants.BASE_URL;

import android.content.Context;
import android.util.Log;

import com.example.easymeal.model.pojo.AreaListResponse;
import com.example.easymeal.model.pojo.CategoryResponse;
import com.example.easymeal.model.pojo.IngredientsResponse;
import com.example.easymeal.model.pojo.MealDetailsResponse;
import com.example.easymeal.model.pojo.MealsResponse;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
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
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
        Observable<CategoryResponse> observable=service.getAllCategories();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull CategoryResponse categoryResponse) {
                        categoriesCallBack.onSuccessResult(categoryResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        categoriesCallBack.onFailure(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*Call<CategoryResponse> mealCategoryCall = service.getAllCategories();
        mealCategoryCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                categoriesCallBack.onSuccessResult(response.body());
                Log.i(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                categoriesCallBack.onFailure(t.getMessage());
            }
        });*/
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

    @Override
    public void getRandomMeal(NetworkCallBack.RandomMealCallBack randomMealCallBack) {
        Call<MealDetailsResponse> randomMeal = service.getRandomMeal();
        randomMeal.enqueue(new Callback<MealDetailsResponse>() {
            @Override
            public void onResponse(Call<MealDetailsResponse> call, Response<MealDetailsResponse> response) {
                randomMealCallBack.onSuccessRandomMeal(response.body());
            }

            @Override
            public void onFailure(Call<MealDetailsResponse> call, Throwable t) {
                randomMealCallBack.onFailRandomMeal(t.getMessage());
            }
        });
    }

    @Override
    public void getMealsByCategory(NetworkCallBack.MealsByCategoryCallBack mealsByCategoryCallBack,
                                   String categoryName) {
        Call<MealsResponse> mealByCategory = service.getMealsByCategory(categoryName);
        mealByCategory.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                mealsByCategoryCallBack.onSuccessMealsByCategory(response.body());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                mealsByCategoryCallBack.onFailMealsByCategory(t.getMessage());

            }
        });
    }

    @Override
    public void getMealsByArea(NetworkCallBack.MealsByAreaCallBack mealsByAreaCallBack,
                               String areaName) {
        Call<MealsResponse> mealsResponseCall = service.getMealsByArea(areaName);
        mealsResponseCall.enqueue(new Callback<MealsResponse>() {
            @Override
            public void onResponse(Call<MealsResponse> call, Response<MealsResponse> response) {
                mealsByAreaCallBack.onSuccessMealsByArea(response.body());
            }

            @Override
            public void onFailure(Call<MealsResponse> call, Throwable t) {
                mealsByAreaCallBack.onFailMealsByArea(t.getMessage());
            }
        });

    }

    @Override
    public void getIngredients(NetworkCallBack.IngredientsCallBack ingredientsCallBack) {
        Observable<IngredientsResponse> observable=service.getIngredients();

                observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IngredientsResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull IngredientsResponse ingredientsResponse) {
                        ingredientsCallBack.onSuccessIngredients(ingredientsResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ingredientsCallBack.onFailIngredients(e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        /*Call<IngredientsResponse>responseCall= service.getIngredients();
        responseCall.enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                ingredientsCallBack.onSuccessIngredients(response.body());
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                ingredientsCallBack.onFailIngredients(t.getMessage());
            }
        });*/
    }
}
